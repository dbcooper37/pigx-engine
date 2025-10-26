package com.pigx.engine.logic.message.service;

import com.pigx.engine.data.core.jpa.repository.BaseJpaRepository;
import com.pigx.engine.data.core.jpa.service.AbstractJpaService;
import com.pigx.engine.logic.message.entity.Announcement;
import com.pigx.engine.logic.message.repository.AnnouncementRepository;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
/* loaded from: logic-module-message-3.5.7.0.jar:cn/herodotus/engine/logic/message/service/AnnouncementService.class */
public class AnnouncementService extends AbstractJpaService<Announcement, String> {
    private final AnnouncementRepository announcementRepository;

    public AnnouncementService(AnnouncementRepository announcementRepository) {
        this.announcementRepository = announcementRepository;
    }

    @Override // com.pigx.engine.data.core.jpa.service.BaseJpaReadableService
    public BaseJpaRepository<Announcement, String> getRepository() {
        return this.announcementRepository;
    }

    public List<Announcement> pullAnnouncements(Date stamp) {
        return this.announcementRepository.findAllByCreateTimeAfter(stamp);
    }
}
