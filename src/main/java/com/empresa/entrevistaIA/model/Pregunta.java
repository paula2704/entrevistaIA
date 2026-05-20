package com.empresa.entrevistaIA.model;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pregunta {
    private String rol;
    private String nivel;
    private String pregunta;
}