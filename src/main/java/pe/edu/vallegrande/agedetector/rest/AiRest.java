package pe.edu.vallegrande.agedetector.rest;

import pe.edu.vallegrande.agedetector.model.AiAnalysis;
import pe.edu.vallegrande.agedetector.service.AiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/api/ai")
public class AiRest {

    private final AiService aiService;

    @Autowired
    public AiRest(AiService aiService) {
        this.aiService = aiService;
    }

    @GetMapping
    public Flux<AiAnalysis> findAll(){
        return aiService.findAll();
    }

    @GetMapping("/{id}")
    public Mono<AiAnalysis> findById(@PathVariable String id){
        return aiService.findById(id);
    }

    @PostMapping("/image")
    public Mono<AiAnalysis> image(@RequestParam String prompt) {
    return aiService.generateAndSaveImage(prompt);
}

    @PostMapping("/analyze")
    public Mono<AiAnalysis> analyze(@RequestParam String url) {
        return aiService.analyze(url);
    }

    @PostMapping("/image/save")
    public Mono<AiAnalysis> imageSave(@RequestParam String prompt) {
    return aiService.generateAndSaveImage(prompt);
}
}