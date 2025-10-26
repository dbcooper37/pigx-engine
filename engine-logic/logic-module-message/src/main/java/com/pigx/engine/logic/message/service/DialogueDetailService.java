package com.pigx.engine.logic.message.service;

import com.pigx.engine.data.core.jpa.repository.BaseJpaRepository;
import com.pigx.engine.data.core.jpa.service.AbstractJpaService;
import com.pigx.engine.logic.message.entity.Dialogue;
import com.pigx.engine.logic.message.entity.DialogueContact;
import com.pigx.engine.logic.message.entity.DialogueDetail;
import com.pigx.engine.logic.message.entity.Notification;
import com.pigx.engine.logic.message.enums.NotificationCategory;
import com.pigx.engine.logic.message.repository.DialogueDetailRepository;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Predicate;
import java.lang.invoke.SerializedLambda;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
/* loaded from: logic-module-message-3.5.7.0.jar:cn/herodotus/engine/logic/message/service/DialogueDetailService.class */
public class DialogueDetailService extends AbstractJpaService<DialogueDetail, String> {
    private final DialogueDetailRepository dialogueDetailRepository;
    private final DialogueContactService dialogueContactService;
    private final DialogueService dialogueService;
    private final NotificationService notificationService;

    private static /* synthetic */ Object $deserializeLambda$(SerializedLambda lambda) {
        switch (lambda.getImplMethodName()) {
            case "lambda$findByCondition$3e1ef908$1":
                if (lambda.getImplMethodKind() == 6 && lambda.getFunctionalInterfaceClass().equals("org/springframework/data/jpa/domain/Specification") && lambda.getFunctionalInterfaceMethodName().equals("toPredicate") && lambda.getFunctionalInterfaceMethodSignature().equals("(Ljakarta/persistence/criteria/Root;Ljakarta/persistence/criteria/CriteriaQuery;Ljakarta/persistence/criteria/CriteriaBuilder;)Ljakarta/persistence/criteria/Predicate;") && lambda.getImplClass().equals("cn/herodotus/engine/logic/message/service/DialogueDetailService") && lambda.getImplMethodSignature().equals("(Ljava/lang/String;Ljakarta/persistence/criteria/Root;Ljakarta/persistence/criteria/CriteriaQuery;Ljakarta/persistence/criteria/CriteriaBuilder;)Ljakarta/persistence/criteria/Predicate;")) {
                    String str = (String) lambda.getCapturedArg(0);
                    return (root, criteriaQuery, criteriaBuilder) -> {
                        List<Predicate> predicates = new ArrayList<>();
                        predicates.add(criteriaBuilder.equal(root.get("dialogueId"), str));
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

    public DialogueDetailService(DialogueDetailRepository dialogueDetailRepository, DialogueContactService dialogueContactService, DialogueService dialogueService, NotificationService notificationService) {
        this.dialogueDetailRepository = dialogueDetailRepository;
        this.dialogueContactService = dialogueContactService;
        this.dialogueService = dialogueService;
        this.notificationService = notificationService;
    }

    @Override // com.pigx.engine.data.core.jpa.service.BaseJpaReadableService
    public BaseJpaRepository<DialogueDetail, String> getRepository() {
        return this.dialogueDetailRepository;
    }

    private Notification convertDialogueDetailToNotification(DialogueDetail dialogueDetail) {
        Notification notification = new Notification();
        notification.setUserId(dialogueDetail.getReceiverId());
        notification.setContent(dialogueDetail.getContent());
        notification.setSenderId(dialogueDetail.getSenderId());
        notification.setSenderName(dialogueDetail.getSenderName());
        notification.setSenderAvatar(dialogueDetail.getSenderAvatar());
        notification.setCategory(NotificationCategory.DIALOGUE);
        return notification;
    }

    @Override // com.pigx.engine.data.core.jpa.service.BaseJpaWriteableService, com.pigx.engine.data.core.service.BaseService
    @Transactional
    public DialogueDetail save(DialogueDetail domain) {
        if (StringUtils.isBlank(domain.getDialogueId())) {
            DialogueContact dialogueContact = this.dialogueContactService.findBySenderIdAndReceiverId(domain.getSenderId(), domain.getReceiverId());
            if (ObjectUtils.isNotEmpty(dialogueContact) && ObjectUtils.isNotEmpty(dialogueContact.getDialogue())) {
                String dialogueId = dialogueContact.getDialogue().getDialogueId();
                domain.setDialogueId(dialogueId);
                this.dialogueService.updateDialogue(dialogueId, domain.getContent());
            } else {
                Dialogue dialogue = this.dialogueService.createDialogue(domain.getContent());
                domain.setDialogueId(dialogue.getDialogueId());
                this.dialogueContactService.createContact(dialogue, domain);
            }
        } else {
            this.dialogueService.updateDialogue(domain.getDialogueId(), domain.getContent());
        }
        this.notificationService.save(convertDialogueDetailToNotification(domain));
        return (DialogueDetail) super.save((DialogueDetailService) domain);
    }

    @Transactional
    public void deleteDialogueById(String dialogueId) {
        this.dialogueContactService.deleteByDialogueId(dialogueId);
        this.dialogueService.deleteById(dialogueId);
        this.dialogueDetailRepository.deleteAllByDialogueId(dialogueId);
    }

    public Page<DialogueDetail> findByCondition(int pageNumber, int pageSize, String dialogueId) {
        PageRequest pageRequestOf = PageRequest.of(pageNumber, pageSize);
        Specification<DialogueDetail> specification = (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.equal(root.get("dialogueId"), dialogueId));
            Predicate[] predicateArray = new Predicate[predicates.size()];
            criteriaQuery.where(criteriaBuilder.and((Predicate[]) predicates.toArray(predicateArray)));
            criteriaQuery.orderBy(new Order[]{criteriaBuilder.desc(root.get("createTime"))});
            return criteriaQuery.getRestriction();
        };
        return findByPage((Specification) specification, (Pageable) pageRequestOf);
    }
}
