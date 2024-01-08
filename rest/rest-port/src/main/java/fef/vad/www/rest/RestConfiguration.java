package fef.vad.www.rest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class RestConfiguration {
  @Bean
  public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurer() {
      @Override
      public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/mail").allowedOrigins(
          "http://localhost:5000",
          "http://localhost:5173",
          "http://127.0.0.1:5173",
          "https://thecatmaincave.com"
        );
      }
    };
  }
}
