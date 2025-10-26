package com.pigx.engine.assistant.captcha.provider;

import com.pigx.engine.assistant.captcha.enums.CaptchaResource;
import com.pigx.engine.assistant.captcha.enums.FontStyle;
import com.pigx.engine.assistant.captcha.properties.CaptchaProperties;
import com.pigx.engine.core.foundation.utils.ResourceResolverUtils;
import cn.hutool.v7.core.data.id.IdUtil;
import cn.hutool.v7.core.io.IORuntimeException;
import cn.hutool.v7.core.io.file.FileUtil;
import cn.hutool.v7.extra.management.ManagementUtil;
import cn.hutool.v7.swing.FontUtil;
import cn.hutool.v7.swing.img.ImgUtil;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
/* loaded from: assistant-module-captcha-3.5.7.0.jar:cn/herodotus/engine/assistant/captcha/provider/ResourceProvider.class */
public class ResourceProvider implements InitializingBean {
    private static final Logger log = LoggerFactory.getLogger(ResourceProvider.class);
    private static final String FONT_RESOURCE = "classpath*:/fonts/*.ttf";
    private static final String FONT_FOLDER = "/usr/share/fonts/herodotus/";
    private final CaptchaProperties captchaProperties;
    private final Map<String, String[]> imageIndexes = new ConcurrentHashMap();
    private final Map<String, String> jigsawOriginalImages = new ConcurrentHashMap();
    private final Map<String, String> jigsawTemplateImages = new ConcurrentHashMap();
    private final Map<String, String> wordClickImages = new ConcurrentHashMap();
    private Map<String, Font> fonts = new ConcurrentHashMap();

    public ResourceProvider(CaptchaProperties captchaProperties) {
        this.captchaProperties = captchaProperties;
    }

    private static Map<String, String> getImages(String location) {
        if (ResourceResolverUtils.isClasspathAllUrl(location)) {
            try {
                Resource[] resources = ResourceResolverUtils.getResources(location);
                Map<String, String> images = new ConcurrentHashMap<>();
                if (ArrayUtils.isNotEmpty(resources)) {
                    Arrays.stream(resources).forEach(resource -> {
                        String data = ResourceResolverUtils.toBase64(resource);
                        if (StringUtils.isNotBlank(data)) {
                            images.put(IdUtil.fastSimpleUUID(), data);
                        }
                    });
                }
                return images;
            } catch (IOException e) {
                log.error("[Herodotus] |- Analysis the  location [{}] catch io error!", location, e);
            }
        }
        return new ConcurrentHashMap(8);
    }

    private static Font getFont(Resource resource) {
        try {
            return FontUtil.createFont(resource.getInputStream());
        } catch (IORuntimeException e) {
            log.warn("[Herodotus] |- Can not read font in the resources folder, maybe in docker.");
            Font fontInfileSystem = getFontUnderDocker(resource.getFilename());
            if (ObjectUtils.isNotEmpty(fontInfileSystem)) {
                return fontInfileSystem;
            }
            return null;
        } catch (IOException e2) {
            log.error("[Herodotus] |- Resource object in resources folder catch io error!", e2);
            return null;
        }
    }

    private static Font getFontUnderDocker(String filename) {
        if (ManagementUtil.getOsInfo().isLinux()) {
            String path = "/usr/share/fonts/herodotus/" + filename;
            File file = new File(path);
            if (ObjectUtils.isNotEmpty(file) && FileUtil.exists(file)) {
                System.out.println(file.getAbsolutePath());
                try {
                    Font font = FontUtil.createFont(file);
                    log.debug("[Herodotus] |- Read font [{}] under the DOCKER.", font.getFontName());
                    return font;
                } catch (IORuntimeException e) {
                    log.error("[Herodotus] |- Read font under the DOCKER catch error.");
                    return null;
                } catch (NullPointerException e2) {
                    log.error("[Herodotus] |- Read font under the DOCKER catch null error.");
                    return null;
                }
            }
            return null;
        }
        return null;
    }

    private static Map<String, Font> getFonts(String location) {
        if (ResourceResolverUtils.isClasspathAllUrl(location)) {
            try {
                Resource[] resources = ResourceResolverUtils.getResources(location);
                Map<String, Font> fonts = new ConcurrentHashMap<>();
                if (ArrayUtils.isNotEmpty(resources)) {
                    Arrays.stream(resources).forEach(resource -> {
                        Font font = getFont(resource);
                        if (ObjectUtils.isNotEmpty(font)) {
                            fonts.put(resource.getFilename(), font);
                        }
                    });
                }
                return fonts;
            } catch (IOException e) {
                log.error("[Herodotus] |- Analysis the  location [{}] catch io error!", location, e);
            }
        }
        return new ConcurrentHashMap(8);
    }

