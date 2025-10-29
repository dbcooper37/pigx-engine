package com.pigx.engine.logic.upms.repository.hr;

import com.pigx.engine.data.core.jpa.repository.BaseJpaRepository;
import com.pigx.engine.logic.upms.entity.hr.SysEmployee;
import jakarta.persistence.QueryHint;
import org.hibernate.jpa.AvailableHints;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.QueryHints;


public interface SysEmployeeRepository extends BaseJpaRepository<SysEmployee, String> {

    /**
     * 根据人员性名查找SysEmployee
     *
     * @param employeeName 人员姓名
     * @return {@link SysEmployee}
     */
    @QueryHints(@QueryHint(name = AvailableHints.HINT_CACHEABLE, value = "true"))
    @EntityGraph(value = "SysEmployeeWithSysUser.Graph", type = EntityGraph.EntityGraphType.FETCH)
    SysEmployee findByEmployeeName(String employeeName);

    @QueryHints(@QueryHint(name = AvailableHints.HINT_CACHEABLE, value = "true"))
    @EntityGraph(value = "SysEmployeeWithSysUser.Graph", type = EntityGraph.EntityGraphType.FETCH)
    @Override
    Page<SysEmployee> findAll(Pageable pageable);


}
