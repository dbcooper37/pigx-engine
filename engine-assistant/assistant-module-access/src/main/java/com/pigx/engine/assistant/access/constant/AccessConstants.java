package com.pigx.engine.assistant.access.constant;

import com.pigx.engine.core.definition.constant.BaseConstants;


public interface AccessConstants extends BaseConstants {

    String PROPERTY_ASSISTANT_ACCESS = PROPERTY_PREFIX_ASSISTANT + ".access";

    String PROPERTY_ACCESS_JUSTAUTH = PROPERTY_ASSISTANT_ACCESS + ".justauth";
    String PROPERTY_ACCESS_WXAPP = PROPERTY_ASSISTANT_ACCESS + ".wxapp";
    String PROPERTY_ACCESS_WXMPP = PROPERTY_ASSISTANT_ACCESS + ".wxmpp";
    String PROPERTY_ACCESS_SMS = PROPERTY_ASSISTANT_ACCESS + ".sms";

    String CACHE_NAME_TOKEN_VERIFICATION_CODE = CACHE_TOKEN_BASE_PREFIX + "verification:";

    String CACHE_NAME_TOKEN_JUSTAUTH = CACHE_TOKEN_BASE_PREFIX + "justauth:";
}
