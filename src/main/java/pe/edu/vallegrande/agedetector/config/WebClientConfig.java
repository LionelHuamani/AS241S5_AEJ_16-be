package pe.edu.vallegrande.agedetector.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${rapidapi.webcontent.url}")
    private String webUrl;

    @Value("${rapidapi.webcontent.host}")
    private String webHost;

    @Value("${rapidapi.webcontent.apikey}")
    private String webKey;

    @Value("${rapidapi.imageai.url}")
    private String imageUrl;

    @Value("${rapidapi.imageai.host}")
    private String imageHost;

    @Value("${rapidapi.imageai.apikey}")
    private String imageKey;

    @Bean
    public WebClient webContentClient() {
        return WebClient.builder()
                .baseUrl(webUrl)
                .defaultHeader("x-rapidapi-host", webHost)
                .defaultHeader("x-rapidapi-key", webKey)
                .build();
    }

    @Bean
    public WebClient imageClient() {
        return WebClient.builder()
                .baseUrl(imageUrl)
                .defaultHeader("x-rapidapi-host", imageHost)
                .defaultHeader("x-rapidapi-key", imageKey)
                .defaultHeader("Content-Type", "application/json")
                .build();
    }
}