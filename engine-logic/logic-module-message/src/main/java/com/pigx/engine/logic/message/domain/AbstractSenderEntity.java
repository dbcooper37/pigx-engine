package com.pigx.engine.logic.message.domain;

import com.pigx.engine.core.definition.constant.ErrorCodeMapperBuilderOrdered;
import com.pigx.engine.data.core.jpa.entity.AbstractAuditEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
/* loaded from: logic-module-message-3.5.7.0.jar:cn/herodotus/engine/logic/message/domain/AbstractSenderEntity.class */
public abstract class AbstractSenderEntity extends AbstractAuditEntity {

    @Column(name = "sender_id", length = 64)
    @Schema(name = "发送人ID")
    private String senderId;

    @Column(name = "sender_name", length = ErrorCodeMapperBuilderOrdered.MESSAGE)
    @Schema(name = "发送人名称", title = "冗余信息，增加该字段减少重复查询")
    private String senderName;

    @Column(name = "sender_avatar", length = 1000)
    @Schema(name = "发送人头像")
    private String senderAvatar;

    public String getSenderId() {
        return this.senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getSenderName() {
        return this.senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderAvatar() {
        return this.senderAvatar;
    }

    public void setSenderAvatar(String senderAvatar) {
        this.senderAvatar = senderAvatar;
    }
}
