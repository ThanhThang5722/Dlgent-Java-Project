package com.example.IS216_Dlegent.middleware;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
public class AdminInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(AdminInterceptor.class);

    // Mật khẩu admin được cấu hình trong application.properties
    @Value("${admin.password:admin123}")
    private String adminPassword;

    @Value("${admin.username:admin}")
    private String adminUsername;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // Kiểm tra nếu là tài nguyên tĩnh (CSS, JS, images)
        String requestPath = request.getRequestURI();
        if (requestPath.contains("/css/") || requestPath.contains("/js/") ||
            requestPath.contains("/images/") || requestPath.contains("/static/")) {
            logger.debug("Allowing static resource: {}", requestPath);
            return true;
        }

        // Lấy header Authorization
        String authHeader = request.getHeader("Authorization");
        logger.info("Checking admin authentication: {}", authHeader);

        if (authHeader != null && authHeader.startsWith("Basic ")) {
            // Giải mã thông tin xác thực
            String base64Credentials = authHeader.substring("Basic ".length());
            byte[] credentialsBytes = Base64.getDecoder().decode(base64Credentials);
            String credentials = new String(credentialsBytes, StandardCharsets.UTF_8);

            // Tách username và password
            final String[] values = credentials.split(":", 2);
            String username = values[0];
            String password = values[1];

            // Kiểm tra username và password
            if (adminUsername.equals(username) && adminPassword.equals(password)) {
                logger.info("Admin authentication successful");
                return true;
            }
        }

        // Nếu không có header hoặc thông tin xác thực không đúng, yêu cầu xác thực
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setHeader("WWW-Authenticate", "Basic realm=\"Admin Area\"");
        return false;
    }
}