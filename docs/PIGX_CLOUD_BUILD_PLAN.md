# KẾ HOẠCH XÂY DỰNG PIGX CLOUD PLATFORM

**Ngày tạo**: 13/01/2026  
**Mục tiêu**: Xây dựng PigX Cloud - một microservice platform dựa trên pigx-engine (component library)  
**Template tham khảo**: Dante Cloud  
**Nguyên tắc**: Cấu trúc gọn gàng, ít module, clean architecture  
**Timeline**: 3-4 tháng  
**Team size**: 2-4 developers

---

## 📋 MỤC LỤC

1. [Kiến Trúc Tổng Quan](#kiến-trúc-tổng-quan)
2. [Cấu Trúc Project](#cấu-trúc-project)
3. [So Sánh Với Dante Cloud](#so-sánh-với-dante-cloud)
4. [Roadmap Chi Tiết](#roadmap-chi-tiết)
5. [Technology Stack](#technology-stack)

---

## 🏗️ KIẾN TRÚC TỔNG QUAN

### Quan Hệ Giữa Các Project

```
┌─────────────────────────────────────────────────────────────┐
│                      PIGX ECOSYSTEM                         │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  ┌──────────────────────┐         ┌───────────────────┐    │
│  │   pigx-engine        │────────>│   pigx-cloud      │    │
│  │  (Component Lib)     │ used by │   (Platform)      │    │
│  │                      │         │                   │    │
│  │ - engine-core        │         │ - services        │    │
│  │ - engine-web         │         │ - gateway         │    │
│  │ - engine-data        │         │ - starters        │    │
│  │ - engine-oauth2      │         │ - modules         │    │
│  │ - engine-cache       │         │                   │    │
│  │ - engine-oss         │         │                   │    │
│  │ - engine-starter     │         │                   │    │
│  └──────────────────────┘         └───────────────────┘    │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

### Nguyên Tắc Thiết Kế

1. **pigx-engine** = Foundation (Component Library)
   - Các module core, web, data, oauth2, cache, oss
   - Các Spring Boot starters
   - Được publish như Maven dependency
   - Version: 1.0-SNAPSHOT

2. **pigx-cloud** = Platform Application
   - Sử dụng pigx-engine như dependency
   - Chứa business services (UAA, UPMS, Gateway, etc.)
   - Deployment-ready applications
   - Clean và minimal structure

---

## 📦 CẤU TRÚC PROJECT

### Cấu Trúc pigx-cloud (Đề Xuất)

```
pigx-cloud/
├── pom.xml                          # Root aggregator
│
├── cloud-dependencies/              # Quản lý versions
│   └── pom.xml                      # BOM cho pigx-cloud
│
├── cloud-starters/                  # 🎯 Starters cho platform (đơn giản hóa)
│   ├── pom.xml
│   ├── platform-auth-starter/       # All-in-one: authentication + authorization
│   ├── platform-service-starter/    # Common cho các service
│   └── platform-gateway-starter/    # Gateway specific
│
├── cloud-modules/                   # 🎯 Shared modules (rất ít, chỉ cần thiết)
│   ├── pom.xml
│   ├── module-common/               # Constants, utils dùng chung
│   └── module-api/                  # API contracts, DTOs giữa services
│
├── cloud-services/                  # 🎯 Platform services (microservices)
│   ├── pom.xml
│   ├── service-uaa/                 # Authentication & Authorization Server
│   ├── service-upms/                # User Permission Management System
│   ├── service-gateway/             # API Gateway
│   ├── service-message/             # Message & WebSocket Service
│   ├── service-monitor/             # Spring Boot Admin Monitoring
│   └── service-oss/                 # Object Storage Service
│
├── cloud-monomer/                   # 🎯 Monolithic application
│   ├── pom.xml
│   └── monomer-application/         # All-in-one cho dev/small deployment
│
├── configurations/                  # Config files, Docker, K8s
│   ├── docker/
│   │   ├── docker-compose.yml
│   │   └── Dockerfile
│   ├── kubernetes/
│   │   └── *.yaml
│   ├── nacos/
│   │   └── config-*.yaml
│   └── scripts/
│       └── *.sh
│
├── docs/                            # Documentation
│   ├── README.md
│   ├── ARCHITECTURE.md
│   ├── DEPLOYMENT.md
│   └── API.md
│
└── README.md
```

### So Sánh Số Lượng Module

| Project | Dante Cloud | PigX Cloud (Đề xuất) | Lý do đơn giản hơn |
|---------|-------------|----------------------|-------------------|
| **Root modules** | 5 (dependencies, modules, packages, platform, services) | 5 (dependencies, starters, modules, services, monomer) | ✅ Tương đương |
| **Starters** | 6+ packages | 3 starters | 🎯 Merge nhiều starter thành ít starter hơn |
| **Shared modules** | 2 (common, monomer-autoconfigure) | 2 (common, api) | ✅ Tối thiểu |
| **Services** | 5+ services | 6 services | ✅ Đầy đủ như Dante Cloud |
| **Tổng modules** | ~20+ | ~17 | 🎯 Giảm ~15% (chỉ đơn giản hóa starters) |

---

## 🔄 SO SÁNH VỚI DANTE CLOUD

### Điểm Học Từ Dante Cloud

#### ✅ 1. Cấu Trúc Platform Services Rõ Ràng

**Dante Cloud:**
```
platform/
├── dante-cloud-uaa/          # Auth service
├── dante-cloud-upms/         # Permission service
├── dante-cloud-gateway/      # Gateway
├── dante-cloud-message/      # Message service
└── dante-cloud-monitor/      # Monitoring
```

**PigX Cloud (Giữ đầy đủ):**
```
cloud-services/
├── service-uaa/              # Auth (tích hợp OAuth2 từ pigx-engine)
├── service-upms/             # Permission
├── service-gateway/          # Gateway
├── service-message/          # Message & WebSocket
├── service-monitor/          # Monitoring (Spring Boot Admin)
└── service-oss/              # Object Storage
```

**Giữ nguyên:**
- ✅ Tất cả 6 services như Dante Cloud
- ✅ Không merge services
- 🎯 Chỉ đơn giản hóa starters (3 thay vì 6+)

#### ✅ 2. Profile-Based Configuration

**Học từ Dante Cloud:**
```yaml
# application.yml
spring:
  profiles:
    active: ${PROFILE:standalone}  # standalone/alibaba/tencent

---
# application-standalone.yml
spring:
  config:
    activate:
      on-profile: standalone
# No service discovery, direct connection

---
# application-alibaba.yml
spring:
  config:
    activate:
      on-profile: alibaba
    import:
      - nacos:pigx-common.yaml
      - nacos:pigx-database.yaml
  cloud:
    nacos:
      discovery:
        server-addr: ${NACOS_ADDR:localhost:8848}
```

**Áp dụng cho PigX Cloud:**
- ✅ Hỗ trợ 3 profiles: `standalone`, `alibaba`, `tencent`
- ✅ Dynamic config từ Nacos
- ✅ Easy switch giữa các profiles

#### ✅ 3. Packages Organization

**Dante Cloud:**
```
packages/
├── authentication-spring-boot-starter/
├── authorization-servlet-spring-boot-starter/
├── facility-spring-boot-starter/
├── rpc-client-uaa-spring-boot-starter/
└── rpc-server-upms-spring-boot-starter/
```

**PigX Cloud (Đơn giản hơn):**
```
cloud-starters/
├── platform-auth-starter/        # Merge authentication + authorization
├── platform-service-starter/     # Merge facility + common dependencies
└── platform-gateway-starter/     # Gateway specific
```

**Đơn giản hóa:**
- ✅ Merge authentication + authorization vào 1 starter
- ✅ Merge facility + common dependencies
- ✅ Không tách RPC client/server riêng (dùng OpenFeign standard)

#### ✅ 4. Monolithic Support

**Dante Cloud:**
```
services/
└── dante-monomer-application/
    ├── pom.xml (dependencies tất cả services)
    └── Application.java
```

**PigX Cloud:**
```
cloud-monomer/
└── monomer-application/
    ├── pom.xml
    └── MonomerApplication.java
```

**Áp dụng:**
- ✅ Một codebase, hai chế độ chạy (microservices vs monolithic)
- ✅ Profile-based switching
- ✅ Shared database trong monolithic mode

### Điểm Khác Biệt (PigX Cloud Tối Ưu)

| Feature | Dante Cloud | PigX Cloud | Khác biệt |
|---------|-------------|------------|-------|
| **Component Library** | dante-engine (external repo) | pigx-engine (existing) | ✅ Đã có sẵn, không cần tạo mới |
| **Service Count** | 5+ services | **6 services** | ✅ Đầy đủ như Dante Cloud |
| **Services** | uaa, upms, gateway, message, monitor | **Same + oss** | ✅ Tất cả services |
| **Starters** | 6+ packages | **3 starters** | 🎯 Merge để đơn giản (auth, service, gateway) |
| **Workflow Engine** | Camunda BPMN | ❌ Optional/Future | 🎯 Không cần ngay, thêm sau khi cần |
| **Infrastructure** | Alibaba + Tencent + Spring | **Alibaba + Spring** | 🎯 Tencent optional, focus Alibaba |
| **Complexity** | ~20 modules | **~17 modules** | 🎯 Giảm ~15% complexity |

---

## 🎯 TECHNOLOGY STACK

### Core Technologies (Từ pigx-engine)

```xml
<!-- Inherited from pigx-engine -->
<properties>
    <java.version>21</java.version>
    <spring-boot.version>3.5.9</spring-boot.version>
    <spring-cloud.version>2023.0.3</spring-cloud.version>
    <spring-cloud-alibaba.version>2023.0.1.2</spring-cloud-alibaba.version>
</properties>
```

### PigX Cloud Specific

```xml
<dependencies>
    <!-- PigX Engine (Component Library) -->
    <dependency>
        <groupId>com.pigx.engine</groupId>
        <artifactId>authentication-spring-boot-starter</artifactId>
        <version>1.0-SNAPSHOT</version>
    </dependency>
    <dependency>
        <groupId>com.pigx.engine</groupId>
        <artifactId>authorization-spring-boot-starter</artifactId>
        <version>1.0-SNAPSHOT</version>
    </dependency>
    <dependency>
        <groupId>com.pigx.engine</groupId>
        <artifactId>data-rdbms-spring-boot-starter</artifactId>
        <version>1.0-SNAPSHOT</version>
    </dependency>
    <dependency>
        <groupId>com.pigx.engine</groupId>
        <artifactId>cache-spring-boot-starter</artifactId>
        <version>1.0-SNAPSHOT</version>
    </dependency>
    <dependency>
        <groupId>com.pigx.engine</groupId>
        <artifactId>webmvc-spring-boot-starter</artifactId>
        <version>1.0-SNAPSHOT</version>
    </dependency>
    
    <!-- Spring Cloud -->
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-gateway</artifactId>
    </dependency>
    
    <!-- Service Discovery -->
    <dependency>
        <groupId>com.alibaba.cloud</groupId>
        <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
    </dependency>
    <dependency>
        <groupId>com.alibaba.cloud</groupId>
        <artifactId>spring-cloud-starter-alibaba-nacos-config</artifactId>
    </dependency>
    
    <!-- Observability -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-actuator</artifactId>
    </dependency>
    <dependency>
        <groupId>io.micrometer</groupId>
        <artifactId>micrometer-registry-prometheus</artifactId>
    </dependency>
</dependencies>
```

---

## 🗺️ ROADMAP CHI TIẾT

### Phase 1: Project Setup (1 tuần)

#### Day 1-2: Initialize Project Structure

```bash
# 1. Tạo project root
mkdir -p ~/Documents/projects/my/java/pigx-cloud
cd ~/Documents/projects/my/java/pigx-cloud

# 2. Initialize Git
git init
echo "target/" > .gitignore
echo ".idea/" >> .gitignore
echo "*.iml" >> .gitignore

# 3. Create root POM
cat > pom.xml << 'EOF'
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 
         http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <parent>
        <groupId>com.pigx</groupId>
        <artifactId>cloud-dependencies</artifactId>
        <version>1.0-SNAPSHOT</version>
        <relativePath>cloud-dependencies/pom.xml</relativePath>
    </parent>

    <artifactId>pigx-cloud</artifactId>
    <packaging>pom</packaging>
    <name>PigX Cloud Platform</name>
    <description>
        PigX Cloud - Enterprise Microservice Platform
        Built on top of pigx-engine component library
    </description>

    <properties>
        <java.version>21</java.version>
        <maven.compiler.source>${java.version}</maven.compiler.source>
        <maven.compiler.target>${java.version}</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        
        <!-- PigX Engine Version -->
        <pigx-engine.version>1.0-SNAPSHOT</pigx-engine.version>
    </properties>

    <modules>
        <module>cloud-dependencies</module>
        <module>cloud-starters</module>
        <module>cloud-modules</module>
        <module>cloud-services</module>
        <module>cloud-monomer</module>
    </modules>
</project>
EOF

# 4. Create module directories
mkdir -p cloud-dependencies
mkdir -p cloud-starters
mkdir -p cloud-modules
mkdir -p cloud-services
mkdir -p cloud-monomer
mkdir -p configurations/{docker,kubernetes,nacos,scripts}
mkdir -p docs

# 5. Create README
cat > README.md << 'EOF'
# PigX Cloud Platform

Enterprise-grade microservice platform built on pigx-engine.

## Features

- 🔐 OAuth2 Authentication & Authorization
- 👥 User Permission Management (RBAC)
- 🌐 API Gateway
- 📊 Spring Boot Admin Monitoring
- 🔄 Service Discovery (Nacos)
- 💾 Multi-tenant Support
- 🚀 One Codebase, Two Modes (Microservices + Monolithic)

## Quick Start

### Monolithic Mode (Development)

```bash
cd cloud-monomer/monomer-application
mvn spring-boot:run
```

### Microservices Mode (Production)

```bash
# Start infrastructure
docker-compose -f configurations/docker/docker-compose.yml up -d

# Start services
./configurations/scripts/start-services.sh
```

## Documentation

- [Architecture](docs/ARCHITECTURE.md)
- [Deployment Guide](docs/DEPLOYMENT.md)
- [API Documentation](docs/API.md)

## Technology Stack

- Java 21
- Spring Boot 3.5.9
- Spring Cloud 2023.0.3
- Spring Cloud Alibaba 2023.0.1.2
- PigX Engine 1.0-SNAPSHOT

## License

Apache License 2.0
EOF
```

#### Day 3-4: Create cloud-dependencies (BOM)

```bash
cd cloud-dependencies

cat > pom.xml << 'EOF'
<?xml version="1.0" encoding="UTF-8"?>
<project>
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.pigx</groupId>
    <artifactId>cloud-dependencies</artifactId>
    <version>1.0-SNAPSHOT</version>
    <packaging>pom</packaging>
    <name>PigX Cloud Dependencies</name>

    <properties>
        <java.version>21</java.version>
        
        <!-- Spring -->
        <spring-boot.version>3.5.9</spring-boot.version>
        <spring-cloud.version>2023.0.3</spring-cloud.version>
        <spring-cloud-alibaba.version>2023.0.1.2</spring-cloud-alibaba.version>
        
        <!-- PigX Engine -->
        <pigx-engine.version>1.0-SNAPSHOT</pigx-engine.version>
    </properties>

    <dependencyManagement>
        <dependencies>
            <!-- Spring Boot -->
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-dependencies</artifactId>
                <version>${spring-boot.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
            
            <!-- Spring Cloud -->
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>${spring-cloud.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
            
            <!-- Spring Cloud Alibaba -->
            <dependency>
                <groupId>com.alibaba.cloud</groupId>
                <artifactId>spring-cloud-alibaba-dependencies</artifactId>
                <version>${spring-cloud-alibaba.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
            
            <!-- PigX Engine Starters -->
            <dependency>
                <groupId>com.pigx.engine</groupId>
                <artifactId>authentication-spring-boot-starter</artifactId>
                <version>${pigx-engine.version}</version>
            </dependency>
            <dependency>
                <groupId>com.pigx.engine</groupId>
                <artifactId>authorization-spring-boot-starter</artifactId>
                <version>${pigx-engine.version}</version>
            </dependency>
            <dependency>
                <groupId>com.pigx.engine</groupId>
                <artifactId>data-rdbms-spring-boot-starter</artifactId>
                <version>${pigx-engine.version}</version>
            </dependency>
            <dependency>
                <groupId>com.pigx.engine</groupId>
                <artifactId>cache-spring-boot-starter</artifactId>
                <version>${pigx-engine.version}</version>
            </dependency>
            <dependency>
                <groupId>com.pigx.engine</groupId>
                <artifactId>webmvc-spring-boot-starter</artifactId>
                <version>${pigx-engine.version}</version>
            </dependency>
            <dependency>
                <groupId>com.pigx.engine</groupId>
                <artifactId>oss-spring-boot-starter</artifactId>
                <version>${pigx-engine.version}</version>
            </dependency>
            
            <!-- PigX Cloud Modules -->
            <dependency>
                <groupId>com.pigx</groupId>
                <artifactId>module-common</artifactId>
                <version>${project.version}</version>
            </dependency>
            <dependency>
                <groupId>com.pigx</groupId>
                <artifactId>module-api</artifactId>
                <version>${project.version}</version>
            </dependency>
            
            <!-- PigX Cloud Starters -->
            <dependency>
                <groupId>com.pigx</groupId>
                <artifactId>platform-auth-starter</artifactId>
                <version>${project.version}</version>
            </dependency>
            <dependency>
                <groupId>com.pigx</groupId>
                <artifactId>platform-service-starter</artifactId>
                <version>${project.version}</version>
            </dependency>
            <dependency>
                <groupId>com.pigx</groupId>
                <artifactId>platform-gateway-starter</artifactId>
                <version>${project.version}</version>
            </dependency>
        </dependencies>
    </dependencyManagement>
    
    <build>
        <pluginManagement>
            <plugins>
                <plugin>
                    <groupId>org.springframework.boot</groupId>
                    <artifactId>spring-boot-maven-plugin</artifactId>
                    <version>${spring-boot.version}</version>
                </plugin>
                <plugin>
                    <groupId>org.apache.maven.plugins</groupId>
                    <artifactId>maven-compiler-plugin</artifactId>
                    <version>3.11.0</version>
                    <configuration>
                        <source>${java.version}</source>
                        <target>${java.version}</target>
                        <encoding>UTF-8</encoding>
                    </configuration>
                </plugin>
            </plugins>
        </pluginManagement>
    </build>
</project>
EOF
```

#### Day 5: Create cloud-modules

**module-common:**

```bash
cd cloud-modules
mkdir -p module-common/src/main/java/com/pigx/cloud/common

cat > module-common/pom.xml << 'EOF'
<project>
    <modelVersion>4.0.0</modelVersion>
    
    <parent>
        <groupId>com.pigx</groupId>
        <artifactId>cloud-modules</artifactId>
        <version>1.0-SNAPSHOT</version>
    </parent>
    
    <artifactId>module-common</artifactId>
    <name>PigX Cloud Module Common</name>
    
    <dependencies>
        <!-- Lombok -->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <scope>provided</scope>
        </dependency>
    </dependencies>
</project>
EOF

# Create ServiceNameConstants
cat > module-common/src/main/java/com/pigx/cloud/common/ServiceNameConstants.java << 'EOF'
package com.pigx.cloud.common;

/**
 * Service name constants for PigX Cloud
 */
public interface ServiceNameConstants {
    
    /**
     * UAA Service - Authentication & Authorization
     */
    String SERVICE_UAA = "pigx-cloud-uaa";
    
    /**
     * UPMS Service - User Permission Management
     */
    String SERVICE_UPMS = "pigx-cloud-upms";
    
    /**
     * Gateway Service - API Gateway
     */
    String SERVICE_GATEWAY = "pigx-cloud-gateway";
    
    /**
     * Admin Service - Monitoring & Management
     */
    String SERVICE_ADMIN = "pigx-cloud-admin";
    
    /**
     * OSS Service - Object Storage (Optional)
     */
    String SERVICE_OSS = "pigx-cloud-oss";
}
EOF
```

**module-api:**

```bash
mkdir -p module-api/src/main/java/com/pigx/cloud/api

cat > module-api/pom.xml << 'EOF'
<project>
    <modelVersion>4.0.0</modelVersion>
    
    <parent>
        <groupId>com.pigx</groupId>
        <artifactId>cloud-modules</artifactId>
        <version>1.0-SNAPSHOT</version>
    </parent>
    
    <artifactId>module-api</artifactId>
    <name>PigX Cloud Module API</name>
    <description>API contracts, DTOs between services</description>
    
    <dependencies>
        <dependency>
            <groupId>com.pigx</groupId>
            <artifactId>module-common</artifactId>
        </dependency>
        
        <!-- Validation -->
        <dependency>
            <groupId>jakarta.validation</groupId>
            <artifactId>jakarta.validation-api</artifactId>
        </dependency>
        
        <!-- Lombok -->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <scope>provided</scope>
        </dependency>
    </dependencies>
</project>
EOF

# Create sample DTO
cat > module-api/src/main/java/com/pigx/cloud/api/UserDTO.java << 'EOF'
package com.pigx.cloud.api;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;

/**
 * User DTO for inter-service communication
 */
@Data
public class UserDTO implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String userId;
    
    @NotBlank(message = "Username is required")
    private String username;
    
    private String email;
    
    private String tenantId;
}
EOF
```

**Deliverables Week 1:**
- [ ] Project structure created
- [ ] Root POM configured
- [ ] cloud-dependencies (BOM) ready
- [ ] cloud-modules created (common + api)
- [ ] README.md written

---

### Phase 2: Platform Starters (1 tuần)

#### Week 2: Create Simplified Starters

**Starter 1: platform-auth-starter** (Merge authentication + authorization)

```bash
cd cloud-starters
mkdir -p platform-auth-starter/src/main/{java,resources}

cat > platform-auth-starter/pom.xml << 'EOF'
<project>
    <modelVersion>4.0.0</modelVersion>
    
    <parent>
        <groupId>com.pigx</groupId>
        <artifactId>cloud-starters</artifactId>
        <version>1.0-SNAPSHOT</version>
    </parent>
    
    <artifactId>platform-auth-starter</artifactId>
    <name>Platform Auth Starter</name>
    <description>All-in-one authentication and authorization starter</description>
    
    <dependencies>
        <!-- PigX Engine OAuth2 -->
        <dependency>
            <groupId>com.pigx.engine</groupId>
            <artifactId>authentication-spring-boot-starter</artifactId>
        </dependency>
        <dependency>
            <groupId>com.pigx.engine</groupId>
            <artifactId>authorization-spring-boot-starter</artifactId>
        </dependency>
        
        <!-- Spring Security -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-security</artifactId>
        </dependency>
        
        <!-- Spring Boot Web -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
    </dependencies>
</project>
EOF
```

**Starter 2: platform-service-starter** (Common dependencies)

```bash
mkdir -p platform-service-starter/src/main/{java,resources}

cat > platform-service-starter/pom.xml << 'EOF'
<project>
    <modelVersion>4.0.0</modelVersion>
    
    <parent>
        <groupId>com.pigx</groupId>
        <artifactId>cloud-starters</artifactId>
        <version>1.0-SNAPSHOT</version>
    </parent>
    
    <artifactId>platform-service-starter</artifactId>
    <name>Platform Service Starter</name>
    <description>Common dependencies for all platform services</description>
    
    <dependencies>
        <!-- PigX Cloud Modules -->
        <dependency>
            <groupId>com.pigx</groupId>
            <artifactId>module-common</artifactId>
        </dependency>
        <dependency>
            <groupId>com.pigx</groupId>
            <artifactId>module-api</artifactId>
        </dependency>
        
        <!-- PigX Engine -->
        <dependency>
            <groupId>com.pigx.engine</groupId>
            <artifactId>webmvc-spring-boot-starter</artifactId>
        </dependency>
        <dependency>
            <groupId>com.pigx.engine</groupId>
            <artifactId>data-rdbms-spring-boot-starter</artifactId>
        </dependency>
        <dependency>
            <groupId>com.pigx.engine</groupId>
            <artifactId>cache-spring-boot-starter</artifactId>
        </dependency>
        
        <!-- Service Discovery -->
        <dependency>
            <groupId>com.alibaba.cloud</groupId>
            <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
        </dependency>
        <dependency>
            <groupId>com.alibaba.cloud</groupId>
            <artifactId>spring-cloud-starter-alibaba-nacos-config</artifactId>
        </dependency>
        
        <!-- OpenFeign -->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-openfeign</artifactId>
        </dependency>
        
        <!-- Actuator -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>
        
        <!-- Micrometer -->
        <dependency>
            <groupId>io.micrometer</groupId>
            <artifactId>micrometer-registry-prometheus</artifactId>
        </dependency>
    </dependencies>
</project>
EOF
```

**Starter 3: platform-gateway-starter**

```bash
mkdir -p platform-gateway-starter/src/main/{java,resources}

cat > platform-gateway-starter/pom.xml << 'EOF'
<project>
    <modelVersion>4.0.0</modelVersion>
    
    <parent>
        <groupId>com.pigx</groupId>
        <artifactId>cloud-starters</artifactId>
        <version>1.0-SNAPSHOT</version>
    </parent>
    
    <artifactId>platform-gateway-starter</artifactId>
    <name>Platform Gateway Starter</name>
    
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
        
        <!-- Load Balancer -->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-loadbalancer</artifactId>
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
        
        <!-- Redis for rate limiting -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-redis-reactive</artifactId>
        </dependency>
    </dependencies>
</project>
EOF
```

**Deliverables Week 2:**
- [ ] 3 platform starters created
- [ ] Dependencies configured
- [ ] Autoconfiguration classes ready

---

### Phase 3: Core Services (4 tuần)

#### Week 3: service-uaa (Authentication & Authorization)

```bash
cd cloud-services
mkdir -p service-uaa/src/main/{java,resources}

# Application class
cat > service-uaa/src/main/java/com/pigx/cloud/uaa/UaaApplication.java << 'EOF'
package com.pigx.cloud.uaa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * UAA Service - Authentication & Authorization
 * 
 * <p>Provides:
 * - OAuth2 Authorization Server
 * - User authentication
 * - Token issuance and validation
 * - Social login integration
 */
@SpringBootApplication
@EnableDiscoveryClient
public class UaaApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(UaaApplication.class, args);
    }
}
EOF

# Configuration
cat > service-uaa/src/main/resources/application.yml << 'EOF'
server:
  port: 9100

spring:
  application:
    name: pigx-cloud-uaa
  profiles:
    active: ${PROFILE:standalone}

---
# Standalone Mode (Development)
spring:
  config:
    activate:
      on-profile: standalone

herodotus:
  platform:
    architecture: MICROSERVICES
    
  oauth2:
    authorization:
      token-format: JWT
      
datasource:
  url: jdbc:postgresql://localhost:5432/pigx_uaa
  username: postgres
  password: postgres

---
# Alibaba Nacos Mode (Production)
spring:
  config:
    activate:
      on-profile: alibaba
    import:
      - nacos:pigx-cloud-common.yaml?group=common
      - nacos:pigx-cloud-database.yaml?group=common
      - nacos:${spring.application.name}.yaml?group=service
  cloud:
    nacos:
      discovery:
        server-addr: ${NACOS_ADDR:localhost:8848}
      config:
        server-addr: ${NACOS_ADDR:localhost:8848}
        
logging:
  level:
    root: INFO
    com.pigx: DEBUG
    org.springframework.security: INFO
EOF

# POM
cat > service-uaa/pom.xml << 'EOF'
<project>
    <modelVersion>4.0.0</modelVersion>
    
    <parent>
        <groupId>com.pigx</groupId>
        <artifactId>cloud-services</artifactId>
        <version>1.0-SNAPSHOT</version>
    </parent>
    
    <artifactId>service-uaa</artifactId>
    <name>PigX Cloud UAA Service</name>
    
    <dependencies>
        <!-- Platform Starters -->
        <dependency>
            <groupId>com.pigx</groupId>
            <artifactId>platform-auth-starter</artifactId>
        </dependency>
        <dependency>
            <groupId>com.pigx</groupId>
            <artifactId>platform-service-starter</artifactId>
        </dependency>
        
        <!-- PostgreSQL -->
        <dependency>
            <groupId>org.postgresql</groupId>
            <artifactId>postgresql</artifactId>
            <scope>runtime</scope>
        </dependency>
    </dependencies>
    
    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
</project>
EOF

# Test
cd service-uaa
mvn clean package
mvn spring-boot:run
```

#### Week 4: service-upms (User Permission Management)

```bash
cd cloud-services
mkdir -p service-upms/src/main/{java,resources}

# Similar structure như service-uaa
# Port: 9101
# Database: pigx_upms
```

#### Week 5: service-gateway (API Gateway)

```bash
cd cloud-services
mkdir -p service-gateway/src/main/{java,resources}

cat > service-gateway/src/main/java/com/pigx/cloud/gateway/GatewayApplication.java << 'EOF'
package com.pigx.cloud.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * API Gateway
 */
@SpringBootApplication
@EnableDiscoveryClient
public class GatewayApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }
}
EOF

cat > service-gateway/src/main/resources/application.yml << 'EOF'
server:
  port: 8080

spring:
  application:
    name: pigx-cloud-gateway
  cloud:
    gateway:
      routes:
        - id: service-uaa
          uri: lb://pigx-cloud-uaa
          predicates:
            - Path=/api/uaa/**
          filters:
            - StripPrefix=2
            
        - id: service-upms
          uri: lb://pigx-cloud-upms
          predicates:
            - Path=/api/upms/**
          filters:
            - StripPrefix=2
EOF
```

#### Week 6: service-message (Message & WebSocket)

```bash
cd cloud-services
mkdir -p service-message/src/main/{java,resources}

cat > service-message/src/main/java/com/pigx/cloud/message/MessageApplication.java << 'EOF'
package com.pigx.cloud.message;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Message Service - WebSocket & Messaging
 */
@SpringBootApplication
@EnableDiscoveryClient
public class MessageApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(MessageApplication.class, args);
    }
}
EOF

cat > service-message/src/main/resources/application.yml << 'EOF'
server:
  port: 9102

spring:
  application:
    name: pigx-cloud-message

herodotus:
  message:
    websocket:
      enabled: true
      endpoint: /stomp/ws
      mode: MULTIPLE
EOF
```

#### Week 7: service-monitor (Spring Boot Admin)

```bash
cd cloud-services
mkdir -p service-monitor/src/main/{java,resources}

cat > service-monitor/src/main/java/com/pigx/cloud/monitor/MonitorApplication.java << 'EOF'
package com.pigx.cloud.monitor;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Monitor Service - Spring Boot Admin
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableAdminServer
public class MonitorApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(MonitorApplication.class, args);
    }
}
EOF

