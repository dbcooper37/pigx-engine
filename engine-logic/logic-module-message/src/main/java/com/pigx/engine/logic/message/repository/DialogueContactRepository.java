package com.pigx.engine.logic.message.repository;

import com.pigx.engine.core.foundation.exception.transaction.TransactionalRollbackException;
import com.pigx.engine.data.core.jpa.repository.BaseJpaRepository;
import com.pigx.engine.logic.message.entity.DialogueContact;
import jakarta.persistence.QueryHint;
import java.util.Optional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/* loaded from: logic-module-message-3.5.7.0.jar:cn/herodotus/engine/logic/message/repository/DialogueContactRepository.class */
public interface DialogueContactRepository extends BaseJpaRepository<DialogueContact, String> {
    @Modifying
    @Transactional(rollbackFor = {TransactionalRollbackException.class})
    @Query("delete from DialogueContact c where c.dialogue.dialogueId = :id")
    void deleteAllByDialogueId(@Param("id") String dialogueId);

    @QueryHints({@QueryHint(name = "org.hibernate.cacheable", value = "true")})
    Optional<DialogueContact> findBySenderIdAndReceiverId(String senderId, String receiverId);
}
