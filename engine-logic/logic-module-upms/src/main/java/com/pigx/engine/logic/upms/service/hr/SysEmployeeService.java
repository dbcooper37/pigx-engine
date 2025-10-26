package com.pigx.engine.logic.upms.service.hr;

import com.pigx.engine.core.definition.constant.SystemConstants;
import com.pigx.engine.core.foundation.exception.transaction.TransactionalRollbackException;
import com.pigx.engine.data.core.jpa.repository.BaseJpaRepository;
import com.pigx.engine.data.core.jpa.service.AbstractJpaService;
import com.pigx.engine.logic.upms.entity.hr.SysDepartment;
import com.pigx.engine.logic.upms.entity.hr.SysEmployee;
import com.pigx.engine.logic.upms.entity.hr.SysOwnership;
import com.pigx.engine.logic.upms.entity.security.SysUser;
import com.pigx.engine.logic.upms.enums.Gender;
import com.pigx.engine.logic.upms.enums.Identity;
import com.pigx.engine.logic.upms.repository.hr.SysEmployeeRepository;
import com.pigx.engine.logic.upms.service.security.SysUserService;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import java.lang.invoke.SerializedLambda;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
/* loaded from: logic-module-upms-3.5.7.0.jar:cn/herodotus/engine/logic/upms/service/hr/SysEmployeeService.class */
public class SysEmployeeService extends AbstractJpaService<SysEmployee, String> {
    private static final Logger log = LoggerFactory.getLogger(SysEmployeeService.class);
    private final SysEmployeeRepository sysEmployeeRepository;
    private final SysOwnershipService sysOwnershipService;
    private final SysUserService sysUserService;

