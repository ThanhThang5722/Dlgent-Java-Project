package com.example.IS216_Dlegent.middleware;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.example.IS216_Dlegent.service.VerifyTokenService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    
    private VerifyTokenService verifyTokenService;

    public AuthInterceptor(){}
    @Autowired
    public AuthInterceptor(VerifyTokenService verifyTokenService) {
        this.verifyTokenService = verifyTokenService;
    }

    private static final Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authToken = null;

        // Retrieve the "auth_token" cookie
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("auth_token".equals(cookie.getName())) {
                    authToken = cookie.getValue();
                    break;
                }
            }
        }

        logger.info("Checking auth token from cookie: {}", authToken);

        if (authToken == null || !isValidToken(authToken)) {
            logger.warn("Unauthorized request - Missing or invalid token");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
            return false;
        }

        return true; // Allow request to continue
    }

    private boolean isValidToken(String authToken) {
        // Simple validation (In real case, check against database or cache)
        return verifyTokenService.isValidToken(authToken); 
    }
}