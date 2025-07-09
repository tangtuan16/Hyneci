package com.example.Aglaea.Controllers.Accounts;

import com.example.Aglaea.DTO.JwtResponse;
import com.example.Aglaea.DTO.LoginRequest;
import com.example.Aglaea.DTO.RegisterRequest;
import com.example.Aglaea.DTO.ResetPasswordRequest;
import com.example.Aglaea.Services.AuthService;
import com.example.Aglaea.Utils.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        if (!model.containsAttribute("loginRequest")) {
            model.addAttribute("loginRequest", new LoginRequest());
        }
        return "auth/login";
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        if (!model.containsAttribute("registerRequest")) {
            model.addAttribute("registerRequest", new RegisterRequest());
        }
        return "auth/register";
    }

    @GetMapping("/forgot-password")
    public String showForgotPasswordForm(Model model) {
        if (!model.containsAttribute("email")) {
            model.addAttribute("email", "");
        }
        return "auth/forgot-password";
    }

    @GetMapping("/reset-password")
    public String showResetForm(@RequestParam String email,
                                @RequestParam String token,
                                Model model) {
        model.addAttribute("email", email);
        model.addAttribute("token", token);
        return "auth/reset-password";
    }

    @PostMapping("/login")
    public String processLogin(@ModelAttribute LoginRequest loginRequest,
                               RedirectAttributes redirectAttributes,
                               HttpServletResponse response) {
        try {
            JwtResponse jwtResponse = authService.login(loginRequest);
            if (jwtResponse == null || jwtResponse.getToken() == null) {
                redirectAttributes.addFlashAttribute("error", "Đăng nhập thất bại");
                return "redirect:/auth/login";
            }
            JwtUtil.addSecureJwtCookie(response, jwtResponse.getToken(), 1000 * 60 * 60 * 10);
            return "redirect:/";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi đăng nhập, vui lòng thử lại.");
            return "redirect:/auth/login";
        }
    }


    @PostMapping("/register")
    public String processRegister(@ModelAttribute RegisterRequest registerRequest,
                                  RedirectAttributes redirectAttributes) {
        String response = authService.register(registerRequest);
        if (response.startsWith("Đăng ký thành công")) {
            redirectAttributes.addFlashAttribute("success", "Đăng kí thành công ! ");
            return "redirect:/auth/login";
        } else {
            redirectAttributes.addFlashAttribute("error", "Đăng kí thất bại. Vui lòng kiểm tra lại.");
            redirectAttributes.addFlashAttribute("registerRequest", registerRequest);
            return "redirect:/auth/register";
        }
    }

    @PostMapping("/forgot-password")
    public String handleForgot(@RequestParam String email,
                               RedirectAttributes redirectAttributes) {
        boolean exists = authService.checkEmailExists(email);
        if (exists) {
            authService.sendResetLink(email);
            redirectAttributes.addFlashAttribute("success", "Thành công! Vui lòng kiểm tra email của bạn.");
        } else {
            redirectAttributes.addFlashAttribute("error", "Email không tồn tại!");
            redirectAttributes.addFlashAttribute("email", email);
        }
        return "redirect:/auth/forgot-password";
    }

    @PostMapping("/reset-password")
    public String handleReset(@ModelAttribute ResetPasswordRequest form,
                              RedirectAttributes redirectAttributes) {
        ResponseEntity<String> response = authService.resetPassword(form);
        if (response.getStatusCode().is2xxSuccessful()) {
            redirectAttributes.addFlashAttribute("success", response.getBody());
        } else {
            redirectAttributes.addFlashAttribute("error", response.getBody());
            redirectAttributes.addFlashAttribute("email", form.getEmail());
            redirectAttributes.addFlashAttribute("token", form.getToken());
        }
        return "redirect:/auth/login";
    }

}
