# TÓM TẮT KẾ HOẠCH CẢI TIẾN - PIGX ENGINE

> **Quick Reference Guide** - Xem [IMPROVEMENT_PLAN.md](./IMPROVEMENT_PLAN.md) để có chi tiết đầy đủ

---

## 🎯 CRITICAL ISSUES (Fix Ngay - 1-2 tuần)

### 🔴 Security (P0 - Highest Priority)

| Issue | File | Fix Time | Status |
|-------|------|----------|--------|
| **OAuth2 Bypass** via Feign header | `ServletSecurityAuthorizationManager.java` | 2-3 days | ⏳ TODO |
| **Plaintext Passwords** trong DB | `SysTenantDataSource.java` | 3-5 days | ⏳ TODO |
| **Silent Crypto Failure** | `HttpCryptoProcessor.java` | 1-2 days | ⏳ TODO |

**Impact**: Có thể bị exploit để bypass authentication và access sensitive data

### 🔴 Resource Management (P0)

| Issue | File | Fix Time | Status |
|-------|------|----------|--------|
| **Connection Leak** | `SchemaMultiTenantConnectionProvider.java` | 1-2 days | ⏳ TODO |
| **Race Condition** | `DatabaseMultiTenantConnectionProvider.java` | 2-3 days | ⏳ TODO |
| **DataSource Lifecycle** | `MultiTenantDataSourceFactory.java` | 2-3 days | ⏳ TODO |

**Impact**: Memory leaks, connection pool exhaustion, system instability

### 🟠 Code Safety (P1)

| Issue | File | Fix Time | Status |
|-------|------|----------|--------|
| **Stack Overflow Risk** | `DecryptRequestBodyAdvice.java` | 1 day | ⏳ TODO |
| **ThreadLocal Cleanup** | `TenantContextHolder.java` | 1 day | ⏳ TODO |

**Impact**: Application crashes, memory leaks

---

## 📋 QUICK FIX CHECKLIST

### Week 1 (Critical Security)
- [ ] Implement Feign token validation với signed JWT
- [ ] Add password encryption cho `SysTenantDataSource`
- [ ] Fix crypto error handling - throw exceptions thay vì return plaintext
- [ ] Add security audit logging

### Week 2 (Critical Stability)
- [ ] Fix connection leak với proper try-finally
- [ ] Fix race condition với `AtomicBoolean` + `ReentrantLock`
- [ ] Implement datasource lifecycle management
- [ ] Add depth limit cho recursive JSON decrypt
- [ ] Implement ThreadLocal cleanup trong interceptors

---

## 🔧 QUICK FIXES

### 1. OAuth2 Bypass Fix

```java
// File: ServletSecurityAuthorizationManager.java
String feignInnerFlag = HeaderUtils.getHerodotusFromIn(request);
if (StringUtils.isNotBlank(feignInnerFlag)) {
    // ✅ ADD: Validate token
    if (!feignTokenValidator.validate(feignInnerFlag, request)) {
        log.warn("[PIGXD] |- Invalid Feign token");
        return new AuthorizationDecision(false);
    }
    return new AuthorizationDecision(true);
}
```

### 2. Connection Leak Fix

```java
// File: SchemaMultiTenantConnectionProvider.java
@Override
public void releaseConnection(String schema, Connection connection) throws SQLException {
    try {
        connection.setSchema(SystemConstants.TENANT_ID);
    } catch (SQLException e) {
        log.error("Failed to reset schema", e);
    } finally {
        releaseAnyConnection(connection);  // ✅ Always close
    }
}
```

### 3. Race Condition Fix

```java
// File: DatabaseMultiTenantConnectionProvider.java
private final AtomicBoolean isDataSourceInit = new AtomicBoolean(false);
private final ReentrantLock initLock = new ReentrantLock();

@Override
protected DataSource selectDataSource(String tenantIdentifier) {
    if (!isDataSourceInit.get()) {
        initLock.lock();
        try {
            if (!isDataSourceInit.get()) {
                initialize();
                isDataSourceInit.set(true);
            }
        } finally {
            initLock.unlock();
        }
    }
    return dataSources.get(tenantIdentifier);
}
```

### 4. Password Encryption

