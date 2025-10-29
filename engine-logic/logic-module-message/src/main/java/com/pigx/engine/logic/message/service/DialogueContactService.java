package com.pigx.engine.logic.message.service;

import com.pigx.engine.data.core.jpa.repository.BaseJpaRepository;
import com.pigx.engine.data.core.jpa.service.AbstractJpaService;
import com.pigx.engine.logic.message.entity.Dialogue;
import com.pigx.engine.logic.message.entity.DialogueContact;
import com.pigx.engine.logic.message.entity.DialogueDetail;
import com.pigx.engine.logic.message.repository.DialogueContactRepository;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class DialogueContactService extends AbstractJpaService<DialogueContact, String> {

    private final DialogueContactRepository dialogueContactRepository;

    public DialogueContactService(DialogueContactRepository dialogueContactRepository) {
        this.dialogueContactRepository = dialogueContactRepository;
    }

    @Override
    public BaseJpaRepository<DialogueContact, String> getRepository() {
        return dialogueContactRepository;
    }

    public List<DialogueContact> createContact(Dialogue dialogue, DialogueDetail dialogueDetail) {
        DialogueContact contact = new DialogueContact();
        contact.setDialogue(dialogue);
        contact.setReceiverId(dialogueDetail.getReceiverId());
        contact.setSenderId(dialogueDetail.getSenderId());
        contact.setSenderName(dialogueDetail.getSenderName());
        contact.setSenderAvatar(dialogueDetail.getSenderAvatar());

        DialogueContact reverseContext = new DialogueContact();
        reverseContext.setDialogue(dialogue);
        reverseContext.setReceiverId(dialogueDetail.getSenderId());
        reverseContext.setSenderId(dialogueDetail.getReceiverId());
        reverseContext.setSenderName(dialogueDetail.getReceiverName());
        reverseContext.setSenderAvatar(dialogueDetail.getReceiverAvatar());

        List<DialogueContact> personalContacts = new ArrayList<>();
        personalContacts.add(contact);
        personalContacts.add(reverseContext);

        return this.saveAll(personalContacts);
    }

    public Page<DialogueContact> findByCondition(int pageNumber, int pageSize, String receiverId) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        Specification<DialogueContact> specification = (root, criteriaQuery, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();

            predicates.add(criteriaBuilder.equal(root.get("receiverId"), receiverId));

            Predicate[] predicateArray = new Predicate[predicates.size()];
            criteriaQuery.where(criteriaBuilder.and(predicates.toArray(predicateArray)));
            criteriaQuery.orderBy(criteriaBuilder.desc(root.get("createTime")));
            return criteriaQuery.getRestriction();
        };

        return this.findByPage(specification, pageable);
    }

    public void deleteByDialogueId(String dialogueId) {
        dialogueContactRepository.deleteAllByDialogueId(dialogueId);
    }

    public DialogueContact findBySenderIdAndReceiverId(String senderId, String receiverId) {
        return dialogueContactRepository.findBySenderIdAndReceiverId(senderId, receiverId).orElse(null);
    }
}
