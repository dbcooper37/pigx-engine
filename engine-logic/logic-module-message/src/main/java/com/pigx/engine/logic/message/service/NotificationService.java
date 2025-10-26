package com.pigx.engine.logic.message.service;

import com.pigx.engine.data.core.jpa.repository.BaseJpaRepository;
import com.pigx.engine.data.core.jpa.service.AbstractJpaService;
import com.pigx.engine.logic.message.entity.Announcement;
import com.pigx.engine.logic.message.entity.Notification;
import com.pigx.engine.logic.message.entity.PullStamp;
import com.pigx.engine.logic.message.enums.NotificationCategory;
import com.pigx.engine.logic.message.repository.NotificationRepository;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Predicate;
import java.lang.invoke.SerializedLambda;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
/* loaded from: logic-module-message-3.5.7.0.jar:cn/herodotus/engine/logic/message/service/NotificationService.class */
public class NotificationService extends AbstractJpaService<Notification, String> {
    private final NotificationRepository notificationRepository;
    private final PullStampService pullStampService;
    private final AnnouncementService announcementService;

    private static /* synthetic */ Object $deserializeLambda$(SerializedLambda lambda) {
        switch (lambda.getImplMethodName()) {
            case "lambda$findByCondition$369cc5e1$1":
                if (lambda.getImplMethodKind() == 6 && lambda.getFunctionalInterfaceClass().equals("org/springframework/data/jpa/domain/Specification") && lambda.getFunctionalInterfaceMethodName().equals("toPredicate") && lambda.getFunctionalInterfaceMethodSignature().equals("(Ljakarta/persistence/criteria/Root;Ljakarta/persistence/criteria/CriteriaQuery;Ljakarta/persistence/criteria/CriteriaBuilder;)Ljakarta/persistence/criteria/Predicate;") && lambda.getImplClass().equals("cn/herodotus/engine/logic/message/service/NotificationService") && lambda.getImplMethodSignature().equals("(Ljava/lang/String;Lcn/herodotus/engine/logic/message/enums/NotificationCategory;Ljava/lang/Boolean;Ljakarta/persistence/criteria/Root;Ljakarta/persistence/criteria/CriteriaQuery;Ljakarta/persistence/criteria/CriteriaBuilder;)Ljakarta/persistence/criteria/Predicate;")) {
                    String str = (String) lambda.getCapturedArg(0);
                    NotificationCategory notificationCategory = (NotificationCategory) lambda.getCapturedArg(1);
                    Boolean bool = (Boolean) lambda.getCapturedArg(2);
                    return (root, criteriaQuery, criteriaBuilder) -> {
                        List<Predicate> predicates = new ArrayList<>();
                        predicates.add(criteriaBuilder.equal(root.get("userId"), str));
                        if (ObjectUtils.isNotEmpty(notificationCategory)) {
                            predicates.add(criteriaBuilder.equal(root.get("category"), notificationCategory));
                        }
                        if (ObjectUtils.isNotEmpty(bool)) {
                            predicates.add(criteriaBuilder.equal(root.get("read"), bool));
                        }
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

    public NotificationService(NotificationRepository notificationRepository, PullStampService pullStampService, AnnouncementService announcementService) {
        this.notificationRepository = notificationRepository;
        this.pullStampService = pullStampService;
        this.announcementService = announcementService;
    }

    @Override // com.pigx.engine.data.core.jpa.service.BaseJpaReadableService
    public BaseJpaRepository<Notification, String> getRepository() {
        return this.notificationRepository;
    }

    public void pullAnnouncements(String userId) {
        PullStamp pullStamp = this.pullStampService.getPullStamp(userId);
        List<Announcement> systemAnnouncements = this.announcementService.pullAnnouncements(pullStamp.getLatestPullTime());
        if (CollectionUtils.isNotEmpty(systemAnnouncements)) {
            List<Notification> notificationQueues = convertAnnouncementsToNotifications(userId, systemAnnouncements);
            saveAll(notificationQueues);
        }
    }

    public Page<Notification> findByCondition(int pageNumber, int pageSize, String userId, NotificationCategory category, Boolean read) {
        PageRequest pageRequestOf = PageRequest.of(pageNumber, pageSize);
        Specification<Notification> specification = (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.equal(root.get("userId"), userId));
            if (ObjectUtils.isNotEmpty(category)) {
                predicates.add(criteriaBuilder.equal(root.get("category"), category));
            }
            if (ObjectUtils.isNotEmpty(read)) {
                predicates.add(criteriaBuilder.equal(root.get("read"), read));
            }
            Predicate[] predicateArray = new Predicate[predicates.size()];
            criteriaQuery.where(criteriaBuilder.and((Predicate[]) predicates.toArray(predicateArray)));
            criteriaQuery.orderBy(new Order[]{criteriaBuilder.desc(root.get("createTime"))});
            return criteriaQuery.getRestriction();
        };
        return findByPage((Specification) specification, (Pageable) pageRequestOf);
    }

    private List<Notification> convertAnnouncementsToNotifications(String userId, List<Announcement> announcements) {
        return (List) announcements.stream().map(announcement -> {
            return convertAnnouncementToNotification(userId, announcement);
        }).collect(Collectors.toList());
    }

    private Notification convertAnnouncementToNotification(String userId, Announcement announcement) {
        Notification notification = new Notification();
        notification.setUserId(userId);
        notification.setContent(announcement.getContent());
        notification.setSenderId(announcement.getSenderId());
        notification.setSenderName(announcement.getSenderName());
        notification.setSenderAvatar(announcement.getSenderAvatar());
        notification.setCategory(NotificationCategory.ANNOUNCEMENT);
        return notification;
    }

    public int setAllRead(String userId) {
        return this.notificationRepository.updateAllRead(userId);
    }
}
