package com.pigx.engine.logic.message.service;

import com.pigx.engine.data.core.jpa.repository.BaseJpaRepository;
import com.pigx.engine.data.core.jpa.service.AbstractJpaService;
import com.pigx.engine.logic.message.entity.Dialogue;
import com.pigx.engine.logic.message.entity.DialogueContact;
import com.pigx.engine.logic.message.entity.DialogueDetail;
import com.pigx.engine.logic.message.repository.DialogueContactRepository;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Predicate;
import java.lang.invoke.SerializedLambda;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
/* loaded from: logic-module-message-3.5.7.0.jar:cn/herodotus/engine/logic/message/service/DialogueContactService.class */
public class DialogueContactService extends AbstractJpaService<DialogueContact, String> {
    private final DialogueContactRepository dialogueContactRepository;

    private static /* synthetic */ Object $deserializeLambda$(SerializedLambda lambda) {
        switch (lambda.getImplMethodName()) {
            case "lambda$findByCondition$51c28f3e$1":
                if (lambda.getImplMethodKind() == 6 && lambda.getFunctionalInterfaceClass().equals("org/springframework/data/jpa/domain/Specification") && lambda.getFunctionalInterfaceMethodName().equals("toPredicate") && lambda.getFunctionalInterfaceMethodSignature().equals("(Ljakarta/persistence/criteria/Root;Ljakarta/persistence/criteria/CriteriaQuery;Ljakarta/persistence/criteria/CriteriaBuilder;)Ljakarta/persistence/criteria/Predicate;") && lambda.getImplClass().equals("cn/herodotus/engine/logic/message/service/DialogueContactService") && lambda.getImplMethodSignature().equals("(Ljava/lang/String;Ljakarta/persistence/criteria/Root;Ljakarta/persistence/criteria/CriteriaQuery;Ljakarta/persistence/criteria/CriteriaBuilder;)Ljakarta/persistence/criteria/Predicate;")) {
                    String str = (String) lambda.getCapturedArg(0);
                    return (root, criteriaQuery, criteriaBuilder) -> {
                        List<Predicate> predicates = new ArrayList<>();
                        predicates.add(criteriaBuilder.equal(root.get("receiverId"), str));
                        Predicate[] predicateArray = new Predicate[predicates.size()];
                        criteriaQuery.where(criteriaBuilder.and((Predicate[]) predicates.toArray(predicateArray)));
                        criteriaQuery.orderBy(new Order[]{criteriaBuilder.desc(root.get("createTime"))});
                        return criteriaQuery.getRestriction();
                    };
                }
                break;
        }
        throw new IllegalArgumentException("Invalid lambda deserialization");
    }

    public DialogueContactService(DialogueContactRepository dialogueContactRepository) {
        this.dialogueContactRepository = dialogueContactRepository;
    }

    @Override // com.pigx.engine.data.core.jpa.service.BaseJpaReadableService
    public BaseJpaRepository<DialogueContact, String> getRepository() {
        return this.dialogueContactRepository;
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
        ArrayList arrayList = new ArrayList();
        arrayList.add(contact);
        arrayList.add(reverseContext);
        return saveAll(arrayList);
    }

    public Page<DialogueContact> findByCondition(int pageNumber, int pageSize, String receiverId) {
        PageRequest pageRequestOf = PageRequest.of(pageNumber, pageSize);
        Specification<DialogueContact> specification = (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.equal(root.get("receiverId"), receiverId));
            Predicate[] predicateArray = new Predicate[predicates.size()];
            criteriaQuery.where(criteriaBuilder.and((Predicate[]) predicates.toArray(predicateArray)));
            criteriaQuery.orderBy(new Order[]{criteriaBuilder.desc(root.get("createTime"))});
            return criteriaQuery.getRestriction();
        };
        return findByPage((Specification) specification, (Pageable) pageRequestOf);
    }

    public void deleteByDialogueId(String dialogueId) {
        this.dialogueContactRepository.deleteAllByDialogueId(dialogueId);
    }

    public DialogueContact findBySenderIdAndReceiverId(String senderId, String receiverId) {
        return this.dialogueContactRepository.findBySenderIdAndReceiverId(senderId, receiverId).orElse(null);
    }
}