cat > service-monitor/src/main/resources/application.yml << 'EOF'
server:
  port: 9200

spring:
  application:
    name: pigx-cloud-monitor
  boot:
    admin:
      context-path: /admin
EOF

cat > service-monitor/pom.xml << 'EOF'
<project>
    <modelVersion>4.0.0</modelVersion>
    
    <parent>
        <groupId>com.pigx</groupId>
        <artifactId>cloud-services</artifactId>
        <version>1.0-SNAPSHOT</version>
    </parent>
    
    <artifactId>service-monitor</artifactId>
    <name>PigX Cloud Monitor Service</name>
    
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
    </dependencies>
</project>
EOF
```

#### Week 8: service-oss (Object Storage)

```bash
cd cloud-services
mkdir -p service-oss/src/main/{java,resources}

cat > service-oss/src/main/java/com/pigx/cloud/oss/OssApplication.java << 'EOF'
package com.pigx.cloud.oss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * OSS Service - Object Storage
 */
@SpringBootApplication
@EnableDiscoveryClient
public class OssApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(OssApplication.class, args);
    }
}
EOF

cat > service-oss/src/main/resources/application.yml << 'EOF'
server:
  port: 9103

spring:
  application:
    name: pigx-cloud-oss

herodotus:
  oss:
    enabled: true
    dialect: MINIO  # or S3
