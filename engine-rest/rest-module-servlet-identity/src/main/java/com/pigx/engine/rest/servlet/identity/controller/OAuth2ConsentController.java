package com.pigx.engine.rest.servlet.identity.controller;

import com.pigx.engine.core.definition.constant.SymbolConstants;
import com.pigx.engine.core.definition.constant.SystemConstants;
import com.pigx.engine.core.definition.domain.view.vue.Option;
import com.pigx.engine.core.foundation.context.ServiceContextHolder;
import com.pigx.engine.logic.identity.entity.OAuth2Application;
import com.pigx.engine.logic.identity.entity.OAuth2Scope;
import com.pigx.engine.logic.identity.service.OAuth2ApplicationService;
import com.pigx.engine.logic.identity.service.OAuth2ScopeService;
import java.security.Principal;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsent;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
/* loaded from: rest-module-servlet-identity-3.5.7.0.jar:cn/herodotus/engine/rest/servlet/identity/controller/OAuth2ConsentController.class */
public class OAuth2ConsentController {
    private final OAuth2ApplicationService applicationService;
    private final OAuth2AuthorizationConsentService authorizationConsentService;
    private final OAuth2ScopeService scopeService;
    private Map<String, OAuth2Scope> dictionaries;

    public OAuth2ConsentController(OAuth2ApplicationService applicationService, OAuth2AuthorizationConsentService authorizationConsentService, OAuth2ScopeService scopeService) {
        this.applicationService = applicationService;
        this.authorizationConsentService = authorizationConsentService;
        this.scopeService = scopeService;
        initDictionaries();
    }

    @GetMapping({SystemConstants.OAUTH2_AUTHORIZATION_CONSENT_URI})
    public String consent(Principal principal, Model model, @RequestParam("client_id") String clientId, @RequestParam("scope") String scope, @RequestParam("state") String state, @RequestParam(value = "user_code", required = false) String userCode) {
        Set<String> scopesToApprove = new HashSet<>();
        Set<String> previouslyApprovedScopes = new HashSet<>();
        OAuth2Application application = this.applicationService.findByClientId(clientId);
        OAuth2AuthorizationConsent currentAuthorizationConsent = this.authorizationConsentService.findById(clientId, principal.getName());
        Set<String> authorizedScopes = (Set) Optional.ofNullable(currentAuthorizationConsent).map((v0) -> {
            return v0.getScopes();
        }).orElse(Collections.emptySet());
        for (String requestedScope : StringUtils.delimitedListToStringArray(scope, SymbolConstants.SPACE)) {
            if (!SystemConstants.SCOPE_OPENID.equals(requestedScope)) {
                if (authorizedScopes.contains(requestedScope)) {
                    previouslyApprovedScopes.add(requestedScope);
                } else {
                    scopesToApprove.add(requestedScope);
                }
            }
        }
        Set<String> redirectUris = StringUtils.commaDelimitedListToSet(application.getRedirectUris());
        model.addAttribute("clientId", clientId);
        model.addAttribute("state", state);
        model.addAttribute("scopesToAuthorize", withDescription(scopesToApprove));
        model.addAttribute("scopesPreviouslyAuthorized", withDescription(previouslyApprovedScopes));
        model.addAttribute("principalName", principal.getName());
        model.addAttribute("applicationName", application.getApplicationName());
        model.addAttribute("logo", application.getLogo());
        model.addAttribute("redirectUri", redirectUris.iterator().next());
        model.addAttribute("userCode", userCode);
        String action = ServiceContextHolder.getAuthorizationEndpoint();
        if (StringUtils.hasText(userCode)) {
            action = ServiceContextHolder.getDeviceVerificationEndpoint();
        }
        model.addAttribute("action", action);
        return "consent";
    }

    private void initDictionaries() {
        List<E> listFindAll = this.scopeService.findAll();
        if (CollectionUtils.isNotEmpty(listFindAll)) {
            if (MapUtils.isEmpty(this.dictionaries) || listFindAll.size() != this.dictionaries.size()) {
                this.dictionaries = (Map) listFindAll.stream().collect(Collectors.toMap((v0) -> {
                    return v0.getScopeCode();
                }, item -> {
                    return item;
                }));
            }
        }
    }

    private Set<Option> withDescription(Set<String> scopes) {
        if (CollectionUtils.isNotEmpty(scopes)) {
            return (Set) scopes.stream().map(item -> {
                return scopeToOption(this.dictionaries.get(item));
            }).collect(Collectors.toSet());
        }
        return new HashSet();
    }

    private Option scopeToOption(OAuth2Scope scope) {
        Option option = new Option();
        String label = scope.getDescription() == null ? scope.getScopeName() : scope.getDescription();
        option.setLabel(label);
        option.setValue(scope.getScopeCode());
        return option;
    }
}
