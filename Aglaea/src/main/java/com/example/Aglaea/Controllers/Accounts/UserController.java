package com.example.Aglaea.Controllers.Accounts;

import com.example.Aglaea.Models.User;
import com.example.Aglaea.Services.UserService;
import com.example.Aglaea.Utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getUsers(Model model, HttpServletRequest request) {
        String token = JwtUtil.extractTokenFromCookies(request);
        System.out.println("token: " + token);

        if (token == null) {
            return "redirect:/auth/login";
        }

        String username = JwtUtil.getUsernameFromToken(token);

        if (username == null) {
            return "redirect:/auth/login";
        }

        User user = userService.getUserByUsername(username);
        model.addAttribute("user", user);
        return "account/user";
    }


    @PostMapping("/update")
    public String updateUser(@ModelAttribute User user,
                             @RequestParam("avatarFile") MultipartFile avatarFile,
                             RedirectAttributes redirectAttributes) {
        try {
            User existingUser = userService.getUserByUsername(user.getUsername());
            if (existingUser == null) {
                redirectAttributes.addFlashAttribute("error", "Người dùng không tồn tại.");
                return "redirect:/users";
            }

            if (!avatarFile.isEmpty()) {
                Path projectRoot = Paths.get("").toAbsolutePath();
                Path uploadPath = projectRoot.resolve("uploads");

                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                String originalFilename = avatarFile.getOriginalFilename();
                String fileName = UUID.randomUUID() + "_" + originalFilename;

                avatarFile.transferTo(uploadPath.resolve(fileName).toFile());

                String oldAvatar = existingUser.getAvatar();
                if (oldAvatar != null && !oldAvatar.isEmpty()) {
                    File oldFile = uploadPath.resolve(oldAvatar).toFile();
                    if (oldFile.exists()) {
                        oldFile.delete();
                    }
                }

                user.setAvatar(fileName);
            } else {
                user.setAvatar(existingUser.getAvatar());
            }

            user.setPassword(existingUser.getPassword());
            user.setRole(existingUser.getRole());

            userService.updateUser(user);
            redirectAttributes.addFlashAttribute("success", "Cập nhật thành công!");
        } catch (IOException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Lỗi khi tải ảnh lên.");
        }
        return "redirect:/users";
    }


}
