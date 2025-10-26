package com.pigx.engine.assistant.access.definition;

import com.pigx.engine.assistant.access.definition.domain.AccessResponse;
import com.pigx.engine.assistant.access.definition.domain.AccessUserDetails;
import com.pigx.engine.core.identity.domain.AccessPrincipal;

/* loaded from: assistant-module-access-3.5.7.0.jar:cn/herodotus/engine/assistant/access/definition/AccessHandler.class */
public interface AccessHandler {
    AccessResponse preProcess(String core, String... params);

    AccessUserDetails loadUserDetails(String source, AccessPrincipal accessPrincipal);
}