```java
// File: SysTenantDataSource.java
@Entity
public class SysTenantDataSource {
    
    @Convert(converter = EncryptedStringConverter.class)
    private String password;  // ✅ Auto encrypt/decrypt
}

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

### 5. Crypto Error Handling

```java
// File: HttpCryptoProcessor.java
public String decrypt(String identity, String content) throws CryptoException {
    try {
        SecretKey secretKey = getSecretKey(identity);
        return symmetricCryptoProcessor.decrypt(content, secretKey.getSymmetricKey());
    } catch (StampHasExpiredException e) {
        auditLogger.logCryptoFailure(identity, "SESSION_EXPIRED", e);
        throw new SessionExpiredException("Session expired", e);  // ✅ Throw
    } catch (Exception e) {
        auditLogger.logCryptoFailure(identity, "DECRYPT_FAILED", e);
        throw new CryptoException("Decrypt failed", e);  // ✅ Don't return plaintext
    }
}
```

### 6. Stack Overflow Prevention

```java
// File: DecryptRequestBodyAdvice.java
private static final int MAX_JSON_DEPTH = 50;

private void decrypt(String sessionKey, JsonNode jsonNode) {
    decrypt(sessionKey, jsonNode, 0, MAX_JSON_DEPTH);
}

private void decrypt(String sessionKey, JsonNode jsonNode, int depth, int maxDepth) {
    if (depth >= maxDepth) {
        throw new CryptoException("JSON depth exceeds maximum: " + maxDepth);
    }
    // ... existing logic with depth + 1
}
```

### 7. ThreadLocal Cleanup

```java
// File: TenantContextHolder.java
public static void clear() {
    CURRENT_CONTEXT.remove();  // ✅ Add cleanup method
}

// File: MultiTenantInterceptor.java
@Override
public void afterCompletion(...) {
    TenantContextHolder.clear();  // ✅ Always cleanup
}
```

---

## 🟠 HIGH PRIORITY (2-4 tuần)

### Performance
- [ ] Fix N+1 query problems với `@EntityGraph`
- [ ] Implement cache warming
- [ ] Tune connection pool settings
- [ ] Add cache metrics

### Code Quality
- [ ] Replace manual singletons với Spring beans
- [ ] Refactor converter object creation (inject instead of new)
- [ ] Implement consistent error handling
- [ ] Add comprehensive logging

### Dependencies
- [ ] Downgrade Hutool từ 7.0.0-M2 → 6.8.1 (stable)
- [ ] Add dependency convergence enforcement
- [ ] Update vulnerable dependencies

---

## 🟡 MEDIUM PRIORITY (1-2 tháng)

### Architecture
- [ ] Refactor `core-foundation` → `core-context`, `core-crypto`, `core-utils`
- [ ] Document cache hierarchy và eviction strategy
- [ ] Implement Spring Modulith boundaries

### Documentation
- [ ] Add JavaDoc cho public APIs
- [ ] Create Architecture Decision Records (ADRs)
- [ ] Update README với setup instructions

### Testing
- [ ] Add integration tests với Testcontainers
- [ ] Implement architecture tests với ArchUnit
- [ ] Add load testing với Gatling

---

## 📊 SUCCESS METRICS

### Must Achieve (Week 2)
- ✅ **Zero** critical security vulnerabilities
- ✅ **Zero** connection leaks detected
- ✅ **Zero** race conditions in multi-tenant code
- ✅ All passwords encrypted at rest

### Should Achieve (Month 1)
- ✅ **< 100ms** P95 response time
- ✅ **> 80%** test coverage
- ✅ **< 5%** error rate
- ✅ All manual singletons replaced

### Nice to Have (Month 3)
- ✅ **A** grade on SonarQube
- ✅ **> 90%** documentation coverage
- ✅ Complete ADR documentation
- ✅ Automated performance testing

---

## 🚀 QUICK START

### 1. Setup Development Environment

```bash
# Clone repo
git clone <repo-url>
cd pigx-engine

# Build
mvn clean install -DskipTests

# Run tests
mvn test

# Run security scan
mvn org.owasp:dependency-check-maven:check
```

### 2. Apply Critical Fixes

```bash
# Create feature branch
git checkout -b fix/critical-security-issues

# Apply fixes from sections above
# Test thoroughly
mvn verify

# Commit and push
git commit -m "fix: critical security and stability issues"
git push origin fix/critical-security-issues
```

### 3. Verify Fixes

```bash
# Run security tests
mvn test -Dtest=SecurityTest

# Run integration tests
mvn verify -P integration-tests

# Check for connection leaks
mvn test -Dtest=ConnectionLeakTest

# Load test
mvn gatling:test
```

---

## 📞 SUPPORT

### Questions?
- 📧 Email: team@pigx.com
- 💬 Slack: #pigx-engine
- 📚 Docs: [IMPROVEMENT_PLAN.md](./IMPROVEMENT_PLAN.md)

### Emergency Issues?
- 🚨 Critical security: Immediately notify security team
- 🔥 Production down: Follow incident response playbook
- 📊 Performance degradation: Check monitoring dashboards

---

**Last Updated**: 13/01/2026  
**Version**: 1.0  
**Status**: 🟡 In Progress
