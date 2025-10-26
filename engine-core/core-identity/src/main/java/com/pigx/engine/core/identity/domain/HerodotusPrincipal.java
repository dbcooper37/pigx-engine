package com.pigx.engine.core.identity.domain;

import java.security.Principal;

/* loaded from: core-identity-3.5.7.0.jar:cn/herodotus/engine/core/identity/domain/HerodotusPrincipal.class */
public interface HerodotusPrincipal extends Principal {
    String getId();

    String getAvatar();

    String getEmail();
}
