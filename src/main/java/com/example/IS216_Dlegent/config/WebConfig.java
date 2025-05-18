package com.example.IS216_Dlegent.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.IS216_Dlegent.middleware.AuthInterceptor;
import com.example.IS216_Dlegent.middleware.CustomerInterceptor;
import com.example.IS216_Dlegent.middleware.PartnerInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {
        private final AuthInterceptor authInterceptor;
        private final PartnerInterceptor partnerInterceptor;
        private final CustomerInterceptor customerInterceptor;

        @Autowired
        public WebConfig(AuthInterceptor authInterceptor, PartnerInterceptor partnerInterceptor,
                        CustomerInterceptor customerInterceptor) {
                this.authInterceptor = authInterceptor;
                this.partnerInterceptor = partnerInterceptor;
                this.customerInterceptor = customerInterceptor;
        }

        @Override
        public void addInterceptors(InterceptorRegistry registry) {

                String[] publicPaths = {
                                "/signin", "/signup",
                                "/api/signin", "/api/partner-signin", "/api/signup",
                                "/", "/getstarted", "/customer-signin", "/customer-signup", "/partner-signin",
                                "/partner-signup",
                                "/api/account/customer", "/api/account/partner"
                };

                // Áp dụng AuthInterceptor cho tất cả các đường dẫn trừ các đường dẫn công khai
                registry.addInterceptor(authInterceptor)
                                .addPathPatterns("/**")
                                .excludePathPatterns(publicPaths);

                // Áp dụng PartnerInterceptor cho các đường dẫn của đối tác
                registry.addInterceptor(partnerInterceptor)
                                .addPathPatterns("/partner/**", "/api/partner/**");

                // Áp dụng CustomerInterceptor cho các đường dẫn của khách hàng
                registry.addInterceptor(customerInterceptor)
                                .addPathPatterns("/user/**", "/api/cart/**", "/gio-hang/**");
        }
}
