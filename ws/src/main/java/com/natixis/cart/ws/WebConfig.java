package com.natixis.cart.ws;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
class WebConffig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowCredentials(false)
                .allowedHeaders("Content-Type","Access-Control-Allow-Headers","Authorization","X-Requested-With")
                .allowedMethods("GET","PUT","DELETE","POST","OPTIONS");
    }
}