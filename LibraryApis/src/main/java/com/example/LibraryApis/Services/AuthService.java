package com.example.LibraryApis.Services;

import com.example.LibraryApis.DTO.LoginRequest;
import com.example.LibraryApis.DTO.RegisterRequest;
import com.example.LibraryApis.DTO.ResetPasswordRequest;
import com.example.LibraryApis.Models.Token;
import com.example.LibraryApis.Models.User;
import com.example.LibraryApis.Repository.TokenRepository;
import com.example.LibraryApis.Repository.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService {

    private final EmailService emailService;

    private final UserRepository userRepository;

    private final TokenRepository tokenRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JWTService jwtService;

    public AuthService(EmailService emailService, UserRepository userRepository, TokenRepository tokenRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JWTService jwtService) {
        this.emailService = emailService;
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public ResponseEntity<String> register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            return ResponseEntity.badRequest().body("Tài khoản đã tồn tại!");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            return ResponseEntity.badRequest().body("Email đã tồn tại !");
        }

        String hashedPassword = passwordEncoder.encode(request.getPassword());
        User newUser = new User(request.getUsername(), hashedPassword, request.getEmail());
        userRepository.save(newUser);

        return ResponseEntity.ok("Đăng ký thành công!");
    }

    public boolean login(LoginRequest request) {
        return userRepository.findByUsername(request.getUsername())
                .map(user -> passwordEncoder.matches(request.getPassword(), user.getPassword()))
                .orElse(false);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Transactional
    public ResponseEntity<String> processForgotPassword(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            return ResponseEntity.badRequest().body("Email không tồn tại!");
        }

        tokenRepository.deleteByEmail(email);

        String tokenString = UUID.randomUUID().toString();
        Token token = new Token();

        token.setEmail(email);
        token.setToken(tokenString);
        token.setExpireAt(LocalDateTime.now().plusMinutes(30));

        tokenRepository.save(token);

        String encodedEmail = URLEncoder.encode(email, StandardCharsets.UTF_8);
        String encodedToken = URLEncoder.encode(tokenString, StandardCharsets.UTF_8);

        String resetLink = "http://localhost/auth/reset-password?token=" + encodedToken + "&email=" + encodedEmail;
        String htmlMsg = """
                    <div style="font-family: Arial, sans-serif; max-width: 600px; margin: auto; padding: 20px; border: 1px solid #eee; border-radius: 10px; background-color: #fafafa;">
                        <h2 style="color: #2c3e50; text-align: center;">Đặt lại mật khẩu <span style="color: #4CAF50;">Aglaea</span></h2>
                        <p>Xin chào,</p>
                        <p>Bạn vừa yêu cầu đặt lại mật khẩu cho tài khoản của mình. Vui lòng nhấn vào nút bên dưới để tiến hành:</p>
                        <div style="text-align: center; margin: 30px 0;">
                            <a href="%s" style="background-color: #4CAF50; color: white; padding: 12px 24px; text-decoration: none; border-radius: 6px; font-weight: bold;">
                                Đặt lại mật khẩu
                            </a>
                        </div>
                        <p>Nếu bạn không yêu cầu hành động này, vui lòng bỏ qua email này.</p>
                        <p style="margin-top: 40px;">Trân trọng,<br><strong>Đội ngũ Aglaea</strong></p>
                        <hr style="margin-top: 40px;">
                        <p style="font-size: 12px; color: #999999; text-align: center;">Email này được gửi tự động, vui lòng không trả lời.</p>
                    </div>
                """.formatted(resetLink);

        try {
            emailService.sendEmail(email, "Đặt lại mật khẩu", htmlMsg);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok("Đã gửi email đặt lại mật khẩu.");
    }


    @Transactional
    public ResponseEntity<String> resetPassword(ResetPasswordRequest request) {
        User user = userRepository.findByEmail(request.getEmail());
        if (user == null) {
            return ResponseEntity.badRequest().body("Email không hợp lệ!");
        }

        Optional<Token> tokenOpt = tokenRepository.findByToken(request.getToken());
        if (tokenOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Token không hợp lệ!");
        }

        Token token = tokenOpt.get();

        if (!token.getEmail().equals(request.getEmail())) {
            return ResponseEntity.badRequest().body("Token không khớp với email!");
        }

        if (token.getExpireAt().isBefore(LocalDateTime.now())) {
            return ResponseEntity.badRequest().body("Token đã hết hạn!");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));

        userRepository.save(user);

        tokenRepository.delete(token);

        return ResponseEntity.ok("Đặt lại mật khẩu thành công!");
    }

    public String loginAndGenerateToken(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String role = userDetails.getAuthorities().stream()
                .findFirst()
                .map(grantedAuthority -> grantedAuthority.getAuthority())
                .orElse("");

        return jwtService.generateToken(userDetails.getUsername(), role);
    }
}
