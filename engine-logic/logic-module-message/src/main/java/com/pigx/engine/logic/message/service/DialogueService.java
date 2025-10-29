package com.pigx.engine.logic.message.service;

import com.pigx.engine.data.core.jpa.repository.BaseJpaRepository;
import com.pigx.engine.data.core.jpa.service.AbstractJpaService;
import com.pigx.engine.logic.message.entity.Dialogue;
import com.pigx.engine.logic.message.repository.DialogueRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class DialogueService extends AbstractJpaService<Dialogue, String> {

    private final DialogueRepository dialogueRepository;

    public DialogueService(DialogueRepository dialogueRepository) {
        this.dialogueRepository = dialogueRepository;
    }

    @Override
    public BaseJpaRepository<Dialogue, String> getRepository() {
        return dialogueRepository;
    }

    public Dialogue createDialogue(String content) {
        Dialogue dialogue = new Dialogue();
        dialogue.setLatestNews(content);
        return this.save(dialogue);
    }

    public Dialogue updateDialogue(String dialogueId, String content) {
        Optional<Dialogue> dialogue = this.findById(dialogueId);

        return dialogue.map(entity -> {
                    entity.setLatestNews(content);
                    return entity;
                })
                .map(this::save)
                .orElse(null);
    }
}