    private static /* synthetic */ Object $deserializeLambda$(SerializedLambda lambda) {
        switch (lambda.getImplMethodName()) {
            case "lambda$findByCondition$ae6272d0$1":
                if (lambda.getImplMethodKind() == 5 && lambda.getFunctionalInterfaceClass().equals("org/springframework/data/jpa/domain/Specification") && lambda.getFunctionalInterfaceMethodName().equals("toPredicate") && lambda.getFunctionalInterfaceMethodSignature().equals("(Ljakarta/persistence/criteria/Root;Ljakarta/persistence/criteria/CriteriaQuery;Ljakarta/persistence/criteria/CriteriaBuilder;)Ljakarta/persistence/criteria/Predicate;") && lambda.getImplClass().equals("cn/herodotus/engine/logic/upms/service/hr/SysEmployeeService") && lambda.getImplMethodSignature().equals("(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lcn/herodotus/engine/logic/upms/enums/Gender;Lcn/herodotus/engine/logic/upms/enums/Identity;Ljakarta/persistence/criteria/Root;Ljakarta/persistence/criteria/CriteriaQuery;Ljakarta/persistence/criteria/CriteriaBuilder;)Ljakarta/persistence/criteria/Predicate;")) {
                    SysEmployeeService sysEmployeeService = (SysEmployeeService) lambda.getCapturedArg(0);
                    String str = (String) lambda.getCapturedArg(1);
                    String str2 = (String) lambda.getCapturedArg(2);
                    String str3 = (String) lambda.getCapturedArg(3);
                    String str4 = (String) lambda.getCapturedArg(4);
                    String str5 = (String) lambda.getCapturedArg(5);
                    Gender gender = (Gender) lambda.getCapturedArg(6);
                    Identity identity = (Identity) lambda.getCapturedArg(7);
                    return (root, criteriaQuery, criteriaBuilder) -> {
                        List<Predicate> predicates = new ArrayList<>();
                        if (StringUtils.isNotBlank(str)) {
                            predicates.add(criteriaBuilder.like(root.get("employeeName"), like(str)));
                        }
                        if (StringUtils.isNotBlank(str2)) {
                            predicates.add(criteriaBuilder.like(root.get("mobilePhoneNumber"), like(str2)));
                        }
                        if (StringUtils.isNotBlank(str3)) {
                            predicates.add(criteriaBuilder.like(root.get("officePhoneNumber"), like(str3)));
                        }
                        if (StringUtils.isNotBlank(str4)) {
                            predicates.add(criteriaBuilder.like(root.get(SystemConstants.SCOPE_EMAIL), like(str4)));
                        }
                        if (StringUtils.isNotBlank(str5)) {
                            predicates.add(criteriaBuilder.like(root.get("pkiEmail"), like(str5)));
                        }
                        if (ObjectUtils.isNotEmpty(gender)) {
                            predicates.add(criteriaBuilder.equal(root.get("gender"), gender));
                        }
                        if (ObjectUtils.isNotEmpty(identity)) {
                            predicates.add(criteriaBuilder.equal(root.get("identity"), identity));
                        }
                        Predicate[] predicateArray = new Predicate[predicates.size()];
                        criteriaQuery.where(criteriaBuilder.and((Predicate[]) predicates.toArray(predicateArray)));
                        return criteriaQuery.getRestriction();
                    };
                }
                break;
            case "lambda$findAllocatable$abd588da$1":
                if (lambda.getImplMethodKind() == 5 && lambda.getFunctionalInterfaceClass().equals("org/springframework/data/jpa/domain/Specification") && lambda.getFunctionalInterfaceMethodName().equals("toPredicate") && lambda.getFunctionalInterfaceMethodSignature().equals("(Ljakarta/persistence/criteria/Root;Ljakarta/persistence/criteria/CriteriaQuery;Ljakarta/persistence/criteria/CriteriaBuilder;)Ljakarta/persistence/criteria/Predicate;") && lambda.getImplClass().equals("cn/herodotus/engine/logic/upms/service/hr/SysEmployeeService") && lambda.getImplMethodSignature().equals("(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lcn/herodotus/engine/logic/upms/enums/Gender;Lcn/herodotus/engine/logic/upms/enums/Identity;Ljakarta/persistence/criteria/Root;Ljakarta/persistence/criteria/CriteriaQuery;Ljakarta/persistence/criteria/CriteriaBuilder;)Ljakarta/persistence/criteria/Predicate;")) {
                    SysEmployeeService sysEmployeeService2 = (SysEmployeeService) lambda.getCapturedArg(0);
                    String str6 = (String) lambda.getCapturedArg(1);
                    String str7 = (String) lambda.getCapturedArg(2);
                    String str8 = (String) lambda.getCapturedArg(3);
                    String str9 = (String) lambda.getCapturedArg(4);
                    String str10 = (String) lambda.getCapturedArg(5);
                    Gender gender2 = (Gender) lambda.getCapturedArg(6);
                    Identity identity2 = (Identity) lambda.getCapturedArg(7);
                    return (root2, criteriaQuery2, criteriaBuilder2) -> {
                        Subquery<SysOwnership> subQuery = criteriaQuery2.subquery(SysOwnership.class);
                        Root<SysOwnership> subRoot = subQuery.from(SysOwnership.class);
                        List<Predicate> subPredicates = new ArrayList<>();
                        subPredicates.add(criteriaBuilder2.equal(subRoot.get(SystemConstants.EMPLOYEE_ID), root2.get(SystemConstants.EMPLOYEE_ID)));
                        if (StringUtils.isNotBlank(str6)) {
                            subPredicates.add(criteriaBuilder2.equal(subRoot.get("organizationId"), str6));
                        }
                        if (StringUtils.isNotBlank(str7)) {
                            subPredicates.add(criteriaBuilder2.equal(subRoot.get("departmentId"), str7));
                        }
                        Predicate[] subPredicateArray = new Predicate[subPredicates.size()];
                        subQuery.where(criteriaBuilder2.and((Predicate[]) subPredicates.toArray(subPredicateArray)));
                        subQuery.select(subRoot.get("ownershipId"));
                        List<Predicate> rootPredicates = new ArrayList<>();
                        rootPredicates.add(criteriaBuilder2.not(criteriaBuilder2.exists(subQuery)));
                        if (StringUtils.isNotBlank(str8)) {
                            rootPredicates.add(criteriaBuilder2.like(root2.get("employeeName"), like(str8)));
                        }
                        if (StringUtils.isNotBlank(str9)) {
                            rootPredicates.add(criteriaBuilder2.like(root2.get("mobilePhoneNumber"), like(str9)));
                        }
                        if (StringUtils.isNotBlank(str10)) {
                            rootPredicates.add(criteriaBuilder2.like(root2.get(SystemConstants.SCOPE_EMAIL), like(str10)));
                        }
                        if (ObjectUtils.isNotEmpty(gender2)) {
                            rootPredicates.add(criteriaBuilder2.equal(root2.get("gender"), gender2));
                        }
                        if (ObjectUtils.isNotEmpty(identity2)) {
                            rootPredicates.add(criteriaBuilder2.equal(root2.get("identity"), identity2));
                        }
                        Predicate[] rootPredicateArray = new Predicate[rootPredicates.size()];
                        criteriaQuery2.where(criteriaBuilder2.and((Predicate[]) rootPredicates.toArray(rootPredicateArray)));
                        return criteriaQuery2.getRestriction();
                    };
                }
                break;
            case "lambda$findByDepartmentId$faf0822b$1":
                if (lambda.getImplMethodKind() == 6 && lambda.getFunctionalInterfaceClass().equals("org/springframework/data/jpa/domain/Specification") && lambda.getFunctionalInterfaceMethodName().equals("toPredicate") && lambda.getFunctionalInterfaceMethodSignature().equals("(Ljakarta/persistence/criteria/Root;Ljakarta/persistence/criteria/CriteriaQuery;Ljakarta/persistence/criteria/CriteriaBuilder;)Ljakarta/persistence/criteria/Predicate;") && lambda.getImplClass().equals("cn/herodotus/engine/logic/upms/service/hr/SysEmployeeService") && lambda.getImplMethodSignature().equals("(Ljava/lang/String;Ljakarta/persistence/criteria/Root;Ljakarta/persistence/criteria/CriteriaQuery;Ljakarta/persistence/criteria/CriteriaBuilder;)Ljakarta/persistence/criteria/Predicate;")) {
                    String str11 = (String) lambda.getCapturedArg(0);
                    return (root3, criteriaQuery3, criteriaBuilder3) -> {
                        Join<SysEmployee, SysDepartment> join = root3.join("departments", JoinType.LEFT);
                        return criteriaBuilder3.equal(join.get("departmentId"), str11);
                    };
                }
                break;
        }
        throw new IllegalArgumentException("Invalid lambda deserialization");
    }

