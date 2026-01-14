# PigX Cloud Services Overview

Tổng quan chi tiết về tất cả 6 services trong PigX Cloud Platform.

---

## 📊 Services Summary

| # | Service | Port | Responsibility | Tech Stack | Dependencies |
|---|---------|------|----------------|------------|--------------|
| 1 | **service-uaa** | 9100 | Authentication & Authorization | OAuth2, JWT, Spring Security | PostgreSQL, Redis |
| 2 | **service-upms** | 9101 | User Permission Management | JPA, RBAC | PostgreSQL, Redis |
| 3 | **service-gateway** | 8080 | API Gateway & Routing | Spring Cloud Gateway, Sentinel | Nacos, Redis |
| 4 | **service-message** | 9102 | WebSocket & Messaging | STOMP, WebSocket | Redis, Kafka (optional) |
| 5 | **service-monitor** | 9200 | Monitoring & Admin | Spring Boot Admin | Nacos |
| 6 | **service-oss** | 9103 | Object Storage | MinIO, AWS S3 | MinIO/S3 |

---

## 🔐 1. service-uaa (Authentication & Authorization)

### Responsibilities
- OAuth2 Authorization Server
- User authentication (username/password, SMS, social login)
- Token issuance (JWT, Opaque)
- Token validation and introspection
- Client management
- Scope management

### Key Features
- ✅ OAuth2 Authorization Code Flow
- ✅ Password Grant (for legacy)
- ✅ Client Credentials
- ✅ Refresh Token
- ✅ JWT Token Format
- ✅ Social Login (WeChat, QQ, etc.)
- ✅ SMS Login
- ✅ Captcha Validation
- ✅ Login attempt limiting
- ✅ Device kickout

### Tech Stack
```xml
<dependencies>
    <!-- PigX Engine Auth -->
    <dependency>
        <groupId>com.pigx.engine</groupId>
        <artifactId>authentication-spring-boot-starter</artifactId>
    </dependency>
    
    <!-- Spring Security OAuth2 -->
    <dependency>
        <groupId>org.springframework.security</groupId>
        <artifactId>spring-security-oauth2-authorization-server</artifactId>
    </dependency>
    
    <!-- Database -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    <dependency>
        <groupId>org.postgresql</groupId>
        <artifactId>postgresql</artifactId>
    </dependency>
    
    <!-- Cache -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-redis</artifactId>
    </dependency>
</dependencies>
```

### Database Tables
```sql
-- OAuth2 Tables (Spring Authorization Server standard)
oauth2_authorization
oauth2_authorization_consent
oauth2_registered_client

-- Custom Tables
sys_user
sys_social_account
sys_login_log
```

### API Endpoints
```
POST   /oauth2/token              # Get token
POST   /oauth2/introspect         # Introspect token
POST   /oauth2/revoke             # Revoke token
GET    /oauth2/authorize          # Authorization endpoint
POST   /login                     # User login
POST   /logout                    # User logout
GET    /.well-known/jwks.json     # JWK Set
GET    /.well-known/oauth-authorization-server  # OAuth2 metadata
```

### Configuration
```yaml
server:
  port: 9100

spring:
  application:
    name: pigx-cloud-uaa

herodotus:
  oauth2:
    authorization:
      token-format: JWT
      sign-in:
        failure-limit: 5
        kick-out:
          enabled: true
          max-session: 1
```

---

## 👥 2. service-upms (User Permission Management)

### Responsibilities
- User management (CRUD)
- Role management (RBAC)
- Permission management
- Department/Organization management
- Menu management
- Data permission
- API permission

### Key Features
- ✅ User CRUD with validation
- ✅ Role-Based Access Control (RBAC)
- ✅ Multi-tenant support
- ✅ Department hierarchy
- ✅ Dynamic menu tree
- ✅ Permission assignment
- ✅ Data scope control
- ✅ User profile management

### Tech Stack
```xml
<dependencies>
    <!-- PigX Engine -->
    <dependency>
        <groupId>com.pigx.engine</groupId>
        <artifactId>authorization-spring-boot-starter</artifactId>
    </dependency>
    <dependency>
        <groupId>com.pigx.engine</groupId>
        <artifactId>data-rdbms-spring-boot-starter</artifactId>
    </dependency>
    
    <!-- Business Module -->
    <dependency>
        <groupId>com.pigx</groupId>
        <artifactId>module-api</artifactId>
    </dependency>
</dependencies>
```

