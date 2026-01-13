## PigX Engine (pigx-engine) – Tài liệu tham khảo source code

Tài liệu này mô tả kiến trúc, module, cơ chế Spring Boot auto-config (starter), các prefix cấu hình `herodotus.*`, và các luồng chính (WebMVC, multi-tenant data, cache, OAuth2, message/websocket, OSS, rest/logic).

### 1) Tổng quan repo

- **Loại repo**: thư viện/framework dạng **Maven multi-module** + nhiều **Spring Boot starters** (không phải 1 ứng dụng chạy độc lập).
- **Java**: 17
- **Spring Boot BOM/Parent**: `dependencies/pom.xml` dùng `spring-boot-starter-parent` **3.5.7**
- **Root aggregator**: `pom.xml` gom các module:
  - `engine-core`, `engine-web`, `engine-data`, `engine-cache`, `engine-message`, `engine-oauth2`, `engine-oss`, `engine-rest`, `engine-logic`, `engine-starter`, `engine-assistant`

### 2) Quy ước cấu hình (prefix)

Tất cả cấu hình “nền tảng” được chuẩn hoá theo `herodotus.*` (xem `engine-core/core-definition/src/main/java/com/pigx/engine/core/definition/constant/BaseConstants.java`):

- `herodotus.reactive.*`
- `herodotus.assistant.*`
- `herodotus.cache.*`
- `herodotus.crypto.*`
- `herodotus.data.*`
- `herodotus.endpoint.*`
- `herodotus.log.*`
- `herodotus.message.*`
- `herodotus.oauth2.*`
- `herodotus.oss.*`
- `herodotus.platform.*`
- `herodotus.secure.*`
- `herodotus.service.*`

### 3) Bản đồ module (theo tầng)

### 3.1) `engine-core` – lõi (definition/foundation/autoconfigure/identity)

- **`core-definition`**:
  - constants/enums/utils chung
  - mô hình lỗi/feedback: `ErrorCodes`, `Feedback*`
  - wrapper response chuẩn: `Result<T>` (và `Response<T, Code>` bên dưới)
  - cơ chế map feedback → code: `ErrorCodeMapper` + `ErrorCodeMapperBuilder` + `ErrorCodeMapperBuilderCustomizer`
- **`core-foundation`**:
  - các implementation nền: context holders, crypto processors, utility nền
  - ví dụ: `ServiceContextHolder`, `TenantContextHolder`
- **`core-autoconfigure`**:
  - `@AutoConfiguration` cho core: Jackson/logging/error-code/crypto/session/oauth2 client&resource-server…
- **`core-identity`**:
  - các abstraction liên quan identity/security (ví dụ bearer token resolver…)

### 3.2) `engine-web` – web (core/service/servlet/api)

- **`web-core`**:
  - annotation/condition/constant/utils cho web
  - ví dụ: `@Crypto`, `@Idempotent`, `@AccessLimited`
- **`web-module-service`**:
  - dựng `ServiceContextHolder` (applicationName, endpoints…)
  - properties: `PlatformProperties`, `EndpointProperties`, `ServiceProperties`, `SecureProperties`
  - customizers: Jackson2 XSS, error-code mapping cho web
- **`web-module-servlet`**:
  - filter/interceptor/advice/resolver cho Servlet WebMVC:
    - multi-tenant interceptor
    - idempotent/access-limit interceptor
    - XSS filter
    - request decrypt / response encrypt
- **`web-module-api`**:
  - các base controller: paging/slice/jpa/mongo/writeable/readable…

### 3.3) `engine-data` – data access + multi-tenant

- `data-core`, `data-core-jpa`, `data-core-mongodb`: base repo/entity helpers
- `data-module-hibernate`: hibernate tenant resolver/provider (schema/discriminator)
- `data-module-tenant`: multi-tenant “orchestrator” (discriminator/schema/database) + datasource factory + entity `sys_tenant_datasource`

### 3.4) `engine-cache` – caching

- `cache-core`: `CacheProperties` + constants
- `cache-module-jetcache`: JetCache integration + cache manager factory
- `cache-module-redisson`: RedissonClient wiring (đọc `spring.data.redisson.*` và fallback `spring.data.redis.*`)
- `cache-module-redis`, `cache-module-caffeine`: remote/local cache layer

### 3.5) `engine-oauth2` – OAuth2

