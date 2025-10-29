package com.pigx.engine.oauth2.persistence.sas.jpa.jackson2;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.pigx.engine.core.identity.domain.HerodotusGrantedAuthority;
import com.pigx.engine.core.identity.domain.HerodotusUser;
import com.pigx.engine.oauth2.core.domain.FormLoginWebAuthenticationDetails;
import org.springframework.security.jackson2.SecurityJackson2Modules;


public class HerodotusJackson2Module extends SimpleModule {

    public HerodotusJackson2Module() {
        super(HerodotusJackson2Module.class.getName(), Jackson2Constants.VERSION);
    }

    @Override
    public void setupModule(SetupContext context) {
        SecurityJackson2Modules.enableDefaultTyping(context.getOwner());
        context.setMixInAnnotations(HerodotusUser.class, HerodotusUserMixin.class);
        context.setMixInAnnotations(HerodotusGrantedAuthority.class, HerodotusGrantedAuthorityMixin.class);
        context.setMixInAnnotations(FormLoginWebAuthenticationDetails.class, FormLoginWebAuthenticationDetailsMixin.class);
    }
}
