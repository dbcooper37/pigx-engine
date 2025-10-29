package com.pigx.engine.oss.rest.minio.service;

import com.pigx.engine.oss.dialect.minio.enums.*;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class MinioConstantsService {

    private static final List<Map<String, Object>> POLICY_ENUM = PolicyEnums.getPreprocessedJsonStructure();
    private static final List<Map<String, Object>> RETENTION_UNIT_ENUM = RetentionUnitEnums.getPreprocessedJsonStructure();
    private static final List<Map<String, Object>> RETENTION_MODE_ENUM = RetentionModeEnums.getPreprocessedJsonStructure();
    private static final List<Map<String, Object>> SSE_CONFIGURATION_ENUM = SseConfigurationEnums.getPreprocessedJsonStructure();
    private static final List<Map<String, Object>> QUOTA_UNIT_ENUMS = QuotaUnitEnums.getPreprocessedJsonStructure();
    private static final List<Map<String, Object>> VERSIONING_STATUS_ENUMS = VersioningStatusEnums.getPreprocessedJsonStructure();

    public Map<String, Object> getAllEnums() {
        Map<String, Object> map = new HashMap<>(8);
        map.put("policy", POLICY_ENUM);
        map.put("retentionUnit", RETENTION_UNIT_ENUM);
        map.put("retentionMode", RETENTION_MODE_ENUM);
        map.put("sseConfiguration", SSE_CONFIGURATION_ENUM);
        map.put("quotaUnit", QUOTA_UNIT_ENUMS);
        map.put("versioningStatus", VERSIONING_STATUS_ENUMS);
        return map;
    }
}
