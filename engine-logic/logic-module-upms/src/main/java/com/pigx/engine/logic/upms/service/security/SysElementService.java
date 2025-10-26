package com.pigx.engine.logic.upms.service.security;

import com.pigx.engine.data.core.jpa.repository.BaseJpaRepository;
import com.pigx.engine.data.core.jpa.service.AbstractJpaService;
import com.pigx.engine.logic.upms.entity.security.SysElement;
import com.pigx.engine.logic.upms.entity.security.SysRole;
import com.pigx.engine.logic.upms.repository.security.SysElementRepository;
import jakarta.persistence.criteria.Predicate;
import java.lang.invoke.SerializedLambda;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
/* loaded from: logic-module-upms-3.5.7.0.jar:cn/herodotus/engine/logic/upms/service/security/SysElementService.class */
public class SysElementService extends AbstractJpaService<SysElement, String> {
    private final SysElementRepository sysElementRepository;

    private static /* synthetic */ Object $deserializeLambda$(SerializedLambda lambda) {
        switch (lambda.getImplMethodName()) {
            case "lambda$findByCondition$67acd74b$1":
                if (lambda.getImplMethodKind() == 5 && lambda.getFunctionalInterfaceClass().equals("org/springframework/data/jpa/domain/Specification") && lambda.getFunctionalInterfaceMethodName().equals("toPredicate") && lambda.getFunctionalInterfaceMethodSignature().equals("(Ljakarta/persistence/criteria/Root;Ljakarta/persistence/criteria/CriteriaQuery;Ljakarta/persistence/criteria/CriteriaBuilder;)Ljakarta/persistence/criteria/Predicate;") && lambda.getImplClass().equals("cn/herodotus/engine/logic/upms/service/security/SysElementService") && lambda.getImplMethodSignature().equals("(Ljava/lang/String;Ljava/lang/String;Ljakarta/persistence/criteria/Root;Ljakarta/persistence/criteria/CriteriaQuery;Ljakarta/persistence/criteria/CriteriaBuilder;)Ljakarta/persistence/criteria/Predicate;")) {
                    SysElementService sysElementService = (SysElementService) lambda.getCapturedArg(0);
                    String str = (String) lambda.getCapturedArg(1);
                    String str2 = (String) lambda.getCapturedArg(2);
                    return (root, criteriaQuery, criteriaBuilder) -> {
                        List<Predicate> predicates = new ArrayList<>();
                        if (ObjectUtils.isNotEmpty(str)) {
                            predicates.add(criteriaBuilder.like(root.get("path"), like(str)));
                        }
                        if (ObjectUtils.isNotEmpty(str2)) {
                            predicates.add(criteriaBuilder.like(root.get("title"), like(str2)));
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

    public SysElementService(SysElementRepository sysElementRepository) {
        this.sysElementRepository = sysElementRepository;
    }

    @Override // com.pigx.engine.data.core.jpa.service.BaseJpaReadableService
    public BaseJpaRepository<SysElement, String> getRepository() {
        return this.sysElementRepository;
    }

    public Page<SysElement> findByCondition(int pageNumber, int pageSize, String path, String title) {
        PageRequest pageRequestOf = PageRequest.of(pageNumber, pageSize);
        Specification<SysElement> specification = (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (ObjectUtils.isNotEmpty(path)) {
                predicates.add(criteriaBuilder.like(root.get("path"), like(path)));
            }
            if (ObjectUtils.isNotEmpty(title)) {
                predicates.add(criteriaBuilder.like(root.get("title"), like(title)));
            }
            Predicate[] predicateArray = new Predicate[predicates.size()];
            criteriaQuery.where(criteriaBuilder.and((Predicate[]) predicates.toArray(predicateArray)));
            return criteriaQuery.getRestriction();
        };
        return findByPage((Specification) specification, (Pageable) pageRequestOf);
    }

    public SysElement assign(String elementId, String[] roles) {
        Set<SysRole> sysRoles = new HashSet<>();
        for (String role : roles) {
            SysRole sysRole = new SysRole();
            sysRole.setRoleId(role);
            sysRoles.add(sysRole);
        }
        return (SysElement) findById(elementId).map(data -> {
            data.setRoles(sysRoles);
            return data;
        }).map((v1) -> {
            return saveAndFlush(v1);
        }).orElse(null);
    }
}