Tách 2 nhóm:
- **Authentication / Authorization Server** (phát token): `oauth2-module-authentication`, `oauth2-module-persistence-jpa`, `oauth2-module-extension`, `oauth2-core`
- **Authorization (Resource Server)** (bảo vệ API): `oauth2-authorization-autoconfigure` + core oauth2 autoconfigure

### 3.6) `engine-message` – message + websocket

- `message-core`: domain/event/strategy (ví dụ `RestMappingScanEventManager`)
- `message-autoconfigure`: auto-config cho message + stream adapter
- `message-module-websocket-servlet`: websocket/stomp config, interceptors, messaging template/adapter

### 3.7) `engine-oss` – object storage

- `oss-dialect`: chọn SDK dialect (Minio/S3) dựa trên `herodotus.oss.dialect`
- `oss-solution`: proxy presigned url (tránh FE gọi trực tiếp)
- `oss-rest`: REST integration/controllers cho OSS
- `oss-specification`: interfaces/spec layer

### 3.8) `engine-logic` & `engine-rest`

- `engine-logic`: business logic (upms/message/identity…)
- `engine-rest`: REST controllers/service bọc logic modules (upms/message/identity)

### 4) Starters & AutoConfiguration.imports (cách “nạp” tính năng)

Spring Boot 3 dùng file:

- `META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports`

để nạp các class auto-config khi bạn thêm dependency starter.

Danh sách starter chính (trong `engine-starter`) và class được import:

- **`cache-spring-boot-starter`**
  - `com.pigx.engine.cache.autoconfigure.CacheAutoConfiguration`
- **`captcha-spring-boot-starter`**
  - `com.pigx.engine.captcha.autoconfigure.CaptchaAutoConfiguration`
- **`data-rdbms-spring-boot-starter`**
  - `com.pigx.engine.data.rdbms.autoconfigure.DataJpaAutoConfiguration`
  - `com.pigx.engine.data.rdbms.autoconfigure.DataQueryDslAutoConfiguration`
  - `com.pigx.engine.data.rdbms.autoconfigure.DataMybatisPlusAutoConfiguration`
- **`data-mongodb-spring-boot-starter`**
  - `com.pigx.engine.data.mongodb.autoconfigure.DataMongodbAutoConfiguration`
- **`webmvc-spring-boot-starter`**
  - `com.pigx.engine.servlet.message.autoconfigure.WebMvcAutoConfiguration`
  - `com.pigx.engine.servlet.message.autoconfigure.WebMvcCryptoAutoConfiguration`
  - `com.pigx.engine.servlet.message.autoconfigure.WebMvcRestMappingScanConfiguration`
- **`servlet-container-spring-boot-starter`**
  - `com.pigx.engine.servlet.container.autoconfigure.ServletContainerAutoConfiguration`
- **`reactive-container-spring-boot-starter`**
  - `com.pigx.engine.reactive.container.autoconfigure.ReactiveContainerAutoConfiguration`
- **`servlet-message-spring-boot-starter`**
  - `com.pigx.engine.servlet.message.autoconfigure.ServletMessageAutoConfiguration`
- **`logging-spring-boot-starter`**
  - `com.pigx.engine.logging.autoconfigure.LoggingAutoConfiguration`
- **facility/gateway/kafka/tencent/alibaba**
  - `FacilityGatewayAutoConfiguration` / `FacilityKafkaAutoConfiguration` / `FacilityTencentAutoConfiguration` …
- **oss**
  - `OssAutoConfiguration` / `OssMinioAutoConfiguration` / `OssS3AutoConfiguration`

### 4.1) Ghi chú sửa lỗi starter (đã áp dụng trong repo)

Trong repo có 2 chỗ `.imports` bị sai khiến auto-config “gãy” khi sử dụng starter. Đã sửa:

- **Cache starter**: `CacheAutoConfiguration` sai package so với FQN trong `.imports`
  - File: `engine-starter/cache-spring-boot-starter/src/main/java/com/pigx/engine/cache.autoconfigure/CacheAutoConfiguration.java`
  - `.imports` trỏ tới: `com.pigx.engine.cache.autoconfigure.CacheAutoConfiguration`
- **WebMVC starter**: `.imports` trỏ tới class không tồn tại `WebMvcSecurityAutoConfiguration`
  - File: `engine-starter/webmvc-spring-boot-starter/src/main/resources/META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports`
  - Đã thay bằng `WebMvcRestMappingScanConfiguration`

### 5) Luồng WebMVC end-to-end

### 5.1) Khởi tạo service context

