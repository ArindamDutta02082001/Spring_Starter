package com.demo.oauth2.security.successHanders;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        // You can add custom behavior here, for example:
        // Logging, redirecting, or adding additional headers.
        // Example: Redirect to a dashboard after login
        response.sendRedirect("/test");
    }

}
