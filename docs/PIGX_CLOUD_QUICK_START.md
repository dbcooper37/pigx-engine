# PigX Cloud - Quick Start Guide

**Mục tiêu**: Tạo project pigx-cloud trong 1 giờ đầu tiên

---

## 🚀 30 Phút Đầu: Setup Cơ Bản

### Bước 1: Tạo Project (5 phút)

```bash
# 1. Tạo thư mục
cd ~/Documents/projects/my/java
mkdir pigx-cloud && cd pigx-cloud

# 2. Init Git
git init
cat > .gitignore << 'EOF'
target/
.idea/
*.iml
*.log
EOF

# 3. Tạo cấu trúc
mkdir -p {cloud-dependencies,cloud-starters,cloud-modules,cloud-services,cloud-monomer,configurations,docs}
```

### Bước 2: Root POM (10 phút)

```bash
# Copy template từ plan
# Hoặc dùng script tự động:
wget https://gist.github.com/.../pigx-cloud-init.sh
chmod +x pigx-cloud-init.sh
./pigx-cloud-init.sh
```

### Bước 3: Verify pigx-engine (5 phút)

```bash
# Đảm bảo pigx-engine đã được install
cd ~/Documents/projects/my/java/pigx-engine
mvn clean install -DskipTests

# Check version
ls ~/.m2/repository/com/pigx/engine/
# Should see: 1.0-SNAPSHOT/
```

### Bước 4: Create BOM (10 phút)

```bash
cd cloud-dependencies
# Tạo pom.xml theo template trong plan
mvn clean install
```

---

## ⏱️ 30 Phút Tiếp Theo: First Service

### Bước 5: Module Common (5 phút)

```bash
cd cloud-modules/module-common
# Tạo ServiceNameConstants.java
mvn clean install
```

### Bước 6: Platform Service Starter (10 phút)

```bash
cd cloud-starters/platform-service-starter
# Tạo pom.xml với dependencies cơ bản
mvn clean install
```

### Bước 7: First Service - UAA (15 phút)

```bash
cd cloud-services/service-uaa

# 1. Create Application.java
# 2. Create application.yml
# 3. Test run
mvn spring-boot:run

# Should see:
# Started UaaApplication in X seconds
# Port: 9100
```

---

## 🎉 Kết Quả Sau 1 Giờ

Bạn sẽ có:

```
pigx-cloud/
├── cloud-dependencies/      ✅ BOM configured
├── cloud-modules/
│   └── module-common/       ✅ Constants ready
├── cloud-starters/
│   └── platform-service-starter/  ✅ Common starter
└── cloud-services/
    └── service-uaa/         ✅ First service running!
```

Test service:
```bash
curl http://localhost:9100/actuator/health
# {"status":"UP"}
```

---

## 📋 Checklist Tuần Đầu

**Day 1:**
- [ ] Project structure created
- [ ] pigx-engine verified and installed
- [ ] cloud-dependencies (BOM) ready

**Day 2:**
- [ ] module-common created
- [ ] platform-service-starter created
- [ ] service-uaa running

**Day 3:**
- [ ] platform-auth-starter created
- [ ] service-upms created

**Day 4:**
- [ ] service-gateway created
- [ ] Basic routing working

**Day 5:**
- [ ] Docker Compose for infrastructure
- [ ] service-message, service-monitor, service-oss scaffold created

---

## 🆘 Common Issues

### Issue 1: pigx-engine not found

```bash
# Solution: Install pigx-engine to local Maven repo
cd ~/Documents/projects/my/java/pigx-engine
mvn clean install -DskipTests
```

### Issue 2: Port already in use

```bash
# Solution: Change port in application.yml
server:
  port: 9101  # Change to available port
```

### Issue 3: Cannot connect to Nacos

```bash
# Solution: Use standalone profile first
spring:
  profiles:
    active: standalone
# Don't use alibaba profile until Nacos is ready
```

---

## 📚 Next Steps

**Week 2-8:** Complete all 6 services
- Week 3: service-uaa ✅ (Auth)
- Week 4: service-upms (Permission)
- Week 5: service-gateway (Gateway)
- Week 6: service-message (WebSocket)
- Week 7: service-monitor (Monitoring)
- Week 8: service-oss (Storage)

**Week 9:** Monolithic mode
- Create monomer-application
- Test all features in single JVM

**Week 10-11:** Deployment & Docs
- Docker Compose
- Kubernetes manifests
- Documentation
- CI/CD pipeline

---

## 💡 Pro Tips

1. **Start Simple**: Begin with standalone profile, add Nacos later
2. **Test Often**: Run `mvn clean install` frequently
3. **Use pigx-engine**: Leverage existing starters, don't reinvent
4. **Keep It Clean**: Fewer modules = easier to maintain
5. **Document As You Go**: Update README.md regularly

---

## 🔗 Resources

- **Detailed Plan**: PIGX_CLOUD_BUILD_PLAN.md
- **pigx-engine Reference**: ../pigx-engine/PIGX_ENGINE_REFERENCE.md
- **Dante Cloud (Template)**: https://github.com/dromara/dante-cloud

---

**Ready to start? Let's build PigX Cloud! 🚀**
