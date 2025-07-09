package com.example.Aglaea.Services;

import com.example.Aglaea.DTO.JwtResponse;
import com.example.Aglaea.DTO.LoginRequest;
import com.example.Aglaea.DTO.RegisterRequest;
import com.example.Aglaea.DTO.ResetPasswordRequest;
import com.example.Aglaea.Models.User;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService extends BaseApiService {

    @Value("${api.base-url}")
    private String API_BASE_URL;

    private final RestTemplate restTemplate;

    @Autowired
    private HttpSession session;

    @Autowired
    public AuthService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public JwtResponse login(LoginRequest request) {
        try {
            return callApi(
                    API_BASE_URL + (API_BASE_URL.endsWith("/") ? "" : "/") + "auth/login",
                    HttpMethod.POST,
                    request,
                    JwtResponse.class,
                    false
            );
        } catch (HttpClientErrorException e) {
            System.out.println("Đăng nhập thất bại: " + e.getResponseBodyAsString());
            return null;
        }
    }

    public User getUserByUsername(String username){
        return callApi(API_BASE_URL + "/users/username/" + username, HttpMethod.GET, null, User.class, true);
    }

    public String register(RegisterRequest request) {
        try {
            return callApi(
                    API_BASE_URL + (API_BASE_URL.endsWith("/") ? "" : "/") + "auth/register",
                    HttpMethod.POST,
                    request,
                    String.class,
                    false
            );
        } catch (HttpClientErrorException e) {
            return "Đăng ký thất bại: " + e.getResponseBodyAsString();
        }
    }

    public boolean checkEmailExists(String email) {
        try {
            return Boolean.TRUE.equals(
                    callApi(
                            API_BASE_URL + (API_BASE_URL.endsWith("/") ? "" : "/") + "auth/check-email?email=" + email,
                            HttpMethod.GET,
                            null,
                            Boolean.class,
                            false
                    )
            );
        } catch (Exception e) {
            System.out.println("Lỗi khi kiểm tra email: " + e.getMessage());
            return false;
        }
    }

    public String sendResetLink(String email) {
        Map<String, String> body = new HashMap<>();
        body.put("email", email);

        try {
            return callApi(
                    API_BASE_URL + (API_BASE_URL.endsWith("/") ? "" : "/") + "auth/forgot-password",
                    HttpMethod.POST,
                    body,
                    String.class,
                    false
            );
        } catch (Exception e) {
            return "Không thể gửi yêu cầu. Lỗi: " + e.getMessage();
        }
    }

    public ResponseEntity<String> resetPassword(ResetPasswordRequest form) {
        Map<String, String> body = new HashMap<>();
        body.put("email", form.getEmail());
        body.put("token", form.getToken());
        body.put("newPassword", form.getNewPassword());

        try {
            String response = callApi(
                    API_BASE_URL + (API_BASE_URL.endsWith("/") ? "" : "/") + "auth/reset-password",
                    HttpMethod.POST,
                    body,
                    String.class,
                    false
            );
            return ResponseEntity.ok(response);
        } catch (HttpStatusCodeException e) {
            String errorMessage = e.getResponseBodyAsString();
            return ResponseEntity.badRequest().body(errorMessage);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Đặt lại mật khẩu thất bại. Lỗi: " + e.getMessage());
        }
    }


    @Override
    protected String getJwtToken() {
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attrs == null) return null;

        HttpServletRequest request = attrs.getRequest();
        if (request.getCookies() == null) return null;

        for (Cookie cookie : request.getCookies()) {
            if ("JWT_TOKEN".equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }

}
