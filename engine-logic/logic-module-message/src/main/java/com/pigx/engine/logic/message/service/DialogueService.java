package com.pigx.engine.logic.message.service;

import com.pigx.engine.data.core.jpa.repository.BaseJpaRepository;
import com.pigx.engine.data.core.jpa.service.AbstractJpaService;
import com.pigx.engine.logic.message.entity.Dialogue;
import com.pigx.engine.logic.message.repository.DialogueRepository;
import org.springframework.stereotype.Service;

@Service
/* loaded from: logic-module-message-3.5.7.0.jar:cn/herodotus/engine/logic/message/service/DialogueService.class */
public class DialogueService extends AbstractJpaService<Dialogue, String> {
    private final DialogueRepository dialogueRepository;

    public DialogueService(DialogueRepository dialogueRepository) {
        this.dialogueRepository = dialogueRepository;
    }

    @Override // com.pigx.engine.data.core.jpa.service.BaseJpaReadableService
    public BaseJpaRepository<Dialogue, String> getRepository() {
        return this.dialogueRepository;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public Dialogue createDialogue(String content) {
        Dialogue dialogue = new Dialogue();
        dialogue.setLatestNews(content);
        return (Dialogue) save(dialogue);
    }

    public Dialogue updateDialogue(String dialogueId, String content) {
        return (Dialogue) findById(dialogueId).map(entity -> {
            entity.setLatestNews(content);
            return entity;
        }).map((v1) -> {
            return save(v1);
        }).orElse(null);
    }
}
