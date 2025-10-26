package com.pigx.engine.core.foundation.utils;

import cn.hutool.v7.core.codec.binary.Base64;
import cn.hutool.v7.core.data.id.IdUtil;
import cn.hutool.v7.core.io.IORuntimeException;
import cn.hutool.v7.core.io.IoUtil;
import cn.hutool.v7.swing.FontUtil;
import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ResourceUtils;

/* loaded from: core-foundation-3.5.7.0.jar:cn/herodotus/engine/core/foundation/utils/ResourceResolverUtils.class */
public class ResourceResolverUtils {
    private static final Logger log = LoggerFactory.getLogger(ResourceResolverUtils.class);
    private static volatile ResourceResolverUtils INSTANCE;
    private final PathMatchingResourcePatternResolver pathMatchingResourcePatternResolver = new PathMatchingResourcePatternResolver();

    private ResourceResolverUtils() {
    }

    private static ResourceResolverUtils getInstance() {
        if (ObjectUtils.isEmpty(INSTANCE)) {
            synchronized (ResourceResolverUtils.class) {
                if (ObjectUtils.isEmpty(INSTANCE)) {
                    INSTANCE = new ResourceResolverUtils();
                }
            }
        }
        return INSTANCE;
    }

    private PathMatchingResourcePatternResolver getPathMatchingResourcePatternResolver() {
        return this.pathMatchingResourcePatternResolver;
    }

    private static PathMatchingResourcePatternResolver getResolver() {
        return getInstance().getPathMatchingResourcePatternResolver();
    }

    public static Resource getResource(String location) {
        Resource resource = getResolver().getResource(location);
        log.debug("[Herodotus] |- Resource at location [{}] is [{}]!", location, Boolean.valueOf(resource.exists()));
        return resource;
    }

    public static Resource[] getResources(String locationPattern) throws IOException {
        return getResolver().getResources(locationPattern);
    }

    public static File getFile(String location) {
        Resource resource = getResource(location);
        try {
            if (resource.isFile()) {
                return resource.getFile();
            }
            return ResourceUtils.getFile(location);
        } catch (FileNotFoundException e) {
            log.warn("[Herodotus] |- File not found in location [{}]", location, e);
            return new File(location);
        } catch (IOException e2) {
            log.error("[Herodotus] |- Cannot found resource use location [{}]", location);
            return new File(location);
        }
    }

    public static InputStream getInputStream(String location) {
        File file = getFile(location);
        return IoUtil.toStream(file);
    }

    public static String getFilename(String location) {
        return getResource(location).getFilename();
    }

    public static URL getURL(String location) throws IOException {
        return getResource(location).getURL();
    }

    public static long contentLength(String location) throws IOException {
        return getResource(location).contentLength();
    }

    public static long lastModified(String location) throws IOException {
        return getResource(location).lastModified();
    }

    public static boolean isUrl(String location) {
        return ResourceUtils.isUrl(location);
    }

    public static boolean isJarUrl(URL url) {
        return ResourceUtils.isJarURL(url);
    }

    public static boolean isFileUrl(URL url) {
        return ResourceUtils.isFileURL(url);
    }

    public static boolean isJarFileUrl(URL url) {
        return ResourceUtils.isJarFileURL(url);
    }

    public static boolean isClasspathUrl(String location) {
        return Strings.CS.startsWith(location, "classpath:");
    }

    public static boolean isClasspathAllUrl(String location) {
        return Strings.CS.startsWith(location, "classpath*:");
    }

    public static byte[] toBytes(Resource resource) {
        try {
            InputStream inputStream = resource.getInputStream();
            return FileCopyUtils.copyToByteArray(inputStream);
        } catch (IOException e) {
            log.error("[Herodotus] |- Converter resource to byte[] error!", e);
            return null;
        }
    }

    public static String toBase64(Resource resource) {
        byte[] bytes = toBytes(resource);
        return Base64.encode(bytes);
    }

    public static Map<String, String> getBase64Images(String locationPattern) {
        if (isClasspathAllUrl(locationPattern)) {
            try {
                Resource[] resources = getResources(locationPattern);
                if (ArrayUtils.isNotEmpty(resources)) {
                    return (Map) Arrays.stream(resources).map(ResourceResolverUtils::toBase64).filter((v0) -> {
                        return StringUtils.isNotBlank(v0);
                    }).collect(Collectors.toMap(item -> {
                        return IdUtil.fastSimpleUUID();
                    }, item2 -> {
                        return item2;
                    }));
                }
            } catch (IOException e) {
                log.error("[Herodotus] |- Analysis the  location [{}] catch io error!", locationPattern, e);
            }
        }
        return new ConcurrentHashMap(8);
    }

    public static Font getFont(Resource resource) {
        try {
            if (ObjectUtils.isNotEmpty(resource)) {
                if (resource.isFile()) {
                    return FontUtil.createFont(resource.getFile());
                }
                return FontUtil.createFont(resource.getInputStream());
            }
            return null;
        } catch (IOException e) {
            log.error("[Herodotus] |- Resource object in resources folder catch io error!", e);
            return null;
        } catch (IORuntimeException e2) {
            log.warn("[Herodotus] |- Can not read font in the resources folder, maybe in docker.");
            return null;
        }
    }

    public static Map<String, Font> getFonts(String locationPattern) {
        if (isClasspathAllUrl(locationPattern)) {
            try {
                Resource[] resources = getResources(locationPattern);
                if (ArrayUtils.isNotEmpty(resources)) {
                    return (Map) Arrays.stream(resources).map(ResourceResolverUtils::getFont).filter((v0) -> {
                        return ObjectUtils.isNotEmpty(v0);
                    }).collect(Collectors.toMap((v0) -> {
                        return v0.getFontName();
                    }, font -> {
                        return font;
                    }));
                }
            } catch (IOException e) {
                log.error("[Herodotus] |- Analysis the  location [{}] catch io error!", locationPattern, e);
            }
        }
        return new ConcurrentHashMap(8);
    }
}
