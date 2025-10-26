package com.pigx.engine.logic.message.entity;

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

@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = LogicMessageConstants.REGION_MESSAGE_ANNOUNCEMENT)
@Schema(name = "系统公告")
@Cacheable
@Entity
@Table(name = "msg_announcement", indexes = {@Index(name = "msg_announcement_id_idx", columnList = "announcement_id")})
/* loaded from: logic-module-message-3.5.7.0.jar:cn/herodotus/engine/logic/message/entity/Announcement.class */
public class Announcement extends AbstractSenderEntity {

    @Id
    @Schema(name = "公告ID")
    @UuidGenerator
    @Column(name = "announcement_id", length = 64)
    private String announcementId;

    @Column(name = "title", length = 128)
    @Schema(name = "公告标题")
    private String title;

    @Column(name = "content", columnDefinition = "TEXT")
    @Schema(name = "公告内容")
    private String content;

    public String getAnnouncementId() {
        return this.announcementId;
    }

    public void setAnnouncementId(String announcementId) {
        this.announcementId = announcementId;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override // com.pigx.engine.data.core.jpa.entity.AbstractAuditEntity, com.pigx.engine.data.core.jpa.entity.AbstractEntity
    public String toString() {
        return MoreObjects.toStringHelper(this).add("announcementId", this.announcementId).add("title", this.title).add("content", this.content).toString();
    }
}
