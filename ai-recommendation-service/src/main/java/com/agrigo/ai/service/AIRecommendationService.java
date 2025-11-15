package com.agrigo.ai.service;

import com.agrigo.ai.dto.*;
import com.agrigo.ai.strategy.RecommendationStrategy;
import com.agrigo.ai.strategy.RecommendationStrategyFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class AIRecommendationService {
    
    @Value("${huggingface.api.url:https://api-inference.huggingface.co/models/gpt2}")
    private String huggingFaceUrl;
    
    @Value("${huggingface.api.key:}")
    private String huggingFaceKey;
    
    private final RecommendationStrategyFactory strategyFactory;
    private final RestTemplate restTemplate = new RestTemplate();
    
    public RecommendationResponse getRecommendation(RecommendationRequest request) {
        RecommendationStrategy strategy = strategyFactory.getStrategy(request.getCropType());
        RecommendationResponse baseRecommendation = strategy.generateRecommendation(request);
        
        String aiInsight = getAIInsight(request);
        baseRecommendation.setAiInsight(aiInsight);
        
        return baseRecommendation;
    }
    
    private String getAIInsight(RecommendationRequest request) {
        try {
            String prompt = String.format(
                "Agricultural recommendation for %s crop in %s soil, %s climate. Suggest fertilizer and pesticide amounts.",
                request.getCropType(),
                request.getSoilType(),
                request.getClimateZone()
            );
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            if (huggingFaceKey != null && !huggingFaceKey.isEmpty()) {
                headers.set("Authorization", "Bearer " + huggingFaceKey);
            }
            
            Map<String, Object> payload = new HashMap<>();
            payload.put("inputs", prompt);
            payload.put("parameters", Map.of("max_length", 100, "temperature", 0.7));
            
            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(payload, headers);
            
            ResponseEntity<List> response = restTemplate.exchange(
                    huggingFaceUrl,
                    HttpMethod.POST,
                    entity,
                    List.class
            );
            
            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                List<Map<String, Object>> body = response.getBody();
                if (!body.isEmpty()) {
                    return (String) body.get(0).get("generated_text");
                }
            }
        } catch (Exception e) {
            log.error("AI insight generation error: {}", e.getMessage());
        }
        
        return "Based on your crop and soil conditions, follow the recommended fertilizer and pesticide guidelines.";
    }
}
