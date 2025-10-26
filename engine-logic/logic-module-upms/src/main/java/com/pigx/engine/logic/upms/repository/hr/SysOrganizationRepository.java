package com.pigx.engine.logic.upms.repository.hr;

import com.pigx.engine.data.core.jpa.repository.BaseJpaRepository;
import com.pigx.engine.logic.upms.entity.hr.SysOrganization;
import com.pigx.engine.logic.upms.enums.OrganizationCategory;
import java.util.List;

/* loaded from: logic-module-upms-3.5.7.0.jar:cn/herodotus/engine/logic/upms/repository/hr/SysOrganizationRepository.class */
public interface SysOrganizationRepository extends BaseJpaRepository<SysOrganization, String> {
    List<SysOrganization> findByCategory(OrganizationCategory category);
}
