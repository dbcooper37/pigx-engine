package com.pigx.engine.web.core.definition;

import io.swagger.v3.oas.models.servers.Server;

import java.util.List;


public interface OpenApiServerResolver {

    /**
     * 获取 Open Api 所需的 Server 地址。
     *
     * @return Open Api Servers 值
     */
    List<Server> getServers();
}