`WebServiceConfiguration` (module service) khởi tạo `ServiceContextHolder` bằng constructor để đảm bảo timing chính xác.

`ServiceContextHolder` giữ:
- applicationName
- ip/port/address/url
- kiến trúc (distributed/monolith)
- data access strategy (remote/local)
- endpoints OAuth2/OIDC (issuer + endpoints)
- uri/name của các service (uaa/upms/message/oss/gateway)

### 5.2) Tenant context (multi-tenant)

- `MultiTenantInterceptor` (Servlet MVC) đọc tenant từ header → set `TenantContextHolder`
- `TenantContextHolder` dùng `TransmittableThreadLocal` → propagate sang thread pool (TTL) khi cần

Kết nối sang data layer:
- `HerodotusTenantIdentifierResolver` (Hibernate) đọc tenant id từ `TenantContextHolder.getTenantId()`.

### 5.3) Idempotent & Access limit (annotation-driven)

Bạn đặt annotation lên controller method:
- `@Idempotent(expire = "PT5S")`
- `@AccessLimited(maxTimes = 10, duration = "PT30S")`

Interceptors:
- `IdempotentInterceptor` → `IdempotentHandler` + `IdempotentStampManager`
- `AccessLimitedInterceptor` → `AccessLimitedHandler` + `AccessLimitedStampManager`

Default config global nằm ở:
- `herodotus.secure.idempotent.expire`
- `herodotus.secure.accessLimited.maxTimes`
- `herodotus.secure.accessLimited.expire`

### 5.4) HTTP Crypto (request decrypt / response encrypt)

Annotation:
- `@Crypto(requestDecrypt = true, responseEncrypt = true)`

Thành phần chính:
- `HttpCryptoProcessor` (dùng asymmetric+symm crypto từ core, + ServerProperties)
- `DecryptRequestBodyAdvice` (decrypt request body)
- `DecryptRequestParamResolver` / `DecryptRequestParamMapResolver` (decrypt query params)
- `EncryptResponseBodyAdvice` (encrypt response body)

Lưu ý: encryption/decryption có phụ thuộc session header (xem `SessionUtils`, `Herodotus session header`).

### 5.5) XSS

- `XssHttpServletFilter` được tạo bean trong `SecureConfiguration`
- `Jackson2XssObjectMapperBuilderCustomizer` customizes Jackson để strip/escape XSS cho JSON string (tuỳ config)

### 6) Data + Multi-tenant (JPA/Mybatis/Mongo)

### 6.1) Starter RDBMS nạp gì?

Khi thêm `data-rdbms-spring-boot-starter`:
- `DataJpaAutoConfiguration`
  - import `DataTenantConfiguration`
  - bật `@EnableJpaAuditing`
- `DataQueryDslAutoConfiguration`
  - tạo `JPAQueryFactory(EntityManager)`
- `DataMybatisPlusAutoConfiguration`
  - tạo `MybatisPlusInterceptor` (pagination)
  - tạo `BlockAttackInnerInterceptor`
  - tạo `IdentifierGenerator` (custom)

### 6.2) Multi-tenant approach (3 chế độ)

Property:
- `herodotus.data.multi-tenant.approach` = `DISCRIMINATOR` | `SCHEMA` | `DATABASE`

#### DISCRIMINATOR (default)
- Dùng `CurrentTenantIdentifierResolver` để resolve tenant id.
- `DiscriminatorApproachConfiguration` tạo `HibernatePropertiesCustomizer` gắn `AvailableSettings.MULTI_TENANT_IDENTIFIER_RESOLVER`.

#### SCHEMA
- `SchemaMultiTenantConnectionProvider` set `Connection.setSchema(schemaTenantId)`
- cấu hình qua `HibernatePropertiesCustomizer` gắn `AvailableSettings.MULTI_TENANT_CONNECTION_PROVIDER`.

#### DATABASE (mỗi tenant 1 DB)
- `DatabaseMultiTenantConnectionProvider` chọn datasource theo tenant id.
- Datasource list load từ bảng `sys_tenant_datasource` (entity `SysTenantDataSource`)
- `MultiTenantDataSourceFactory` build `HikariDataSource` cho từng tenant.
- Tự dựng `LocalContainerEntityManagerFactoryBean` và **bắt buộc set `packagesToScan` thủ công** (comment trong code).

### 6.3) Starter MongoDB

