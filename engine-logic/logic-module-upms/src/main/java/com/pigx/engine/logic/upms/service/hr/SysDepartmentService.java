package com.pigx.engine.logic.upms.service.hr;

import com.pigx.engine.data.core.jpa.repository.BaseJpaRepository;
import com.pigx.engine.data.core.jpa.service.AbstractJpaService;
import com.pigx.engine.logic.upms.entity.hr.SysDepartment;
import com.pigx.engine.logic.upms.repository.hr.SysDepartmentRepository;
import jakarta.persistence.criteria.Predicate;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class SysDepartmentService extends AbstractJpaService<SysDepartment, String> {

    private final SysDepartmentRepository sysDepartmentRepository;
    private final SysOwnershipService sysOwnershipService;

    public SysDepartmentService(SysDepartmentRepository sysDepartmentRepository, SysOwnershipService sysOwnershipService) {
        this.sysDepartmentRepository = sysDepartmentRepository;
        this.sysOwnershipService = sysOwnershipService;
    }

    @Override
    public BaseJpaRepository<SysDepartment, String> getRepository() {
        return sysDepartmentRepository;
    }

    public Page<SysDepartment> findByCondition(int pageNumber, int pageSize, String organizationId) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        Specification<SysDepartment> specification = (root, criteriaQuery, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (ObjectUtils.isNotEmpty(organizationId)) {
                predicates.add(criteriaBuilder.equal(root.get("organizationId"), organizationId));
            }

            Predicate[] predicateArray = new Predicate[predicates.size()];
            criteriaQuery.where(criteriaBuilder.and(predicates.toArray(predicateArray)));
            return criteriaQuery.getRestriction();
        };

        return this.findByPage(specification, pageable);
    }

    public List<SysDepartment> findAll(String organizationId) {
        if (ObjectUtils.isNotEmpty(organizationId)) {
            return sysDepartmentRepository.findByOrganizationId(organizationId);
        } else {
            return sysDepartmentRepository.findAll();
        }
    }

    @Override
    public void deleteById(String departmentId) {
        sysOwnershipService.deleteByDepartmentId(departmentId);
        super.deleteById(departmentId);
    }
}