EOF

cat > service-oss/pom.xml << 'EOF'
<project>
    <modelVersion>4.0.0</modelVersion>
    
    <parent>
        <groupId>com.pigx</groupId>
        <artifactId>cloud-services</artifactId>
        <version>1.0-SNAPSHOT</version>
    </parent>
    
    <artifactId>service-oss</artifactId>
    <name>PigX Cloud OSS Service</name>
    
    <dependencies>
        <!-- Platform Service Starter -->
        <dependency>
            <groupId>com.pigx</groupId>
            <artifactId>platform-service-starter</artifactId>
        </dependency>
        
        <!-- PigX Engine OSS -->
        <dependency>
            <groupId>com.pigx.engine</groupId>
            <artifactId>oss-spring-boot-starter</artifactId>
        </dependency>
    </dependencies>
</project>
EOF
```

**Deliverables Phase 3:**
- [ ] service-uaa running (Week 3)
- [ ] service-upms running (Week 4)
- [ ] service-gateway routing correctly (Week 5)
- [ ] service-message handling WebSocket (Week 6)
- [ ] service-monitor monitoring all services (Week 7)
- [ ] service-oss managing object storage (Week 8)

---

### Phase 4: Monolithic Application & Deployment (3 tuần)

#### Week 9: cloud-monomer

```bash
cd cloud-monomer
mkdir -p monomer-application/src/main/{java,resources}

