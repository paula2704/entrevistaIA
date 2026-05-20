package com.empresa.entrevistaIA.controller;

import com.empresa.entrevistaIA.model.PreguntaRespuesta;
import com.empresa.entrevistaIA.model.RespuestaEntrevista;
import com.empresa.entrevistaIA.model.Sesion;
import com.empresa.entrevistaIA.service.GroqService;
import com.empresa.entrevistaIA.service.SesionService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/entrevista")
@CrossOrigin(origins = "*")
public class EntrevistaController {

    private final GroqService groqService;
    private final SesionService sesionService;

    public EntrevistaController(GroqService groqService, SesionService sesionService) {
        this.groqService = groqService;
        this.sesionService = sesionService;
    }

    @GetMapping("/ping")
    public String ping() {
        return "Servidor funcionando";
    }

    @PostMapping("/sesion")
    public Sesion crearSesion(@RequestBody RespuestaEntrevista datos) {
        return sesionService.crearSesion(datos.getRol(), datos.getNivel(), datos.getTemas());
    }

    @PostMapping("/pregunta")
    public String generarPregunta(@RequestBody RespuestaEntrevista datos) {
        return groqService.generarPregunta(datos.getRol(), datos.getNivel(), datos.getTemas());
    }

    @PostMapping("/evaluar")
    public String evaluarRespuesta(@RequestBody RespuestaEntrevista respuesta) {
        String feedback = groqService.evaluarRespuesta(
            respuesta.getRol(),
            respuesta.getNivel(),
            respuesta.getTemas(),
            respuesta.getPregunta(),
            respuesta.getRespuestaUsuario()
        );

        if (respuesta.getSesionId() != null) {
            sesionService.guardarPregunta(
                respuesta.getSesionId(),
                respuesta.getPregunta(),
                respuesta.getRespuestaUsuario(),
                feedback
            );
        }

        return feedback;
    }

    @GetMapping("/historial")
    public List<Sesion> obtenerHistorial() {
        return sesionService.obtenerTodasLasSesiones();
    }

    @GetMapping("/historial/{id}")
    public Sesion obtenerSesion(@PathVariable Long id) {
        return sesionService.obtenerSesion(id);
    }
}