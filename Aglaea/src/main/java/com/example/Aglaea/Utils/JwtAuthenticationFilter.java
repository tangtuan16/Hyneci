package com.example.Aglaea.Utils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider tokenProvider;

    public JwtAuthenticationFilter(JwtTokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // Lấy token từ cookie JWT_TOKEN nếu có
        String tokenFromCookie = null;
        if (request.getCookies() != null) {
            tokenFromCookie = Arrays.stream(request.getCookies())
                    .filter(cookie -> "JWT_TOKEN".equals(cookie.getName()))
                    .findFirst()
                    .map(Cookie::getValue)
                    .orElse(null);
        }

        // Nếu header Authorization chưa có mà có token cookie, bọc request để thêm header Authorization ảo
        if (tokenFromCookie != null && request.getHeader("Authorization") == null) {
            String finalTokenFromCookie = tokenFromCookie;
            HttpServletRequest wrappedRequest = new HttpServletRequestWrapper(request) {
                @Override
                public String getHeader(String name) {
                    if ("Authorization".equalsIgnoreCase(name)) {
                        return "Bearer " + finalTokenFromCookie;
                    }
                    return super.getHeader(name);
                }
            };

            processToken(wrappedRequest, response, filterChain);
            return;
        }

        // Nếu header Authorization đã có hoặc không có token cookie thì xử lý request gốc
        processToken(request, response, filterChain);
    }

    private void processToken(HttpServletRequest request,
                              HttpServletResponse response,
                              FilterChain filterChain) throws IOException, ServletException {
        String header = request.getHeader("Authorization");
        String token = null;

        if (header != null && header.startsWith("Bearer ")) {
            token = header.substring(7);
        }

        try {
            if (token != null && tokenProvider.validateToken(token)) {
                Authentication auth = tokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        } catch (Exception e) {
            System.out.println("Invalid JWT token: " + e.getMessage());
        }

        filterChain.doFilter(request, response);
    }
}
