package com.pigx.engine.core.identity.utils;

import com.pigx.engine.core.definition.constant.SymbolConstants;
import com.pigx.engine.core.definition.utils.WellFormedUtils;
import com.pigx.engine.core.identity.domain.UserPrincipal;
import java.util.Optional;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

/* loaded from: core-identity-3.5.7.0.jar:cn/herodotus/engine/core/identity/utils/SecurityUtils.class */
public class SecurityUtils {
    public static final String PREFIX_ROLE = "ROLE_";
    private static final PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    public static boolean matches(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    public static boolean isEncrypted(String password) {
        return Strings.CS.containsAny(password, new CharSequence[]{SymbolConstants.OPEN_CURLY_BRACE, "}"});
    }

    public static String encrypt(String password) {
        return encrypt(password, passwordEncoder);
    }

    public static String encrypt(String password, PasswordEncoder passwordEncoder2) {
        if (StringUtils.isNotBlank(password)) {
            if (isEncrypted(password)) {
                return password;
            }
            return passwordEncoder2.encode(password);
        }
        return null;
    }

    public static String wellFormRolePrefix(String content) {
        return WellFormedUtils.robustness(content, PREFIX_ROLE, true, true);
    }

    public static UserPrincipal getUserPrincipal(Authentication authentication) {
        Object details = authentication.getDetails();
        if (ObjectUtils.isNotEmpty(details) && (details instanceof UserPrincipal)) {
            UserPrincipal userPrincipal = (UserPrincipal) details;
            return userPrincipal;
        }
        return null;
    }

    public static Optional<UserPrincipal> findUserPrincipal(Authentication authentication) {
        return Optional.ofNullable(getUserPrincipal(authentication));
    }

    public static Optional<String> findUsername(Authentication authentication) {
        return findUserPrincipal(authentication).map((v0) -> {
            return v0.getName();
        });
    }

    public static String getUsername(Authentication authentication) {
        return findUsername(authentication).orElse(null);
    }

    public static Optional<String> findUserId(Authentication authentication) {
        return findUserPrincipal(authentication).map((v0) -> {
            return v0.getId();
        });
    }

    public static String getUserId(Authentication authentication) {
        return findUserId(authentication).orElse(null);
    }

    public static Optional<String> findEmail(Authentication authentication) {
        return findUserPrincipal(authentication).map((v0) -> {
            return v0.getEmail();
        });
    }

    public static String getEmail(Authentication authentication) {
        return findEmail(authentication).orElse(null);
    }
}
