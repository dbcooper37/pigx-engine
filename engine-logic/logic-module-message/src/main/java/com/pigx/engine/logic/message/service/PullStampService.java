package com.pigx.engine.logic.message.service;

import com.pigx.engine.data.core.jpa.repository.BaseJpaRepository;
import com.pigx.engine.data.core.jpa.service.AbstractJpaService;
import com.pigx.engine.logic.message.entity.PullStamp;
import com.pigx.engine.logic.message.repository.PullStampRepository;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
public class PullStampService extends AbstractJpaService<PullStamp, String> {

    private final PullStampRepository pullStampRepository;

    public PullStampService(PullStampRepository pullStampRepository) {
        this.pullStampRepository = pullStampRepository;
    }

    @Override
    public BaseJpaRepository<PullStamp, String> getRepository() {
        return pullStampRepository;
    }

    public PullStamp findByUserId(String userId) {
        return pullStampRepository.findByUserId(userId).orElse(null);
    }

    public PullStamp getPullStamp(String userId) {

        PullStamp stamp = findByUserId(userId);
        if (ObjectUtils.isEmpty(stamp)) {
            stamp = new PullStamp();
            stamp.setUserId(userId);
        }
        stamp.setLatestPullTime(new Date());

        return this.save(stamp);
    }
}
