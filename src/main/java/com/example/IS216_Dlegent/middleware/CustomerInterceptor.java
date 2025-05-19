package com.example.IS216_Dlegent.middleware;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.example.IS216_Dlegent.service.VerifyTokenService;
import com.example.IS216_Dlegent.utils.CookieUtils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Interceptor to check if the user has customer role
 */
@Component
public class CustomerInterceptor implements HandlerInterceptor {
    private final VerifyTokenService verifyTokenService;
    private static final Logger logger = LoggerFactory.getLogger(CustomerInterceptor.class);

    @Autowired
    public CustomerInterceptor(VerifyTokenService verifyTokenService) {
        this.verifyTokenService = verifyTokenService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // Get auth token from cookie
        String authToken = CookieUtils.getCookieValue(request, "auth_token");
        
        logger.info("Checking auth token from cookie customer: {}", authToken);

        // Check if token is valid
        if (authToken == null || !verifyTokenService.isValidToken(authToken)) {
            response.sendRedirect("/customer-signin");
            return false;
        }

        // Check if user has customer role
        String userRole = CookieUtils.getUserRoleFromCookie(request);
        if (!"CUSTOMER".equals(userRole)) {
            logger.warn("Forbidden request - User does not have Customer role");
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Forbidden - Customer role required");
            return false;
        }

        return true;
    }
}
