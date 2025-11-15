package com.agrigo.farmer.service;

import com.agrigo.common.exception.ResourceNotFoundException;
import com.agrigo.farmer.dto.FarmerDTO;
import com.agrigo.farmer.dto.FarmerProductDTO;
import com.agrigo.farmer.entity.Farmer;
import com.agrigo.farmer.entity.FarmerProduct;
import com.agrigo.farmer.repository.FarmerProductRepository;
import com.agrigo.farmer.repository.FarmerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FarmerService {
    
    private final FarmerRepository farmerRepository;
    private final FarmerProductRepository productRepository;
    
    public FarmerDTO createFarmer(FarmerDTO dto) {
        Farmer farmer = new Farmer();
        farmer.setUserId(dto.getUserId());
        farmer.setName(dto.getName());
        farmer.setLocation(dto.getLocation());
        farmer.setFarmSize(dto.getFarmSize());
        farmer.setCropTypes(dto.getCropTypes());
        farmer.setSoilType(dto.getSoilType());
        farmer.setClimateZone(dto.getClimateZone());
        
        Farmer saved = farmerRepository.save(farmer);
        return toDTO(saved);
    }
    
    public FarmerDTO getFarmerById(Long id) {
        Farmer farmer = farmerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Farmer not found"));
        return toDTO(farmer);
    }
    
    public FarmerDTO getFarmerByUserId(Long userId) {
        Farmer farmer = farmerRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Farmer not found"));
        return toDTO(farmer);
    }
    
    public List<FarmerDTO> getAllFarmers() {
        return farmerRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
    
    public FarmerDTO updateFarmer(Long id, FarmerDTO dto) {
        Farmer farmer = farmerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Farmer not found"));
        
        farmer.setName(dto.getName());
        farmer.setLocation(dto.getLocation());
        farmer.setFarmSize(dto.getFarmSize());
        farmer.setCropTypes(dto.getCropTypes());
        farmer.setSoilType(dto.getSoilType());
        farmer.setClimateZone(dto.getClimateZone());
        
        Farmer updated = farmerRepository.save(farmer);
        return toDTO(updated);
    }
    
    public FarmerProductDTO createProduct(Long farmerId, FarmerProductDTO dto) {
        FarmerProduct product = new FarmerProduct();
        product.setFarmerId(farmerId);
        product.setProductName(dto.getProductName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setQuantity(dto.getQuantity());
        product.setUnit(dto.getUnit());
        product.setCategory(dto.getCategory());
        product.setImageUrl(dto.getImageUrl());
        
        FarmerProduct saved = productRepository.save(product);
        return toProductDTO(saved);
    }
    
    public List<FarmerProductDTO> getFarmerProducts(Long farmerId) {
        return productRepository.findByFarmerId(farmerId).stream()
                .map(this::toProductDTO)
                .collect(Collectors.toList());
    }
    
    private FarmerDTO toDTO(Farmer farmer) {
        return new FarmerDTO(
                farmer.getId(),
                farmer.getUserId(),
                farmer.getName(),
                farmer.getLocation(),
                farmer.getFarmSize(),
                farmer.getCropTypes(),
                farmer.getSoilType(),
                farmer.getClimateZone()
        );
    }
    
    private FarmerProductDTO toProductDTO(FarmerProduct product) {
        return new FarmerProductDTO(
                product.getId(),
                product.getFarmerId(),
                product.getProductName(),
                product.getDescription(),
                product.getPrice(),
                product.getQuantity(),
                product.getUnit(),
                product.getCategory(),
                product.getImageUrl(),
                product.getStatus()
        );
    }
}
