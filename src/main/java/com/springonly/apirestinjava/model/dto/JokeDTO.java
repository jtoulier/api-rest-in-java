package com.springonly.apirestinjava.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class JokeDTO
{
    private int id;
    private String type;
    private String setup;
    private String punchline;
}
