package com.pigx.engine.logic.identity.service;

import com.pigx.engine.core.identity.enums.PermissionExpression;
import com.pigx.engine.logic.identity.enums.ApplicationType;
import com.pigx.engine.logic.identity.enums.AuthenticationMethod;
import com.pigx.engine.logic.identity.enums.GrantType;
import com.pigx.engine.oauth2.core.enums.AllJwsAlgorithm;
import com.pigx.engine.oauth2.core.enums.Database;
import com.pigx.engine.oauth2.core.enums.ServerDevice;
import com.pigx.engine.oauth2.core.enums.SignatureJwsAlgorithm;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class OAuth2ConstantService {

    private static final List<Map<String, Object>> APPLICATION_TYPE_ENUM = ApplicationType.getPreprocessedJsonStructure();
    private static final List<Map<String, Object>> GRANT_TYPE_ENUM = GrantType.getPreprocessedJsonStructure();
    private static final List<Map<String, Object>> SIGNATURE_JWS_ALGORITHM_ENUM = SignatureJwsAlgorithm.getPreprocessedJsonStructure();
    private static final List<Map<String, Object>> ALL_JWS_ALGORITHM_ENUM = AllJwsAlgorithm.getPreprocessedJsonStructure();
    private static final List<Map<String, Object>> AUTHENTICATION_METHOD_ENUM = AuthenticationMethod.getPreprocessedJsonStructure();
    private static final List<Map<String, Object>> PERMISSION_EXPRESSION_ENUM = PermissionExpression.getPreprocessedJsonStructure();
    private static final List<Map<String, Object>> DATABASE_ENUM = Database.getPreprocessedJsonStructure();
    private static final List<Map<String, Object>> SERVER_DEVICE_ENUM = ServerDevice.getPreprocessedJsonStructure();

    public Map<String, Object> getAllEnums() {
        Map<String, Object> map = new HashMap<>(8);
        map.put("applicationType", APPLICATION_TYPE_ENUM);
        map.put("grantType", GRANT_TYPE_ENUM);
        map.put("signatureJwsAlgorithm", SIGNATURE_JWS_ALGORITHM_ENUM);
        map.put("allJwsAlgorithm", ALL_JWS_ALGORITHM_ENUM);
        map.put("permissionExpression", PERMISSION_EXPRESSION_ENUM);
        map.put("authenticationMethod", AUTHENTICATION_METHOD_ENUM);
        map.put("database", DATABASE_ENUM);
        map.put("serverDevice", SERVER_DEVICE_ENUM);
        return map;
    }
}
