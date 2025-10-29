package com.pigx.engine.rest.servlet.identity.controller;

import com.pigx.engine.core.definition.constant.SymbolConstants;
import com.pigx.engine.core.definition.constant.SystemConstants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class OpenDeviceController {

    @GetMapping(SystemConstants.OAUTH2_DEVICE_ACTIVATION_URI)
    public String activate(@RequestParam(value = OAuth2ParameterNames.USER_CODE, required = false) String userCode) {
        if (StringUtils.isNotBlank(userCode)) {
            return "redirect:" + SystemConstants.OAUTH2_DEVICE_VERIFICATION_ENDPOINT + SymbolConstants.QUESTION + OAuth2ParameterNames.USER_CODE + SymbolConstants.EQUAL + userCode;
        }
        return "activation";
    }

    @GetMapping(value = SystemConstants.OAUTH2_DEVICE_VERIFICATION_SUCCESS_URI)
    public String activated() {
        return "activation-allowed";
    }

    @GetMapping(value = "/", params = "success")
    public String success() {
        return "activation-allowed";
    }
}
