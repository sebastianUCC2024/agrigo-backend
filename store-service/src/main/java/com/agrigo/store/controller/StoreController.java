package com.agrigo.store.controller;

import com.agrigo.common.dto.ApiResponse;
import com.agrigo.store.dto.StoreDTO;
import com.agrigo.store.dto.StoreProductDTO;
import com.agrigo.store.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stores")
@RequiredArgsConstructor
public class StoreController {
    
    private final StoreService storeService;
    
    @PostMapping
    public ResponseEntity<ApiResponse<StoreDTO>> createStore(@RequestBody StoreDTO storeDTO) {
        StoreDTO created = storeService.createStore(storeDTO);
        return ResponseEntity.ok(ApiResponse.success(created));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<StoreDTO>> getStore(@PathVariable Long id) {
        StoreDTO store = storeService.getStoreById(id);
        return ResponseEntity.ok(ApiResponse.success(store));
    }
    
    @GetMapping
    public ResponseEntity<ApiResponse<List<StoreDTO>>> getAllStores() {
        List<StoreDTO> stores = storeService.getAllStores();
        return ResponseEntity.ok(ApiResponse.success(stores));
    }
    
    @PostMapping("/{storeId}/products")
    public ResponseEntity<ApiResponse<StoreProductDTO>> createProduct(@PathVariable Long storeId, @RequestBody StoreProductDTO productDTO) {
        StoreProductDTO created = storeService.createProduct(storeId, productDTO);
        return ResponseEntity.ok(ApiResponse.success(created));
    }
    
    @GetMapping("/{storeId}/products")
    public ResponseEntity<ApiResponse<List<StoreProductDTO>>> getStoreProducts(@PathVariable Long storeId) {
        List<StoreProductDTO> products = storeService.getStoreProducts(storeId);
        return ResponseEntity.ok(ApiResponse.success(products));
    }
    
    @GetMapping("/products/search")
    public ResponseEntity<ApiResponse<List<StoreProductDTO>>> searchProducts(@RequestParam String productName) {
        List<StoreProductDTO> products = storeService.searchProducts(productName);
        return ResponseEntity.ok(ApiResponse.success(products));
    }
    
    @GetMapping("/products/cheapest")
    public ResponseEntity<ApiResponse<StoreProductDTO>> getCheapestProduct(@RequestParam String productName) {
        StoreProductDTO product = storeService.getCheapestProduct(productName);
        return ResponseEntity.ok(ApiResponse.success(product));
    }
}
