package com.pigx.engine.message.websocket.servlet.controller;

import com.pigx.engine.core.definition.domain.Result;
import com.pigx.engine.message.websocket.servlet.definition.WebSocketMessageSender;
import com.pigx.engine.message.websocket.servlet.utils.WebSocketUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping({"/message/websocket"})
@Tags({@Tag(name = "消息接口"), @Tag(name = "WebSocket消息接口")})
@RestController
/* loaded from: message-module-websocket-servlet-3.5.7.0.jar:cn/herodotus/engine/message/websocket/servlet/controller/WebSocketMessageController.class */
public class WebSocketMessageController {
    private final WebSocketMessageSender webSocketMessageSender;

    public WebSocketMessageController(WebSocketMessageSender webSocketMessageSender) {
        this.webSocketMessageSender = webSocketMessageSender;
    }

    @PostMapping({"/send/notice"})
    @Operation(summary = "后端发送通知", description = "后端发送 WebSocket 广播通知接口", requestBody = @RequestBody(content = {@Content(mediaType = "application/json")}), responses = {@ApiResponse(description = "是否成功", content = {@Content(mediaType = "application/json")})})
    @Parameters({@Parameter(name = "message", required = true, description = "消息实体")})
    public Result<String> sendNotice(@org.springframework.web.bind.annotation.RequestBody String message) {
        if (StringUtils.isNotBlank(message)) {
            this.webSocketMessageSender.announcement(message);
        }
        return Result.success(message);
    }

    @GetMapping({"/stat"})
    @Operation(summary = "获取统计信息", description = "获取WebSocket相关的统计信息")
    public Result<Map<String, Object>> findAllStat() {
        Map<String, Object> stat = new HashMap<>();
        stat.put("onlineCount", Integer.valueOf(WebSocketUtils.getOnlineCount()));
        return Result.success("获取统计信息成功", stat);
    }
}
