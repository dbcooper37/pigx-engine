package com.pigx.engine.logic.upms.repository.hr;

import com.pigx.engine.data.core.jpa.repository.BaseJpaRepository;
import com.pigx.engine.logic.upms.entity.hr.SysEmployee;
import jakarta.persistence.QueryHint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.QueryHints;

/* loaded from: logic-module-upms-3.5.7.0.jar:cn/herodotus/engine/logic/upms/repository/hr/SysEmployeeRepository.class */
public interface SysEmployeeRepository extends BaseJpaRepository<SysEmployee, String> {
    @QueryHints({@QueryHint(name = "org.hibernate.cacheable", value = "true")})
    @EntityGraph(value = "SysEmployeeWithSysUser.Graph", type = EntityGraph.EntityGraphType.FETCH)
    SysEmployee findByEmployeeName(String employeeName);

    @Override // com.pigx.engine.data.core.jpa.repository.BaseJpaRepository
    @QueryHints({@QueryHint(name = "org.hibernate.cacheable", value = "true")})
    @EntityGraph(value = "SysEmployeeWithSysUser.Graph", type = EntityGraph.EntityGraphType.FETCH)
    Page<SysEmployee> findAll(Pageable pageable);
}
