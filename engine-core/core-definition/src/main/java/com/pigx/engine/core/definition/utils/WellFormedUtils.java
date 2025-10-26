package com.pigx.engine.core.definition.utils;

import com.pigx.engine.core.definition.constant.SystemConstants;
import com.pigx.engine.core.definition.enums.Protocol;
import com.pigx.engine.core.definition.exception.PropertyValueIsNotSetException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: core-definition-3.5.7.0.jar:cn/herodotus/engine/core/definition/utils/WellFormedUtils.class */
public class WellFormedUtils {
    private static final Logger log = LoggerFactory.getLogger(WellFormedUtils.class);

    public static String robustness(String content, String symbol, boolean isStartsWith, boolean isRetain) {
        if (isStartsWith) {
            if (isRetain) {
                if (Strings.CS.startsWith(content, symbol)) {
                    return content;
                }
                return symbol + content;
            }
            if (Strings.CS.startsWith(content, symbol)) {
                return Strings.CS.removeStart(content, symbol);
            }
            return content;
        }
        if (isRetain) {
            if (Strings.CS.endsWith(content, symbol)) {
                return content;
            }
            return content + symbol;
        }
        if (Strings.CS.endsWith(content, symbol)) {
            return Strings.CS.removeEnd(content, symbol);
        }
        return content;
    }

    public static String forwardSlashRobustness(String content, boolean isStartsWith, boolean isRetain) {
        return robustness(content, "/", isStartsWith, isRetain);
    }

    public static String startsWithForwardSlash(String content, boolean isRetain) {
        return forwardSlashRobustness(content, true, isRetain);
    }

    public static String startsWithForwardSlash(String content) {
        return startsWithForwardSlash(content, true);
    }

    public static String endsWithForwardSlash(String content, boolean isRetain) {
        return forwardSlashRobustness(content, false, isRetain);
    }

    public static String endsWithForwardSlash(String content) {
        return endsWithForwardSlash(content, true);
    }

    public static String url(String url) {
        return endsWithForwardSlash(url);
    }

    public static String parentId(String parentId) {
        if (StringUtils.isBlank(parentId)) {
            return "0";
        }
        return parentId;
    }

    public static String addressToUri(String address, Protocol protocol, boolean endWithForwardSlash) {
        StringBuilder stringBuilder = new StringBuilder();
        if (!Strings.CS.startsWith(address, protocol.getFormat())) {
            stringBuilder.append(protocol.getFormat());
        }
        if (endWithForwardSlash) {
            stringBuilder.append(url(address));
        } else {
            stringBuilder.append(address);
        }
        return stringBuilder.toString();
    }

    public static String addressToUri(String address, boolean endWithForwardSlash) {
        return addressToUri(address, Protocol.HTTP, endWithForwardSlash);
    }

    public static String addressToUri(String address) {
        return addressToUri(address, false);
    }

    public static String getHostAddress() throws UnknownHostException {
        try {
            InetAddress address = InetAddress.getLocalHost();
            return address.getHostAddress();
        } catch (UnknownHostException e) {
            log.error("[Herodotus] |- Get host address error: {}", e.getLocalizedMessage());
            return null;
        }
    }

    public static String serviceUri(String serviceUri, String serviceName, String gatewayServiceUri, String abbreviation) {
        if (StringUtils.isNotBlank(serviceUri)) {
            return serviceUri;
        }
        if (StringUtils.isBlank(serviceName)) {
            log.error("[Herodotus] |- Property [{} Service Name] is not set or property format is incorrect!", abbreviation);
            throw new PropertyValueIsNotSetException();
        }
        if (StringUtils.isBlank(gatewayServiceUri)) {
            log.error("[Herodotus] |- Property [gateway-service-uri] is not set or property format is incorrect!");
            throw new PropertyValueIsNotSetException();
        }
        return url(gatewayServiceUri) + serviceName;
    }

    public static String sasUri(String uri, String endpoint, String issuerUri) {
        if (StringUtils.isNotBlank(uri)) {
            return uri;
        }
        if (StringUtils.isBlank(issuerUri)) {
            log.error("[Herodotus] |- Property [issuer-uri] is not set or property format is incorrect!");
            throw new PropertyValueIsNotSetException();
        }
        return issuerUri + endpoint;
    }

    public static boolean isToken(String token) {
        return !StringUtils.isNotBlank(token) || Strings.CS.startsWith(token, SystemConstants.BEARER_TOKEN) || Strings.CS.startsWith(token, SystemConstants.BASIC_TOKEN);
    }
}
