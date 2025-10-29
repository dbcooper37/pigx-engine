package com.pigx.engine.logic.message.service;

import com.pigx.engine.data.core.jpa.repository.BaseJpaRepository;
import com.pigx.engine.data.core.jpa.service.AbstractJpaService;
import com.pigx.engine.logic.message.entity.Announcement;
import com.pigx.engine.logic.message.repository.AnnouncementRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
public class AnnouncementService extends AbstractJpaService<Announcement, String> {

    private final AnnouncementRepository announcementRepository;

    public AnnouncementService(AnnouncementRepository announcementRepository) {
        this.announcementRepository = announcementRepository;
    }

    @Override
    public BaseJpaRepository<Announcement, String> getRepository() {
        return announcementRepository;
    }

    public List<Announcement> pullAnnouncements(Date stamp) {
        return announcementRepository.findAllByCreateTimeAfter(stamp);
    }
}
