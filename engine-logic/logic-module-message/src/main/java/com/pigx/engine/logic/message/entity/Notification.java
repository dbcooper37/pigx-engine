package com.pigx.engine.logic.message.entity;

import com.pigx.engine.logic.message.constant.LogicMessageConstants;
import com.pigx.engine.logic.message.domain.AbstractSenderEntity;
import com.pigx.engine.logic.message.enums.NotificationCategory;
import com.google.common.base.MoreObjects;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Cacheable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.UuidGenerator;

@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = LogicMessageConstants.REGION_MESSAGE_NOTIFICATION)
@Schema(name = "通知队列")
@Cacheable
@Entity
@Table(name = "msg_notification", indexes = {@Index(name = "msg_notification_id_idx", columnList = "queue_id"), @Index(name = "msg_notification_sid_idx", columnList = "user_id")})
/* loaded from: logic-module-message-3.5.7.0.jar:cn/herodotus/engine/logic/message/entity/Notification.class */
public class Notification extends AbstractSenderEntity {

    @Id
    @Schema(name = "队列ID")
    @UuidGenerator
    @Column(name = "queue_id", length = 64)
    private String queueId;

    @Column(name = "user_id", length = 64)
    @Schema(name = "用户ID")
    private String userId;

    @Column(name = "content", columnDefinition = "TEXT")
    @Schema(name = "公告内容")
    private String content;

    @Column(name = "is_read")
    @Schema(name = "是否已经读取", title = "false 未读，true 已读")
    private Boolean read = false;

    @Column(name = "category")
    @Enumerated(EnumType.ORDINAL)
    @Schema(name = "通知类别", title = "1. 公告，2.私信")
    private NotificationCategory category = NotificationCategory.ANNOUNCEMENT;

    public String getQueueId() {
        return this.queueId;
    }

    public void setQueueId(String queueId) {
        this.queueId = queueId;
    }

    public Boolean getRead() {
        return this.read;
    }

    public void setRead(Boolean read) {
        this.read = read;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public NotificationCategory getCategory() {
        return this.category;
    }

    public void setCategory(NotificationCategory category) {
        this.category = category;
    }

    @Override // com.pigx.engine.data.core.jpa.entity.AbstractAuditEntity, com.pigx.engine.data.core.jpa.entity.AbstractEntity
    public String toString() {
        return MoreObjects.toStringHelper(this).add("queueId", this.queueId).add("read", this.read).add("userId", this.userId).add("content", this.content).add("category", this.category).toString();
    }
}
