package com.pigx.engine.oauth2.authentication.utils;

import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AuthorizationGrantAuthenticationToken;
import org.springframework.util.StringUtils;

/**
 * A verifier for DPoP Proof {@link Jwt}'s.
 *
 * @author Joe Grandja
 * @see DPoPProofJwtDecoderFactory
 * @see <a target="_blank" href="https://datatracker.ietf.org/doc/html/rfc9449">RFC 9449
 * OAuth 2.0 Demonstrating Proof of Possession (DPoP)</a>
 * @since 1.5
 */
public final class DPoPProofVerifier {

    private static final JwtDecoderFactory<DPoPProofContext> dPoPProofVerifierFactory = new DPoPProofJwtDecoderFactory();

    private DPoPProofVerifier() {
    }

    public static Jwt verifyIfAvailable(OAuth2AuthorizationGrantAuthenticationToken authorizationGrantAuthentication) {
        String dPoPProof = (String) authorizationGrantAuthentication.getAdditionalParameters().get("dpop_proof");
        if (!StringUtils.hasText(dPoPProof)) {
            return null;
        }

        String method = (String) authorizationGrantAuthentication.getAdditionalParameters().get("dpop_method");
        String targetUri = (String) authorizationGrantAuthentication.getAdditionalParameters().get("dpop_target_uri");

        Jwt dPoPProofJwt;
        try {
            // @formatter:off
			DPoPProofContext dPoPProofContext = DPoPProofContext.withDPoPProof(dPoPProof)
					.method(method)
					.targetUri(targetUri)
					.build();
			// @formatter:on
            JwtDecoder dPoPProofVerifier = dPoPProofVerifierFactory.createDecoder(dPoPProofContext);
            dPoPProofJwt = dPoPProofVerifier.decode(dPoPProof);
        } catch (Exception ex) {
            throw new OAuth2AuthenticationException(new OAuth2Error(OAuth2ErrorCodes.INVALID_DPOP_PROOF), ex);
        }

        return dPoPProofJwt;
    }

}
