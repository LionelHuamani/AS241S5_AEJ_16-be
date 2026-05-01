package pe.edu.vallegrande.agedetector.repository;

import pe.edu.vallegrande.agedetector.model.AiAnalysis;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface AiAnalysisRepository extends ReactiveMongoRepository<AiAnalysis, String> {

    Flux<AiAnalysis> findByStatus(String status);
}