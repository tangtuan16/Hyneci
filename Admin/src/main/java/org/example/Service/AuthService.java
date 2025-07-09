package org.example.Service;

import java.net.http.*;
import java.net.URI;
import java.net.http.HttpRequest.BodyPublishers;
import java.util.Base64;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.DTO.LoginRequest;

public class AuthService {
    private static final String API_LOGIN_URL = "http://localhost:8080/api/auth/login";

    private final HttpClient client;
    private final ObjectMapper mapper;

    private static AuthService instance;

    private String jwtToken;

    private AuthService() {
        client = HttpClient.newHttpClient();
        mapper = new ObjectMapper();
    }

    public static AuthService getInstance() {
        if (instance == null) {
            synchronized (AuthService.class) {
                if (instance == null) {
                    instance = new AuthService();
                }
            }
        }
        return instance;
    }

    public boolean login(LoginRequest request) throws Exception {
        String jsonReq = mapper.writeValueAsString(request);
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(API_LOGIN_URL))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonReq))
                .build();

        HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            var node = mapper.readTree(response.body());
            if (node.has("token")) {
                String token = node.get("token").asText();
                String[] parts = token.split("\\.");
                if (parts.length == 3) {
                    String payloadJson = new String(Base64.getUrlDecoder().decode(parts[1]));
                    JsonNode payload = mapper.readTree(payloadJson);
                    String role = payload.get("role").asText();
                    System.out.println("Role: " + role);
                    if ("ROLE_ADMIN".equals(role)) {
                        jwtToken = token;
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public String getJwtToken() {
        return jwtToken;
    }
}
