package com.pigx.engine.logic.message.repository;

import com.pigx.engine.core.foundation.exception.transaction.TransactionalRollbackException;
import com.pigx.engine.data.core.jpa.repository.BaseJpaRepository;
import com.pigx.engine.logic.message.entity.DialogueContact;
import jakarta.persistence.QueryHint;
import org.hibernate.jpa.AvailableHints;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


public interface DialogueContactRepository extends BaseJpaRepository<DialogueContact, String> {

    @Transactional(rollbackFor = TransactionalRollbackException.class)
    @Modifying
    @Query("delete from DialogueContact c where c.dialogue.dialogueId = :id")
    void deleteAllByDialogueId(@Param("id") String dialogueId);

    @QueryHints(@QueryHint(name = AvailableHints.HINT_CACHEABLE, value = "true"))
    Optional<DialogueContact> findBySenderIdAndReceiverId(String senderId, String receiverId);
}
