package com.pigx.engine.logic.upms.repository.hr;

import com.pigx.engine.data.core.jpa.repository.BaseJpaRepository;
import com.pigx.engine.logic.upms.entity.hr.SysOrganization;
import com.pigx.engine.logic.upms.enums.OrganizationCategory;

import java.util.List;


public interface SysOrganizationRepository extends BaseJpaRepository<SysOrganization, String> {

    /**
     * 根据组织分类查询组织
     *
     * @param category 组织分类 {@link OrganizationCategory}
     * @return 组织列表
     */
    List<SysOrganization> findByCategory(OrganizationCategory category);

}
