package com.pigx.engine.logic.upms.service.security;

import com.pigx.engine.data.core.jpa.repository.BaseJpaRepository;
import com.pigx.engine.data.core.jpa.service.AbstractJpaService;
import com.pigx.engine.logic.upms.entity.security.SysSocialUser;
import com.pigx.engine.logic.upms.repository.security.SysSocialUserRepository;
import org.springframework.stereotype.Service;

@Service
/* loaded from: logic-module-upms-3.5.7.0.jar:cn/herodotus/engine/logic/upms/service/security/SysSocialUserService.class */
public class SysSocialUserService extends AbstractJpaService<SysSocialUser, String> {
    private final SysSocialUserRepository sysSocialUserRepository;

    public SysSocialUserService(SysSocialUserRepository sysSocialUserRepository) {
        this.sysSocialUserRepository = sysSocialUserRepository;
    }

    @Override // com.pigx.engine.data.core.jpa.service.BaseJpaReadableService
    public BaseJpaRepository<SysSocialUser, String> getRepository() {
        return this.sysSocialUserRepository;
    }

    public SysSocialUser findByUuidAndSource(String uuid, String source) {
        return this.sysSocialUserRepository.findSysSocialUserByUuidAndSource(uuid, source);
    }
}
