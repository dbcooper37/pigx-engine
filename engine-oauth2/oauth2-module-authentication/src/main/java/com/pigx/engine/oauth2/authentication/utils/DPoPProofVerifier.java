package com.pigx.engine.oauth2.authentication.utils;

import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.jwt.DPoPProofContext;
import org.springframework.security.oauth2.jwt.DPoPProofJwtDecoderFactory;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoderFactory;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AuthorizationGrantAuthenticationToken;
import org.springframework.util.StringUtils;

/* loaded from: oauth2-module-authentication-3.5.7.0.jar:cn/herodotus/engine/oauth2/authentication/utils/DPoPProofVerifier.class */
public final class DPoPProofVerifier {
    private static final JwtDecoderFactory<DPoPProofContext> dPoPProofVerifierFactory = new DPoPProofJwtDecoderFactory();

    private DPoPProofVerifier() {
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: org.springframework.security.oauth2.core.OAuth2AuthenticationException */
    public static Jwt verifyIfAvailable(OAuth2AuthorizationGrantAuthenticationToken authorizationGrantAuthentication) throws OAuth2AuthenticationException {
        String dPoPProof = (String) authorizationGrantAuthentication.getAdditionalParameters().get("dpop_proof");
        if (!StringUtils.hasText(dPoPProof)) {
            return null;
        }
        String method = (String) authorizationGrantAuthentication.getAdditionalParameters().get("dpop_method");
        String targetUri = (String) authorizationGrantAuthentication.getAdditionalParameters().get("dpop_target_uri");
        try {
            DPoPProofContext dPoPProofContext = DPoPProofContext.withDPoPProof(dPoPProof).method(method).targetUri(targetUri).build();
            JwtDecoder dPoPProofVerifier = dPoPProofVerifierFactory.createDecoder(dPoPProofContext);
            Jwt dPoPProofJwt = dPoPProofVerifier.decode(dPoPProof);
            return dPoPProofJwt;
        } catch (Exception ex) {
            throw new OAuth2AuthenticationException(new OAuth2Error("invalid_dpop_proof"), ex);
        }
    }
}
