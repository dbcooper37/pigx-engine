package com.pigx.engine.core.definition.utils;

import com.pigx.engine.core.definition.constant.SymbolConstants;
import com.pigx.engine.core.definition.constant.SystemConstants;
import com.pigx.engine.core.definition.enums.Protocol;
import com.pigx.engine.core.definition.exception.PropertyValueIsNotSetException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;


public class WellFormedUtils {

    private static final Logger log = LoggerFactory.getLogger(WellFormedUtils.class);

    /**
     * 含字符的字符串鲁棒性校验。
     *
     * @param content      字符串内容
     * @param symbol       指定的字符
     * @param isStartsWith 开头还是结尾：true 字符串开头；false 字符串结尾
     * @param isRetain     是否保留：true 保留，没有该字符就加上；false 去除，有该字符则去掉
     * @return 健壮的字符串
     */
    public static String robustness(String content, String symbol, boolean isStartsWith, boolean isRetain) {
        if (isStartsWith) {
            if (isRetain) {
                if (Strings.CS.startsWith(content, symbol)) {
                    return content;
                } else {
                    return symbol + content;
                }
            } else {
                if (Strings.CS.startsWith(content, symbol)) {
                    return Strings.CS.removeStart(content, symbol);
                } else {
                    return content;
                }
            }
        } else {
            if (isRetain) {
                if (Strings.CS.endsWith(content, symbol)) {
                    return content;
                } else {
                    return content + symbol;
                }
            } else {
                if (Strings.CS.endsWith(content, symbol)) {
                    return Strings.CS.removeEnd(content, symbol);
                } else {
                    return content;
                }
            }
        }
    }

    public static String forwardSlashRobustness(String content, boolean isStartsWith, boolean isRetain) {
        return robustness(content, SymbolConstants.FORWARD_SLASH, isStartsWith, isRetain);
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

    /**
     * 符合语法规则的 URL
     * <p>
     * 检测地址相关字符串是否以"/"结尾，如果没有就帮助增加一个 ""/""
     *
     * @param url http 请求地址字符串
     * @return 结构合理的请求地址字符串
     */
    public static String url(String url) {
        return endsWithForwardSlash(url);
    }

    /**
     * 符合语法规则的 ParentId
     * <p>
     * 树形结构 ParentId 健壮性校验方法。
     *
     * @param parentId 父节点ID
     * @return 格式友好的 parentId
     */
    public static String parentId(String parentId) {
        if (StringUtils.isBlank(parentId)) {
            return SystemConstants.TREE_ROOT_ID;
        } else {
            return parentId;
        }
    }

    /**
     * 将IP地址加端口号，转换为http地址。
     *
     * @param address             ip地址加端口号，格式：ip:port
     * @param protocol            http协议类型 {@link Protocol}
     * @param endWithForwardSlash 是否在结尾添加“/”
     * @return http格式地址
     */
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

    /**
     * 将IP地址加端口号，转换为http地址。
     *
     * @param address             ip地址加端口号，格式：ip:port
     * @param endWithForwardSlash 是否在结尾添加“/”
     * @return http格式地址
     */
    public static String addressToUri(String address, boolean endWithForwardSlash) {
        return addressToUri(address, Protocol.HTTP, endWithForwardSlash);
    }

    /**
     * 将IP地址加端口号，转换为http地址。
     *
     * @param address ip地址加端口号，格式：ip:port
     * @return http格式地址
     */
    public static String addressToUri(String address) {
        return addressToUri(address, false);
    }

    /**
     * 获取运行主机ip地址
     *
     * @return ip地址，或者null
     */
    public static String getHostAddress() {
        InetAddress address;
        try {
            address = InetAddress.getLocalHost();
            return address.getHostAddress();
        } catch (UnknownHostException e) {
            log.error("[PIGXD] |- Get host address error: {}", e.getLocalizedMessage());
            return null;
        }
    }

    public static String serviceUri(String serviceUri, String serviceName, String gatewayServiceUri, String abbreviation) {
        if (StringUtils.isNotBlank(serviceUri)) {
            return serviceUri;
        } else {
            if (StringUtils.isBlank(serviceName)) {
                log.error("[PIGXD] |- Property [{} Service Name] is not set or property format is incorrect!", abbreviation);
                throw new PropertyValueIsNotSetException();
            } else {
                if (StringUtils.isBlank(gatewayServiceUri)) {
                    log.error("[PIGXD] |- Property [gateway-service-uri] is not set or property format is incorrect!");
                    throw new PropertyValueIsNotSetException();
                } else {
                    return WellFormedUtils.url(gatewayServiceUri) + serviceName;
                }
            }
        }
    }

    public static String sasUri(String uri, String endpoint, String issuerUri) {
        if (StringUtils.isNotBlank(uri)) {
            return uri;
        } else {
            if (StringUtils.isBlank(issuerUri)) {
                log.error("[PIGXD] |- Property [issuer-uri] is not set or property format is incorrect!");
                throw new PropertyValueIsNotSetException();
            } else {
                return issuerUri + endpoint;
            }
        }
    }

    public static boolean isToken(String token) {
        if (StringUtils.isNotBlank(token)) {
            return Strings.CS.startsWith(token, SystemConstants.BEARER_TOKEN) || Strings.CS.startsWith(token, SystemConstants.BASIC_TOKEN);
        } else {
            return true;
        }
    }
}
