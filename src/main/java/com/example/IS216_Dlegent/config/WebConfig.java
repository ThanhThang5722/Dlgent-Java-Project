package com.example.IS216_Dlegent.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.IS216_Dlegent.middleware.AdminInterceptor;
import com.example.IS216_Dlegent.middleware.AuthInterceptor;
import com.example.IS216_Dlegent.middleware.CustomerInterceptor;
import com.example.IS216_Dlegent.middleware.PartnerInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {
        private final AuthInterceptor authInterceptor;
        private final PartnerInterceptor partnerInterceptor;
        private final CustomerInterceptor customerInterceptor;
        private final AdminInterceptor adminInterceptor;

        @Autowired
        public WebConfig(AuthInterceptor authInterceptor, PartnerInterceptor partnerInterceptor,
                        CustomerInterceptor customerInterceptor, AdminInterceptor adminInterceptor) {
                this.authInterceptor = authInterceptor;
                this.partnerInterceptor = partnerInterceptor;
                this.customerInterceptor = customerInterceptor;
                this.adminInterceptor = adminInterceptor;
        }

        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
                registry.addResourceHandler("/css/**")
                        .addResourceLocations("classpath:/static/css/");
                registry.addResourceHandler("/js/**")
                        .addResourceLocations("classpath:/static/js/");
                registry.addResourceHandler("/images/**")
                        .addResourceLocations("classpath:/static/images/");
                registry.addResourceHandler("/static/**")
                        .addResourceLocations("classpath:/static/");
        }

        @Override
        public void addInterceptors(InterceptorRegistry registry) {

                String[] publicPaths = {
                                "/signin", "/signup",
                                "/api/signin", "/api/partner-signin", "/api/signup",
                                "/", "/getstarted", "/customer-signin", "/customer-signup", "/partner-signin",
                                "/partner-signup",
                                "/api/account/customer", "/api/account/partner",
                                "/tim-kiem-resort/**", "/resort-detail/**"
                };

                // Định nghĩa các đường dẫn tài nguyên tĩnh cần loại trừ khỏi tất cả các interceptor
                String[] staticResourcePaths = {
                                "/css/**", "/js/**", "/images/**", "/static/**", "/webjars/**", "/favicon.ico"
                };

                // Áp dụng AdminInterceptor cho các đường dẫn admin
                registry.addInterceptor(adminInterceptor)
                                .addPathPatterns("/admin/**", "/api/admin/**")
                                .excludePathPatterns(staticResourcePaths);

                // Áp dụng PartnerInterceptor cho các đường dẫn của đối tác
                registry.addInterceptor(partnerInterceptor)
                                .addPathPatterns("/partner/**", "/api/partner/**")
                                .excludePathPatterns(staticResourcePaths);

                // Áp dụng CustomerInterceptor cho các đường dẫn của khách hàng
                registry.addInterceptor(customerInterceptor)
                                .addPathPatterns("/user/**", "/api/cart/**", "/gio-hang/**", "/api/zalopay/**")
                                .excludePathPatterns(staticResourcePaths);

                registry.addInterceptor(authInterceptor)
                                .addPathPatterns("/**")
                                .excludePathPatterns(publicPaths)
                                .excludePathPatterns("/admin/**", "/api/admin/**")
                                .excludePathPatterns("/partner/**", "/api/partner/**")
                                .excludePathPatterns("/user/**", "/api/cart/**", "/gio-hang/**")
                                .excludePathPatterns(staticResourcePaths);
        }
}
