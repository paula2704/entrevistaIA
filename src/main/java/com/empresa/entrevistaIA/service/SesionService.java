package com.empresa.entrevistaIA.service;

import com.empresa.entrevistaIA.model.PreguntaRespuesta;
import com.empresa.entrevistaIA.model.Sesion;
import com.empresa.entrevistaIA.repository.PreguntaRespuestaRepository;
import com.empresa.entrevistaIA.repository.SesionRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class SesionService {

    private final SesionRepository sesionRepository;
    private final PreguntaRespuestaRepository preguntaRespuestaRepository;

    public SesionService(SesionRepository sesionRepository,
                         PreguntaRespuestaRepository preguntaRespuestaRepository) {
        this.sesionRepository = sesionRepository;
        this.preguntaRespuestaRepository = preguntaRespuestaRepository;
    }

    public Sesion crearSesion(String rol, String nivel, List<String> temas) {
        Sesion sesion = new Sesion();
        sesion.setRol(rol);
        sesion.setNivel(nivel);
        sesion.setTemas(temas);
        sesion.setFecha(LocalDateTime.now());
        return sesionRepository.save(sesion);
    }

    public PreguntaRespuesta guardarPregunta(Long sesionId, String pregunta,
                                              String respuesta, String feedback) {
        Sesion sesion = sesionRepository.findById(sesionId).orElseThrow();
        PreguntaRespuesta pr = new PreguntaRespuesta();
        pr.setPregunta(pregunta);
        pr.setRespuesta(respuesta);
        pr.setFeedback(feedback);
        pr.setSesion(sesion);
        return preguntaRespuestaRepository.save(pr);
    }

    public List<Sesion> obtenerTodasLasSesiones() {
        return sesionRepository.findAll();
    }

    public Sesion obtenerSesion(Long id) {
        return sesionRepository.findById(id).orElseThrow();
    }
}