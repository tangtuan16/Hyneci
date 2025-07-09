package com.example.Aglaea.Services;

import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

public abstract class BaseApiService {

    protected final RestTemplate restTemplate = new RestTemplate();


    protected abstract String getJwtToken();

    protected <T> T callApi(String url,
                            HttpMethod method,
                            Object requestBody,
                            Class<T> responseType,
                            boolean includeToken) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        if (includeToken) {
            String token = getJwtToken();
            if (token != null && !token.isEmpty()) {
                headers.setBearerAuth(token);
            }
        }
        String token = getJwtToken();
        if (token == null || token.isEmpty()) {
            System.out.println("Token is null or empty, cannot call API with auth");
        } else {
            System.out.println("Send token ok !");
        }

        HttpEntity<Object> entity = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<T> response = restTemplate.exchange(url, method, entity, responseType);
            return response.getBody();
        } catch (HttpClientErrorException e) {
            System.out.println("API call failed - Status code: " + e.getStatusCode());
            System.out.println("Response body: " + e.getResponseBodyAsString());
            System.out.println("Request URL: " + url);
            return null;
        }
    }


}