    public SysEmployeeService(SysEmployeeRepository sysEmployeeRepository, SysOwnershipService sysOwnershipService, SysUserService sysUserService) {
        this.sysEmployeeRepository = sysEmployeeRepository;
        this.sysOwnershipService = sysOwnershipService;
        this.sysUserService = sysUserService;
    }

    @Override // com.pigx.engine.data.core.jpa.service.BaseJpaReadableService
    public BaseJpaRepository<SysEmployee, String> getRepository() {
        return this.sysEmployeeRepository;
    }

    public Page<SysEmployee> findByCondition(int pageNumber, int pageSize, String employeeName, String mobilePhoneNumber, String officePhoneNumber, String email, String pkiEmail, Gender gender, Identity identity) {
        PageRequest pageRequestOf = PageRequest.of(pageNumber, pageSize);
        Specification<SysEmployee> specification = (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.isNotBlank(employeeName)) {
                predicates.add(criteriaBuilder.like(root.get("employeeName"), like(employeeName)));
            }
            if (StringUtils.isNotBlank(mobilePhoneNumber)) {
                predicates.add(criteriaBuilder.like(root.get("mobilePhoneNumber"), like(mobilePhoneNumber)));
            }
            if (StringUtils.isNotBlank(officePhoneNumber)) {
                predicates.add(criteriaBuilder.like(root.get("officePhoneNumber"), like(officePhoneNumber)));
            }
            if (StringUtils.isNotBlank(email)) {
                predicates.add(criteriaBuilder.like(root.get(SystemConstants.SCOPE_EMAIL), like(email)));
            }
            if (StringUtils.isNotBlank(pkiEmail)) {
                predicates.add(criteriaBuilder.like(root.get("pkiEmail"), like(pkiEmail)));
            }
            if (ObjectUtils.isNotEmpty(gender)) {
                predicates.add(criteriaBuilder.equal(root.get("gender"), gender));
            }
            if (ObjectUtils.isNotEmpty(identity)) {
                predicates.add(criteriaBuilder.equal(root.get("identity"), identity));
            }
            Predicate[] predicateArray = new Predicate[predicates.size()];
            criteriaQuery.where(criteriaBuilder.and((Predicate[]) predicates.toArray(predicateArray)));
            return criteriaQuery.getRestriction();
        };
        return findByPage((Specification) specification, (Pageable) pageRequestOf);
    }

    public Page<SysEmployee> findAllocatable(int pageNumber, int pageSize, String organizationId, String departmentId, String employeeName, String mobilePhoneNumber, String email, Gender gender, Identity identity) {
        PageRequest pageRequestOf = PageRequest.of(pageNumber, pageSize);
        Specification<SysEmployee> specification = (root2, criteriaQuery2, criteriaBuilder2) -> {
            Subquery<SysOwnership> subQuery = criteriaQuery2.subquery(SysOwnership.class);
            Root<SysOwnership> subRoot = subQuery.from(SysOwnership.class);
            List<Predicate> subPredicates = new ArrayList<>();
            subPredicates.add(criteriaBuilder2.equal(subRoot.get(SystemConstants.EMPLOYEE_ID), root2.get(SystemConstants.EMPLOYEE_ID)));
            if (StringUtils.isNotBlank(organizationId)) {
                subPredicates.add(criteriaBuilder2.equal(subRoot.get("organizationId"), organizationId));
            }
            if (StringUtils.isNotBlank(departmentId)) {
                subPredicates.add(criteriaBuilder2.equal(subRoot.get("departmentId"), departmentId));
            }
            Predicate[] subPredicateArray = new Predicate[subPredicates.size()];
            subQuery.where(criteriaBuilder2.and((Predicate[]) subPredicates.toArray(subPredicateArray)));
            subQuery.select(subRoot.get("ownershipId"));
            List<Predicate> rootPredicates = new ArrayList<>();
            rootPredicates.add(criteriaBuilder2.not(criteriaBuilder2.exists(subQuery)));
            if (StringUtils.isNotBlank(employeeName)) {
                rootPredicates.add(criteriaBuilder2.like(root2.get("employeeName"), like(employeeName)));
            }
            if (StringUtils.isNotBlank(mobilePhoneNumber)) {
                rootPredicates.add(criteriaBuilder2.like(root2.get("mobilePhoneNumber"), like(mobilePhoneNumber)));
            }
            if (StringUtils.isNotBlank(email)) {
                rootPredicates.add(criteriaBuilder2.like(root2.get(SystemConstants.SCOPE_EMAIL), like(email)));
            }
            if (ObjectUtils.isNotEmpty(gender)) {
                rootPredicates.add(criteriaBuilder2.equal(root2.get("gender"), gender));
            }
            if (ObjectUtils.isNotEmpty(identity)) {
                rootPredicates.add(criteriaBuilder2.equal(root2.get("identity"), identity));
            }
            Predicate[] rootPredicateArray = new Predicate[rootPredicates.size()];
            criteriaQuery2.where(criteriaBuilder2.and((Predicate[]) rootPredicates.toArray(rootPredicateArray)));
            return criteriaQuery2.getRestriction();
        };
        return findByPage((Specification) specification, (Pageable) pageRequestOf);
    }

    public Page<SysEmployee> findByDepartmentId(int pageNumber, int pageSize, String departmentId) {
        PageRequest pageRequestOf = PageRequest.of(pageNumber, pageSize);
        Specification<SysEmployee> specification = (root3, criteriaQuery3, criteriaBuilder3) -> {
            Join<SysEmployee, SysDepartment> join = root3.join("departments", JoinType.LEFT);
            return criteriaBuilder3.equal(join.get("departmentId"), departmentId);
        };
        return findByPage((Specification) specification, (Pageable) pageRequestOf);
    }

    @Transactional(rollbackFor = {TransactionalRollbackException.class})
    public SysEmployee authorize(String employeeId) {
        Optional map = findById(employeeId).map(entity -> {
            SysUser sysUser = this.sysUserService.register(entity);
            return (SysUser) Optional.ofNullable(sysUser).map(item -> {
                item.setEmployee(entity);
                return item;
            }).orElse(null);
        });
        SysUserService sysUserService = this.sysUserService;
        Objects.requireNonNull(sysUserService);
        return (SysEmployee) map.map((v1) -> {
            return r1.saveAndFlush(v1);
        }).map((v0) -> {
            return v0.getEmployee();
        }).orElse(null);
    }

    @Override // com.pigx.engine.data.core.jpa.service.BaseJpaWriteableService, com.pigx.engine.data.core.service.BaseService
    @Transactional(rollbackFor = {TransactionalRollbackException.class})
    public void deleteById(String employeeId) {
        this.sysOwnershipService.deleteByEmployeeId(employeeId);
        super.deleteById((SysEmployeeService) employeeId);
    }

    @Transactional(rollbackFor = {TransactionalRollbackException.class})
    public boolean deployAllocatable(List<SysEmployee> sysEmployees, List<SysOwnership> sysOwnerships) {
        if (CollectionUtils.isNotEmpty(sysEmployees) && CollectionUtils.isNotEmpty(sysOwnerships)) {
            List<SysEmployee> result = this.sysEmployeeRepository.saveAllAndFlush(sysEmployees);
            if (CollectionUtils.isNotEmpty(result)) {
                this.sysOwnershipService.saveAll(sysOwnerships);
                return true;
            }
            return false;
        }
        return false;
    }

    @Transactional(rollbackFor = {TransactionalRollbackException.class})
    public boolean removeAllocatable(String organizationId, String departmentId, String employeeId) {
        Optional<SysEmployee> sysEmployee = super.findById(employeeId);
        return ((Boolean) sysEmployee.map(entity -> {
            SysDepartment sysDepartment = new SysDepartment();
            sysDepartment.setDepartmentId(departmentId);
            entity.getDepartments().remove(sysDepartment);
            return (SysEmployee) super.save(entity);
        }).map(result -> {
            this.sysOwnershipService.delete(organizationId, departmentId, employeeId);
            return true;
        }).orElse(false)).booleanValue();
    }

    public SysEmployee findByEmployeeName(String employeeName) {
        return this.sysEmployeeRepository.findByEmployeeName(employeeName);
    }
}