    public CaptchaProperties getCaptchaProperties() {
        return this.captchaProperties;
    }

    public void afterPropertiesSet() throws Exception {
        String systemName = ManagementUtil.getOsInfo().getName();
        log.debug("[Herodotus] |- Before captcha resource loading, check system. Current system is [{}]", systemName);
        log.debug("[Herodotus] |- Captcha resource loading is BEGIN！");
        loadImages(this.jigsawOriginalImages, getCaptchaProperties().getJigsaw().getOriginalResource(), CaptchaResource.JIGSAW_ORIGINAL);
        loadImages(this.jigsawTemplateImages, getCaptchaProperties().getJigsaw().getTemplateResource(), CaptchaResource.JIGSAW_TEMPLATE);
        loadImages(this.wordClickImages, getCaptchaProperties().getWordClick().getImageResource(), CaptchaResource.WORD_CLICK);
        loadFonts();
        log.debug("[Herodotus] |- Jigsaw captcha resource loading is END！");
    }

    private void loadImages(Map<String, String> container, String location, CaptchaResource captchaResource) {
        Map<String, String> resource = getImages(location);
        if (MapUtils.isNotEmpty(resource)) {
            container.putAll(resource);
            log.debug("[Herodotus] |- {} load complete, total number is [{}]", captchaResource.getContent(), Integer.valueOf(resource.size()));
            this.imageIndexes.put(captchaResource.name(), (String[]) resource.keySet().toArray(new String[0]));
        }
    }

    private void loadFonts() {
        if (MapUtils.isEmpty(this.fonts)) {
            this.fonts = getFonts(FONT_RESOURCE);
            log.debug("[Herodotus] |- Font load complete, total number is [{}]", Integer.valueOf(this.fonts.size()));
        }
    }

    private Font getDefaultFont(String fontName, int fontSize, FontStyle fontStyle) {
        if (StringUtils.isNotBlank(fontName)) {
            return new Font(fontName, fontStyle.getMapping(), fontSize);
        }
        return new Font("WenQuanYi Zen Hei", fontStyle.getMapping(), fontSize);
    }

    public Font getFont(String fontName, int fontSize, FontStyle fontStyle) {
        if (MapUtils.isEmpty(this.fonts) || ObjectUtils.isEmpty(this.fonts.get(fontName))) {
            return getDefaultFont(fontName, fontSize, fontStyle);
        }
        return this.fonts.get(fontName).deriveFont(fontStyle.getMapping(), Integer.valueOf(fontSize).floatValue());
    }

    public Font getFont(String fontName) {
        return getFont(fontName, 32, FontStyle.BOLD);
    }

    public Font getGraphicFont() {
        String fontName = getCaptchaProperties().getGraphics().getFont().getFontName();
        return getFont(fontName);
    }

    public Font getWaterMarkFont(int fontSize) {
        String fontName = getCaptchaProperties().getWatermark().getFontName();
        FontStyle fontStyle = getCaptchaProperties().getWatermark().getFontStyle();
        return getFont(fontName, fontSize, fontStyle);
    }

    public Font getChineseFont() {
        return getFont("WenQuanYi Zen Hei", 25, FontStyle.PLAIN);
    }

    private String getRandomBase64Image(Map<String, String> container, CaptchaResource captchaResource) {
        String[] data = this.imageIndexes.get(captchaResource.name());
        if (ArrayUtils.isNotEmpty(data)) {
            int randomInt = RandomProvider.randomInt(0, data.length);
            return container.get(data[randomInt]);
        }
        return null;
    }

    protected BufferedImage getRandomImage(Map<String, String> container, CaptchaResource captchaResource) {
        String data = getRandomBase64Image(container, captchaResource);
        if (StringUtils.isNotBlank(data)) {
            return ImgUtil.toImage(data);
        }
        return null;
    }

    public String getRandomBase64OriginalImage() {
        return getRandomBase64Image(this.jigsawOriginalImages, CaptchaResource.JIGSAW_ORIGINAL);
    }

    public String getRandomBase64TemplateImage() {
        return getRandomBase64Image(this.jigsawTemplateImages, CaptchaResource.JIGSAW_TEMPLATE);
    }

    public BufferedImage getRandomOriginalImage() {
        return getRandomImage(this.jigsawOriginalImages, CaptchaResource.JIGSAW_ORIGINAL);
    }

    public BufferedImage getRandomTemplateImage() {
        return getRandomImage(this.jigsawOriginalImages, CaptchaResource.JIGSAW_ORIGINAL);
    }

    public BufferedImage getRandomWordClickImage() {
        return getRandomImage(this.wordClickImages, CaptchaResource.WORD_CLICK);
    }
}