cat > monomer-application/src/main/java/com/pigx/cloud/monomer/MonomerApplication.java << 'EOF'
package com.pigx.cloud.monomer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * PigX Cloud Monolithic Application
 * 
 * All services in one JVM - suitable for development and small deployments
 */
@SpringBootApplication(scanBasePackages = {
    "com.pigx.cloud",
    "com.pigx.engine"
})
public class MonomerApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(MonomerApplication.class, args);
    }
}
EOF

cat > monomer-application/src/main/resources/application.yml << 'EOF'
server:
  port: 8080

spring:
  application:
    name: pigx-cloud-monomer

herodotus:
  platform:
    architecture: MONOLITHIC
    data-access-strategy: LOCAL

# All services share same database
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/pigx_cloud_monomer
    username: postgres
    password: postgres
EOF

cat > monomer-application/pom.xml << 'EOF'
<project>
    <modelVersion>4.0.0</modelVersion>
    
    <parent>
        <groupId>com.pigx</groupId>
        <artifactId>cloud-monomer</artifactId>
        <version>1.0-SNAPSHOT</version>
    </parent>
    
    <artifactId>monomer-application</artifactId>
    <name>PigX Cloud Monomer Application</name>
    
    <dependencies>
        <!-- All Platform Starters -->
        <dependency>
            <groupId>com.pigx</groupId>
            <artifactId>platform-auth-starter</artifactId>
        </dependency>
        <dependency>
            <groupId>com.pigx</groupId>
            <artifactId>platform-service-starter</artifactId>
        </dependency>
        
        <!-- Database -->
        <dependency>
            <groupId>org.postgresql</groupId>
            <artifactId>postgresql</artifactId>
        </dependency>
    </dependencies>
    
    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
