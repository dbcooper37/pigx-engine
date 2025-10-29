package com.pigx.engine.core.identity.utils;

import com.pigx.engine.core.definition.constant.SymbolConstants;
import com.pigx.engine.core.definition.utils.WellFormedUtils;
import com.pigx.engine.core.identity.domain.UserPrincipal;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;


public class SecurityUtils {

    public static final String PREFIX_ROLE = "ROLE_";

    private static final PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    /**
     * 密码验证
     *
     * @param rawPassword     原始密码
     * @param encodedPassword 加密后的密码
     * @return 密码是否匹配
     */
    public static boolean matches(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    /**
     * 判断是否为已经是加密过的密码
     *
     * @param password 明文密码
     * @return 是否已经加密
     */
    public static boolean isEncrypted(String password) {
        return Strings.CS.containsAny(password, SymbolConstants.OPEN_CURLY_BRACE, SymbolConstants.CLOSE_CURLY_BRACE);
    }

    /**
     * 密码加密
     *
     * @param password 明文密码
     * @return 已加密密码
     */
    public static String encrypt(String password) {
        return encrypt(password, passwordEncoder);
    }

    public static String encrypt(String password, PasswordEncoder passwordEncoder) {
        if (StringUtils.isNotBlank(password)) {
            if (isEncrypted(password)) {
                return password;
            } else {
                return passwordEncoder.encode(password);
            }
        } else {
            return null;
        }
    }

    public static String wellFormRolePrefix(String content) {
        return WellFormedUtils.robustness(content, PREFIX_ROLE, true, true);
    }

    /**
     * 从 {@link Authentication} 读取用户信息 {@link UserPrincipal}
     *
     * @param authentication {@link Authentication}
     * @return 用户信息 {@link UserPrincipal}
     */
    public static UserPrincipal getUserPrincipal(Authentication authentication) {
        Object details = authentication.getDetails();
        if (ObjectUtils.isNotEmpty(details) && details instanceof UserPrincipal userPrincipal) {
            return userPrincipal;
        }
        return null;
    }

    /**
     * 从 {@link Authentication} 查找用户信息 {@link UserPrincipal}
     *
     * @param authentication {@link Authentication}
     * @return optional {@link Optional}
     */
    public static Optional<UserPrincipal> findUserPrincipal(Authentication authentication) {
        return Optional.ofNullable(getUserPrincipal(authentication));
    }

    /**
     * 从 {@link Authentication} 查找用户标识信息
     *
     * @param authentication {@link Authentication}
     * @return optional {@link Optional}
     */
    public static Optional<String> findUsername(Authentication authentication) {
        return findUserPrincipal(authentication).map(UserPrincipal::getName);
    }

    /**
     * 从 {@link Authentication} 读取用户标识信息
     *
     * @param authentication {@link Authentication}
     * @return 用户名
     */
    public static String getUsername(Authentication authentication) {
        return findUsername(authentication).orElse(null);
    }

    /**
     * 从 {@link Authentication} 查找用户Id
     *
     * @param authentication {@link Authentication}
     * @return optional {@link Optional}
     */
    public static Optional<String> findUserId(Authentication authentication) {
        return findUserPrincipal(authentication).map(UserPrincipal::getId);
    }

    /**
     * 从 {@link Authentication} 读取用户Id
     *
     * @param authentication {@link Authentication}
     * @return 用户ID
     */
    public static String getUserId(Authentication authentication) {
        return findUserId(authentication).orElse(null);
    }

    /**
     * 从 {@link Authentication} 查找用户Email
     *
     * @param authentication {@link Authentication}
     * @return optional {@link Optional}
     */
    public static Optional<String> findEmail(Authentication authentication) {
        return findUserPrincipal(authentication).map(UserPrincipal::getEmail);
    }

    /**
     * 从 {@link Authentication} 读取用户Email
     *
     * @param authentication {@link Authentication}
     * @return 用户Email
     */
    public static String getEmail(Authentication authentication) {
        return findEmail(authentication).orElse(null);
    }
}
