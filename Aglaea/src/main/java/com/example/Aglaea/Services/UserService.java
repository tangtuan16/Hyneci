package com.example.Aglaea.Services;

import com.example.Aglaea.Models.User;
import com.example.Aglaea.Utils.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;

@Service
public class UserService extends BaseApiService {
    private final String BASE_URL = "http://library-api:8080/api/users";

    public List<User> getUsers() {
        return callApi(BASE_URL, HttpMethod.GET, null, List.class, true);
    }

    public User getUserByUsername(String username) {
        return callApi(BASE_URL + "/username/" + username, HttpMethod.GET, null, User.class, true);
    }

    public User updateUser(User user) {
        String url = BASE_URL + "/" + user.getId();
        return callApi(url, HttpMethod.PUT, user, User.class, true);
    }


    @Override
    protected String getJwtToken() {
        ServletRequestAttributes attrs;
        attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
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
