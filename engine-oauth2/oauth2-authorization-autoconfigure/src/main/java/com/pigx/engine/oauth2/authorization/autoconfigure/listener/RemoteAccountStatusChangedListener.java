package com.pigx.engine.oauth2.authorization.autoconfigure.listener;

import com.pigx.engine.core.definition.utils.Jackson2Utils;
import com.pigx.engine.data.core.enums.DataItemStatus;
import com.pigx.engine.logic.upms.service.security.SysUserService;
import com.pigx.engine.message.core.domain.AccountStatus;
import com.pigx.engine.oauth2.authorization.autoconfigure.bus.RemoteAccountStatusChangedEvent;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/* JADX WARN: Classes with same name are omitted:
  
 */
@Component
/* loaded from: oauth2-authorization-autoconfigure-3.5.7.0.jar:cn/herodotus/engine/oauth2/authorization/autoconfigure/listener/RemoteAccountStatusChangedListener.class */
public class RemoteAccountStatusChangedListener implements ApplicationListener<RemoteAccountStatusChangedEvent> {
    private static final Logger log = LoggerFactory.getLogger(RemoteAccountStatusChangedListener.class);
    private final SysUserService sysUserService;

    @Autowired
    public RemoteAccountStatusChangedListener(SysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }

    public void onApplicationEvent(RemoteAccountStatusChangedEvent event) {
        log.info("[Herodotus] |- Account status changed REMOTE listener, response service [{}] event!", event.getOriginService());
        String data = event.getData();
        if (ObjectUtils.isNotEmpty(data)) {
            AccountStatus accountStatus = (AccountStatus) Jackson2Utils.toObject(data, AccountStatus.class);
            if (ObjectUtils.isNotEmpty(accountStatus)) {
                DataItemStatus dataItemStatus = DataItemStatus.valueOf(accountStatus.getStatus());
                if (ObjectUtils.isNotEmpty(dataItemStatus)) {
                    log.debug("[Herodotus] |- [A2] Account status changed process BEGIN!");
                    this.sysUserService.changeStatus(accountStatus.getUserId(), dataItemStatus);
                }
            }
        }
    }
}
