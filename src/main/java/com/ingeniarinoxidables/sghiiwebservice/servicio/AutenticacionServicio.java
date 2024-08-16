package com.ingeniarinoxidables.sghiiwebservice.servicio;

import com.ingeniarinoxidables.sghiiwebservice.DTOs.UPATDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

@Service
public class AutenticacionServicio {

    @Autowired
    private RestTemplate restTemplate;

    public UPATDto getAuth(String token) {
        String url = "http://localhost:1535/api/auth/validation";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<UPATDto> response = restTemplate.exchange(url, HttpMethod.GET, entity, UPATDto.class);
        if (response.hasBody()) {
            return response.getBody();
        } else {
            return null;
        }
    }

}


