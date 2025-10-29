package com.pigx.engine.oauth2.extension.service;

import cn.hutool.v7.http.server.servlet.ServletUtil;
import cn.hutool.v7.http.useragent.UserAgent;
import cn.hutool.v7.http.useragent.UserAgentUtil;
import com.google.common.net.HttpHeaders;
import com.pigx.engine.data.core.jpa.repository.BaseJpaRepository;
import com.pigx.engine.data.core.jpa.service.AbstractJpaService;
import com.pigx.engine.oauth2.extension.entity.OAuth2UserLogging;
import com.pigx.engine.oauth2.extension.repository.OAuth2UserLoggingRepository;
import jakarta.persistence.criteria.Predicate;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class OAuth2UserLoggingService extends AbstractJpaService<OAuth2UserLogging, String> {

    private static final Logger log = LoggerFactory.getLogger(OAuth2UserLoggingService.class);

    private final OAuth2UserLoggingRepository complianceRepository;

    public OAuth2UserLoggingService(OAuth2UserLoggingRepository complianceRepository) {
        this.complianceRepository = complianceRepository;
    }

    @Override
    public BaseJpaRepository<OAuth2UserLogging, String> getRepository() {
        return complianceRepository;
    }

    public Page<OAuth2UserLogging> findByCondition(int pageNumber, int pageSize, String principalName, String clientId, String ip) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

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
            criteriaQuery.where(criteriaBuilder.and(predicates.toArray(predicateArray)));
            return criteriaQuery.getRestriction();
        };

        return this.findByPage(specification, pageable);
    }

    public OAuth2UserLogging save(String principalName, String clientId, String operation, HttpServletRequest request) {
        OAuth2UserLogging compliance = toEntity(principalName, clientId, operation, request);
        log.debug("[PIGXD] |- Sign in user is [{}]", compliance);
        return super.save(compliance);
    }

    private UserAgent getUserAgent(HttpServletRequest request) {
        return UserAgentUtil.parse(request.getHeader(HttpHeaders.USER_AGENT));
    }

    private String getIp(HttpServletRequest request) {
        return ServletUtil.getClientIP(request, "");
    }

    public OAuth2UserLogging toEntity(String principalName, String clientId, String operation, HttpServletRequest request) {
        OAuth2UserLogging audit = new OAuth2UserLogging();
        audit.setPrincipalName(principalName);
        audit.setClientId(clientId);
        audit.setOperation(operation);

        UserAgent userAgent = getUserAgent(request);
        if (ObjectUtils.isNotEmpty(userAgent)) {
            audit.setIp(getIp(request));
            audit.setMobile(userAgent.isMobile());
            audit.setOsName(userAgent.getOs().getName());
            audit.setBrowserName(userAgent.getBrowser().getName());
            audit.setMobileBrowser(userAgent.getBrowser().isMobile());
            audit.setEngineName(userAgent.getEngine().getName());
            audit.setMobilePlatform(userAgent.getPlatform().isMobile());
            audit.setIphoneOrIpod(userAgent.getPlatform().isIPhoneOrIPod());
            audit.setIpad(userAgent.getPlatform().isIPad());
            audit.setIos(userAgent.getPlatform().isIos());
            audit.setAndroid(userAgent.getPlatform().isAndroid());
        }

        return audit;
    }
}
