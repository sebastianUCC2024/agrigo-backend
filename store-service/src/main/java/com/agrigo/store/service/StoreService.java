package com.agrigo.store.service;

import com.agrigo.common.exception.ResourceNotFoundException;
import com.agrigo.store.dto.StoreDTO;
import com.agrigo.store.dto.StoreProductDTO;
import com.agrigo.store.entity.Store;
import com.agrigo.store.entity.StoreProduct;
import com.agrigo.store.repository.StoreProductRepository;
import com.agrigo.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StoreService {
    
    private final StoreRepository storeRepository;
    private final StoreProductRepository productRepository;
    
    public StoreDTO createStore(StoreDTO dto) {
        Store store = new Store();
        store.setUserId(dto.getUserId());
        store.setStoreName(dto.getStoreName());
        store.setDescription(dto.getDescription());
        store.setLocation(dto.getLocation());
        store.setPhone(dto.getPhone());
        store.setEmail(dto.getEmail());
        store.setAddress(dto.getAddress());
        
        Store saved = storeRepository.save(store);
        return toDTO(saved);
    }
    
    public StoreDTO getStoreById(Long id) {
        Store store = storeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Store not found"));
        return toDTO(store);
    }
    
    public List<StoreDTO> getAllStores() {
        return storeRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
    
    public StoreProductDTO createProduct(Long storeId, StoreProductDTO dto) {
        StoreProduct product = new StoreProduct();
        product.setStoreId(storeId);
        product.setProductName(dto.getProductName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setStock(dto.getStock());
        product.setCategory(dto.getCategory());
        product.setBrand(dto.getBrand());
        product.setImageUrl(dto.getImageUrl());
        product.setProductType(dto.getProductType());
        
        StoreProduct saved = productRepository.save(product);
        return toProductDTO(saved);
    }
    
    public List<StoreProductDTO> getStoreProducts(Long storeId) {
        return productRepository.findByStoreId(storeId).stream()
                .map(this::toProductDTO)
                .collect(Collectors.toList());
    }
    
    public List<StoreProductDTO> searchProducts(String productName) {
        return productRepository.findByProductNameContainingIgnoreCase(productName).stream()
                .map(this::toProductDTO)
                .collect(Collectors.toList());
    }
    
    public StoreProductDTO getCheapestProduct(String productName) {
        List<StoreProduct> products = productRepository.findByProductNameContainingIgnoreCase(productName);
        if (products.isEmpty()) {
            throw new ResourceNotFoundException("Product not found");
        }
        
        StoreProduct cheapest = products.stream()
                .min(Comparator.comparing(StoreProduct::getPrice))
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        
        return toProductDTO(cheapest);
    }
    
    private StoreDTO toDTO(Store store) {
        return new StoreDTO(
                store.getId(),
                store.getUserId(),
                store.getStoreName(),
                store.getDescription(),
                store.getLocation(),
                store.getPhone(),
                store.getEmail(),
                store.getAddress()
        );
    }
    
    private StoreProductDTO toProductDTO(StoreProduct product) {
        return new StoreProductDTO(
                product.getId(),
                product.getStoreId(),
                product.getProductName(),
                product.getDescription(),
                product.getPrice(),
                product.getStock(),
                product.getCategory(),
                product.getBrand(),
                product.getImageUrl(),
                product.getProductType()
        );
    }
}
