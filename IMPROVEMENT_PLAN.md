# KẾ HOẠCH CẢI TIẾN TOÀN DIỆN - PIGX ENGINE

**Ngày phân tích**: 13/01/2026  
**Framework**: Spring Boot 3.5.7, Java 17  
**Kiến trúc**: Multi-module Maven, Multi-tenant, OAuth2, Microservices-ready

---

## 📋 MỤC LỤC

1. [Executive Summary](#executive-summary)
2. [Phân Tích Chi Tiết](#phân-tích-chi-tiết)
3. [Đề Xuất Cải Tiến](#đề-xuất-cải-tiến)
4. [Roadmap Triển Khai](#roadmap-triển-khai)
5. [Metrics & KPIs](#metrics--kpis)

---

## 🎯 EXECUTIVE SUMMARY

### Tổng Quan Findings

Sau khi phân tích toàn bộ codebase (1000+ files, 10 modules chính), đã xác định được:

- **🔴 Critical Issues**: 4 vấn đề nghiêm trọng cần fix ngay
- **🟠 High Priority**: 15 vấn đề quan trọng cần fix trong 2-4 tuần
- **🟡 Medium Priority**: 23 vấn đề cần cải tiến trong 1-2 tháng
- **🟢 Low Priority**: 12 cải tiến dài hạn

### Critical Issues (Cần Fix Ngay)

| # | Vấn Đề | Module | Impact | Effort |
|---|--------|--------|--------|--------|
| 1 | **OAuth2 Security Bypass** qua Feign header | oauth2-module-authorization | 🔴 Critical | 2-3 days |
| 2 | **Connection Leak** trong SchemaMultiTenantConnectionProvider | data-module-hibernate | 🔴 Critical | 1-2 days |
| 3 | **Race Condition** trong DatabaseMultiTenantConnectionProvider | data-module-tenant | 🔴 Critical | 2-3 days |
| 4 | **Plaintext Password Storage** trong SysTenantDataSource | data-module-tenant | 🔴 Critical | 3-5 days |

### Đối Chiếu Codebase (Đã Xác Nhận)

- `engine-oauth2/oauth2-module-authorization/src/main/java/com/pigx/engine/oauth2/authorization/servlet/ServletSecurityAuthorizationManager.java`: bypass khi có `X-Herodotus-From-In` header.
- `engine-data/data-module-hibernate/src/main/java/com/pigx/engine/data/hibernate/tenant/SchemaMultiTenantConnectionProvider.java`: `releaseConnection` không có `try/finally` trước khi `close`.
- `engine-data/data-module-tenant/src/main/java/com/pigx/engine/data/tenant/hibernate/DatabaseMultiTenantConnectionProvider.java`: `isDataSourceInit` không thread-safe.
- `engine-data/data-module-tenant/src/main/java/com/pigx/engine/data/tenant/entity/SysTenantDataSource.java`: field `password` lưu plaintext.

---

## 🔍 PHÂN TÍCH CHI TIẾT

### 1. ARCHITECTURE & DESIGN

#### 1.1 Module Dependencies

**Hiện trạng**:
```
engine-core (foundation)
    ↓
engine-web, engine-data, engine-cache, engine-oauth2
    ↓
engine-logic
    ↓
engine-rest
    ↓
engine-starter
```

**Vấn đề**:
- ✅ **Tốt**: Layered architecture rõ ràng, separation of concerns tốt
- ⚠️ **Cần cải thiện**: 
  - Một số circular dependencies tiềm ẩn giữa `web-module-servlet` và `oauth2-module-authorization`
  - `core-foundation` có quá nhiều responsibilities (context holders, crypto, utilities)

**Đề xuất**:
1. Tách `core-foundation` thành:
   - `core-context` (ServiceContextHolder, TenantContextHolder)
   - `core-crypto` (crypto processors)
   - `core-utils` (utilities)
2. Sử dụng Spring Modulith để enforce module boundaries
3. Tạo dependency graph visualization

#### 1.2 Design Patterns

**Patterns được sử dụng tốt**:
- ✅ Strategy Pattern (CryptoProcessor, MultiTenant approaches)
- ✅ Factory Pattern (MultiTenantDataSourceFactory, JetCacheCreateCacheFactory)
- ✅ Template Method (AbstractResponseHandler, AbstractStampManager)
- ✅ Builder Pattern (ErrorCodeMapperBuilder)

**Patterns cần cải thiện**:
- ⚠️ **Singleton Pattern**: Nhiều class dùng double-checked locking
  ```java
  // Current (verbose)
  private static volatile XssUtils INSTANCE;
  public static XssUtils getInstance() {
      if (INSTANCE == null) {
          synchronized (XssUtils.class) {
              if (INSTANCE == null) {
                  INSTANCE = new XssUtils();
              }
          }
      }
      return INSTANCE;
  }
  
  // Recommended: Enum Singleton hoặc Spring Bean
  @Component
  public class XssUtils {
      // Spring manages lifecycle
  }
  ```

---

### 2. SECURITY VULNERABILITIES

#### 2.1 🔴 CRITICAL: OAuth2 Authorization Bypass

**File**: `engine-oauth2/oauth2-module-authorization/src/main/java/com/pigx/engine/oauth2/authorization/servlet/ServletSecurityAuthorizationManager.java`

**Vấn đề**:
```java
String feignInnerFlag = HeaderUtils.getHerodotusFromIn(request);
if (StringUtils.isNotBlank(feignInnerFlag)) {
    log.trace("[PIGXD] |- Is feign inner invoke : [{}], Passed!", url);
    return new AuthorizationDecision(true);  // ❌ BYPASS!
}
```

**Exploit Scenario**:
```bash
# Attacker có thể bypass authentication bằng cách fake header
curl -H "X-Herodotus-From-In: fake-value" https://api.example.com/admin/users
```

**Fix**:
```java
// Solution 1: Validate Feign header với signed token
String feignInnerFlag = HeaderUtils.getHerodotusFromIn(request);
if (StringUtils.isNotBlank(feignInnerFlag)) {
    if (!feignTokenValidator.validate(feignInnerFlag, request)) {
        log.warn("[PIGXD] |- Invalid Feign token from [{}]", request.getRemoteAddr());
        return new AuthorizationDecision(false);
    }
    log.trace("[PIGXD] |- Valid feign inner invoke : [{}], Passed!", url);
    return new AuthorizationDecision(true);
}

// Solution 2: Use mTLS cho internal communication
// Solution 3: IP whitelist + signed JWT
```

#### 2.2 🔴 CRITICAL: Plaintext Password Storage

**File**: `engine-data/data-module-tenant/src/main/java/com/pigx/engine/data/tenant/entity/SysTenantDataSource.java`

**Vấn đề**:
- Tenant datasource passwords được lưu plaintext trong database
- Không có encryption at rest

**Fix**:
```java
@Entity
public class SysTenantDataSource {
    
    @Convert(converter = EncryptedStringConverter.class)
    private String password;  // Auto encrypt/decrypt
    
    // Or use Jasypt
    @Column(name = "password")
    private String encryptedPassword;
    
    public String getPassword() {
        return jasyptEncryptor.decrypt(encryptedPassword);
    }
}

// Converter implementation
@Converter
public class EncryptedStringConverter implements AttributeConverter<String, String> {
    
    @Autowired
    private SymmetricCryptoProcessor crypto;
    
    @Override
    public String convertToDatabaseColumn(String attribute) {
        return crypto.encrypt(attribute, getEncryptionKey());
    }
    
    @Override
    public String convertToEntityAttribute(String dbData) {
        return crypto.decrypt(dbData, getEncryptionKey());
    }
}
```

#### 2.3 🟠 HIGH: Silent Crypto Failure

**File**: `engine-web/web-module-servlet/src/main/java/com/pigx/engine/web/servlet/crypto/HttpCryptoProcessor.java`

**Vấn đề**:
```java
public String decrypt(String identity, String content) {
    try {
        // ... decrypt logic
        return result;
    } catch (Exception e) {
        log.warn("[PIGXD] |- Symmetric can not Decrypt content [{}], Skip!", content);
        return content;  // ❌ Returns unencrypted content on failure!
    }
}
```

**Security Impact**:
- Nếu decrypt fail, sensitive data được return plaintext
- Không có audit trail cho crypto failures
- Client không biết data không được encrypted

**Fix**:
```java
public String decrypt(String identity, String content) throws CryptoException {
    try {
        SecretKey secretKey = getSecretKey(identity);
        String result = symmetricCryptoProcessor.decrypt(content, secretKey.getSymmetricKey());
        log.debug("[PIGXD] |- Decrypt content successfully");
        return result;
    } catch (StampHasExpiredException e) {
        // Audit log
        auditLogger.logCryptoFailure(identity, "SESSION_EXPIRED", e);
        throw new SessionExpiredException("Session has expired", e);
    } catch (Exception e) {
        // Audit log
        auditLogger.logCryptoFailure(identity, "DECRYPT_FAILED", e);
        throw new CryptoException("Failed to decrypt content", e);
    }
}
```

#### 2.4 🟡 MEDIUM: Stack Overflow in Recursive Decrypt

**File**: `engine-web/web-module-servlet/src/main/java/com/pigx/engine/web/servlet/crypto/DecryptRequestBodyAdvice.java`

**Vấn đề**:
```java
private void decrypt(String sessionKey, JsonNode jsonNode) {
    if (jsonNode.isObject()) {
        Iterator<Map.Entry<String, JsonNode>> it = jsonNode.fields();
        while (it.hasNext()) {
            // ...
            decrypt(sessionKey, entry.getValue());  // ❌ No depth limit!
        }
    }
    if (jsonNode.isArray()) {
        for (JsonNode node : jsonNode) {
            decrypt(sessionKey, node);  // ❌ Recursive
        }
    }
}
```

**Attack Scenario**:
```json
// Deeply nested JSON có thể gây stack overflow
{
  "level1": {
    "level2": {
      "level3": {
        // ... 1000 levels deep
      }
    }
  }
}
```

**Fix**:
```java
private void decrypt(String sessionKey, JsonNode jsonNode) {
    decrypt(sessionKey, jsonNode, 0, MAX_DEPTH);
}

private void decrypt(String sessionKey, JsonNode jsonNode, int currentDepth, int maxDepth) {
    if (currentDepth >= maxDepth) {
        throw new CryptoException("JSON nesting depth exceeds maximum allowed: " + maxDepth);
    }
    
    if (jsonNode.isObject()) {
        Iterator<Map.Entry<String, JsonNode>> it = jsonNode.fields();
        while (it.hasNext()) {
            Map.Entry<String, JsonNode> entry = it.next();
            if (entry.getValue() instanceof TextNode t && entry.getValue().isValueNode()) {
                String value = httpCryptoProcessor.decrypt(sessionKey, t.asText());
                entry.setValue(new TextNode(value));
            }
            decrypt(sessionKey, entry.getValue(), currentDepth + 1, maxDepth);
        }
    }
    
    if (jsonNode.isArray()) {
        for (JsonNode node : jsonNode) {
            decrypt(sessionKey, node, currentDepth + 1, maxDepth);
        }
    }
}
```

---

### 3. CONCURRENCY & THREADING

#### 3.1 🔴 CRITICAL: Race Condition in DataSource Initialization

**File**: `engine-data/data-module-tenant/src/main/java/com/pigx/engine/data/tenant/hibernate/DatabaseMultiTenantConnectionProvider.java`

**Vấn đề**:
```java
private boolean isDataSourceInit = false;  // ❌ Not thread-safe!

@Override
protected DataSource selectDataSource(String tenantIdentifier) {
    if (!isDataSourceInit) {  // ❌ Race condition!
        initialize();
    }
    // ...
}
```

**Race Condition Scenario**:
```
Thread 1: Check isDataSourceInit = false
Thread 2: Check isDataSourceInit = false
Thread 1: Call initialize()
Thread 2: Call initialize()  // ❌ Duplicate initialization!
```

**Fix**:
```java
private final AtomicBoolean isDataSourceInit = new AtomicBoolean(false);
private final ReentrantLock initLock = new ReentrantLock();

@Override
protected DataSource selectDataSource(String tenantIdentifier) {
    if (!isDataSourceInit.get()) {
        initLock.lock();
        try {
            if (!isDataSourceInit.get()) {  // Double-check inside lock
                initialize();
                isDataSourceInit.set(true);
            }
        } finally {
            initLock.unlock();
        }
    }
    
    DataSource currentDataSource = dataSources.get(tenantIdentifier);
    if (ObjectUtils.isNotEmpty(currentDataSource)) {
        return currentDataSource;
    } else {
        log.warn("[PIGXD] |- Cannot found the dataSource for tenant [{}]", tenantIdentifier);
        return defaultDataSource;
    }
}

// Better: Use ConcurrentHashMap
private final ConcurrentMap<String, DataSource> dataSources = new ConcurrentHashMap<>();
```

#### 3.2 🟡 MEDIUM: Singleton Pattern Issues

**Files**: Multiple files sử dụng double-checked locking

**Vấn đề**:
- Verbose code
- Có thể có subtle bugs nếu không implement đúng
- Không cần thiết trong Spring context

**Fix**:
```java
// Before: Manual singleton
public class XssUtils {
    private static volatile XssUtils INSTANCE;
    
    public static XssUtils getInstance() {
        if (INSTANCE == null) {
            synchronized (XssUtils.class) {
                if (INSTANCE == null) {
                    INSTANCE = new XssUtils();
                }
            }
        }
        return INSTANCE;
    }
}

// After: Spring Bean
@Component
public class XssUtils {
    // Spring manages singleton lifecycle
    // Thread-safe by default
}

// Or: Enum Singleton (if really need non-Spring singleton)
public enum XssUtils {
    INSTANCE;
    
    public String sanitize(String input) {
        // ...
    }
}
```

#### 3.3 🟡 MEDIUM: TenantContextHolder Cleanup

**File**: `engine-core/core-foundation/src/main/java/com/pigx/engine/core/foundation/context/TenantContextHolder.java`

**Vấn đề**:
```java
private static final ThreadLocal<String> CURRENT_CONTEXT = new TransmittableThreadLocal<>();

public static void setTenantId(String tenantId) {
    CURRENT_CONTEXT.set(tenantId);
}

// ❌ No explicit cleanup method!
```

**Memory Leak Risk**:
- ThreadLocal không được cleanup trong thread pools
- TransmittableThreadLocal tốt hơn nhưng vẫn cần explicit cleanup

**Fix**:
```java
public class TenantContextHolder {
    
    private static final ThreadLocal<String> CURRENT_CONTEXT = new TransmittableThreadLocal<>();
    
    public static void setTenantId(String tenantId) {
        CURRENT_CONTEXT.set(tenantId);
    }
    
    public static String getTenantId() {
        return ObjectUtils.defaultIfNull(CURRENT_CONTEXT.get(), SystemConstants.TENANT_ID);
    }
    
    // ✅ Explicit cleanup
    public static void clear() {
        CURRENT_CONTEXT.remove();
    }
}

// Usage in interceptor
@Component
public class MultiTenantInterceptor implements HandlerInterceptor {
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String tenantId = extractTenantId(request);
        TenantContextHolder.setTenantId(tenantId);
        return true;
    }
    
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        TenantContextHolder.clear();  // ✅ Always cleanup
    }
}
```

---

### 4. RESOURCE MANAGEMENT

#### 4.1 🔴 CRITICAL: Connection Leak

**File**: `engine-data/data-module-hibernate/src/main/java/com/pigx/engine/data/hibernate/tenant/SchemaMultiTenantConnectionProvider.java`

**Vấn đề**:
```java
@Override
public void releaseConnection(String schema, Connection connection) throws SQLException {
    connection.setSchema(SystemConstants.TENANT_ID);  // ❌ If this fails...
    releaseAnyConnection(connection);  // ...connection never closed!
}
```

**Fix**:
```java
@Override
public void releaseConnection(String schema, Connection connection) throws SQLException {
    try {
        connection.setSchema(SystemConstants.TENANT_ID);
    } catch (SQLException e) {
        log.error("[PIGXD] |- Failed to reset schema, but will still close connection", e);
    } finally {
        releaseAnyConnection(connection);  // ✅ Always close
    }
}
```

#### 4.2 🟠 HIGH: DataSource Lifecycle Management

**File**: `engine-data/data-module-tenant/src/main/java/com/pigx/engine/data/tenant/service/MultiTenantDataSourceFactory.java`

**Vấn đề**:
- Tạo HikariDataSource mới cho mỗi tenant
- Không có mechanism để close datasources
- Memory leak khi tenant bị xóa

**Fix**:
```java
@Component
public class MultiTenantDataSourceFactory implements DisposableBean {
    
    private final ConcurrentMap<String, HikariDataSource> managedDataSources = new ConcurrentHashMap<>();
    
    @Autowired
    private SysTenantDataSourceRepository sysTenantDataSourceRepository;
    
    private HikariDataSource createDataSource(DataSource defaultDataSource, SysTenantDataSource sysTenantDataSource) {
        // ... existing creation logic
        HikariDataSource dataSource = new HikariDataSource(hikariConfig);
        
        // ✅ Track for lifecycle management
        managedDataSources.put(sysTenantDataSource.getTenantId(), dataSource);
        
        return dataSource;
    }
    
    public Map<String, DataSource> getAll(DataSource defaultDataSource) {
        List<SysTenantDataSource> sysTenantDataSources = sysTenantDataSourceRepository.findAll();
        if (CollectionUtils.isNotEmpty(sysTenantDataSources)) {
            return sysTenantDataSources.stream()
                .collect(Collectors.toMap(
                    SysTenantDataSource::getTenantId, 
                    value -> createDataSource(defaultDataSource, value)
                ));
        }
        return new HashMap<>();
    }
    
    // ✅ Close datasource when tenant removed
    public void removeTenant(String tenantId) {
        HikariDataSource dataSource = managedDataSources.remove(tenantId);
        if (dataSource != null) {
            dataSource.close();
            log.info("[PIGXD] |- Closed datasource for tenant [{}]", tenantId);
        }
    }
    
    // ✅ Cleanup on shutdown
    @Override
    public void destroy() {
        log.info("[PIGXD] |- Closing all managed datasources...");
        managedDataSources.values().forEach(HikariDataSource::close);
        managedDataSources.clear();
    }
}
```

#### 4.3 🟡 MEDIUM: Cache Configuration

**Vấn đề**:
- Nhiều cache managers (Caffeine, Redis, JetCache) nhưng không rõ coordination
- Có thể có cache inconsistency issues

**Đề xuất**:
1. Document cache hierarchy rõ ràng:
   ```
   L1: Caffeine (local, fast)
   L2: Redis (distributed, shared)
   L3: JetCache (unified API)
   ```

2. Implement cache eviction strategy:
   ```java
   @Configuration
   public class CacheEvictionConfiguration {
       
       @Autowired
       private CaffeineCacheManager caffeineCacheManager;
       
       @Autowired
       private RedisCacheManager redisCacheManager;
       
       @EventListener
       public void onCacheEviction(CacheEvictionEvent event) {
           // Evict from all levels
           caffeineCacheManager.getCache(event.getCacheName()).evict(event.getKey());
           redisCacheManager.getCache(event.getCacheName()).evict(event.getKey());
       }
   }
   ```

---

### 5. CODE QUALITY

#### 5.1 🟡 MEDIUM: Converter Object Creation

**Files**: Multiple files trong `engine-oss`

**Vấn đề**:
```java
public ListObjectsDomain listObjects(ListObjectsRequest request) {
    Converter<ObjectListing, ListObjectsDomain> toDomain = new ObjectListingToDomainConverter();  // ❌ New object every call!
    ObjectListing objectListing = s3Client.listObjects(request);
    return toDomain.convert(objectListing);
}
```

**Fix**:
```java
@Component
public class S3ObjectRepository {
    
    private final Converter<ObjectListing, ListObjectsDomain> objectListingConverter;
    private final Converter<ListObjectsV2Result, ListObjectsV2Domain> listObjectsV2Converter;
    
    @Autowired
    public S3ObjectRepository(
            Converter<ObjectListing, ListObjectsDomain> objectListingConverter,
            Converter<ListObjectsV2Result, ListObjectsV2Domain> listObjectsV2Converter) {
        this.objectListingConverter = objectListingConverter;
        this.listObjectsV2Converter = listObjectsV2Converter;
    }
    
    public ListObjectsDomain listObjects(ListObjectsRequest request) {
        ObjectListing objectListing = s3Client.listObjects(request);
        return objectListingConverter.convert(objectListing);  // ✅ Reuse
    }
}
```

#### 5.2 🟡 MEDIUM: Error Handling Consistency

**Vấn đề**:
- Một số nơi catch Exception rồi return default value
- Một số nơi throw custom exception
- Không consistent

**Đề xuất**:
```java
// Define clear error handling strategy

// 1. For recoverable errors: Return Result<T>
public Result<User> getUser(String id) {
    try {
        User user = userRepository.findById(id);
        return Result.success(user);
    } catch (DataAccessException e) {
        log.error("Failed to get user", e);
        return Result.failure(ErrorCodes.DATABASE_ERROR);
    }
}

// 2. For unrecoverable errors: Throw exception
public void validateUser(User user) {
    if (user.getAge() < 18) {
        throw new ValidationException("User must be 18+");
    }
}

// 3. Never silently swallow exceptions
// BAD:
try {
    // ...
} catch (Exception e) {
    return null;  // ❌
}

// GOOD:
try {
    // ...
} catch (Exception e) {
    log.error("Operation failed", e);
    throw new BusinessException("Operation failed", e);  // ✅
}
```

#### 5.3 🟢 LOW: Code Documentation

**Vấn đề**:
- Nhiều class thiếu JavaDoc
- Comment chủ yếu bằng tiếng Trung
- Thiếu architecture decision records (ADRs)

**Đề xuất**:
1. Thêm JavaDoc cho public APIs:
   ```java
   /**
    * Manages multi-tenant datasource lifecycle.
    * 
    * <p>This factory creates and manages HikariCP datasources for each tenant.
    * Datasources are lazily initialized and cached for reuse.
    * 
    * <p><b>Thread Safety:</b> This class is thread-safe. All operations on the
    * internal datasource map are synchronized.
    * 
    * @author PigX Team
    * @since 1.0.0
    * @see SysTenantDataSource
    * @see DatabaseMultiTenantConnectionProvider
    */
   @Component
   public class MultiTenantDataSourceFactory implements DisposableBean {
       // ...
   }
   ```

2. Tạo ADRs cho các quyết định quan trọng:
   ```markdown
   # ADR-001: Multi-Tenant Strategy
   
   ## Status
   Accepted
   
   ## Context
   System needs to support multiple tenants with different isolation levels.
   
   ## Decision
   Implement 3 strategies: DISCRIMINATOR, SCHEMA, DATABASE
   
   ## Consequences
   - Pros: Flexible, supports different use cases
   - Cons: Complex configuration, potential performance impact
   ```

---

### 6. PERFORMANCE

#### 6.1 🟠 HIGH: N+1 Query Problem

**Vấn đề**: Có thể có N+1 queries trong relationships

**Kiểm tra**:
```bash
# Enable query logging
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
logging.level.org.hibernate.SQL=DEBUG
logging.level.org.hibernate.type.descriptor.sql.BasicBinder=TRACE
```

**Fix**:
```java
// Use @EntityGraph hoặc JOIN FETCH

@EntityGraph(attributePaths = {"roles", "permissions"})
@Query("SELECT u FROM User u WHERE u.id = :id")
Optional<User> findByIdWithRoles(@Param("id") String id);

// Or
@Query("SELECT u FROM User u LEFT JOIN FETCH u.roles WHERE u.id = :id")
Optional<User> findByIdWithRoles(@Param("id") String id);
```

#### 6.2 🟡 MEDIUM: Cache Warming

**Đề xuất**:
```java
@Component
public class CacheWarmingService {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private ConfigService configService;
    
    @EventListener(ApplicationReadyEvent.class)
    public void warmUpCaches() {
        log.info("[PIGXD] |- Starting cache warm-up...");
        
        // Warm up frequently accessed data
        CompletableFuture.runAsync(() -> {
            configService.getAllConfigs();  // Cached
            userService.getActiveUsers();   // Cached
        });
        
        log.info("[PIGXD] |- Cache warm-up completed");
    }
}
```

#### 6.3 🟡 MEDIUM: Connection Pool Tuning

**Đề xuất**:
```yaml
spring:
  datasource:
    hikari:
      # Tune based on load testing
      maximum-pool-size: 20
      minimum-idle: 5
      connection-timeout: 30000
      idle-timeout: 600000
      max-lifetime: 1800000
      
      # Performance tuning
      auto-commit: false
      connection-test-query: SELECT 1
      
      # Leak detection
      leak-detection-threshold: 60000
      
      # Monitoring
      register-mbeans: true
```

---

### 7. DEPENDENCIES

#### 7.1 🟡 MEDIUM: Unstable Dependency

**Vấn đề**:
```xml
<hutool.version>7.0.0-M2</hutool.version>  <!-- ❌ Milestone version! -->
```

**Risk**:
- M2 = Milestone 2 (not stable)
- API có thể thay đổi
- Bugs chưa được fix

**Fix**:
```xml
<!-- Option 1: Pin to latest stable GA version -->
<hutool.version>7.x.y</hutool.version>

<!-- Option 2: Use latest 6.8.x stable (if 7.x GA not acceptable) -->
<hutool.version>6.8.z</hutool.version>

<!-- Option 3: Fork and maintain internally -->
```

#### 7.2 🟢 LOW: Dependency Convergence

**Đề xuất**: Enforce dependency convergence
```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-enforcer-plugin</artifactId>
    <executions>
        <execution>
            <id>enforce</id>
            <goals>
                <goal>enforce</goal>
            </goals>
            <configuration>
                <rules>
                    <dependencyConvergence/>
                    <requireMavenVersion>
                        <version>[3.8.0,)</version>
                    </requireMavenVersion>
                    <requireJavaVersion>
                        <version>[17,)</version>
                    </requireJavaVersion>
                </rules>
            </configuration>
        </execution>
    </executions>
</plugin>
```

---

## 💡 ĐỀ XUẤT CẢI TIẾN

### Priority Matrix

```
Impact ↑
│
│  🔴 P1: Fix Now        🟠 P2: High Priority
│  - OAuth2 Bypass       - N+1 Queries
│  - Connection Leak     - Singleton Pattern
│  - Race Conditions     - Hutool Dependency
│  - Password Storage    
│
│  🟡 P3: Medium         🟢 P4: Low Priority
│  - Cache Strategy      - Documentation
│  - Converter Reuse     - Code Style
│  - Error Handling      - Metrics
│
└─────────────────────────────────────→ Effort
```

### Cải Tiến Theo Module

#### engine-oauth2
1. **Fix OAuth2 bypass** với signed tokens hoặc mTLS
2. **Implement rate limiting** cho token endpoints
3. **Add security audit logging**

#### engine-data
1. **Fix connection leak** trong SchemaMultiTenantConnectionProvider
2. **Fix race condition** trong DatabaseMultiTenantConnectionProvider
3. **Implement datasource lifecycle** management
4. **Encrypt tenant passwords** at rest
5. **Add connection pool monitoring**

#### engine-web
1. **Fix silent crypto failure** - throw exceptions
2. **Add depth limit** cho recursive JSON decrypt
3. **Implement request/response size limits**
4. **Add crypto audit logging**

#### engine-cache
1. **Document cache hierarchy** và eviction strategy
2. **Implement cache warming** cho frequently accessed data
3. **Add cache metrics** (hit rate, eviction rate)

#### engine-core
1. **Refactor core-foundation** thành smaller modules
2. **Replace manual singletons** với Spring beans
3. **Add ThreadLocal cleanup** mechanisms

---

## 🗺️ ROADMAP TRIỂN KHAI

### Phase 1: Critical Fixes (1-2 tuần)

**Week 1**:
- [ ] Fix OAuth2 security bypass
- [ ] Fix connection leak trong SchemaMultiTenantConnectionProvider
- [ ] Fix race condition trong DatabaseMultiTenantConnectionProvider
- [ ] Add ThreadLocal cleanup

**Week 2**:
- [ ] Implement password encryption cho tenant datasources
- [ ] Fix silent crypto failure
- [ ] Add depth limit cho recursive JSON decrypt
- [ ] Implement datasource lifecycle management

**Deliverables**:
- Security patch release
- Updated security documentation
- Penetration test scope + environment readiness

### Phase 2: High Priority (2-4 tuần)

**Week 3-4**:
- [ ] Replace manual singletons với Spring beans
- [ ] Refactor converter object creation
- [ ] Downgrade Hutool to stable version
- [ ] Add connection pool monitoring

**Week 5-6**:
- [ ] Implement cache warming
- [ ] Fix N+1 query problems
- [ ] Add security audit logging
- [ ] Implement rate limiting

**Deliverables**:
- Performance improvement release
- Updated architecture documentation
- Load testing report
- Penetration test report

### Phase 3: Medium Priority (1-2 tháng)

**Month 2**:
- [ ] Refactor core-foundation module
- [ ] Document cache hierarchy
- [ ] Implement error handling consistency
- [ ] Add cache metrics

**Month 3**:
- [ ] Add comprehensive JavaDoc
- [ ] Create ADRs
- [ ] Implement dependency convergence
- [ ] Add integration tests

**Deliverables**:
- Code quality improvement release
- Complete API documentation
- Architecture decision records

### Phase 4: Long-term (3-6 tháng)

**Q2 2026**:
- [ ] Migrate to Spring Modulith
- [ ] Implement observability (OpenTelemetry)
- [ ] Add chaos engineering tests
- [ ] Performance optimization

**Q3 2026**:
- [ ] Implement blue-green deployment
- [ ] Add canary release capability
- [ ] Implement feature flags
- [ ] Complete test coverage (80%+)

**Deliverables**:
- Production-ready platform
- Complete observability
- Automated deployment pipeline

---

## 📊 METRICS & KPIs

### Security Metrics
- [ ] **Zero** critical vulnerabilities
- [ ] **100%** authentication bypass tests passed
- [ ] **< 1 hour** mean time to patch (MTTP)
- [ ] **100%** sensitive data encrypted

### Performance Metrics
- [ ] **< 100ms** P95 response time (core APIs)
- [ ] **< 1%** server error rate (5xx)
- [ ] **> 99.9%** uptime (monthly)
- [ ] **< 10** N+1 queries detected

### Code Quality Metrics
- [ ] **> 80%** test coverage
- [ ] **< 5%** code duplication
- [ ] **A** grade on SonarQube
- [ ] **Zero** critical code smells

### Operational Metrics
- [ ] **< 5 minutes** deployment time
- [ ] **< 15 minutes** rollback time
- [ ] **> 90%** critical paths automated
- [ ] **> 90%** documentation coverage

---

## 🔧 TOOLS & AUTOMATION

### Recommended Tools

1. **Security**:
   - OWASP Dependency Check
   - Snyk
   - SonarQube Security

2. **Performance**:
   - JMeter / Gatling
   - JProfiler / YourKit
   - Grafana + Prometheus

3. **Code Quality**:
   - SonarQube
   - SpotBugs
   - PMD / Checkstyle

4. **Testing**:
   - JUnit 5
   - Testcontainers
   - ArchUnit (architecture tests)

### CI/CD Pipeline

```yaml
# .github/workflows/ci.yml
name: CI/CD Pipeline

on: [push, pull_request]

jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v3
      
      - name: Set up JDK 17
        uses: actions/setup-java@v3
        with:
          java-version: '17'
          
      - name: Build with Maven
        run: mvn clean verify
        
      - name: Run Security Scan
        run: mvn org.owasp:dependency-check-maven:check
        
      - name: Run SonarQube Analysis
        run: mvn sonar:sonar
        
      - name: Run Integration Tests
        run: mvn verify -P integration-tests
        
      - name: Publish Test Results
        uses: EnricoMi/publish-unit-test-result-action@v2
```

---

## 📝 NOTES

### Ưu tiên tuyệt đối
1. **Security fixes** - không thể compromise
2. **Data integrity** - connection leaks, race conditions
3. **Stability** - error handling, resource management

### Cân nhắc khi implement
- **Backward compatibility**: Cần maintain API compatibility
- **Migration path**: Provide migration guides cho breaking changes
- **Performance impact**: Load test trước khi deploy
- **Documentation**: Update docs đồng thời với code changes

### Success Criteria
- ✅ All critical issues resolved
- ✅ Security audit passed
- ✅ Performance benchmarks met
- ✅ Code quality metrics achieved
- ✅ Documentation complete

---

**Tài liệu này sẽ được cập nhật định kỳ theo tiến độ implementation.**

**Last Updated**: 13/01/2026  
**Version**: 1.0  
**Maintainer**: PigX Engine Team
