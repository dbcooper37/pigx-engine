package com.pigx.engine.servlet.message.autoconfigure;

import com.google.common.collect.Lists;
import com.pigx.engine.core.autoconfigure.crypto.CryptoAutoConfiguration;
import com.pigx.engine.web.servlet.config.HttpCryptoConfiguration;
import com.pigx.engine.web.servlet.crypto.DecryptRequestParamMapResolver;
import com.pigx.engine.web.servlet.crypto.DecryptRequestParamResolver;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.web.method.annotation.RequestParamMapMethodArgumentResolver;
import org.springframework.web.method.annotation.RequestParamMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import java.util.List;


@AutoConfiguration(after = CryptoAutoConfiguration.class)
@Import({
        HttpCryptoConfiguration.class
})
public class WebMvcCryptoAutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(WebMvcCryptoAutoConfiguration.class);
    private final RequestMappingHandlerAdapter requestMappingHandlerAdapter;
    private final DecryptRequestParamResolver decryptRequestParamResolver;
    private final DecryptRequestParamMapResolver decryptRequestParamMapResolver;

    public WebMvcCryptoAutoConfiguration(RequestMappingHandlerAdapter requestMappingHandlerAdapter, DecryptRequestParamResolver decryptRequestParamResolver, DecryptRequestParamMapResolver decryptRequestParamMapResolver) {
        this.requestMappingHandlerAdapter = requestMappingHandlerAdapter;
        this.decryptRequestParamResolver = decryptRequestParamResolver;
        this.decryptRequestParamMapResolver = decryptRequestParamMapResolver;
    }

    @PostConstruct
    public void postConstruct() {
        log.info("[PIGXD] |- Auto [Web Crypto] Configure.");

        List<HandlerMethodArgumentResolver> unmodifiableList = requestMappingHandlerAdapter.getArgumentResolvers();
        List<HandlerMethodArgumentResolver> list = Lists.newArrayList();
        for (HandlerMethodArgumentResolver methodArgumentResolver : unmodifiableList) {
            //需要在requestParam之前执行自定义逻辑，然后再执行下一个逻辑（责任链模式）
            if (methodArgumentResolver instanceof RequestParamMapMethodArgumentResolver) {
                decryptRequestParamMapResolver.setRequestParamMapMethodArgumentResolver((RequestParamMapMethodArgumentResolver) methodArgumentResolver);
                list.add(decryptRequestParamMapResolver);
            }
            if (methodArgumentResolver instanceof RequestParamMethodArgumentResolver) {
                decryptRequestParamResolver.setRequestParamMethodArgumentResolver((RequestParamMethodArgumentResolver) methodArgumentResolver);
                list.add(decryptRequestParamResolver);
            }
            list.add(methodArgumentResolver);
        }
        log.debug("[PIGXD] |- Rest Crypto HandlerMethodArgumentResolver Configure.");

        requestMappingHandlerAdapter.setArgumentResolvers(list);
    }
}
