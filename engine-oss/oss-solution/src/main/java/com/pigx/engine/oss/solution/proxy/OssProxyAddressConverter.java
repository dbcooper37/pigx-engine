package com.pigx.engine.oss.solution.proxy;

import com.pigx.engine.oss.solution.constants.OssSolutionConstants;
import com.pigx.engine.oss.solution.properties.OssProxyProperties;
import org.apache.commons.lang3.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;


public class OssProxyAddressConverter implements Converter<String, String> {

    private static final Logger log = LoggerFactory.getLogger(OssProxyAddressConverter.class);

    private final OssProxyProperties ossProxyProperties;

    public OssProxyAddressConverter(OssProxyProperties ossProxyProperties) {
        this.ossProxyProperties = ossProxyProperties;
    }

    @Override
    public String convert(String source) {
        if (ossProxyProperties.getEnabled()) {
            String endpoint = ossProxyProperties.getSource() + OssSolutionConstants.PRESIGNED_OBJECT_URL_PROXY;
            String target = Strings.CS.replace(source, ossProxyProperties.getDestination(), endpoint);
            log.debug("[Herodotus] |- Convert presignedObjectUrl [{}] to [{}].", endpoint, target);
            return target;
        }

        return source;
    }
}
