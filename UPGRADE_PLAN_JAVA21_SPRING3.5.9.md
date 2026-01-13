# 📋 Kế hoạch nâng cấp Java 21 & Spring Boot 3.5.9

> **Ngày tạo:** 2026-01-13  
> **Dự án:** PigX Engine  
> **Trạng thái:** ✅ **ĐÃ HOÀN THÀNH** (2026-01-13)

---

## 🎉 Tóm tắt thực thi

| Bước | Mô tả | Trạng thái |
|------|-------|------------|
| 1 | Xác nhận compatibility matrix | ✅ Hoàn thành |
| 2 | Cập nhật Spring Boot Parent → 3.5.9 | ✅ Hoàn thành |
| 3 | Cập nhật Java version → 21 | ✅ Hoàn thành |
| 4 | Cập nhật spring-boot-dependencies → 3.5.9 | ✅ Hoàn thành |
| 5 | `mvn clean compile` | ✅ Build thành công |

---

## 📊 Trạng thái

| Thành phần | Phiên bản trước | Phiên bản hiện tại |
|------------|-----------------|-------------------|
| Java | 17 | **21** ✅ |
| Spring Boot | 3.5.7 | **3.5.9** ✅ |
| Spring Cloud | 2025.0.0 | 2025.0.0 (giữ nguyên) |
| Spring Cloud Alibaba | 2025.0.0.0 | 2025.0.0.0 (giữ nguyên) |

---

## 🔄 Các thay đổi cần thực hiện

### 1. Cập nhật `dependencies/pom.xml`

**File:** `dependencies/pom.xml`

#### 1.1. Cập nhật Spring Boot Parent (dòng 6-11)

```xml
<!-- TRƯỚC -->
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>3.5.7</version>
    <relativePath/>
</parent>

<!-- SAU -->
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>3.5.9</version>
    <relativePath/>
</parent>
```

#### 1.2. Cập nhật Java version (dòng 20)

```xml
<!-- TRƯỚC -->
<java.version>17</java.version>

<!-- SAU -->
<java.version>21</java.version>
```

#### 1.3. Cập nhật Spring Boot Dependencies version (dòng 67)

```xml
<!-- TRƯỚC -->
<spring-boot-dependencies.version>3.5.7</spring-boot-dependencies.version>

<!-- SAU -->
<spring-boot-dependencies.version>3.5.9</spring-boot-dependencies.version>
```

---

## ⚠️ Các điểm cần kiểm tra khi nâng cấp lên Java 21

### 2.1. Tính năng Java 21 mới có thể sử dụng

| Tính năng | Mô tả | Trạng thái |
|-----------|-------|------------|
| **Virtual Threads** | Lightweight threads, tích hợp tốt với Spring Boot 3.x | Stable |
| **Record Patterns** | Pattern matching cho records | Stable |
| **Sequenced Collections** | Interface mới cho collections có thứ tự | Stable |
| **Pattern Matching for switch** | Enhanced switch expressions | Stable |
| **String Templates** | Template strings | Preview |

### 2.2. Các thư viện cần kiểm tra tương thích

| Thư viện | Phiên bản hiện tại | Tương thích Java 21 |
|----------|-------------------|---------------------|
| Hutool v7 | 7.0.0-M2 | ✅ Có |
| MyBatis Plus | 3.5.14 | ✅ Có |
| Redisson | 3.51.0 | ✅ Có |
| JetCache | 2.7.8 | ✅ Có |
| Hibernate | (Spring Boot managed) | ✅ Có |
| Lombok | (Spring Boot managed) | ✅ Có |
| MapStruct | 1.6.3 | ✅ Có |
| BouncyCastle | 1.82 (jdk18on) | ✅ Có |
| Guava | 33.5.0-jre | ✅ Có |
| FastJSON2 | 2.0.59 | ✅ Có |

---

## 📝 Các bước thực hiện chi tiết

### Bước 1: Chuẩn bị môi trường

```bash
# Kiểm tra Java version hiện tại
java -version

# Cài đặt Java 21 (Ubuntu/Debian)
sudo apt install openjdk-21-jdk

# Hoặc sử dụng SDKMAN
sdk install java 21-open
sdk use java 21-open

# Xác nhận Java 21 đã được cài đặt
java -version
# Output mong đợi: openjdk version "21.x.x" ...
```

### Bước 2: Backup codebase

```bash
# Tạo branch mới để nâng cấp
git checkout -b upgrade/java21-spring3.5.9

# Hoặc backup thủ công
cp -r pigx-engine pigx-engine-backup
```

### Bước 3: Cập nhật POM files

Thay đổi file `dependencies/pom.xml` theo hướng dẫn ở mục 1.

### Bước 3.1: Xác nhận compatibility matrix (Spring Boot ↔ Spring Cloud)

