package com.pigx.engine.logic.upms.repository.security;

import com.pigx.engine.data.core.jpa.repository.BaseJpaRepository;
import com.pigx.engine.logic.upms.entity.security.SysAttribute;
import jakarta.persistence.QueryHint;
import java.util.List;
import org.springframework.data.jpa.repository.QueryHints;

/* loaded from: logic-module-upms-3.5.7.0.jar:cn/herodotus/engine/logic/upms/repository/security/SysAttributeRepository.class */
public interface SysAttributeRepository extends BaseJpaRepository<SysAttribute, String> {
    @QueryHints({@QueryHint(name = "org.hibernate.cacheable", value = "true")})
    List<SysAttribute> findByAttributeIdIn(List<String> ids);

    @QueryHints({@QueryHint(name = "org.hibernate.cacheable", value = "true")})
    List<SysAttribute> findAllByServiceId(String serviceId);
}
