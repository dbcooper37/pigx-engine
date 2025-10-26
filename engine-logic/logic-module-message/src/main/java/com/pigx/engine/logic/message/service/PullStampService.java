package com.pigx.engine.logic.message.service;

import com.pigx.engine.data.core.jpa.repository.BaseJpaRepository;
import com.pigx.engine.data.core.jpa.service.AbstractJpaService;
import com.pigx.engine.logic.message.entity.PullStamp;
import com.pigx.engine.logic.message.repository.PullStampRepository;
import java.util.Date;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

@Service
/* loaded from: logic-module-message-3.5.7.0.jar:cn/herodotus/engine/logic/message/service/PullStampService.class */
public class PullStampService extends AbstractJpaService<PullStamp, String> {
    private final PullStampRepository pullStampRepository;

    public PullStampService(PullStampRepository pullStampRepository) {
        this.pullStampRepository = pullStampRepository;
    }

    @Override // com.pigx.engine.data.core.jpa.service.BaseJpaReadableService
    public BaseJpaRepository<PullStamp, String> getRepository() {
        return this.pullStampRepository;
    }

    public PullStamp findByUserId(String userId) {
        return this.pullStampRepository.findByUserId(userId).orElse(null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public PullStamp getPullStamp(String userId) {
        PullStamp stamp = findByUserId(userId);
        if (ObjectUtils.isEmpty(stamp)) {
            stamp = new PullStamp();
            stamp.setUserId(userId);
        }
        stamp.setLatestPullTime(new Date());
        return (PullStamp) save(stamp);
    }
}