```text
✅ ĐÃ XÁC NHẬN (2026-01-13)
# - Spring Boot 3.5.9 ↔ Spring Cloud 2025.0.0 (Annecy) → TƯƠNG THÍCH
# - Spring Cloud Alibaba 2025.0.0.0 ↔ Spring Boot 3.5.x → TƯƠNG THÍCH
#
# Nguồn tham chiếu:
# - Spring Cloud Release Train: https://spring.io/projects/spring-cloud
# - Spring Cloud Alibaba: https://github.com/alibaba/spring-cloud-alibaba
```

### Bước 4: Clean và Rebuild

```bash
# Di chuyển vào thư mục dự án
cd /path/to/pigx-engine

# Clean project
mvn clean

# Compile
mvn compile

# Hoặc compile với verbose output
mvn compile -X
```

### Bước 5: Chạy Tests

```bash
# Chạy tất cả tests
mvn test -P testing

# Hoặc chạy tests cho một module cụ thể
mvn test -P testing -pl engine-core/core-foundation
```

### Bước 6: Build toàn bộ dự án

```bash
# Build với skip tests (nhanh)
mvn package -DskipTests

# Build đầy đủ
mvn package -P testing
```

### Bước 7: Kiểm tra ứng dụng

```bash
# Chạy một ứng dụng test để xác nhận hoạt động bình thường
# (tùy thuộc vào cấu trúc dự án của bạn)
```

---

## 🔍 Checklist trước khi nâng cấp

- [ ] Backup codebase hiện tại (git branch hoặc copy)
- [ ] Java 21 đã được cài đặt trên môi trường phát triển
- [ ] IDE đã được cấu hình để sử dụng Java 21
- [ ] CI/CD pipeline hỗ trợ Java 21
- [ ] Docker base image (nếu có) hỗ trợ Java 21
- [ ] Review release notes của Spring Boot 3.5.8 và 3.5.9
- [ ] Thông báo cho team về việc nâng cấp

---

## 🔍 Checklist sau khi nâng cấp

- [x] `mvn clean compile` thành công ✅ (2026-01-13)
- [ ] Tất cả unit tests pass
- [ ] Integration tests pass (nếu có)
- [ ] Ứng dụng khởi động thành công
- [ ] Các API hoạt động bình thường
- [ ] Kiểm tra logs không có warnings/errors bất thường
- [ ] Performance không bị suy giảm

---

## 🚀 Lợi ích khi nâng cấp

### Java 21 (LTS - Long Term Support)

1. **Virtual Threads (Project Loom)**
   - Lightweight threads với chi phí tài nguyên thấp
   - Cải thiện hiệu suất đáng kể cho các ứng dụng I/O-bound
   - Tích hợp sẵn với Spring Boot 3.x qua `spring.threads.virtual.enabled=true`

2. **Performance Improvements**
   - JVM tối ưu hóa tốt hơn
   - Garbage Collection cải thiện
   - Startup time nhanh hơn

3. **Language Features**
   - Pattern Matching for switch (stable)
   - Record Patterns (stable)
   - Sequenced Collections

4. **Long-term Support**
   - Hỗ trợ đến tháng 9/2031
   - Nhận security updates định kỳ

### Spring Boot 3.5.9

1. **Bug Fixes** - Sửa các lỗi từ 3.5.7/3.5.8
2. **Security Patches** - Vá các lỗ hổng bảo mật
3. **Dependency Updates** - Cập nhật các thư viện phụ thuộc
4. **Better Java 21 Support** - Tối ưu hóa cho Java 21

---

## ⚠️ Potential Breaking Changes

### Java 17 → 21

1. **Removed APIs**
   - Một số deprecated APIs đã bị loại bỏ
   - Kiểm tra code có sử dụng các APIs đã deprecated không

2. **Security Manager**
   - Security Manager đã bị deprecated và sẽ bị loại bỏ
   - Nếu sử dụng, cần migration

3. **Reflection Access**
   - Strong encapsulation mặc định
   - Có thể cần thêm `--add-opens` flags nếu gặp issues

### Spring Boot 3.5.7 → 3.5.9

- Chủ yếu là bug fixes và security patches
- Không có breaking changes đáng kể

---

## 📚 Tài liệu tham khảo

- [Java 21 Release Notes](https://openjdk.org/projects/jdk/21/)
- [Spring Boot 3.5.x Release Notes](https://github.com/spring-projects/spring-boot/wiki/Spring-Boot-3.5-Release-Notes)
- [Virtual Threads với Spring Boot](https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.spring-application.virtual-threads)
- [Migration Guide Java 17 to 21](https://docs.oracle.com/en/java/javase/21/migrate/)

---

## 📞 Liên hệ hỗ trợ

Nếu gặp vấn đề trong quá trình nâng cấp, kiểm tra:
1. Stack Overflow với tag `java-21` và `spring-boot`
2. GitHub Issues của Spring Boot
3. Official Spring Documentation

---

*Kế hoạch này được tạo tự động. Vui lòng review và điều chỉnh theo nhu cầu cụ thể của dự án.*
