package com.example.IS216_Dlegent.middleware;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.example.IS216_Dlegent.service.VerifyTokenService;
import com.example.IS216_Dlegent.utils.CookieUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    private VerifyTokenService verifyTokenService;

    public AuthInterceptor() {
    }

    @Autowired
    public AuthInterceptor(VerifyTokenService verifyTokenService) {
        this.verifyTokenService = verifyTokenService;
    }

    private static final Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        String requestPath = request.getRequestURI();
        logger.info("AuthInterceptor processing request: {}", requestPath);

        // Skip authentication for specific public paths
        if (requestPath.startsWith("/api/zalopay/") || requestPath.startsWith("/api/payment/")) {
            logger.info("Skipping auth for public path: {}", requestPath);
            return true;
        }

        // Get auth token from cookie
        String authToken = CookieUtils.getCookieValue(request, "auth_token");

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