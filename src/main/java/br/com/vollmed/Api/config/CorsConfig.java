package br.com.vollmed.Api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer{

    @Override
    public void addCorsMappings(CorsRegistry registry){
        registry
<<<<<<< HEAD
        .addMapping("/**")// Aplicar em todas as rotas
=======
        .addMapping("/**") // Aplicar em todas as rotas
>>>>>>> 7f0c1f573623ede75d0b60d8c8988d335becd68d
        .allowedOrigins("*") // qualquer origem
        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
        .allowedHeaders("*")
        .maxAge(3600);
    }
    
}