### Database Tables
```sql
sys_user
sys_role
sys_permission
sys_user_role
sys_role_permission
sys_department
sys_menu
sys_dict
```

### API Endpoints
```
# User Management
GET    /api/users              # List users
POST   /api/users              # Create user
PUT    /api/users/{id}         # Update user
DELETE /api/users/{id}         # Delete user
GET    /api/users/{id}         # Get user detail

# Role Management
GET    /api/roles              # List roles
POST   /api/roles              # Create role
PUT    /api/roles/{id}         # Update role
DELETE /api/roles/{id}         # Delete role

# Permission Management
GET    /api/permissions        # List permissions
POST   /api/permissions        # Create permission
PUT    /api/permissions/{id}   # Update permission
DELETE /api/permissions/{id}   # Delete permission

# Assignment
POST   /api/users/{id}/roles   # Assign roles to user
POST   /api/roles/{id}/permissions  # Assign permissions to role
```

### Configuration
```yaml
server:
  port: 9101

spring:
  application:
    name: pigx-cloud-upms

herodotus:
  data:
    multi-tenant:
      enabled: true
      approach: SCHEMA  # or DISCRIMINATOR, DATABASE
```

---

## 🌐 3. service-gateway (API Gateway)

### Responsibilities
- Request routing
- Load balancing
- Rate limiting
- Authentication verification
- Request/Response transformation
- Circuit breaking
- API documentation aggregation

### Key Features
- ✅ Dynamic routing từ Nacos
- ✅ Service discovery integration
- ✅ OAuth2 resource server
- ✅ Rate limiting (Redis-based)
- ✅ Circuit breaker (Sentinel)
- ✅ Request logging
- ✅ Global filters
- ✅ Path rewriting

### Tech Stack
```xml
<dependencies>
    <!-- Gateway -->
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-gateway</artifactId>
    </dependency>
    
    <!-- Service Discovery -->
    <dependency>
        <groupId>com.alibaba.cloud</groupId>
        <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
    </dependency>
    
    <!-- Sentinel -->
    <dependency>
        <groupId>com.alibaba.cloud</groupId>
        <artifactId>spring-cloud-starter-alibaba-sentinel</artifactId>
    </dependency>
    <dependency>
        <groupId>com.alibaba.cloud</groupId>
        <artifactId>spring-cloud-alibaba-sentinel-gateway</artifactId>
    </dependency>
    
    <!-- Rate Limiting -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-redis-reactive</artifactId>
    </dependency>
</dependencies>
```

### Route Configuration
```yaml
spring:
  cloud:
    gateway:
      routes:
        # UAA Service
        - id: service-uaa
          uri: lb://pigx-cloud-uaa
          predicates:
            - Path=/api/auth/**
          filters:
            - StripPrefix=2
            - name: RequestRateLimiter
              args:
                redis-rate-limiter.replenishRate: 10
                redis-rate-limiter.burstCapacity: 20
                
        # UPMS Service
        - id: service-upms
          uri: lb://pigx-cloud-upms
          predicates:
            - Path=/api/upms/**
          filters:
            - StripPrefix=2
            
        # Message Service
        - id: service-message
          uri: lb://pigx-cloud-message
          predicates:
            - Path=/api/message/**
          filters:
            - StripPrefix=2
            
        # OSS Service
        - id: service-oss
          uri: lb://pigx-cloud-oss
          predicates:
            - Path=/api/oss/**
          filters:
            - StripPrefix=2
```

### Global Filters
- Authentication Filter
- Rate Limiting Filter
- Logging Filter
- CORS Filter
- Cache Body Filter

---

## 💬 4. service-message (Message & WebSocket)

### Responsibilities
- WebSocket connections
- Real-time messaging
- Message broadcasting
- User notifications
- System announcements
- Chat functionality

### Key Features
- ✅ STOMP over WebSocket
- ✅ Topic subscription
- ✅ Queue messaging
- ✅ User-to-user messaging
- ✅ Broadcast messaging
- ✅ Message history
- ✅ Online user tracking
- ✅ Heartbeat mechanism

