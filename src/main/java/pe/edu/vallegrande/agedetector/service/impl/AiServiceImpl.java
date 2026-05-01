package pe.edu.vallegrande.agedetector.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

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

                    ai.setStatus("A"); // 🔥 IMPORTANTE

                    ai.setCreationDate(LocalDateTime.now());
                    ai.setUpdateDate(LocalDateTime.now());

                    return repository.save(ai);
                });
    }

    @Override
    public Mono<AiAnalysis> update(String id, AiAnalysis newData) {
        return repository.findById(id)
                .flatMap(existing -> {

                    existing.setType(newData.getType());
                    existing.setUrl(newData.getUrl());
                    existing.setWebContent(newData.getWebContent());
                    existing.setAiResponse(newData.getAiResponse());
                    existing.setImageUrl(newData.getImageUrl());

                    existing.setUpdateDate(LocalDateTime.now());

                    return repository.save(existing);
                });
    }

    @Override
    public Mono<AiAnalysis> generateAndSaveImage(String prompt) {
        return generateImage(prompt)
                .flatMap(response -> {
                    try {
                        ObjectMapper mapper = new ObjectMapper();
                        JsonNode root = mapper.readTree(response);

                        JsonNode results = root.path("final_result");

                        String imageUrl = null;

                        for (JsonNode node : results) {
                            if (!node.path("nsfw").asBoolean()) {
                                imageUrl = node.path("origin").asText().replace("\\/", "/");
                                break;
                            }
                        }

                        if (imageUrl == null) {
                            return Mono.error(new RuntimeException("No se encontró imagen válida"));
                        }

                        AiAnalysis ai = new AiAnalysis();
                        ai.setType("image");
                        ai.setAiResponse(prompt);
                        ai.setImageUrl(imageUrl);

                        ai.setStatus("A");
                        ai.setCreationDate(LocalDateTime.now());
                        ai.setUpdateDate(LocalDateTime.now());

                        return repository.save(ai);

                    } catch (Exception e) {
                        return Mono.error(new RuntimeException("Error procesando JSON"));
                    }
                });
    }

    @Override
    public Mono<AiAnalysis> delete(String id) {
        return repository.findById(id)
                .flatMap(ai -> {
                    ai.setStatus("I");
                    ai.setUpdateDate(LocalDateTime.now());
                    return repository.save(ai);
                });
    }

    @Override
    public Mono<AiAnalysis> restore(String id) {
        return repository.findById(id)
                .flatMap(ai -> {
                    ai.setStatus("A");
                    ai.setUpdateDate(LocalDateTime.now());
                    return repository.save(ai);
                });
    }

    @Override
    public Flux<AiAnalysis> findAll() {
        return repository.findByStatus("A");
    }

    @Override
    public Flux<AiAnalysis> findInactive() {
        return repository.findByStatus("I");
    }
}
