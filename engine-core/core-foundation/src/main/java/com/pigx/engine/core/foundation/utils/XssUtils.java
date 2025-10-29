package com.pigx.engine.core.foundation.utils;

import cn.hutool.v7.json.JSONUtil;
import com.pigx.engine.core.definition.constant.SymbolConstants;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.Strings;
import org.apache.commons.text.StringEscapeUtils;
import org.owasp.validator.html.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;


public class XssUtils {

    private static final Logger log = LoggerFactory.getLogger(XssUtils.class);

    private static volatile XssUtils INSTANCE;
    private final AntiSamy antiSamy;
    private final String nbsp;
    private final String quot;

    private XssUtils() {
        Policy policy = createPolicy();
        this.antiSamy = ObjectUtils.isNotEmpty(policy) ? new AntiSamy(policy) : new AntiSamy();
        this.nbsp = cleanHtml(SymbolConstants.NBSP);
        this.quot = cleanHtml(SymbolConstants.QUOTE);
    }

    private static XssUtils getInstance() {
        if (ObjectUtils.isEmpty(INSTANCE)) {
            synchronized (XssUtils.class) {
                if (ObjectUtils.isEmpty(INSTANCE)) {
                    INSTANCE = new XssUtils();
                }
            }
        }

        return INSTANCE;
    }

    public static String process(String taintedHTML) {
        // 对转义的HTML特殊字符（<、>、"等）进行反转义，因为AntiSamy调用scan方法时会将特殊字符转义
        String cleanHtml = StringEscapeUtils.unescapeHtml4(getInstance().cleanHtml(taintedHTML));

        if (Strings.CS.startsWith(cleanHtml, SymbolConstants.NEW_LINE)) {
            // StringEscapeUtils.unescapeHtml4 转换某些内容时，会在开头增加 \n。去除之后才好判断，否则下面判断是否是 json 会出错。
            cleanHtml = Strings.CS.removeStart(cleanHtml, SymbolConstants.NEW_LINE);
        }

        if (JSONUtil.isTypeJSON(cleanHtml) && Strings.CS.contains(cleanHtml, SymbolConstants.NEW_LINE)) {
            // AntiSamy会把“ ”转换 \n。如果出现时间字符串，中间包含空格就会出现错误"
            cleanHtml = cleanHtml.replaceAll(SymbolConstants.NEW_LINE, SymbolConstants.SPACE);
        }
        // AntiSamy会把“&nbsp;”转换成乱码，把双引号转换成"&quot;" 先将&nbsp;的乱码替换为空，双引号的乱码替换为双引号
        String temp = cleanHtml.replaceAll(getInstance().nbsp, SymbolConstants.BLANK);
        String result = temp.replaceAll(getInstance().quot, SymbolConstants.QUOTE);
        log.trace("[PIGXD] |- Antisamy process value from [{}] to [{}]", taintedHTML, result);
        return result;
    }

    private Policy createPolicy() {
        try {
            URL url = ResourceResolverUtils.getURL("classpath:antisamy/antisamy-anythinggoes.xml");
            return Policy.getInstance(url);
        } catch (IOException | PolicyException e) {
            log.warn("[PIGXD] |- Antisamy create policy error! {}", e.getMessage());
            return null;
        }
    }

    private CleanResults scan(String taintedHtml) throws ScanException, PolicyException {
        return antiSamy.scan(taintedHtml);
    }

    private String cleanHtml(String taintedHtml) {
        try {
            // 使用AntiSamy清洗数据
            final CleanResults cleanResults = scan(taintedHtml);
            return cleanResults.getCleanHTML();
        } catch (ScanException | PolicyException e) {
            log.error("[PIGXD] |- Antisamy scan catch error! {}", e.getMessage());
            return taintedHtml;
        }
    }
}
