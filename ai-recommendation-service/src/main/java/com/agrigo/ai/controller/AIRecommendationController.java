package com.agrigo.ai.controller;

import com.agrigo.ai.dto.RecommendationRequest;
import com.agrigo.ai.dto.RecommendationResponse;
import com.agrigo.ai.service.AIRecommendationService;
import com.agrigo.common.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/recommendations")
@RequiredArgsConstructor
public class AIRecommendationController {
    
    private final AIRecommendationService aiService;
    
    @PostMapping
    public ResponseEntity<ApiResponse<RecommendationResponse>> getRecommendation(@RequestBody RecommendationRequest request) {
        RecommendationResponse response = aiService.getRecommendation(request);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