</project>
EOF

# Build và test
cd monomer-application
mvn clean package
java -jar target/monomer-application-1.0-SNAPSHOT.jar
```

**Deliverables Week 9:**
- [ ] Monolithic application running
- [ ] All features working in single JVM
- [ ] Performance acceptable

#### Week 10: Docker & Kubernetes

**Docker Compose:**

```bash
cd configurations/docker

cat > docker-compose.yml << 'EOF'
version: '3.8'

services:
  # Nacos
  nacos:
    image: nacos/nacos-server:v2.3.0
    ports:
      - "8848:8848"
      - "9848:9848"
    environment:
      - MODE=standalone
      
  # PostgreSQL
  postgres:
    image: postgres:16-alpine
    ports:
      - "5432:5432"
    environment:
      - POSTGRES_PASSWORD=postgres
      
  # Redis
  redis:
    image: redis:7-alpine
    ports:
      - "6379:6379"
      
  # UAA Service
  service-uaa:
    build: ../../cloud-services/service-uaa
    ports:
      - "9100:9100"
    depends_on:
      - nacos
      - postgres
      - redis
    environment:
      - PROFILE=alibaba
      - NACOS_ADDR=nacos:8848
      
  # Gateway
  service-gateway:
    build: ../../cloud-services/service-gateway
    ports:
      - "8080:8080"
    depends_on:
      - nacos
      - service-uaa
