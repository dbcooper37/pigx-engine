package com.pigx.engine.logic.message.repository;

import com.pigx.engine.data.core.jpa.repository.BaseJpaRepository;
import com.pigx.engine.logic.message.entity.Announcement;
import jakarta.persistence.QueryHint;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.QueryHints;

/* loaded from: logic-module-message-3.5.7.0.jar:cn/herodotus/engine/logic/message/repository/AnnouncementRepository.class */
public interface AnnouncementRepository extends BaseJpaRepository<Announcement, String> {
    @QueryHints({@QueryHint(name = "org.hibernate.cacheable", value = "true")})
    List<Announcement> findAllByCreateTimeAfter(Date stamp);
}
