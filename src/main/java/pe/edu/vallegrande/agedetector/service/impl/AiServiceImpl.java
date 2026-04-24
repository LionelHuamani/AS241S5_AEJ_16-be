package pe.edu.vallegrande.agedetector.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import pe.edu.vallegrande.agedetector.model.AiAnalysis;
import pe.edu.vallegrande.agedetector.repository.AiAnalysisRepository;
import pe.edu.vallegrande.agedetector.service.AiService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AiServiceImpl implements AiService {

    private final AiAnalysisRepository repository;
    private final WebClient webContentClient;
    private final WebClient imageClient; 

    @Override
    public Flux<AiAnalysis> findAll() {
        return repository.findAll();
    }

    @Override
    public Mono<AiAnalysis> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public Mono<String> getTranscript(String url) {
        return webContentClient.post()
                .uri("/transcribe-ig-video")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .bodyValue("url=" + url)
                .retrieve()
                .bodyToMono(String.class)
                .doOnNext(res -> System.out.println("TRANSCRIPT: " + res));
    }

    @Override
    public Mono<String> generateImage(String prompt) {
        return imageClient.post()
                .uri("/aaaaaaaaaaaaaaaaaiimagegenerator/quick.php")
                .bodyValue("""
                {
                  "prompt": "%s",
                  "style_id": 4,
                  "size": "1-1"
                }
                """.formatted(prompt))
                .retrieve()
                .bodyToMono(String.class)
                .doOnNext(res -> System.out.println("IMAGE RESPONSE: " + res))
                .onErrorResume(e -> {
                    System.out.println("ERROR IMAGE API: " + e.getMessage());
                    return Mono.just("Error generando imagen");
                });
    }

    @Override
    public Mono<AiAnalysis> analyze(String url) {

        return getTranscript(url)
            .flatMap(transcript -> {

                String cleanText = transcript.replaceAll(".*\"text\":\"(.*?)\".*", "$1");

                AiAnalysis ai = new AiAnalysis();
                ai.setType("transcript"); 
                ai.setUrl(url);
                ai.setWebContent(cleanText);
                ai.setAiResponse("Transcript generado");

                ai.setCreationDate(LocalDateTime.now());
                ai.setUpdateDate(LocalDateTime.now());

                return repository.save(ai);
            });
    }

    @Override
    public Mono<AiAnalysis> generateAndSaveImage(String prompt) {
        return generateImage(prompt)
            .flatMap(response -> {

                String imageUrl = response.replaceAll(".*\"origin\":\"(.*?)\".*", "$1");

                AiAnalysis ai = new AiAnalysis();
                ai.setType("image"); 
                ai.setUrl(null);
                ai.setWebContent(null);
                ai.setAiResponse(prompt);
                ai.setImageUrl(imageUrl);

                ai.setCreationDate(LocalDateTime.now());
                ai.setUpdateDate(LocalDateTime.now());

                return repository.save(ai);
            });
    }
}