package com.pigx.engine.logic.upms.service.security;

import com.pigx.engine.data.core.jpa.repository.BaseJpaRepository;
import com.pigx.engine.data.core.jpa.service.AbstractJpaService;
import com.pigx.engine.logic.upms.entity.security.SysSocialUser;
import com.pigx.engine.logic.upms.repository.security.SysSocialUserRepository;
import org.springframework.stereotype.Service;


@Service
public class SysSocialUserService extends AbstractJpaService<SysSocialUser, String> {

    private final SysSocialUserRepository sysSocialUserRepository;

    public SysSocialUserService(SysSocialUserRepository sysSocialUserRepository) {
        this.sysSocialUserRepository = sysSocialUserRepository;
    }

    @Override
    public BaseJpaRepository<SysSocialUser, String> getRepository() {
        return sysSocialUserRepository;
    }

    public SysSocialUser findByUuidAndSource(String uuid, String source) {
        return sysSocialUserRepository.findSysSocialUserByUuidAndSource(uuid, source);
    }
}
