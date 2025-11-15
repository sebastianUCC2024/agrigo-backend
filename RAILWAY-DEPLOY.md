# 🚂 Deployment en Railway - Paso a Paso

## ✅ Requisitos previos
- Cuenta en Railway.app (gratis)
- Cuenta de GitHub con el repositorio
- Los JARs compilados (ya los tienes)

---

## 📦 PASO 1: Configurar MySQL en Railway

### 1.1 Crear base de datos
1. Ve a https://railway.app
2. Click en **"Start a New Project"**
3. Selecciona **"Provision MySQL"**
4. Espera 30 segundos a que se cree

### 1.2 Obtener credenciales
1. Click en el servicio MySQL
2. Ve a la pestaña **"Variables"**
3. Copia estos valores:
   ```
   MYSQL_HOST
   MYSQL_PORT
   MYSQL_USER
   MYSQL_PASSWORD
   MYSQL_DATABASE
   ```

### 1.3 Conectar y crear tablas
1. Abre MySQL Workbench
2. Nueva conexión con los datos de Railway
3. Ejecuta el archivo `init-db.sql`
4. Verifica que se crearon las 11 tablas

---

## 🚀 PASO 2: Desplegar Microservicios

### Método A: Desde GitHub (Recomendado)

Para cada servicio, repite estos pasos:

#### AUTH SERVICE (Puerto 8081)

1. **En Railway, click "New" → "GitHub Repo"**
2. **Selecciona tu repositorio:** `agrigo-backend`
3. **Configuración:**
   - Name: `auth-service`
   - Root Directory: `auth-service`
   - Build Command: `mvn clean package -DskipTests`
   - Start Command: `java -jar target/auth-service-1.0.0.jar`

4. **Variables de entorno** (click en "Variables"):
   ```
   DB_URL=jdbc:mysql://[MYSQL_HOST]:[MYSQL_PORT]/railway
   DB_USERNAME=[MYSQL_USER]
   DB_PASSWORD=[MYSQL_PASSWORD]
   JWT_SECRET=agrigo-secret-key-2024-super-secure-minimum-256-bits-long
   PORT=8081
   ```

5. **Deploy:** Railway desplegará automáticamente

6. **Obtener URL:** 
   - Ve a "Settings" → "Generate Domain"
   - Guarda la URL: `https://auth-service-xxxxx.up.railway.app`

---

#### FARMER SERVICE (Puerto 8082)

Repite el proceso anterior pero con:
- Name: `farmer-service`
- Root Directory: `farmer-service`
- Start Command: `java -jar target/farmer-service-1.0.0.jar`
- Variables:
  ```
  DB_URL=jdbc:mysql://[MYSQL_HOST]:[MYSQL_PORT]/railway
  DB_USERNAME=[MYSQL_USER]
  DB_PASSWORD=[MYSQL_PASSWORD]
  PORT=8082
  ```

---

#### STORE SERVICE (Puerto 8083)

- Name: `store-service`
- Root Directory: `store-service`
- Start Command: `java -jar target/store-service-1.0.0.jar`
- Variables:
  ```
  DB_URL=jdbc:mysql://[MYSQL_HOST]:[MYSQL_PORT]/railway
  DB_USERNAME=[MYSQL_USER]
  DB_PASSWORD=[MYSQL_PASSWORD]
  PORT=8083
  ```

---

#### PAYMENT SERVICE (Puerto 8084)

- Name: `payment-service`
- Root Directory: `payment-service`
- Start Command: `java -jar target/payment-service-1.0.0.jar`
- Variables:
  ```
  DB_URL=jdbc:mysql://[MYSQL_HOST]:[MYSQL_PORT]/railway
  DB_USERNAME=[MYSQL_USER]
  DB_PASSWORD=[MYSQL_PASSWORD]
  BANCOLOMBIA_API_KEY=
  NEQUI_API_KEY=
  NEQUI_CLIENT_ID=
  PORT=8084
  ```

---

#### AI RECOMMENDATION SERVICE (Puerto 8085)

- Name: `ai-recommendation-service`
- Root Directory: `ai-recommendation-service`
- Start Command: `java -jar target/ai-recommendation-service-1.0.0.jar`
- Variables:
  ```
  HUGGINGFACE_API_KEY=[tu_key_opcional]
  PORT=8085
  ```

