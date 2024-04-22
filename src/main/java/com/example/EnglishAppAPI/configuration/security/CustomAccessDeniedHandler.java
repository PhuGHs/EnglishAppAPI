package com.example.EnglishAppAPI.configuration.security;

import com.example.EnglishAppAPI.entities.Role;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>) authentication.getAuthorities();
        if (authorities.stream().anyMatch(auth -> auth.getAuthority().equals(Role.ADMIN))) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Admin access denied");
        } else if (authorities.stream().anyMatch(auth -> auth.getAuthority().equals(Role.LEARNER))) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Learner access denied");
        } else {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied");
        }
    }
}
