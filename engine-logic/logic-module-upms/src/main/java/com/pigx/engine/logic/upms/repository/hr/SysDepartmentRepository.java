package com.pigx.engine.logic.upms.repository.hr;

import com.pigx.engine.data.core.jpa.repository.BaseJpaRepository;
import com.pigx.engine.logic.upms.entity.hr.SysDepartment;
import jakarta.persistence.QueryHint;
import org.hibernate.jpa.AvailableHints;
import org.springframework.data.jpa.repository.QueryHints;

import java.util.List;


public interface SysDepartmentRepository extends BaseJpaRepository<SysDepartment, String> {

    @QueryHints(@QueryHint(name = AvailableHints.HINT_CACHEABLE, value = "true"))
    List<SysDepartment> findByOrganizationId(String organizationId);
}
