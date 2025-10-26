package com.pigx.engine.logic.upms.service.hr;

import com.pigx.engine.data.core.jpa.repository.BaseJpaRepository;
import com.pigx.engine.data.core.jpa.service.AbstractJpaService;
import com.pigx.engine.logic.upms.entity.hr.SysDepartment;
import com.pigx.engine.logic.upms.repository.hr.SysDepartmentRepository;
import jakarta.persistence.criteria.Predicate;
import java.lang.invoke.SerializedLambda;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
/* loaded from: logic-module-upms-3.5.7.0.jar:cn/herodotus/engine/logic/upms/service/hr/SysDepartmentService.class */
public class SysDepartmentService extends AbstractJpaService<SysDepartment, String> {
    private final SysDepartmentRepository sysDepartmentRepository;
    private final SysOwnershipService sysOwnershipService;

    private static /* synthetic */ Object $deserializeLambda$(SerializedLambda lambda) {
        switch (lambda.getImplMethodName()) {
            case "lambda$findByCondition$5b71db0e$1":
                if (lambda.getImplMethodKind() == 6 && lambda.getFunctionalInterfaceClass().equals("org/springframework/data/jpa/domain/Specification") && lambda.getFunctionalInterfaceMethodName().equals("toPredicate") && lambda.getFunctionalInterfaceMethodSignature().equals("(Ljakarta/persistence/criteria/Root;Ljakarta/persistence/criteria/CriteriaQuery;Ljakarta/persistence/criteria/CriteriaBuilder;)Ljakarta/persistence/criteria/Predicate;") && lambda.getImplClass().equals("cn/herodotus/engine/logic/upms/service/hr/SysDepartmentService") && lambda.getImplMethodSignature().equals("(Ljava/lang/String;Ljakarta/persistence/criteria/Root;Ljakarta/persistence/criteria/CriteriaQuery;Ljakarta/persistence/criteria/CriteriaBuilder;)Ljakarta/persistence/criteria/Predicate;")) {
                    String str = (String) lambda.getCapturedArg(0);
                    return (root, criteriaQuery, criteriaBuilder) -> {
                        List<Predicate> predicates = new ArrayList<>();
                        if (ObjectUtils.isNotEmpty(str)) {
                            predicates.add(criteriaBuilder.equal(root.get("organizationId"), str));
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

    public SysDepartmentService(SysDepartmentRepository sysDepartmentRepository, SysOwnershipService sysOwnershipService) {
        this.sysDepartmentRepository = sysDepartmentRepository;
        this.sysOwnershipService = sysOwnershipService;
    }

    @Override // com.pigx.engine.data.core.jpa.service.BaseJpaReadableService
    public BaseJpaRepository<SysDepartment, String> getRepository() {
        return this.sysDepartmentRepository;
    }

    public Page<SysDepartment> findByCondition(int pageNumber, int pageSize, String organizationId) {
        PageRequest pageRequestOf = PageRequest.of(pageNumber, pageSize);
        Specification<SysDepartment> specification = (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (ObjectUtils.isNotEmpty(organizationId)) {
                predicates.add(criteriaBuilder.equal(root.get("organizationId"), organizationId));
            }
            Predicate[] predicateArray = new Predicate[predicates.size()];
            criteriaQuery.where(criteriaBuilder.and((Predicate[]) predicates.toArray(predicateArray)));
            return criteriaQuery.getRestriction();
        };
        return findByPage((Specification) specification, (Pageable) pageRequestOf);
    }

    public List<SysDepartment> findAll(String organizationId) {
        if (ObjectUtils.isNotEmpty(organizationId)) {
            return this.sysDepartmentRepository.findByOrganizationId(organizationId);
        }
        return this.sysDepartmentRepository.m105findAll();
    }

    @Override // com.pigx.engine.data.core.jpa.service.BaseJpaWriteableService, com.pigx.engine.data.core.service.BaseService
    public void deleteById(String departmentId) {
        this.sysOwnershipService.deleteByDepartmentId(departmentId);
        super.deleteById((SysDepartmentService) departmentId);
    }
}
