package com.example.Aglaea.Controllers.Exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/error")
public class ErrorsController implements ErrorController {

    @GetMapping
    public String handleError(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("jakarta.servlet.error.status_code");
        String uri = (String) request.getAttribute("jakarta.servlet.error.request_uri");

        System.out.println("URI ERROR: " + uri);
        System.out.println("StatusCode: " + statusCode);

        if (statusCode != null) {
            if (statusCode == 403) {
                return "exception/403";
            } else if (statusCode == 404) {
                return "exception/404";
            }
        }

        return "redirect:/";
    }
}
