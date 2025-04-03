package com.example.IS216_Dlegent.middleware;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.servlet.http.Cookie;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.example.IS216_Dlegent.service.VerifyTokenService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class PartnerInterceptor implements HandlerInterceptor {
    private final VerifyTokenService verifyTokenService;
    private static final Logger logger = LoggerFactory.getLogger(PartnerInterceptor.class);

    @Autowired
    public PartnerInterceptor(VerifyTokenService verifyTokenService) {
        this.verifyTokenService = verifyTokenService;
    }

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

        if (authToken == null || !verifyTokenService.isValidToken(authToken)) {
            logger.warn("Unauthorized request - Missing or invalid token");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
            return false;
        }

        if (!verifyTokenService.isPartner(authToken)) {
            logger.warn("Unauthorized request - User does not have Partner role");
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Forbidden - Partner role required");
            return false;
        }

        return true;
    }
}
