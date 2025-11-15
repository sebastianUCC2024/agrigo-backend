package com.agrigo.farmer.controller;

import com.agrigo.common.dto.ApiResponse;
import com.agrigo.farmer.dto.FarmerDTO;
import com.agrigo.farmer.dto.FarmerProductDTO;
import com.agrigo.farmer.service.FarmerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/farmers")
@RequiredArgsConstructor
public class FarmerController {
    
    private final FarmerService farmerService;
    
    @PostMapping
    public ResponseEntity<ApiResponse<FarmerDTO>> createFarmer(@RequestBody FarmerDTO farmerDTO) {
        FarmerDTO created = farmerService.createFarmer(farmerDTO);
        return ResponseEntity.ok(ApiResponse.success(created));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<FarmerDTO>> getFarmer(@PathVariable Long id) {
        FarmerDTO farmer = farmerService.getFarmerById(id);
        return ResponseEntity.ok(ApiResponse.success(farmer));
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<FarmerDTO>> getFarmerByUserId(@PathVariable Long userId) {
        FarmerDTO farmer = farmerService.getFarmerByUserId(userId);
        return ResponseEntity.ok(ApiResponse.success(farmer));
    }
    
    @GetMapping
    public ResponseEntity<ApiResponse<List<FarmerDTO>>> getAllFarmers() {
        List<FarmerDTO> farmers = farmerService.getAllFarmers();
        return ResponseEntity.ok(ApiResponse.success(farmers));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<FarmerDTO>> updateFarmer(@PathVariable Long id, @RequestBody FarmerDTO farmerDTO) {
        FarmerDTO updated = farmerService.updateFarmer(id, farmerDTO);
        return ResponseEntity.ok(ApiResponse.success(updated));
    }
    
    @PostMapping("/{farmerId}/products")
    public ResponseEntity<ApiResponse<FarmerProductDTO>> createProduct(@PathVariable Long farmerId, @RequestBody FarmerProductDTO productDTO) {
        FarmerProductDTO created = farmerService.createProduct(farmerId, productDTO);
        return ResponseEntity.ok(ApiResponse.success(created));
    }
    
    @GetMapping("/{farmerId}/products")
    public ResponseEntity<ApiResponse<List<FarmerProductDTO>>> getFarmerProducts(@PathVariable Long farmerId) {
        List<FarmerProductDTO> products = farmerService.getFarmerProducts(farmerId);
        return ResponseEntity.ok(ApiResponse.success(products));
    }
}
