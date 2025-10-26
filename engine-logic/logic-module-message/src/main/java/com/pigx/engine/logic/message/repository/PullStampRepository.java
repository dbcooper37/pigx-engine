package com.pigx.engine.logic.message.repository;

import com.pigx.engine.data.core.jpa.repository.BaseJpaRepository;
import com.pigx.engine.logic.message.entity.PullStamp;
import jakarta.persistence.QueryHint;
import java.util.Optional;
import org.springframework.data.jpa.repository.QueryHints;

/* loaded from: logic-module-message-3.5.7.0.jar:cn/herodotus/engine/logic/message/repository/PullStampRepository.class */
public interface PullStampRepository extends BaseJpaRepository<PullStamp, String> {
    @QueryHints({@QueryHint(name = "org.hibernate.cacheable", value = "true")})
    Optional<PullStamp> findByUserId(String userId);
}
