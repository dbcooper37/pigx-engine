package com.pigx.engine.logic.message.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.base.MoreObjects;
import com.pigx.engine.core.definition.constant.SystemConstants;
import com.pigx.engine.core.definition.domain.BaseEntity;
import com.pigx.engine.logic.message.constant.LogicMessageConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.UuidGenerator;

import java.util.Date;


@Schema(name = "拉取标记")
@Entity
@Table(name = "msg_pull_stamp", indexes = {
        @Index(name = "msg_pull_stamp_id_idx", columnList = "stamp_id"),
        @Index(name = "msg_pull_stamp_sid_idx", columnList = "user_id")
})
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = LogicMessageConstants.REGION_MESSAGE_PULL_STAMP)
public class PullStamp implements BaseEntity {

    @Id
    @UuidGenerator
    @Column(name = "stamp_id", length = 64)
    private String stampId;

    @Schema(name = "用户ID")
    @Column(name = "user_id", length = 64)
    private String userId;

    @Schema(name = "来源", title = "预留字段，以备支持不同端的情况")
    @Column(name = "source", length = 50)
    private String source;

    @Schema(name = "上次拉取时间")
    @Column(name = "latest_pull_time", updatable = false)
    @JsonFormat(pattern = SystemConstants.DATE_TIME_FORMAT)
    private Date latestPullTime = new Date();

    public String getStampId() {
        return stampId;
    }

    public void setStampId(String stampId) {
        this.stampId = stampId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Date getLatestPullTime() {
        return latestPullTime;
    }

    public void setLatestPullTime(Date latestPullTime) {
        this.latestPullTime = latestPullTime;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("stampId", stampId)
                .add("userId", userId)
                .add("source", source)
                .add("latestPullTime", latestPullTime)
                .toString();
    }
}
