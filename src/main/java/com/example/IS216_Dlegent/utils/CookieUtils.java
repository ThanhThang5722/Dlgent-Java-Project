package com.example.IS216_Dlegent.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseCookie;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import java.time.Duration;

/**
 * Utility class for handling cookies
 */
public class CookieUtils {
    private static final Logger logger = LoggerFactory.getLogger(CookieUtils.class);

    /**
     * Get a cookie value by name
     * 
     * @param request The HTTP request
     * @param name    The cookie name
     * @return The cookie value or null if not found
     */
    public static String getCookieValue(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (name.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    /**
     * Create a secure HTTP-only cookie
     * 
     * @param name   The cookie name
     * @param value  The cookie value
     * @param maxAge The cookie max age in minutes
     * @return A ResponseCookie object
     */
    public static ResponseCookie createSecureCookie(String name, String value, int maxAge) {
        return ResponseCookie.from(name, value)
                .httpOnly(false)
                .secure(false)
                .path("/")
                .maxAge(Duration.ofMinutes(maxAge))
                .build();
    }

    /**
     * Extract user ID from auth token cookie
     * 
     * @param request The HTTP request
     * @return The user ID or null if not found
     */
    public static Long getUserIdFromCookie(HttpServletRequest request) {
        try {
            String userId = getCookieValue(request, "user_id");
            if (userId != null && !userId.isEmpty()) {
                return Long.parseLong(userId);
            }
        } catch (NumberFormatException e) {
            logger.error("Error parsing user ID from cookie", e);
        }
        return null;
    }

    /**
     * Get user role from cookie
     * 
     * @param request The HTTP request
     * @return The user role or null if not found
     */
    public static String getUserRoleFromCookie(HttpServletRequest request) {
        return getCookieValue(request, "user_role");
    }

    /**
     * Check if user has a specific role
     * 
     * @param request The HTTP request
     * @param role    The role to check
     * @return true if user has the role, false otherwise
     */
    public static boolean hasRole(HttpServletRequest request, String role) {
        String userRole = getUserRoleFromCookie(request);
        return role.equals(userRole);
    }

    /**
     * Create a cookie to expire/delete it
     * 
     * @param name The cookie name
     * @return A ResponseCookie object set to expire
     */
    public static ResponseCookie createExpiredCookie(String name) {
        return ResponseCookie.from(name, "")
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(0)
                .build();
    }
}
