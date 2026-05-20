package com.empresa.entrevistaIA.model;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RespuestaEntrevista {
    private String rol;
    private String nivel;
    private List<String> temas;
    private String pregunta;
    private String respuestaUsuario;
    private Long sesionId;
}