package com.pigx.engine.logic.upms.service.security;

import com.pigx.engine.data.core.jpa.repository.BaseJpaRepository;
import com.pigx.engine.data.core.jpa.service.AbstractJpaService;
import com.pigx.engine.logic.upms.converter.RequestMappingToSysInterfaceConverter;
import com.pigx.engine.logic.upms.entity.security.SysAttribute;
import com.pigx.engine.logic.upms.entity.security.SysInterface;
import com.pigx.engine.logic.upms.repository.security.SysInterfaceRepository;
import com.pigx.engine.message.core.domain.RestMapping;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import java.lang.invoke.SerializedLambda;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
/* loaded from: logic-module-upms-3.5.7.0.jar:cn/herodotus/engine/logic/upms/service/security/SysInterfaceService.class */
public class SysInterfaceService extends AbstractJpaService<SysInterface, String> {
    private final SysInterfaceRepository sysInterfaceRepository;
    private final Converter<RestMapping, SysInterface> toSysInterface = new RequestMappingToSysInterfaceConverter();

    private static /* synthetic */ Object $deserializeLambda$(SerializedLambda lambda) {
        switch (lambda.getImplMethodName()) {
            case "lambda$findAllocatable$c5f995df$1":
                if (lambda.getImplMethodKind() == 6 && lambda.getFunctionalInterfaceClass().equals("org/springframework/data/jpa/domain/Specification") && lambda.getFunctionalInterfaceMethodName().equals("toPredicate") && lambda.getFunctionalInterfaceMethodSignature().equals("(Ljakarta/persistence/criteria/Root;Ljakarta/persistence/criteria/CriteriaQuery;Ljakarta/persistence/criteria/CriteriaBuilder;)Ljakarta/persistence/criteria/Predicate;") && lambda.getImplClass().equals("cn/herodotus/engine/logic/upms/service/security/SysInterfaceService") && lambda.getImplMethodSignature().equals("(Ljakarta/persistence/criteria/Root;Ljakarta/persistence/criteria/CriteriaQuery;Ljakarta/persistence/criteria/CriteriaBuilder;)Ljakarta/persistence/criteria/Predicate;")) {
                    return (root, criteriaQuery, criteriaBuilder) -> {
                        Subquery<SysAttribute> subQuery = criteriaQuery.subquery(SysAttribute.class);
                        Root<SysAttribute> subRoot = subQuery.from(SysAttribute.class);
                        Predicate subPredicate = criteriaBuilder.equal(subRoot.get("attributeId"), root.get("interfaceId"));
                        subQuery.where(subPredicate);
                        subQuery.select(subRoot.get("attributeId"));
                        criteriaQuery.where(criteriaBuilder.not(criteriaBuilder.exists(subQuery)));
                        return criteriaQuery.getRestriction();
                    };
                }
                break;
        }
        throw new IllegalArgumentException("Invalid lambda deserialization");
    }

    public SysInterfaceService(SysInterfaceRepository sysInterfaceRepository) {
        this.sysInterfaceRepository = sysInterfaceRepository;
    }

    @Override // com.pigx.engine.data.core.jpa.service.BaseJpaReadableService
    public BaseJpaRepository<SysInterface, String> getRepository() {
        return this.sysInterfaceRepository;
    }

    public List<SysInterface> findAllocatable() {
        Specification<SysInterface> specification = (root, criteriaQuery, criteriaBuilder) -> {
            Subquery<SysAttribute> subQuery = criteriaQuery.subquery(SysAttribute.class);
            Root<SysAttribute> subRoot = subQuery.from(SysAttribute.class);
            Predicate subPredicate = criteriaBuilder.equal(subRoot.get("attributeId"), root.get("interfaceId"));
            subQuery.where(subPredicate);
            subQuery.select(subRoot.get("attributeId"));
            criteriaQuery.where(criteriaBuilder.not(criteriaBuilder.exists(subQuery)));
            return criteriaQuery.getRestriction();
        };
        return findAll(specification);
    }

    public List<SysInterface> storeRequestMappings(Collection<RestMapping> restMappings) {
        List<SysInterface> sysAuthorities = toSysInterfaces(restMappings);
        return saveAllAndFlush(sysAuthorities);
    }

    private List<SysInterface> toSysInterfaces(Collection<RestMapping> restMappings) {
        if (CollectionUtils.isNotEmpty(restMappings)) {
            Stream<RestMapping> stream = restMappings.stream();
            Converter<RestMapping, SysInterface> converter = this.toSysInterface;
            Objects.requireNonNull(converter);
            return (List) stream.map((v1) -> {
                return r1.convert(v1);
            }).collect(Collectors.toList());
        }
        return new ArrayList();
    }
}