`data-mongodb-spring-boot-starter` nạp `DataMongodbAutoConfiguration` và bật `@EnableMongoAuditing` (ở servlet context).

### 7) Cache (JetCache + Redis + Redisson)

### 7.1) Wiring từ starter

`cache-spring-boot-starter` nạp `CacheAutoConfiguration` và import:
- `CacheJetCacheConfiguration`
- `CacheRedissonConfiguration`

### 7.2) JetCache

- `CacheJetCacheConfiguration`:
  - bật `@EnableConfigurationProperties(CacheProperties)`
  - import `CacheCaffeineConfiguration` + `CacheRedisConfiguration`
  - tạo `JetCacheCreateCacheFactory`
  - tạo `HerodotusCacheManager` (primary)

### 7.3) Redisson

Properties:
- `spring.data.redisson.*` (enable + mode + config)
- fallback: `spring.data.redis.*`

`CacheRedissonConfiguration`:
- đọc config file (yaml)
- nếu không có, build config theo `single/sentinel/cluster` từ redis properties
- tạo `RedissonClient`

### 8) OAuth2 (Authentication Server + Resource Server)

### 8.1) Resource Server (bảo vệ API)

Properties:
- `herodotus.oauth2.authorization.tokenFormat` = `OPAQUE` | `JWT`
- `herodotus.oauth2.authorization.strict` = true/false
- `herodotus.oauth2.authorization.matcher.*`:
  - `staticResources`
  - `permitAll`
  - `hasAuthenticated`

`ServletOAuth2AuthorizationAutoConfiguration`:
- tạo `ServletOAuth2ResourceMatcherConfigurer`
- tạo `AuditorAware` (nếu có)
- tuỳ token format:
  - OPAQUE: tạo `HerodotusServletOpaqueTokenIntrospector` + resolver
  - JWT: tạo `HerodotusServletJwtTokenResolver`

Exception mapping:
- `SecurityGlobalExceptionHandler` map OAuth2/Security exceptions → `Result` theo `ErrorCodes`.

### 8.2) Authentication/Authorization Server (phát token)

`OAuth2AuthenticationAutoConfiguration` import:
- `OAuth2PersistenceSasJpaConfiguration` (JPA storage cho Spring Authorization Server)
- `OAuth2AuthenticationConfiguration`
- `OAuth2AuthenticationMessageConfiguration`

`OAuth2AuthenticationProperties` (`herodotus.oauth2.authentication.*`) chứa:
- sign-in failure limit
- endpoint limited (same device)
- kickout
- JWK / certificate / JKS info
- form-login (login page, params, captcha…)

### 8.3) Security metadata sync (UPMS) & RestMapping scan

Mục đích: tự động quét các `@RequestMapping` → tạo `RestMapping` list → publish local/remote events để đồng bộ permission/mapping.

Thành phần:
- `RestMappingScanner` (MVC): quét handler mappings → `RestMapping`
- `RestMappingScanEventManager`:
  - `isPerformScan()`:
    - distributed: chỉ scan nếu service có annotation marker
    - monolith: scan luôn
- `DefaultRestMappingScanEventManager` (oauth2 authorization autoconfigure):
  - marker annotation: `@EnableWebSecurity`
  - local process: `securityAttributeAnalyzer.processRequestMatchers()`
  - publish local `RestMappingGatherEvent` và remote `RemoteRestMappingGatherEvent` (Spring Cloud Bus)

### 9) Message + WebSocket

### 9.1) Message autoconfigure & Stream

- `MessageAutoConfiguration`: thêm error-code mapping cho message
- `StreamAutoConfiguration`: nếu có `StreamBridge` thì tạo adapter để gửi message qua Spring Cloud Stream

### 9.2) WebSocket servlet module

Enable:
- `@EnableHerodotusServletWebSocket` (được bật từ `ServletMessageAutoConfiguration`)

Properties:
- `herodotus.message.websocket.*` (xem `WebSocketProperties`):
  - mode: `SINGLE` / `MULTIPLE`
  - endpoint: mặc định `/stomp/ws`
  - principal header: mặc định `X_HERODOTUS_OPEN_ID`
  - prefixes, topic…

Module này chứa:
- broker configuration
- handshake interceptors (auth)
- channel interceptors
- message sender/adapter cho single/multiple instance
- controllers publish message

### 10) OSS (dialect + solution + rest)

### 10.1) Dialect autoconfigure

