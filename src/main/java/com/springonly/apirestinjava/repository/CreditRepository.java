package com.springonly.apirestinjava.repository;

import com.springonly.apirestinjava.entity.CreditEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CreditRepository implements PanacheRepository<CreditEntity> {
}
