package com.example.IS216_Dlegent.config;
import org.springframework.aop.interceptor.AbstractTraceInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.IS216_Dlegent.middleware.AuthInterceptor;
import com.example.IS216_Dlegent.middleware.PartnerInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final AuthInterceptor authInterceptor;
    private final PartnerInterceptor partnerInterceptor;

    @Autowired
    public WebConfig(AuthInterceptor authInterceptor, PartnerInterceptor partnerInterceptor) {
        this.authInterceptor = authInterceptor;
        this.partnerInterceptor = partnerInterceptor;
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        /*registry.addInterceptor(authInterceptor)
                .addPathPatterns("/role/**") // Apply to all routes
                .excludePathPatterns("/signin", "/signup", "/api/signin", "/partner/**"); // Skip auth for login/register
        
        registry.addInterceptor(partnerInterceptor)
                .addPathPatterns("/partner/**");*/
    }
}

