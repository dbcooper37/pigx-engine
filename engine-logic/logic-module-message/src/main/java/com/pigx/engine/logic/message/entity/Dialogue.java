package com.pigx.engine.logic.message.entity;

import com.google.common.base.MoreObjects;
import com.pigx.engine.data.core.jpa.entity.AbstractAuditEntity;
import com.pigx.engine.logic.message.constant.LogicMessageConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.UuidGenerator;


@Schema(name = "私信对话")
@Entity
@Table(name = "msg_dialogue", indexes = {@Index(name = "msg_dialogue_id_idx", columnList = "dialogue_id")})
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = LogicMessageConstants.REGION_MESSAGE_DIALOGUE)
public class Dialogue extends AbstractAuditEntity {

    @Schema(name = "对话ID")
    @Id
    @UuidGenerator
    @Column(name = "dialogue_id", length = 64)
    private String dialogueId;

    @Schema(name = "最新内容")
    @Column(name = "latest_news", columnDefinition = "TEXT")
    private String latestNews;

    public String getDialogueId() {
        return dialogueId;
    }

    public void setDialogueId(String dialogueId) {
        this.dialogueId = dialogueId;
    }

    public String getLatestNews() {
        return latestNews;
    }

    public void setLatestNews(String latestNews) {
        this.latestNews = latestNews;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("dialogueId", dialogueId)
                .add("latestNews", latestNews)
                .toString();
    }
}
