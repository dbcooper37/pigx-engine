package com.pigx.engine.logic.message.repository;

import com.pigx.engine.core.foundation.exception.transaction.TransactionalRollbackException;
import com.pigx.engine.data.core.jpa.repository.BaseJpaRepository;
import com.pigx.engine.logic.message.entity.Notification;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/* loaded from: logic-module-message-3.5.7.0.jar:cn/herodotus/engine/logic/message/repository/NotificationRepository.class */
public interface NotificationRepository extends BaseJpaRepository<Notification, String> {
    @Modifying
    @Transactional(rollbackFor = {TransactionalRollbackException.class})
    @Query("update Notification n set n.read = true where n.userId = :userId")
    int updateAllRead(@Param("userId") String userId);
}
