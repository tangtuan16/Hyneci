package com.example.Aglaea.Utils;

import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Base64;
import org.json.JSONException;
import org.json.JSONObject;

public class JwtUtil {

    public static String getUsernameFromToken(String token) {
        String[] parts = token.split("\\.");
        if (parts.length < 2) return null;

        try {
            String payload = new String(Base64.getUrlDecoder().decode(parts[1]));
            JSONObject json = new JSONObject(payload);
            return json.getString("sub");
        } catch (IllegalArgumentException | JSONException e) {
            return null;
        }
    }

    public static String extractTokenFromCookies(HttpServletRequest request) {
        if (request.getCookies() == null) return null;
        for (Cookie cookie : request.getCookies()) {
            if ("JWT_TOKEN".equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }
    public static String getUsernameFromRequest(HttpServletRequest request) {
        String token = extractTokenFromCookies(request);
        if (token == null) return null;
        return getUsernameFromToken(token);
    }

    public static void addSecureJwtCookie(HttpServletResponse response, String token, int maxAgeSeconds) {
        Cookie cookie = new Cookie("JWT_TOKEN", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(maxAgeSeconds);
        response.addCookie(cookie);

        String sameSiteCookie = String.format(
                "JWT_TOKEN=%s; Max-Age=%d; Path=/; HttpOnly; Secure; SameSite=Strict",
                URLEncoder.encode(token, StandardCharsets.UTF_8),
                maxAgeSeconds
        );

        response.addHeader("Set-Cookie", sameSiteCookie);
    }
}