EOF
```

#### Week 11: Documentation

**docs/ARCHITECTURE.md:**
```markdown
# PigX Cloud Architecture

## Overview

PigX Cloud is built on top of pigx-engine component library...

## Module Structure

...

## Deployment Modes

### Monolithic Mode
...

### Microservices Mode
...
```

**Deliverables Phase 4:**
- [ ] Monolithic application ready (Week 9)
- [ ] Docker Compose working (Week 10)
- [ ] Kubernetes manifests ready (Week 10)
- [ ] Documentation complete (Week 11)
- [ ] API docs generated (Week 11)

---

## 📊 SUCCESS METRICS

| Metric | Target | How to Measure |
|--------|--------|----------------|
| **Module Count** | < 15 modules | Count top-level modules |
| **Build Time** | < 2 mins | `mvn clean package -T4` |
| **Startup Time (Monomer)** | < 20 secs | Time to ApplicationReady |
| **Startup Time (Microservices)** | < 30 secs/service | Individual service startup |
| **Lines of Code** | < 10K LOC | cloc . |
| **Service Count** | 6 services | Count services in cloud-services/ |

---

## ✅ FINAL CHECKLIST

### Infrastructure
- [ ] Nacos running
- [ ] PostgreSQL running
- [ ] Redis running

### Services (Microservices Mode)
- [ ] service-uaa deployable (Port 9100)
- [ ] service-upms deployable (Port 9101)
- [ ] service-gateway routing correctly (Port 8080)
- [ ] service-message handling WebSocket (Port 9102)
- [ ] service-monitor monitoring all services (Port 9200)
- [ ] service-oss managing storage (Port 9103)

### Monolithic Mode
- [ ] monomer-application runs standalone
- [ ] All features work in single JVM
- [ ] Acceptable performance

### Documentation
- [ ] README.md complete
- [ ] ARCHITECTURE.md written
- [ ] DEPLOYMENT.md written
- [ ] API documentation generated

### Testing
- [ ] Unit tests passing
- [ ] Integration tests passing
- [ ] Load testing done

---

## 📞 NEXT STEPS

### Timeline Overview (11 tuần)

1. **Week 1**: Setup project structure ✅
2. **Week 2**: Create platform starters (3 starters)
3. **Week 3**: Build service-uaa (Auth)
4. **Week 4**: Build service-upms (Permission)
5. **Week 5**: Build service-gateway (Gateway)
6. **Week 6**: Build service-message (WebSocket)
7. **Week 7**: Build service-monitor (Monitoring)
8. **Week 8**: Build service-oss (Storage)
9. **Week 9**: Monolithic application
10. **Week 10**: Docker & Kubernetes
11. **Week 11**: Documentation & Polish

**Start Date**: TBD  
**Expected Completion**: ~3 months (11 weeks)  
**Team**: 2-4 developers  
**Status**: PLANNING

---

**This is your roadmap to build PigX Cloud Platform!**

**Last Updated**: 13/01/2026  
**Version**: 1.0
