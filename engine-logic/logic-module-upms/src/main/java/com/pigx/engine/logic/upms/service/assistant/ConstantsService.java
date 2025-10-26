package com.pigx.engine.logic.upms.service.assistant;

import com.pigx.engine.data.core.enums.DataItemStatus;
import com.pigx.engine.logic.upms.enums.Gender;
import com.pigx.engine.logic.upms.enums.Identity;
import com.pigx.engine.logic.upms.enums.OrganizationCategory;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
/* loaded from: logic-module-upms-3.5.7.0.jar:cn/herodotus/engine/logic/upms/service/assistant/ConstantsService.class */
public class ConstantsService {
    private static final List<Map<String, Object>> STATUS_ENUM = DataItemStatus.getPreprocessedJsonStructure();
    private static final List<Map<String, Object>> GENDER_ENUM = Gender.getPreprocessedJsonStructure();
    private static final List<Map<String, Object>> IDENTITY_ENUM = Identity.getPreprocessedJsonStructure();
    private static final List<Map<String, Object>> ORGANIZATION_CATEGORY_ENUM = OrganizationCategory.getPreprocessedJsonStructure();

    public Map<String, Object> getAllEnums() {
        Map<String, Object> map = new HashMap<>(8);
        map.put("status", STATUS_ENUM);
        map.put("gender", GENDER_ENUM);
        map.put("identity", IDENTITY_ENUM);
        map.put("organizationCategory", ORGANIZATION_CATEGORY_ENUM);
        return map;
    }
}
