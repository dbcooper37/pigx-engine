package com.pigx.engine.logic.message.service;

import com.pigx.engine.data.core.jpa.repository.BaseJpaRepository;
import com.pigx.engine.data.core.jpa.service.AbstractJpaService;
import com.pigx.engine.logic.message.entity.Announcement;
import com.pigx.engine.logic.message.entity.Notification;
import com.pigx.engine.logic.message.entity.PullStamp;
import com.pigx.engine.logic.message.enums.NotificationCategory;
import com.pigx.engine.logic.message.repository.NotificationRepository;
import jakarta.persistence.criteria.Predicate;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class NotificationService extends AbstractJpaService<Notification, String> {

    private final NotificationRepository notificationRepository;
    private final PullStampService pullStampService;
    private final AnnouncementService announcementService;

    public NotificationService(NotificationRepository notificationRepository, PullStampService pullStampService, AnnouncementService announcementService) {
        this.notificationRepository = notificationRepository;
        this.pullStampService = pullStampService;
        this.announcementService = announcementService;
    }

    @Override
    public BaseJpaRepository<Notification, String> getRepository() {
        return notificationRepository;
    }

    public void pullAnnouncements(String userId) {
        PullStamp pullStamp = pullStampService.getPullStamp(userId);
        List<Announcement> systemAnnouncements = announcementService.pullAnnouncements(pullStamp.getLatestPullTime());
        if (CollectionUtils.isNotEmpty(systemAnnouncements)) {
            List<Notification> notificationQueues = convertAnnouncementsToNotifications(userId, systemAnnouncements);
            this.saveAll(notificationQueues);
        }
    }

    public Page<Notification> findByCondition(int pageNumber, int pageSize, String userId, NotificationCategory category, Boolean read) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

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
            criteriaQuery.where(criteriaBuilder.and(predicates.toArray(predicateArray)));
            criteriaQuery.orderBy(criteriaBuilder.desc(root.get("createTime")));
            return criteriaQuery.getRestriction();
        };

        return this.findByPage(specification, pageable);
    }

    private List<Notification> convertAnnouncementsToNotifications(String userId, List<Announcement> announcements) {
        return announcements.stream().map(announcement -> convertAnnouncementToNotification(userId, announcement)).collect(Collectors.toList());
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
        return notificationRepository.updateAllRead(userId);
    }
}
