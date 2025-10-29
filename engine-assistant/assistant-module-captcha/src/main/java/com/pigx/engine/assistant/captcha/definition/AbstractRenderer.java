package com.pigx.engine.assistant.captcha.definition;

import cn.hutool.v7.swing.img.ImgUtil;
import com.pigx.engine.assistant.captcha.properties.CaptchaProperties;
import com.pigx.engine.assistant.captcha.provider.ResourceProvider;
import com.pigx.engine.cache.jetcache.stamp.AbstractStampManager;
import com.pigx.engine.core.definition.support.CaptchaRenderer;

import java.awt.image.BufferedImage;


public abstract class AbstractRenderer<K, V> extends AbstractStampManager<K, V> implements CaptchaRenderer {

    protected static final String BASE64_PNG_IMAGE_PREFIX = "data:image/png;base64,";
    protected static final String BASE64_GIF_IMAGE_PREFIX = "data:image/gif;base64,";

    private final ResourceProvider resourceProvider;

    protected AbstractRenderer(ResourceProvider resourceProvider, String cacheName) {
        super(cacheName);
        this.resourceProvider = resourceProvider;
    }

    protected ResourceProvider getResourceProvider() {
        return resourceProvider;
    }

    protected CaptchaProperties getCaptchaProperties() {
        return getResourceProvider().getCaptchaProperties();
    }

    protected String getBase64ImagePrefix() {
        return BASE64_PNG_IMAGE_PREFIX;
    }

    protected String toBase64(BufferedImage bufferedImage) {
        String image = ImgUtil.toBase64(bufferedImage, ImgUtil.IMAGE_TYPE_PNG);
        return getBase64ImagePrefix() + image;
    }
}
