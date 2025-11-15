# Guía de Deployment - Senode

## Paso 1: Preparar el Proyecto

1. Build de todos los servicios:
```bash
./build-all.sh  # Linux/Mac
build-all.bat   # Windows
```

2. Verificar que todos los JARs estén en `target/` de cada servicio

## Paso 2: Configurar Senode

1. Crear cuenta en Senode.com
2. Crear nuevo proyecto "agrigo-backend"
3. Seleccionar región más cercana

## Paso 3: Configurar Base de Datos MySQL

1. En Senode, crear servicio MySQL 8.0
2. Anotar credenciales:
   - Host
   - Puerto
   - Usuario
   - Contraseña
   - Nombre de BD

3. Ejecutar `init-db.sql` en la BD de Senode

## Paso 4: Variables de Entorno en Senode

Configurar en cada servicio:

```
DB_URL=jdbc:mysql://[SENODE_MYSQL_HOST]:[PORT]/agrigo_[service_name]
DB_USERNAME=[SENODE_DB_USER]
DB_PASSWORD=[SENODE_DB_PASSWORD]
JWT_SECRET=agrigo-secret-key-2024-super-secure-minimum-256-bits-long
BANCOLOMBIA_API_KEY=[TU_KEY]
NEQUI_API_KEY=[TU_KEY]
NEQUI_CLIENT_ID=[TU_CLIENT_ID]
HUGGINGFACE_API_KEY=[TU_KEY]
```

## Paso 5: Deploy de Servicios

Para cada microservicio:

1. Crear nuevo servicio en Senode
2. Seleccionar "Docker"
3. Subir Dockerfile del servicio
4. Subir JAR compilado
5. Configurar puerto (8081-8088)
6. Agregar variables de entorno
7. Deploy

## Paso 6: Configurar Networking

1. Habilitar comunicación entre servicios
2. Configurar DNS interno de Senode
3. Actualizar URLs de Feign clients si es necesario

## Paso 7: Verificar Deployment

```bash
curl https://auth-service.senode.app/api/auth/login
```

## URLs de Producción

- Auth: https://auth-service-[tu-proyecto].senode.app
- Farmer: https://farmer-service-[tu-proyecto].senode.app
- Store: https://store-service-[tu-proyecto].senode.app
- Payment: https://payment-service-[tu-proyecto].senode.app
- AI: https://ai-service-[tu-proyecto].senode.app
- Marketplace: https://marketplace-service-[tu-proyecto].senode.app
- Inventory: https://inventory-service-[tu-proyecto].senode.app
- Notification: https://notification-service-[tu-proyecto].senode.app

## Monitoreo

1. Logs en tiempo real en dashboard de Senode
2. Métricas de CPU/RAM
3. Health checks automáticos

## Rollback

Si algo falla:
1. Ir a "Deployments" en Senode
2. Seleccionar versión anterior
3. Click en "Rollback"
