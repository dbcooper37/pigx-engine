package com.pigx.engine.assistant.captcha.config;

import com.pigx.engine.assistant.captcha.properties.CaptchaProperties;
import com.pigx.engine.assistant.captcha.provider.ResourceProvider;
import com.pigx.engine.assistant.captcha.renderer.behavior.JigsawCaptchaRenderer;
import com.pigx.engine.assistant.captcha.renderer.behavior.WordClickCaptchaRenderer;
import com.pigx.engine.assistant.captcha.renderer.graphic.*;
import com.pigx.engine.assistant.captcha.renderer.hutool.CircleCaptchaRenderer;
import com.pigx.engine.assistant.captcha.renderer.hutool.GifCaptchaRenderer;
import com.pigx.engine.assistant.captcha.renderer.hutool.LineCaptchaRenderer;
import com.pigx.engine.assistant.captcha.renderer.hutool.ShearCaptchaRenderer;
import com.pigx.engine.core.foundation.enums.CaptchaCategory;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(CaptchaProperties.class)
public class AssistantCaptchaConfiguration {

    private static final Logger log = LoggerFactory.getLogger(AssistantCaptchaConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.debug("[PIGXD] |- Module [Assistant Captcha] Configure.");
    }

    @Bean
    @ConditionalOnMissingBean
    public ResourceProvider resourceProvider(CaptchaProperties captchaProperties) {
        ResourceProvider resourceProvider = new ResourceProvider(captchaProperties);
        log.trace("[PIGXD] |- Bean [Resource Provider] Configure.");
        return resourceProvider;
    }

    @Configuration(proxyBeanMethods = false)
    static class BehaviorCaptchaConfiguration {

        @Bean(CaptchaCategory.JIGSAW_CAPTCHA)
        public JigsawCaptchaRenderer jigsawCaptchaRenderer(ResourceProvider resourceProvider) {
            JigsawCaptchaRenderer jigsawCaptchaRenderer = new JigsawCaptchaRenderer(resourceProvider);
            log.trace("[PIGXD] |- Bean [Jigsaw Captcha Renderer] Configure.");
            return jigsawCaptchaRenderer;
        }

        @Bean(CaptchaCategory.WORD_CLICK_CAPTCHA)
        public WordClickCaptchaRenderer wordClickCaptchaRenderer(ResourceProvider resourceProvider) {
            WordClickCaptchaRenderer wordClickCaptchaRenderer = new WordClickCaptchaRenderer(resourceProvider);
            log.trace("[PIGXD] |- Bean [Word Click Captcha Renderer] Configure.");
            return wordClickCaptchaRenderer;
        }
    }

    @Configuration(proxyBeanMethods = false)
    static class GraphicCaptchaConfiguration {

        @Bean(CaptchaCategory.ARITHMETIC_CAPTCHA)
        public ArithmeticCaptchaRenderer arithmeticCaptchaRenderer(ResourceProvider resourceProvider) {
            ArithmeticCaptchaRenderer arithmeticCaptchaRenderer = new ArithmeticCaptchaRenderer(resourceProvider);
            log.trace("[PIGXD] |- Bean [Arithmetic Captcha Renderer] Configure.");
            return arithmeticCaptchaRenderer;
        }

        @Bean(CaptchaCategory.CHINESE_CAPTCHA)
        public ChineseCaptchaRenderer chineseCaptchaRenderer(ResourceProvider resourceProvider) {
            ChineseCaptchaRenderer chineseCaptchaRenderer = new ChineseCaptchaRenderer(resourceProvider);
            log.trace("[PIGXD] |- Bean [Chinese Captcha Renderer] Configure.");
            return chineseCaptchaRenderer;
        }

        @Bean(CaptchaCategory.CHINESE_GIF_CAPTCHA)
        public ChineseGifCaptchaRenderer chineseGifCaptchaRenderer(ResourceProvider resourceProvider) {
            ChineseGifCaptchaRenderer chineseGifCaptchaRenderer = new ChineseGifCaptchaRenderer(resourceProvider);
            log.trace("[PIGXD] |- Bean [Chinese Gif Captcha Renderer] Configure.");
            return chineseGifCaptchaRenderer;
        }

        @Bean(CaptchaCategory.SPEC_GIF_CAPTCHA)
        public SpecGifCaptchaRenderer specGifCaptchaRenderer(ResourceProvider resourceProvider) {
            SpecGifCaptchaRenderer specGifCaptchaRenderer = new SpecGifCaptchaRenderer(resourceProvider);
            log.trace("[PIGXD] |- Bean [Spec Gif Captcha Renderer] Configure.");
            return specGifCaptchaRenderer;
        }

        @Bean(CaptchaCategory.SPEC_CAPTCHA)
        public SpecCaptchaRenderer specCaptchaRenderer(ResourceProvider resourceProvider) {
            SpecCaptchaRenderer specCaptchaRenderer = new SpecCaptchaRenderer(resourceProvider);
            log.trace("[PIGXD] |- Bean [Spec Captcha Renderer] Configure.");
            return specCaptchaRenderer;
        }
    }

    @Configuration(proxyBeanMethods = false)
    static class HutoolCaptchaConfiguration {

        @Bean(CaptchaCategory.HUTOOL_LINE_CAPTCHA)
        public LineCaptchaRenderer lineCaptchaRenderer(ResourceProvider resourceProvider) {
            LineCaptchaRenderer lineCaptchaRenderer = new LineCaptchaRenderer(resourceProvider);
            log.trace("[PIGXD] |- Bean [Hutool Line Captcha Renderer] Configure.");
            return lineCaptchaRenderer;
        }

        @Bean(CaptchaCategory.HUTOOL_CIRCLE_CAPTCHA)
        public CircleCaptchaRenderer circleCaptchaRenderer(ResourceProvider resourceProvider) {
            CircleCaptchaRenderer circleCaptchaRenderer = new CircleCaptchaRenderer(resourceProvider);
            log.trace("[PIGXD] |- Bean [Hutool Circle Captcha Renderer] Configure.");
            return circleCaptchaRenderer;
        }

        @Bean(CaptchaCategory.HUTOOL_SHEAR_CAPTCHA)
        public ShearCaptchaRenderer shearCaptchaRenderer(ResourceProvider resourceProvider) {
            ShearCaptchaRenderer shearCaptchaRenderer = new ShearCaptchaRenderer(resourceProvider);
            log.trace("[PIGXD] |- Bean [Hutool Shear Captcha Renderer] Configure.");
            return shearCaptchaRenderer;
        }

        @Bean(CaptchaCategory.HUTOOL_GIF_CAPTCHA)
        public GifCaptchaRenderer gifCaptchaRenderer(ResourceProvider resourceProvider) {
            GifCaptchaRenderer gifCaptchaRenderer = new GifCaptchaRenderer(resourceProvider);
            log.trace("[PIGXD] |- Bean [Hutool Gif Captcha Renderer] Configure.");
            return gifCaptchaRenderer;
        }
    }
}
