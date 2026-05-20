package com.empresa.entrevistaIA.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Sesion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String rol;
    private String nivel;
    private LocalDateTime fecha;

    @ElementCollection
    private List<String> temas;

    @OneToMany(mappedBy = "sesion", cascade = CascadeType.ALL)
    private List<PreguntaRespuesta> preguntas;
}