package com.pigx.engine.core.identity.strategy;

import com.pigx.engine.core.identity.domain.HerodotusPermission;
import java.util.List;

/* loaded from: core-identity-3.5.7.0.jar:cn/herodotus/engine/core/identity/strategy/StrategyPermissionDetailsService.class */
public interface StrategyPermissionDetailsService {
    List<HerodotusPermission> findAll();
}
