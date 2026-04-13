package pe.edu.vallegrande.agedetector.service;

import pe.edu.vallegrande.agedetector.model.AiAnalysis;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AiService {

    Flux<AiAnalysis> findAll();

    Mono<AiAnalysis> findById(String id);

    Mono<AiAnalysis> analyze(String url);

    Mono<String> getTranscript(String url);

    Mono<String> generateImage(String prompt);

    Mono<AiAnalysis> generateAndSaveImage(String prompt);
}