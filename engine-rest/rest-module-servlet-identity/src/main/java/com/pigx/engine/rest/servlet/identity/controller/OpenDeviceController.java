package com.pigx.engine.rest.servlet.identity.controller;

import com.pigx.engine.core.definition.constant.SystemConstants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
/* loaded from: rest-module-servlet-identity-3.5.7.0.jar:cn/herodotus/engine/rest/servlet/identity/controller/OpenDeviceController.class */
public class OpenDeviceController {
    @GetMapping({SystemConstants.OAUTH2_DEVICE_ACTIVATION_URI})
    public String activate(@RequestParam(value = "user_code", required = false) String userCode) {
        if (StringUtils.isNotBlank(userCode)) {
            return "redirect:/oauth2/device_verification?user_code=" + userCode;
        }
        return "activation";
    }

    @GetMapping({SystemConstants.OAUTH2_DEVICE_VERIFICATION_SUCCESS_URI})
    public String activated() {
        return "activation-allowed";
    }

    @GetMapping(value = {"/"}, params = {"success"})
    public String success() {
        return "activation-allowed";
    }
}
