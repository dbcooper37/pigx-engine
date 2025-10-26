package com.pigx.engine.logic.message.entity;

import com.pigx.engine.core.definition.constant.ErrorCodeMapperBuilderOrdered;
import com.pigx.engine.logic.message.constant.LogicMessageConstants;
import com.pigx.engine.logic.message.domain.AbstractSenderEntity;
import com.google.common.base.MoreObjects;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Cacheable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.UuidGenerator;

@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = LogicMessageConstants.REGION_MESSAGE_DIALOGUE_DETAIL)
@Schema(name = "私信对话详情")
@Cacheable
@Entity
@Table(name = "msg_dialogue_detail", indexes = {@Index(name = "msg_dialogue_detail_id_idx", columnList = "detail_id"), @Index(name = "msg_dialogue_detail_sid_idx", columnList = "sender_id"), @Index(name = "msg_dialogue_detail_rid_idx", columnList = "receiver_id"), @Index(name = "msg_dialogue_detail_did_idx", columnList = "dialogue_id")})
/* loaded from: logic-module-message-3.5.7.0.jar:cn/herodotus/engine/logic/message/entity/DialogueDetail.class */
public class DialogueDetail extends AbstractSenderEntity {

    @Id
    @Schema(name = "对话详情ID")
    @UuidGenerator
    @Column(name = "detail_id", length = 64)
    private String detailId;

    @Column(name = "receiver_id", length = 64)
    @Schema(name = "接收人ID")
    private String receiverId;

    @Column(name = "receiver_name", length = ErrorCodeMapperBuilderOrdered.MESSAGE)
    @Schema(name = "接收人名称", title = "冗余信息，增加该字段减少重复查询")
    private String receiverName;

    @Column(name = "receiver_avatar", length = 1000)
    @Schema(name = "发送人头像")
    private String receiverAvatar;

    @Column(name = "content", columnDefinition = "TEXT")
    @Schema(name = "公告内容")
    private String content;

    @Column(name = "dialogue_id", length = 64)
    @Schema(name = "对话ID")
    private String dialogueId;

    public String getDetailId() {
        return this.detailId;
    }

    public void setDetailId(String detailId) {
        this.detailId = detailId;
    }

    public String getReceiverId() {
        return this.receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getReceiverName() {
        return this.receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverAvatar() {
        return this.receiverAvatar;
    }

    public void setReceiverAvatar(String receiverAvatar) {
        this.receiverAvatar = receiverAvatar;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDialogueId() {
        return this.dialogueId;
    }

    public void setDialogueId(String dialogueId) {
        this.dialogueId = dialogueId;
    }

    @Override // com.pigx.engine.data.core.jpa.entity.AbstractAuditEntity, com.pigx.engine.data.core.jpa.entity.AbstractEntity
    public String toString() {
        return MoreObjects.toStringHelper(this).add("detailId", this.detailId).add("receiverId", this.receiverId).add("receiverName", this.receiverName).add("receiverAvatar", this.receiverAvatar).add("content", this.content).add("dialogueId", this.dialogueId).toString();
    }
}