### Tech Stack
```xml
<dependencies>
    <!-- PigX Engine Message -->
    <dependency>
        <groupId>com.pigx.engine</groupId>
        <artifactId>servlet-message-spring-boot-starter</artifactId>
    </dependency>
    
    <!-- WebSocket -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-websocket</artifactId>
    </dependency>
    
    <!-- Message Queue (Optional) -->
    <dependency>
        <groupId>org.springframework.kafka</groupId>
        <artifactId>spring-kafka</artifactId>
    </dependency>
</dependencies>
```

### WebSocket Configuration
```yaml
herodotus:
  message:
    websocket:
      enabled: true
      endpoint: /stomp/ws
      mode: MULTIPLE  # SINGLE or MULTIPLE
      allowed-origins: '*'
      
      # STOMP Configuration
      application-destination-prefixes: /app
      user-destination-prefix: /user
      
      # Broker Configuration
      broker:
        relay:
          enabled: false  # Use simple broker
```

### WebSocket Endpoints
```
# Connection
WS /stomp/ws  # WebSocket connection endpoint

# Topics
/topic/announcements    # System announcements
/topic/notifications    # Notifications
/user/queue/messages    # Private messages

# Application Destinations
/app/chat.send          # Send chat message
/app/notification.read  # Mark notification as read
```

### Database Tables
```sql
sys_message
sys_notification
sys_announcement
sys_websocket_session
```

---

## 📊 5. service-monitor (Monitoring & Admin)

### Responsibilities
- Service health monitoring
- Application metrics collection
- Log aggregation
- Performance monitoring
- Alert management
- Admin dashboard

### Key Features
- ✅ Spring Boot Admin UI
- ✅ Service discovery integration
- ✅ Health check monitoring
- ✅ Metrics visualization
- ✅ Log tailing
- ✅ Thread dump
- ✅ Heap dump
- ✅ Environment properties
- ✅ Alert notifications (Email, Slack, etc.)

### Tech Stack
```xml
<dependencies>
    <!-- Spring Boot Admin Server -->
    <dependency>
        <groupId>de.codecentric</groupId>
        <artifactId>spring-boot-admin-starter-server</artifactId>
    </dependency>
    
    <!-- Service Discovery -->
    <dependency>
        <groupId>com.alibaba.cloud</groupId>
        <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
    </dependency>
    
    <!-- Notification (Optional) -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-mail</artifactId>
    </dependency>
</dependencies>
```

### Configuration
```yaml
server:
  port: 9200

spring:
  application:
    name: pigx-cloud-monitor
  boot:
    admin:
      ui:
        title: PigX Cloud Admin
        brand: <img src="/assets/logo.png"/>
      monitor:
        connect-timeout: 5000
        read-timeout: 5000
      notify:
        mail:
          enabled: true
          to: admin@pigx.com
          from: noreply@pigx.com
```

### Monitored Metrics
- **Health**: Status, uptime, version
- **Memory**: Heap, non-heap, GC
- **Threads**: Active, daemon, peak
- **CPU**: Usage percentage
- **HTTP**: Requests, status codes, response times
- **Database**: Connections, queries
- **Cache**: Hit rate, evictions

---

## 📦 6. service-oss (Object Storage)

### Responsibilities
- File upload/download
- Object storage management
- Presigned URL generation
- Bucket management
- File metadata management
- Storage quota management

### Key Features
- ✅ MinIO integration
- ✅ AWS S3 compatible
- ✅ File upload (single/multipart)
- ✅ File download
- ✅ Presigned URL
- ✅ Bucket operations
- ✅ File metadata
- ✅ Storage statistics
- ✅ Access control

### Tech Stack
```xml
<dependencies>
    <!-- PigX Engine OSS -->
    <dependency>
        <groupId>com.pigx.engine</groupId>
        <artifactId>oss-spring-boot-starter</artifactId>
    </dependency>
    
    <!-- MinIO SDK -->
    <dependency>
        <groupId>io.minio</groupId>
        <artifactId>minio</artifactId>
    </dependency>
    
    <!-- AWS S3 SDK (Optional) -->
    <dependency>
        <groupId>software.amazon.awssdk</groupId>
        <artifactId>s3</artifactId>
    </dependency>
</dependencies>
```

