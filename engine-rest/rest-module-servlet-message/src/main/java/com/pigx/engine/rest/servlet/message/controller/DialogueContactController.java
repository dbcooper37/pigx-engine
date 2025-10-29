package com.pigx.engine.rest.servlet.message.controller;

import com.pigx.engine.core.definition.domain.Result;
import com.pigx.engine.data.core.jpa.service.BaseJpaWriteableService;
import com.pigx.engine.logic.message.entity.DialogueContact;
import com.pigx.engine.logic.message.service.DialogueContactService;
import com.pigx.engine.web.api.servlet.AbstractJpaWriteableController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
@RequestMapping("/message/dialogue/contact")
@Tags({
        @Tag(name = "消息管理接口"),
        @Tag(name = "私信管理接口"),
        @Tag(name = "私信联系人管理接口")
})
public class DialogueContactController extends AbstractJpaWriteableController<DialogueContact, String> {

    private final DialogueContactService dialogueContactService;

    public DialogueContactController(DialogueContactService dialogueContactService) {
        this.dialogueContactService = dialogueContactService;
    }

    @Override
    public BaseJpaWriteableService<DialogueContact, String> getService() {
        return dialogueContactService;
    }

    @Operation(summary = "条件查询私信联系人分页数据", description = "根据输入的字段条件查询联系人信息",
            responses = {@ApiResponse(description = "联系人列表", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Map.class)))})
    @Parameters({
            @Parameter(name = "pageNumber", required = true, description = "当前页码", schema = @Schema(type = "integer")),
            @Parameter(name = "pageSize", required = true, description = "每页显示数量", schema = @Schema(type = "integer")),
            @Parameter(name = "receiverId", required = true, description = "收信人ID，即当前用户ID"),
    })
    @GetMapping("/condition")
    public Result<Map<String, Object>> findByCondition(
            @NotNull @RequestParam("pageNumber") Integer pageNumber,
            @NotNull @RequestParam("pageSize") Integer pageSize,
            @NotNull @RequestParam("receiverId") String receiverId) {
        Page<DialogueContact> pages = dialogueContactService.findByCondition(pageNumber, pageSize, receiverId);
        return resultFromPage(pages);
    }
}
