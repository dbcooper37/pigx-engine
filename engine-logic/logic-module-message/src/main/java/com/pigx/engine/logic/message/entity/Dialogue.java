package com.pigx.engine.logic.message.entity;

import com.pigx.engine.data.core.jpa.entity.AbstractAuditEntity;
import com.pigx.engine.logic.message.constant.LogicMessageConstants;
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

@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = LogicMessageConstants.REGION_MESSAGE_DIALOGUE)
@Schema(name = "私信对话")
@Cacheable
@Entity
@Table(name = "msg_dialogue", indexes = {@Index(name = "msg_dialogue_id_idx", columnList = "dialogue_id")})
/* loaded from: logic-module-message-3.5.7.0.jar:cn/herodotus/engine/logic/message/entity/Dialogue.class */
public class Dialogue extends AbstractAuditEntity {

    @Id
    @Schema(name = "对话ID")
    @UuidGenerator
    @Column(name = "dialogue_id", length = 64)
    private String dialogueId;

    @Column(name = "latest_news", columnDefinition = "TEXT")
    @Schema(name = "最新内容")
    private String latestNews;

    public String getDialogueId() {
        return this.dialogueId;
    }

    public void setDialogueId(String dialogueId) {
        this.dialogueId = dialogueId;
    }

    public String getLatestNews() {
        return this.latestNews;
    }

    public void setLatestNews(String latestNews) {
        this.latestNews = latestNews;
    }

    @Override // com.pigx.engine.data.core.jpa.entity.AbstractAuditEntity, com.pigx.engine.data.core.jpa.entity.AbstractEntity
    public String toString() {
        return MoreObjects.toStringHelper(this).add("dialogueId", this.dialogueId).add("latestNews", this.latestNews).toString();
    }
}
