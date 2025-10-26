package com.pigx.engine.oauth2.persistence.sas.jpa.jackson2;

import com.pigx.engine.core.identity.domain.HerodotusGrantedAuthority;
import com.pigx.engine.core.identity.domain.HerodotusUser;
import com.pigx.engine.oauth2.core.domain.FormLoginWebAuthenticationDetails;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.security.jackson2.SecurityJackson2Modules;

/* loaded from: oauth2-module-persistence-jpa-3.5.7.0.jar:cn/herodotus/engine/oauth2/persistence/sas/jpa/jackson2/HerodotusJackson2Module.class */
public class HerodotusJackson2Module extends SimpleModule {
    public HerodotusJackson2Module() {
        super(HerodotusJackson2Module.class.getName(), Jackson2Constants.VERSION);
    }

    public void setupModule(Module.SetupContext context) {
        SecurityJackson2Modules.enableDefaultTyping(context.getOwner());
        context.setMixInAnnotations(HerodotusUser.class, HerodotusUserMixin.class);
        context.setMixInAnnotations(HerodotusGrantedAuthority.class, HerodotusGrantedAuthorityMixin.class);
        context.setMixInAnnotations(FormLoginWebAuthenticationDetails.class, FormLoginWebAuthenticationDetailsMixin.class);
    }
}
