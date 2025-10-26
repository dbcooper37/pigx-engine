package com.pigx.engine.oauth2.extension.service;

import com.pigx.engine.core.definition.constant.SymbolConstants;
import com.pigx.engine.data.core.jpa.repository.BaseJpaRepository;
import com.pigx.engine.data.core.jpa.service.AbstractJpaService;
import com.pigx.engine.oauth2.extension.entity.OAuth2UserLogging;
import com.pigx.engine.oauth2.extension.repository.OAuth2UserLoggingRepository;
import cn.hutool.v7.http.server.servlet.ServletUtil;
import cn.hutool.v7.http.useragent.UserAgent;
import cn.hutool.v7.http.useragent.UserAgentUtil;
import jakarta.persistence.criteria.Predicate;
import jakarta.servlet.http.HttpServletRequest;
import java.lang.invoke.SerializedLambda;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
/* loaded from: oauth2-module-extension-3.5.7.0.jar:cn/herodotus/engine/oauth2/extension/service/OAuth2UserLoggingService.class */
public class OAuth2UserLoggingService extends AbstractJpaService<OAuth2UserLogging, String> {
    private static final Logger log = LoggerFactory.getLogger(OAuth2UserLoggingService.class);
    private final OAuth2UserLoggingRepository complianceRepository;

    private static /* synthetic */ Object $deserializeLambda$(SerializedLambda lambda) {
        switch (lambda.getImplMethodName()) {
            case "lambda$findByCondition$febd9a2$1":
                if (lambda.getImplMethodKind() == 6 && lambda.getFunctionalInterfaceClass().equals("org/springframework/data/jpa/domain/Specification") && lambda.getFunctionalInterfaceMethodName().equals("toPredicate") && lambda.getFunctionalInterfaceMethodSignature().equals("(Ljakarta/persistence/criteria/Root;Ljakarta/persistence/criteria/CriteriaQuery;Ljakarta/persistence/criteria/CriteriaBuilder;)Ljakarta/persistence/criteria/Predicate;") && lambda.getImplClass().equals("cn/herodotus/engine/oauth2/extension/service/OAuth2UserLoggingService") && lambda.getImplMethodSignature().equals("(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljakarta/persistence/criteria/Root;Ljakarta/persistence/criteria/CriteriaQuery;Ljakarta/persistence/criteria/CriteriaBuilder;)Ljakarta/persistence/criteria/Predicate;")) {
                    String str = (String) lambda.getCapturedArg(0);
                    String str2 = (String) lambda.getCapturedArg(1);
                    String str3 = (String) lambda.getCapturedArg(2);
                    return (root, criteriaQuery, criteriaBuilder) -> {
                        List<Predicate> predicates = new ArrayList<>();
                        if (StringUtils.isNotBlank(str)) {
                            predicates.add(criteriaBuilder.equal(root.get("principalName"), str));
                        }
                        if (StringUtils.isNotBlank(str2)) {
                            predicates.add(criteriaBuilder.equal(root.get("clientId"), str2));
                        }
                        if (StringUtils.isNotBlank(str3)) {
                            predicates.add(criteriaBuilder.equal(root.get("ip"), str3));
                        }
                        Predicate[] predicateArray = new Predicate[predicates.size()];
                        criteriaQuery.where(criteriaBuilder.and((Predicate[]) predicates.toArray(predicateArray)));
                        return criteriaQuery.getRestriction();
                    };
                }
                break;
        }
        throw new IllegalArgumentException("Invalid lambda deserialization");
    }

    public OAuth2UserLoggingService(OAuth2UserLoggingRepository complianceRepository) {
        this.complianceRepository = complianceRepository;
    }

    @Override // com.pigx.engine.data.core.jpa.service.BaseJpaReadableService
    public BaseJpaRepository<OAuth2UserLogging, String> getRepository() {
        return this.complianceRepository;
    }

    public Page<OAuth2UserLogging> findByCondition(int pageNumber, int pageSize, String principalName, String clientId, String ip) {
        PageRequest pageRequestOf = PageRequest.of(pageNumber, pageSize);
        Specification<OAuth2UserLogging> specification = (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.isNotBlank(principalName)) {
                predicates.add(criteriaBuilder.equal(root.get("principalName"), principalName));
            }
            if (StringUtils.isNotBlank(clientId)) {
                predicates.add(criteriaBuilder.equal(root.get("clientId"), clientId));
            }
            if (StringUtils.isNotBlank(ip)) {
                predicates.add(criteriaBuilder.equal(root.get("ip"), ip));
            }
            Predicate[] predicateArray = new Predicate[predicates.size()];
            criteriaQuery.where(criteriaBuilder.and((Predicate[]) predicates.toArray(predicateArray)));
            return criteriaQuery.getRestriction();
        };
        return findByPage((Specification) specification, (Pageable) pageRequestOf);
    }

    public OAuth2UserLogging save(String principalName, String clientId, String operation, HttpServletRequest request) {
        OAuth2UserLogging compliance = toEntity(principalName, clientId, operation, request);
        log.debug("[Herodotus] |- Sign in user is [{}]", compliance);
        return (OAuth2UserLogging) super.save(compliance);
    }

    private UserAgent getUserAgent(HttpServletRequest request) {
        return UserAgentUtil.parse(request.getHeader("User-Agent"));
    }

    private String getIp(HttpServletRequest request) {
        return ServletUtil.getClientIP(request, new String[]{SymbolConstants.BLANK});
    }

    public OAuth2UserLogging toEntity(String principalName, String clientId, String operation, HttpServletRequest request) {
        OAuth2UserLogging audit = new OAuth2UserLogging();
        audit.setPrincipalName(principalName);
        audit.setClientId(clientId);
        audit.setOperation(operation);
        UserAgent userAgent = getUserAgent(request);
        if (ObjectUtils.isNotEmpty(userAgent)) {
            audit.setIp(getIp(request));
            audit.setMobile(Boolean.valueOf(userAgent.isMobile()));
            audit.setOsName(userAgent.getOs().getName());
            audit.setBrowserName(userAgent.getBrowser().getName());
            audit.setMobileBrowser(Boolean.valueOf(userAgent.getBrowser().isMobile()));
            audit.setEngineName(userAgent.getEngine().getName());
            audit.setMobilePlatform(Boolean.valueOf(userAgent.getPlatform().isMobile()));
            audit.setIphoneOrIpod(Boolean.valueOf(userAgent.getPlatform().isIPhoneOrIPod()));
            audit.setIpad(Boolean.valueOf(userAgent.getPlatform().isIPad()));
            audit.setIos(Boolean.valueOf(userAgent.getPlatform().isIos()));
            audit.setAndroid(Boolean.valueOf(userAgent.getPlatform().isAndroid()));
        }
        return audit;
    }
}
