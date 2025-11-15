CREATE DATABASE IF NOT EXISTS agrigo_auth;
CREATE DATABASE IF NOT EXISTS agrigo_farmer;
CREATE DATABASE IF NOT EXISTS agrigo_store;
CREATE DATABASE IF NOT EXISTS agrigo_payment;
CREATE DATABASE IF NOT EXISTS agrigo_marketplace;
CREATE DATABASE IF NOT EXISTS agrigo_inventory;
CREATE DATABASE IF NOT EXISTS agrigo_notification;

USE agrigo_auth;

INSERT INTO users (email, password, name, role, phone, created_at) VALUES
('farmer1@agrigo.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'Juan Pérez', 'FARMER', '3001234567', NOW()),
('store1@agrigo.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'Agro Store', 'STORE', '3007654321', NOW()),
('admin@agrigo.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'Admin', 'ADMIN', '3009876543', NOW());

USE agrigo_farmer;

INSERT INTO farmers (user_id, name, location, farm_size, crop_types, soil_type, climate_zone, created_at) VALUES
(1, 'Juan Pérez', 'Valle del Cauca', '5 hectares', 'Rice, Corn', 'Clay', 'Tropical', NOW());

INSERT INTO farmer_products (farmer_id, product_name, description, price, quantity, unit, category, status, created_at) VALUES
(1, 'Arroz Premium', 'Arroz de alta calidad', 2500.00, 1000, 'kg', 'Grains', 'AVAILABLE', NOW()),
(1, 'Maíz Amarillo', 'Maíz fresco', 1800.00, 500, 'kg', 'Grains', 'AVAILABLE', NOW());

USE agrigo_store;

INSERT INTO stores (user_id, store_name, description, location, phone, email, address, created_at) VALUES
(2, 'Agro Store Central', 'Insumos agrícolas de calidad', 'Bogotá', '3007654321', 'store1@agrigo.com', 'Calle 100 #15-20', NOW());

INSERT INTO store_products (store_id, product_name, description, price, stock, category, brand, product_type, created_at) VALUES
(1, 'Fertilizante NPK 15-15-15', 'Fertilizante completo', 45000.00, 200, 'Fertilizers', 'AgroMax', 'FERTILIZER', NOW()),
(1, 'Pesticida Orgánico', 'Control de plagas natural', 35000.00, 150, 'Pesticides', 'EcoAgro', 'PESTICIDE', NOW()),
(1, 'Herbicida Selectivo', 'Control de malezas', 28000.00, 100, 'Herbicides', 'AgroMax', 'HERBICIDE', NOW());
