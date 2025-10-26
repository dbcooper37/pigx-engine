package com.pigx.engine.web.core.definition;

import io.swagger.v3.oas.models.servers.Server;
import java.util.List;

/* loaded from: web-core-3.5.7.0.jar:cn/herodotus/engine/web/core/definition/OpenApiServerResolver.class */
public interface OpenApiServerResolver {
    List<Server> getServers();
}
