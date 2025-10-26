package com.pigx.engine.core.foundation.utils;

import com.pigx.engine.core.definition.constant.SymbolConstants;
import cn.hutool.v7.json.JSONUtil;
import java.io.IOException;
import java.net.URL;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.Strings;
import org.apache.commons.text.StringEscapeUtils;
import org.owasp.validator.html.AntiSamy;
import org.owasp.validator.html.CleanResults;
import org.owasp.validator.html.Policy;
import org.owasp.validator.html.PolicyException;
import org.owasp.validator.html.ScanException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;

public class XssUtils {
    private static final Logger log = LoggerFactory.getLogger(XssUtils.class);
    private static volatile XssUtils INSTANCE;
    private final AntiSamy antiSamy;
    private final String nbsp;
    private final String quot;

    private XssUtils() {
        Policy policy = this.createPolicy();
        this.antiSamy = ObjectUtils.isNotEmpty(policy) ? new AntiSamy(policy) : new AntiSamy();
        this.nbsp = this.cleanHtml("&nbsp;");
        this.quot = this.cleanHtml("\"");
    }

    private static XssUtils getInstance() {
        if (ObjectUtils.isEmpty(INSTANCE)) {
            synchronized(XssUtils.class) {
                if (ObjectUtils.isEmpty(INSTANCE)) {
                    INSTANCE = new XssUtils();
                }
            }
        }

        return INSTANCE;
    }

    public static String process(String taintedHTML) {
        String cleanHtml = StringEscapeUtils.unescapeHtml4(getInstance().cleanHtml(taintedHTML));
        if (Strings.CS.startsWith(cleanHtml, "\n")) {
            cleanHtml = Strings.CS.removeStart(cleanHtml, "\n");
        }

        if (JSONUtil.isTypeJSON(cleanHtml) && Strings.CS.contains(cleanHtml, "\n")) {
            cleanHtml = cleanHtml.replaceAll("\n", " ");
        }

        String temp = cleanHtml.replaceAll(getInstance().nbsp, "");
        String result = temp.replaceAll(getInstance().quot, "\"");
        log.trace("[Herodotus] |- Antisamy process value from [{}] to [{}]", taintedHTML, result);
        return result;
    }

    private Policy createPolicy() {
        try {
            URL url = ResourceResolverUtils.getURL("classpath:antisamy/antisamy-anythinggoes.xml");
            return Policy.getInstance(url);
        } catch (PolicyException | IOException e) {
            log.warn("[Herodotus] |- Antisamy create policy error! {}", ((Exception)e).getMessage());
            return null;
        }
    }

    private CleanResults scan(String taintedHtml) throws ScanException, PolicyException {
        return this.antiSamy.scan(taintedHtml);
    }

    private String cleanHtml(String taintedHtml) {
        try {
            CleanResults cleanResults = this.scan(taintedHtml);
            return cleanResults.getCleanHTML();
        } catch (PolicyException | ScanException e) {
            log.error("[Herodotus] |- Antisamy scan catch error! {}", ((Exception)e).getMessage());
            return taintedHtml;
        }
    }
}