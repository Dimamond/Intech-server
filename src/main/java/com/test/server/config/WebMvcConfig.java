package com.test.server.config;

import com.test.server.config.interceptor.AuthorizationInterceptor;
import com.test.server.security.JwtTokenProviderService;
import com.test.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private JwtTokenProviderService jwtTokenProviderService;

    @Autowired
    private UserService userService;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthorizationInterceptor(jwtTokenProviderService, userService))
                .addPathPatterns("/private/**")
                .excludePathPatterns("/private/user/authorization/");

    }
}
