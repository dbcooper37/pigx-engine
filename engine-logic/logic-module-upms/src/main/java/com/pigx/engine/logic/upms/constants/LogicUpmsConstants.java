package com.pigx.engine.logic.upms.constants;

import com.pigx.engine.core.definition.constant.BaseConstants;


public interface LogicUpmsConstants extends BaseConstants {

    String UPMS_AREA_PREFIX = AREA_PREFIX + "upms:";

    String REGION_SYS_USER = UPMS_AREA_PREFIX + "sys:user";
    String REGION_SYS_ROLE = UPMS_AREA_PREFIX + "sys:role";
    String REGION_SYS_DEFAULT_ROLE = UPMS_AREA_PREFIX + "sys:defaults:role";
    String REGION_SYS_PERMISSION = UPMS_AREA_PREFIX + "sys:permission";
    String REGION_SYS_OWNERSHIP = UPMS_AREA_PREFIX + "sys:ownership";
    String REGION_SYS_ELEMENT = UPMS_AREA_PREFIX + "sys:element";
    String REGION_SYS_SOCIAL_USER = UPMS_AREA_PREFIX + "sys:social:user";
    String REGION_SYS_DEPARTMENT = UPMS_AREA_PREFIX + "sys:department";
    String REGION_SYS_EMPLOYEE = UPMS_AREA_PREFIX + "sys:employee";
    String REGION_SYS_ORGANIZATION = UPMS_AREA_PREFIX + "sys:organization";
}