---

#### PRODUCT MARKETPLACE SERVICE (Puerto 8086)

- Name: `product-marketplace-service`
- Root Directory: `product-marketplace-service`
- Start Command: `java -jar target/product-marketplace-service-1.0.0.jar`
- Variables:
  ```
  DB_URL=jdbc:mysql://[MYSQL_HOST]:[MYSQL_PORT]/railway
  DB_USERNAME=[MYSQL_USER]
  DB_PASSWORD=[MYSQL_PASSWORD]
  PORT=8086
  ```

---

#### INVENTORY SERVICE (Puerto 8087)

- Name: `inventory-service`
- Root Directory: `inventory-service`
- Start Command: `java -jar target/inventory-service-1.0.0.jar`
- Variables:
  ```
  DB_URL=jdbc:mysql://[MYSQL_HOST]:[MYSQL_PORT]/railway
  DB_USERNAME=[MYSQL_USER]
  DB_PASSWORD=[MYSQL_PASSWORD]
  PORT=8087
  ```

---

#### NOTIFICATION SERVICE (Puerto 8088)

- Name: `notification-service`
- Root Directory: `notification-service`
- Start Command: `java -jar target/notification-service-1.0.0.jar`
- Variables:
  ```
  DB_URL=jdbc:mysql://[MYSQL_HOST]:[MYSQL_PORT]/railway
  DB_USERNAME=[MYSQL_USER]
  DB_PASSWORD=[MYSQL_PASSWORD]
  PORT=8088
  ```

---

## ✅ PASO 3: Verificar Deployment

### 3.1 Revisar logs
1. En Railway, click en cada servicio
2. Ve a la pestaña **"Deployments"**
3. Click en el deployment activo
4. Revisa los logs - debe decir: `Started [ServiceName]Application`

### 3.2 Probar endpoints

Usa Postman o curl para probar:

```bash
# Auth Service - Login
curl -X POST https://auth-service-xxxxx.up.railway.app/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"farmer1@agrigo.com","password":"password"}'

# Farmer Service - Listar agricultores
curl https://farmer-service-xxxxx.up.railway.app/api/farmers

# AI Service - Recomendación
curl -X POST https://ai-service-xxxxx.up.railway.app/api/recommendations \
  -H "Content-Type: application/json" \
  -d '{"cropType":"RICE","soilType":"Clay","climateZone":"Tropical"}'
```

---

## 📝 PASO 4: Guardar URLs

Crea un archivo `PRODUCTION-URLS.txt` con todas tus URLs:

```
AUTH_SERVICE=https://auth-service-xxxxx.up.railway.app
FARMER_SERVICE=https://farmer-service-xxxxx.up.railway.app
STORE_SERVICE=https://store-service-xxxxx.up.railway.app
PAYMENT_SERVICE=https://payment-service-xxxxx.up.railway.app
AI_SERVICE=https://ai-service-xxxxx.up.railway.app
MARKETPLACE_SERVICE=https://marketplace-service-xxxxx.up.railway.app
INVENTORY_SERVICE=https://inventory-service-xxxxx.up.railway.app
NOTIFICATION_SERVICE=https://notification-service-xxxxx.up.railway.app
```

---

## 🔧 Solución de Problemas

### Error: "Build failed"
- Verifica que el `Root Directory` sea correcto
- Asegúrate de que el JAR esté en el repo

### Error: "Application failed to start"
- Revisa los logs en Railway
- Verifica las variables de entorno
- Asegúrate de que MySQL esté corriendo

### Error: "Connection refused"
- Verifica que `DB_URL` tenga el host correcto de Railway
- Revisa que el usuario y contraseña sean correctos

---

## 💰 Costos

Railway ofrece:
- **$5 USD gratis al mes** (suficiente para desarrollo)
- Después: ~$0.000463 por GB-hora
- MySQL: incluido en el plan gratuito

---

## 🎉 ¡Listo!

Tu backend está desplegado en Railway. Ahora puedes:
1. ✅ Probar todos los endpoints
2. ✅ Conectar el frontend
3. ✅ Monitorear en el dashboard de Railway
