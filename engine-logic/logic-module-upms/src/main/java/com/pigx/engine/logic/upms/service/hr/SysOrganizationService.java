package com.pigx.engine.logic.upms.service.hr;

import com.pigx.engine.core.foundation.exception.transaction.TransactionalRollbackException;
import com.pigx.engine.data.core.jpa.repository.BaseJpaRepository;
import com.pigx.engine.data.core.jpa.service.AbstractJpaService;
import com.pigx.engine.logic.upms.entity.hr.SysDepartment;
import com.pigx.engine.logic.upms.entity.hr.SysOrganization;
import com.pigx.engine.logic.upms.enums.OrganizationCategory;
import com.pigx.engine.logic.upms.repository.hr.SysOrganizationRepository;
import jakarta.persistence.criteria.Predicate;
import java.lang.invoke.SerializedLambda;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
/* loaded from: logic-module-upms-3.5.7.0.jar:cn/herodotus/engine/logic/upms/service/hr/SysOrganizationService.class */
public class SysOrganizationService extends AbstractJpaService<SysOrganization, String> {
    private final SysOrganizationRepository sysOrganizationRepository;
    private final SysOwnershipService sysOwnershipService;
    private final SysDepartmentService sysDepartmentService;

    private static /* synthetic */ Object $deserializeLambda$(SerializedLambda lambda) {
        switch (lambda.getImplMethodName()) {
            case "lambda$findByCondition$5a0a2f2e$1":
                if (lambda.getImplMethodKind() == 6 && lambda.getFunctionalInterfaceClass().equals("org/springframework/data/jpa/domain/Specification") && lambda.getFunctionalInterfaceMethodName().equals("toPredicate") && lambda.getFunctionalInterfaceMethodSignature().equals("(Ljakarta/persistence/criteria/Root;Ljakarta/persistence/criteria/CriteriaQuery;Ljakarta/persistence/criteria/CriteriaBuilder;)Ljakarta/persistence/criteria/Predicate;") && lambda.getImplClass().equals("cn/herodotus/engine/logic/upms/service/hr/SysOrganizationService") && lambda.getImplMethodSignature().equals("(Lcn/herodotus/engine/logic/upms/enums/OrganizationCategory;Ljakarta/persistence/criteria/Root;Ljakarta/persistence/criteria/CriteriaQuery;Ljakarta/persistence/criteria/CriteriaBuilder;)Ljakarta/persistence/criteria/Predicate;")) {
                    OrganizationCategory organizationCategory = (OrganizationCategory) lambda.getCapturedArg(0);
                    return (root, criteriaQuery, criteriaBuilder) -> {
                        List<Predicate> predicates = new ArrayList<>();
                        if (ObjectUtils.isNotEmpty(organizationCategory)) {
                            predicates.add(criteriaBuilder.equal(root.get("category"), organizationCategory));
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

    public SysOrganizationService(SysOrganizationRepository sysOrganizationRepository, SysOwnershipService sysOwnershipService, SysDepartmentService sysDepartmentService) {
        this.sysOrganizationRepository = sysOrganizationRepository;
        this.sysOwnershipService = sysOwnershipService;
        this.sysDepartmentService = sysDepartmentService;
    }

    @Override // com.pigx.engine.data.core.jpa.service.BaseJpaReadableService
    public BaseJpaRepository<SysOrganization, String> getRepository() {
        return this.sysOrganizationRepository;
    }

    public List<SysOrganization> findAll(OrganizationCategory organizationCategory) {
        if (ObjectUtils.isNotEmpty(organizationCategory)) {
            return this.sysOrganizationRepository.findByCategory(organizationCategory);
        }
        return this.sysOrganizationRepository.m105findAll();
    }

    public Page<SysOrganization> findByCondition(int pageNumber, int pageSize, OrganizationCategory organizationCategory) {
        PageRequest pageRequestOf = PageRequest.of(pageNumber, pageSize);
        Specification<SysOrganization> specification = (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (ObjectUtils.isNotEmpty(organizationCategory)) {
                predicates.add(criteriaBuilder.equal(root.get("category"), organizationCategory));
            }
            Predicate[] predicateArray = new Predicate[predicates.size()];
            criteriaQuery.where(criteriaBuilder.and((Predicate[]) predicates.toArray(predicateArray)));
            return criteriaQuery.getRestriction();
        };
        return findByPage((Specification) specification, (Pageable) pageRequestOf);
    }

    @Override // com.pigx.engine.data.core.jpa.service.BaseJpaWriteableService, com.pigx.engine.data.core.service.BaseService
    @Transactional(rollbackFor = {TransactionalRollbackException.class})
    public void deleteById(String organizationId) {
        this.sysOwnershipService.deleteByOrganizationId(organizationId);
        super.deleteById((SysOrganizationService) organizationId);
    }

    public boolean isInUse(String organizationId) {
        List<SysDepartment> sysDepartments = this.sysDepartmentService.findAll(organizationId);
        return CollectionUtils.isNotEmpty(sysDepartments);
    }
}
