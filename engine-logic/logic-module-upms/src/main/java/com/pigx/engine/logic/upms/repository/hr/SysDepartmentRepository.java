package com.pigx.engine.logic.upms.repository.hr;

import com.pigx.engine.data.core.jpa.repository.BaseJpaRepository;
import com.pigx.engine.logic.upms.entity.hr.SysDepartment;
import jakarta.persistence.QueryHint;
import java.util.List;
import org.springframework.data.jpa.repository.QueryHints;

/* loaded from: logic-module-upms-3.5.7.0.jar:cn/herodotus/engine/logic/upms/repository/hr/SysDepartmentRepository.class */
public interface SysDepartmentRepository extends BaseJpaRepository<SysDepartment, String> {
    @QueryHints({@QueryHint(name = "org.hibernate.cacheable", value = "true")})
    List<SysDepartment> findByOrganizationId(String organizationId);
}
