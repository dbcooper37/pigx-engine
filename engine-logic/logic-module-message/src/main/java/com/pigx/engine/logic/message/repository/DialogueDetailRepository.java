package com.pigx.engine.logic.message.repository;

import com.pigx.engine.data.core.jpa.repository.BaseJpaRepository;
import com.pigx.engine.logic.message.entity.DialogueDetail;


public interface DialogueDetailRepository extends BaseJpaRepository<DialogueDetail, String> {

    void deleteAllByDialogueId(String dialogueId);
}
