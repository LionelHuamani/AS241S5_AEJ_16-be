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

    @Value("${rapidapi.chatgpt.url}")
    private String chatUrl;

    @Value("${rapidapi.chatgpt.host}")
    private String chatHost;

    @Value("${rapidapi.chatgpt.apikey}")
    private String chatKey;

    @Bean
    public WebClient webContentClient() {
        return WebClient.builder()
                .baseUrl(webUrl)
                .defaultHeader("x-rapidapi-host", webHost)
                .defaultHeader("x-rapidapi-key", webKey)
                .defaultHeader("Content-Type", "application/json")
                .build();
    }

    @Bean
    public WebClient chatGPTClient() {
        return WebClient.builder()
                .baseUrl(chatUrl)
                .defaultHeader("x-rapidapi-host", chatHost)
                .defaultHeader("x-rapidapi-key", chatKey)
                .defaultHeader("Content-Type", "application/json")
                .build();
    }
}