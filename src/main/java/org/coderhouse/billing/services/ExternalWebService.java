package org.coderhouse.billing.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Service
public class ExternalWebService {
    @Autowired
    RestTemplate restTemplate;

    public LocalDateTime getCurrentDate() {
        // URL del servicio web externo que proporciona la fecha actual
        final String url = "http://worldtimeapi.org/api/ip";

        // Realizar la solicitud HTTP GET y obtener la respuesta como un objeto Map
        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<Map<String, Object>>() {});

        // Extraer el valor del campo "datetime" del mapa
        String dateString = (String) response.getBody().get("datetime");

        // Parsear la cadena de fecha en un objeto LocalDateTime
        LocalDateTime currentDate = LocalDateTime.parse(dateString, DateTimeFormatter.ISO_OFFSET_DATE_TIME);

        return currentDate;
    }
}
