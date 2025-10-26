package com.pigx.engine.logic.message.repository;

import com.pigx.engine.data.core.jpa.repository.BaseJpaRepository;
import com.pigx.engine.logic.message.entity.DialogueDetail;

/* loaded from: logic-module-message-3.5.7.0.jar:cn/herodotus/engine/logic/message/repository/DialogueDetailRepository.class */
public interface DialogueDetailRepository extends BaseJpaRepository<DialogueDetail, String> {
    void deleteAllByDialogueId(String dialogueId);
}
