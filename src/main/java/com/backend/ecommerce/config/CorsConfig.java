package com.backend.ecommerce.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter(){

        CorsConfiguration corsConfiguration = new CorsConfiguration();

        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));

        corsConfiguration.setAllowedHeaders(Arrays.asList("Origin","Access-Control-Allow-Origin",
                "Content-Type","Accept", "Authorization", "Origin, Accept",
                "X-Requested-With", "Access-Control-Request-Header", "Access-Control-Request-Header"));

        corsConfiguration.setExposedHeaders(Arrays.asList("Origin","Access-Control-Allow-Origin","Access-Control-Allow-Origin",
                "Content-Type","Accept", "Authorization","Access-Control-Allow-Credentials"));

        corsConfiguration.setAllowedMethods(Arrays.asList("GET","¨POST","PUT","DELETE","OPTIONS","PATCH"));

        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**",corsConfiguration);

        return new CorsFilter(urlBasedCorsConfigurationSource);

    }

}
