package com.pigx.engine.logic.message.constant;

import com.pigx.engine.core.definition.constant.BaseConstants;


public interface LogicMessageConstants extends BaseConstants {

    String MESSAGE_AREA_PREFIX = AREA_PREFIX + "message:";

    String REGION_MESSAGE_ANNOUNCEMENT = MESSAGE_AREA_PREFIX + "system_announcement";
    String REGION_MESSAGE_DIALOGUE_CONTACT = MESSAGE_AREA_PREFIX + "personal:contact";
    String REGION_MESSAGE_DIALOGUE = MESSAGE_AREA_PREFIX + "personal:dialogue";
    String REGION_MESSAGE_DIALOGUE_DETAIL = MESSAGE_AREA_PREFIX + "personal:dialogue:detail";
    String REGION_MESSAGE_NOTIFICATION = MESSAGE_AREA_PREFIX + "notification_queue";
    String REGION_MESSAGE_PULL_STAMP = MESSAGE_AREA_PREFIX + "pull_stamp";
}
