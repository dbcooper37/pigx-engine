package com.pigx.engine.oauth2.authorization.autoconfigure.listener;

import com.pigx.engine.data.core.enums.DataItemStatus;
import com.pigx.engine.logic.upms.service.security.SysUserService;
import com.pigx.engine.message.core.domain.AccountStatus;
import com.pigx.engine.message.core.event.AccountStatusChangedEvent;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;


@Component
public class LocalAccountStatusChangedListener implements ApplicationListener<AccountStatusChangedEvent> {

    private static final Logger log = LoggerFactory.getLogger(LocalAccountStatusChangedListener.class);

    private final SysUserService sysUserService;

    public LocalAccountStatusChangedListener(SysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }

    @Override
    public void onApplicationEvent(AccountStatusChangedEvent event) {

        log.info("[PIGXD] |- Account status changed LOCAL listener, response event!");

        AccountStatus accountStatus = event.getData();
        if (ObjectUtils.isNotEmpty(accountStatus)) {
            DataItemStatus dataItemStatus = DataItemStatus.valueOf(accountStatus.getStatus());
            if (ObjectUtils.isNotEmpty(dataItemStatus)) {
                log.debug("[PIGXD] |- [A2] Account status changed process BEGIN!");
                sysUserService.changeStatus(accountStatus.getUserId(), dataItemStatus);
            }
        }
    }
}
