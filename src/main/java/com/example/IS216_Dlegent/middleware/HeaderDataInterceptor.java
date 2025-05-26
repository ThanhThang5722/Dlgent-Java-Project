package com.example.IS216_Dlegent.middleware;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.example.IS216_Dlegent.payload.dto.ChiTietDatPhongDTO;
import com.example.IS216_Dlegent.service.ChiTietDatPhongService;
import com.example.IS216_Dlegent.service.VerifyTokenService;
import com.example.IS216_Dlegent.utils.CookieUtils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

/**
 * Interceptor to automatically add header data (authentication status and cart count) 
 * to all pages that use the header component
 */
@Component
public class HeaderDataInterceptor implements HandlerInterceptor {
    
    private static final Logger logger = LoggerFactory.getLogger(HeaderDataInterceptor.class);
    
    @Autowired
    private VerifyTokenService verifyTokenService;
    
    @Autowired
    private ChiTietDatPhongService chiTietDatPhongService;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
        
        // Only process if we have a ModelAndView and it's not a redirect
        if (modelAndView != null && !isRedirectView(modelAndView)) {
            
            try {
                // Check authentication status
                String authToken = CookieUtils.getCookieValue(request, "auth_token");
                boolean isLoggedIn = authToken != null && verifyTokenService.isValidToken(authToken);
                
                logger.debug("HeaderDataInterceptor - isLoggedIn: {}", isLoggedIn);
                
                // Add authentication status to model
                modelAndView.addObject("isLoggedIn", isLoggedIn);
                
                // If logged in, get cart count
                if (isLoggedIn) {
                    Long userId = CookieUtils.getUserIdFromCookie(request);
                    if (userId != null) {
                        List<ChiTietDatPhongDTO> cartItems = chiTietDatPhongService.getChiTietDatPhongByDatPhongId(userId);
                        int cartCount = cartItems != null ? cartItems.size() : 0;
                        modelAndView.addObject("cartCount", cartCount);
                        
                        logger.debug("HeaderDataInterceptor - userId: {}, cartCount: {}", userId, cartCount);
                    } else {
                        modelAndView.addObject("cartCount", 0);
                    }
                } else {
                    modelAndView.addObject("cartCount", 0);
                }
                
            } catch (Exception e) {
                logger.error("Error in HeaderDataInterceptor: {}", e.getMessage(), e);
                // Set default values in case of error
                modelAndView.addObject("isLoggedIn", false);
                modelAndView.addObject("cartCount", 0);
            }
        }
    }
    
    /**
     * Check if the view is a redirect view
     */
    private boolean isRedirectView(ModelAndView modelAndView) {
        String viewName = modelAndView.getViewName();
        return viewName != null && (viewName.startsWith("redirect:") || viewName.startsWith("forward:"));
    }
}
