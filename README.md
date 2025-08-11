# Entorno DEV

## Probar en vivo
```bash
mvn quarkus:dev
```

## Compilar y ejecutar
```bash
clear
mvn clean package -Dquarkus.profile=dev
java -jar ./target/quarkus-app/quarkus-run.jar 
```

# Entorno PROD

## Compilar y ejecutar
```bash
clear
mvn clean package
java -jar ./target/quarkus-app/quarkus-run.jar
```

# Open API
http://localhost:8080/q/openapi

# Creación

## Crear variables de entorno
```bash
# Valores por defecto
export SUBS=subs1
export RG=rgcantolao
export LOCATION=centralus

export SQL_SERVER_NAME=sqlsrvcantolao
export SQL_SERVER_DB_NAME=dbcantolao
export SQL_SERVER_LOGIN_NAME=sajtoulier
export SQL_SERVER_LOGIN_PASSWORD='P@ssw0rd$1234'

export ACR_NAME=acrcantolao

export DOCKER_IMAGE_NAME=quarkus/api-rest-in-java
export DOCKER_IMAGE_TAG=v1
export DOCKER_IMAGE_LOCAL_NAME=$DOCKER_IMAGE_NAME:$DOCKER_IMAGE_TAG
export DOCKER_IMAGE_CLOUD_NAME=$ACR_NAME.azurecr.io/$DOCKER_IMAGE_NAME:$DOCKER_IMAGE_TAG

export LOG_ANALYTICS_WORKSPACE_NAME=lawscantolao

export CONTAINER_APP_ENV_NAME=cappenvcantolao
export CONTAINER_APP_NAME=cappcantolao
```

## Crear imágenes Docker
```bash
docker build -f src/main/docker/Dockerfile -t $DOCKER_IMAGE_LOCAL_NAME .
docker tag $DOCKER_IMAGE_LOCAL_NAME $DOCKER_IMAGE_CLOUD_NAME
````

## Crear recursos Azure
```bash
# Logearse a Azure
az login

# Configurar suscripción por defecto
az account set \
    --subscription $SUBS

az group create \
    --resource-group $RG \
    --location "$LOCATION"

# Crear servidor y base de datos y agregar IP local
az sql server create \
    --name $SQL_SERVER_NAME \
    --resource-group $RG \
    --location "$LOCATION" \
    --admin-user $SQL_SERVER_LOGIN_NAME \
    --admin-password "$SQL_SERVER_LOGIN_PASSWORD"
  
az sql db create \
    --name $SQL_SERVER_DB_NAME \
    --resource-group $RG \
    --server $SQL_SERVER_NAME \
    --edition GeneralPurpose \
    --compute-model Serverless \
    --family Gen5 \
    --capacity 2 \
    --min-capacity 0.5 \
    --max-size 5GB \
    --zone-redundant false
    
az sql server firewall-rule create \
    --resource-group $RG \
    --server $SQL_SERVER_NAME \
    --name AllowLocalIP \
    --start-ip-address $(curl -s -4 ifconfig.me) \
    --end-ip-address $(curl -s -4 ifconfig.me)
```

## Ejecutar manualmente en la base de datos el siguiente script
src/main/resources/sql/schema.sql

## Crear y configurar Azure Container Registry
```bash
az acr create \
    --resource-group $RG \
    --location $LOCATION \
    --name $ACR_NAME \
    --dnl-scope Unsecure \
    --sku Basic \
    --role-assignment-mode rbac

az acr update \
    --resource-group $RG \
    --name $ACR_NAME \
    --admin-enabled true

# Logearse al ACR y subir imagen Docker
az acr login \
    --resource-group $RG \
    --name $ACR_NAME

docker push $DOCKER_IMAGE_CLOUD_NAME

# Crear Log Analytics Workspace para el Container App Environment
az monitor log-analytics workspace create \
    --resource-group $RG \
    --location $LOCATION \
    --workspace-name $LOG_ANALYTICS_WORKSPACE_NAME

# Crear el Container App Environment 
az containerapp env create \
    --resource-group $RG \
    --location $LOCATION \
    --name $CONTAINER_APP_ENV_NAME \
    --logs-destination log-analytics \
    --logs-workspace-id  $(az monitor log-analytics workspace show            --resource-group $RG --workspace-name $LOG_ANALYTICS_WORKSPACE_NAME --query customerId       --output tsv) \
    --logs-workspace-key $(az monitor log-analytics workspace get-shared-keys --resource-group $RG --workspace-name $LOG_ANALYTICS_WORKSPACE_NAME --query primarySharedKey --output tsv)

# Crear el Container App 
az containerapp create \
    --resource-group $RG \
    --name $CONTAINER_APP_NAME \
    --environment $CONTAINER_APP_ENV_NAME \
    --image $DOCKER_IMAGE_CLOUD_NAME \
    --target-port 8080 \
    --ingress 'external' \
    --registry-server $ACR_NAME.azurecr.io \
    --registry-username $(az acr credential show --resource-group $RG --name $ACR_NAME --query username -o tsv) \
    --registry-password $(az acr credential show --resource-group $RG --name $ACR_NAME --query passwords[0].value -o tsv)
```

## Agregar manualmente la IP del Container App en las reglas de firewall de la BD
Ir al Shell de Azure
```bash
# Dentro del bash del shell
export RG=rgcantolao
export CONTAINER_APP_NAME=cappcantolao
export SQL_SERVER_NAME=sqlsrvcantolao

az containerapp exec \
  --name $CONTAINER_APP_NAME \
  --resource-group $RG \
  --command "/bin/bash"

# Dentro del bash del Container App, ejecutar y tomar nota de la IP retornada
curl -s -4 ifconfig.me
exit


# Nuevamente dentro del bash del shell usar la IP retornada en el paso anterior
export CONTAINER_APP_IP=104.43.204.253

az sql server firewall-rule create \
    --resource-group $RG \
    --server $SQL_SERVER_NAME \
    --name AllowContainerAppIP \
    --start-ip-address $CONTAINER_APP_IP \
    --end-ip-address $CONTAINER_APP_IP
```

# Limpieza

## Eliminar recursos Docker locales
```bash
docker rmi "$DOCKER_IMAGE_CLOUD_NAME"
docker rmi "$DOCKER_IMAGE_LOCAL_NAME"
```

## Eliminar recursos Azure
```bash
# Eliminar Container App
az containerapp delete \
    --resource-group $RG \
    --name $CONTAINER_APP_NAME \
    --yes

# Eliminar Container App Environment (demora varios minutos)
az containerapp env delete \
    --resource-group $RG \
    --name $CONTAINER_APP_ENV_NAME \
    --yes

# Eliminar Log Analytics workspace
az monitor log-analytics workspace delete \
    --resource-group $RG \
    --workspace-name $LOG_ANALYTICS_WORKSPACE_NAME \
    --force yes \
    --yes

# Eliminar Azure Container Registry
az acr delete \
    --resource-group $RG \
    --name $ACR_NAME \
    --yes

# Eliminar Base de Datos
az sql db delete \
    --name $SQL_SERVER_DB_NAME \
    --resource-group $RG \
    --server $SQL_SERVER_NAME \
    --yes

# Eliminar el Servidor de Base de Datos
az sql server delete \
    --name $SQL_SERVER_NAME \
    --resource-group $RG \
    --yes
  
# Eliminar el Resource Group
az group delete \
    --name $RG \
    --yes
```