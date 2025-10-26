package com.pigx.engine.logic.message.entity;

import com.pigx.engine.core.definition.constant.ErrorCodeMapperBuilderOrdered;
import com.pigx.engine.core.definition.constant.SystemConstants;
import com.pigx.engine.core.definition.domain.BaseEntity;
import com.pigx.engine.logic.message.constant.LogicMessageConstants;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.base.MoreObjects;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Cacheable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import java.util.Date;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.UuidGenerator;

@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = LogicMessageConstants.REGION_MESSAGE_PULL_STAMP)
@Schema(name = "拉取标记")
@Cacheable
@Entity
@Table(name = "msg_pull_stamp", indexes = {@Index(name = "msg_pull_stamp_id_idx", columnList = "stamp_id"), @Index(name = "msg_pull_stamp_sid_idx", columnList = "user_id")})
/* loaded from: logic-module-message-3.5.7.0.jar:cn/herodotus/engine/logic/message/entity/PullStamp.class */
public class PullStamp implements BaseEntity {

    @Id
    @Column(name = "stamp_id", length = 64)
    @UuidGenerator
    private String stampId;

    @Column(name = "user_id", length = 64)
    @Schema(name = "用户ID")
    private String userId;

    @Column(name = SystemConstants.SOURCE, length = ErrorCodeMapperBuilderOrdered.MESSAGE)
    @Schema(name = "来源", title = "预留字段，以备支持不同端的情况")
    private String source;

    @Column(name = "latest_pull_time", updatable = false)
    @Schema(name = "上次拉取时间")
    @JsonFormat(pattern = SystemConstants.DATE_TIME_FORMAT)
    private Date latestPullTime = new Date();

    public String getStampId() {
        return this.stampId;
    }

    public void setStampId(String stampId) {
        this.stampId = stampId;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSource() {
        return this.source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Date getLatestPullTime() {
        return this.latestPullTime;
    }

    public void setLatestPullTime(Date latestPullTime) {
        this.latestPullTime = latestPullTime;
    }

    public String toString() {
        return MoreObjects.toStringHelper(this).add("stampId", this.stampId).add("userId", this.userId).add(SystemConstants.SOURCE, this.source).add("latestPullTime", this.latestPullTime).toString();
    }
}