### Configuration
```yaml
server:
  port: 9103

spring:
  application:
    name: pigx-cloud-oss
  servlet:
    multipart:
      max-file-size: 100MB
      max-request-size: 100MB

herodotus:
  oss:
    enabled: true
    dialect: MINIO  # or S3
    endpoint: http://localhost:9000
    access-key: minioadmin
    secret-key: minioadmin
    bucket-name: pigx-cloud
```

### API Endpoints
```
# Upload
POST   /api/oss/upload          # Upload single file
POST   /api/oss/upload/multi    # Upload multiple files
POST   /api/oss/upload/chunk    # Chunk upload

# Download
GET    /api/oss/download/{id}   # Download file
GET    /api/oss/preview/{id}    # Preview file

# Management
GET    /api/oss/buckets         # List buckets
POST   /api/oss/buckets         # Create bucket
DELETE /api/oss/buckets/{name}  # Delete bucket

GET    /api/oss/objects         # List objects
DELETE /api/oss/objects/{id}    # Delete object

# Presigned URL
GET    /api/oss/presigned/upload   # Get upload URL
GET    /api/oss/presigned/download # Get download URL
```

### Database Tables
```sql
sys_oss_object
sys_oss_bucket
sys_oss_statistics
```

---

## 🔗 Service Dependencies

```
service-gateway (8080)
    ├─→ service-uaa (9100)
    ├─→ service-upms (9101)
    ├─→ service-message (9102)
    └─→ service-oss (9103)

service-uaa (9100)
    └─→ service-upms (9101)  # User info

service-upms (9101)
    └─→ service-uaa (9100)   # Token validation

service-message (9102)
    └─→ service-uaa (9100)   # Authentication

service-oss (9103)
    └─→ service-uaa (9100)   # Authentication

service-monitor (9200)
    └─→ All services (via Nacos)
```

---

## 🚀 Startup Order

**Development (Monolithic):**
```bash
# All in one JVM
cd cloud-monomer/monomer-application
mvn spring-boot:run
```

**Production (Microservices):**
```bash
# 1. Infrastructure
docker-compose up -d nacos postgres redis minio

# 2. Core Services (parallel)
cd cloud-services
mvn -pl service-uaa spring-boot:run &
mvn -pl service-upms spring-boot:run &

# 3. Gateway
mvn -pl service-gateway spring-boot:run &

# 4. Additional Services (parallel)
mvn -pl service-message spring-boot:run &
mvn -pl service-monitor spring-boot:run &
mvn -pl service-oss spring-boot:run &
```

---

## 📊 Resource Requirements

| Service | CPU | Memory | Storage | Notes |
|---------|-----|--------|---------|-------|
| service-uaa | 1 core | 512MB | 10GB | Auth server |
| service-upms | 1 core | 512MB | 10GB | User data |
| service-gateway | 0.5 core | 256MB | 1GB | Lightweight |
| service-message | 0.5 core | 512MB | 5GB | WebSocket |
| service-monitor | 0.5 core | 512MB | 5GB | Metrics |
| service-oss | 1 core | 512MB | 100GB+ | File storage |
| **Total** | **4.5 cores** | **2.8GB** | **131GB+** | Minimum |

---

## 🔐 Security Checklist

**service-uaa:**
- [ ] Password encryption (BCrypt)
- [ ] Token signing (RSA/HMAC)
- [ ] Rate limiting on login
- [ ] Captcha verification
- [ ] Session management
- [ ] Audit logging

**service-upms:**
- [ ] Permission validation
- [ ] Data scope control
- [ ] Tenant isolation
- [ ] Audit logging

**service-gateway:**
- [ ] OAuth2 token validation
- [ ] Rate limiting
- [ ] CORS configuration
- [ ] SQL injection prevention
- [ ] XSS protection

**service-message:**
- [ ] WebSocket authentication
- [ ] Message authorization
- [ ] Rate limiting

**service-oss:**
- [ ] File type validation
- [ ] Size limitation
- [ ] Access control
- [ ] Virus scanning (optional)

---

**Ready to build all 6 services! 🚀**

**Next**: Follow PIGX_CLOUD_BUILD_PLAN.md for detailed implementation guide.