`OssDialectAutoConfiguration`:
- đọc `herodotus.oss.*` (`OssProperties`)
- chọn dialect: `herodotus.oss.dialect` (MINIO default hoặc S3)
- import `OssDialectMinioConfiguration` hoặc `OssDialectS3Configuration`

### 10.2) Solution (proxy presigned url)

`OssSolutionConfiguration`:
- tạo `OssPresignedUrlProxy`

`OssProxyProperties`:
- enabled/source/destination để cấu hình proxy

### 10.3) REST integration

- `OssRestMinioConfiguration`: scan service/controller cho minio REST
- `OssRestIntegrationConfiguration`: import `OssSolutionConfiguration`

### 11) engine-logic & engine-rest (business + REST)

Pattern phổ biến:
- **logic module** cung cấp service/business.
- **rest module** scan controller/service bọc logic module.

Ví dụ:
- `RestServletUpmsConfiguration`:
  - bật `@EnableHerodotusLogicUpms`
  - scan controller nhóm upms
  - tạo bean controller social theo condition (SMS/JustAuth/Wxapp)
- `RestServletMessageConfiguration`:
  - bật `@EnableHerodotusLogicMessage`
  - scan controller message
- `RestServletIdentityConfiguration`:
  - `@ConditionalOnClass(LogicIdentityConfiguration.class)` rồi scan identity controllers/services

### 12) Cấu hình mẫu (application.yml) – “khung” tham khảo

> Đây là khung tham khảo; bạn sẽ cần điều chỉnh theo kiến trúc (monolith/distributed), tokenFormat, endpoints thật sự.

```yaml
herodotus:
  platform:
    architecture: MONOCOQUE # hoặc DISTRIBUTED
    data-access-strategy: REMOTE
    protocol: HTTP
    swagger:
      enabled: true

  endpoint:
    gateway-service-uri: http://localhost:8080
    uaa-service-name: uaa
    upms-service-name: upms
    message-service-name: message
    oss-service-name: oss
    issuer-uri: http://localhost:8080 # Spring Authorization Server issuer

  service:
    scan:
      enabled: true
      just-scan-rest-controller: false
      # scan-group-ids: ["com.pigx.engine", "..."]

  secure:
    idempotent:
      expire: 5s
    access-limited:
      max-times: 10
      expire: 30s

  crypto:
    strategy: SM # hoặc STANDARD

  oauth2:
    authorization:
      token-format: OPAQUE # hoặc JWT
      strict: true
      matcher:
        permit-all:
          - /actuator/**
          - /swagger-ui/**
          - /v3/api-docs/**

spring:
  data:
    redis:
      host: localhost
      port: 6379
      database: 0
    redisson:
      enabled: false

  session:
    redis:
      flush-mode: on_save
      namespace: spring:session

  sql:
    init:
      platform: postgresql
```

### 13) “Chỗ để đọc tiếp” khi cần debug

- **Auto-config nạp/không nạp**:
  - các file `engine-starter/**/META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports`
  - các class `*AutoConfiguration` tương ứng
- **Service context & endpoints**:
  - `engine-web/web-module-service/.../WebServiceConfiguration`
  - `engine-core/core-foundation/.../ServiceContextHolder`
  - `engine-web/web-module-service/.../EndpointProperties`
- **Web request pipeline**:
  - `engine-starter/webmvc-spring-boot-starter/.../WebMvcAutoConfiguration`
  - `engine-web/web-module-servlet/.../secure/*Interceptor`
  - `engine-web/web-module-servlet/.../tenant/MultiTenantInterceptor`
  - `engine-web/web-module-servlet/.../crypto/*Advice/*Resolver`
- **Multi-tenant data**:
  - `engine-data/data-module-tenant/.../config/*ApproachConfiguration`
  - `engine-data/data-module-hibernate/.../tenant/*`
  - `engine-core/core-foundation/.../TenantContextHolder`
- **OAuth2**:
  - `engine-oauth2/oauth2-module-authentication/...`
  - `engine-oauth2/oauth2-module-persistence-jpa/...`
  - `engine-core/core-autoconfigure/oauth2/...`
  - `engine-oauth2/oauth2-authorization-autoconfigure/...`
- **Message/websocket**:
  - `engine-message/message-module-websocket-servlet/...`
  - `engine-web/web-module-servlet/.../initializer/RestMappingScanner`
- **OSS**:
  - `engine-oss/oss-dialect/dialect-autoconfigure/...`
  - `engine-oss/oss-solution/...`
  - `engine-oss/oss-rest/...`

