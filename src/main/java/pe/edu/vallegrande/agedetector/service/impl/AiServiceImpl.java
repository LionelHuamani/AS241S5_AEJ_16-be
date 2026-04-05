package pe.edu.vallegrande.agedetector.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
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

    @Override
    public Flux<AiAnalysis> findAll() {
        return repository.findAll();
    }

    @Override
    public Mono<AiAnalysis> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public Mono<AiAnalysis> analyze(String url) {

        AiAnalysis ai = new AiAnalysis();
        ai.setUrl(url);
        ai.setWebContent("Contenido simulado");
        ai.setAiResponse("Respuesta IA simulada");
        ai.setCreationDate(LocalDateTime.now());
        ai.setUpdateDate(LocalDateTime.now());

        return repository.save(ai);
    }
}