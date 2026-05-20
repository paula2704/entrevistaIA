package com.empresa.entrevistaIA.service;

import com.empresa.entrevistaIA.config.GroqConfig;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

import java.util.*;

@Service
public class GroqService {

    private final GroqConfig config;
    private final RestTemplate restTemplate = new RestTemplate();

    public GroqService(GroqConfig config) {
        this.config = config;
    }

    private String llamarGroq(String prompt) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(config.getApiKey());

        Map<String, Object> mensaje = new HashMap<>();
        mensaje.put("role", "user");
        mensaje.put("content", prompt);

        Map<String, Object> body = new HashMap<>();
        body.put("model", config.getModel());
        body.put("messages", List.of(mensaje));

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        Map response = restTemplate.postForObject(
            config.getApiUrl(), request, Map.class
        );

        List<Map> choices = (List<Map>) response.get("choices");
        Map message = (Map) choices.get(0).get("message");
        return message.get("content").toString();
    }

    public String generarPregunta(String rol, String nivel, List<String> temas) {
    String temasStr = String.join(", ", temas);
    String prompt = String.format("""
        Eres un entrevistador técnico senior en una empresa de tecnología de alto nivel.
        Tu tarea es generar UNA sola pregunta técnica para un candidato al rol de %s con nivel %s.
        
        Los temas que el candidato prefiere que le pregunten son: %s
        
        Reglas:
        - La pregunta debe estar relacionada con alguno de los temas indicados
        - Debe evaluar conocimiento práctico, no solo teórico
        - Debe ser específica y situacional
        - El nivel de dificultad debe ser apropiado: Junior (conceptos base), Mid (aplicación práctica), Senior (decisiones de arquitectura)
        - No incluyas la respuesta ni pistas
        - Devuelve SOLO la pregunta, sin numeración ni explicaciones
        """, rol, nivel, temasStr);
    return llamarGroq(prompt);
}

public String evaluarRespuesta(String rol, String nivel, List<String> temas, String pregunta, String respuesta) {
    String temasStr = String.join(", ", temas);
    String prompt = String.format("""
        Eres un entrevistador técnico senior evaluando a un candidato para el rol de %s con nivel %s.
        Los temas evaluados son: %s
        
        Pregunta realizada: %s
        Respuesta del candidato: %s
        
        Evalúa con criterio profesional y usa EXACTAMENTE este formato:
        
        PUNTAJE: X/10
        
        FORTALEZAS:
        - (punto positivo 1)
        - (punto positivo 2)
        
        DEBILIDADES:
        - (punto a mejorar 1)
        - (punto a mejorar 2)
        
        RESPUESTA IDEAL:
        (Explica cómo debería haberse respondido con detalles técnicos precisos)
        
        CONSEJO FINAL:
        (Un consejo concreto y accionable para que el candidato mejore)
        
        Sé honesto pero constructivo. Ajusta tu criterio al nivel %s esperado.
        """, rol, nivel, temasStr, pregunta, respuesta, nivel);
    return llamarGroq(prompt);
}
}