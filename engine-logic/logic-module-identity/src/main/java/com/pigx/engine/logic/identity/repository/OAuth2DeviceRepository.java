package com.pigx.engine.logic.identity.repository;

import com.pigx.engine.data.core.jpa.repository.BaseJpaRepository;
import com.pigx.engine.logic.identity.entity.OAuth2Device;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/* loaded from: logic-module-identity-3.5.7.0.jar:cn/herodotus/engine/logic/identity/repository/OAuth2DeviceRepository.class */
public interface OAuth2DeviceRepository extends BaseJpaRepository<OAuth2Device, String> {
    OAuth2Device findByClientId(String clientId);

    @Modifying
    @Transactional
    @Query("update OAuth2Device d set d.activated = ?2 where d.clientId = ?1")
    int activate(String clientId, boolean isActivated);
}
