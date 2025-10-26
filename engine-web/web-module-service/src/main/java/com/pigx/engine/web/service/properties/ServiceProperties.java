package com.pigx.engine.web.service.properties;

import com.pigx.engine.core.definition.constant.BaseConstants;
import com.pigx.engine.core.definition.constant.SystemConstants;
import com.google.common.base.MoreObjects;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = BaseConstants.PROPERTY_PREFIX_SERVICE)
/* loaded from: web-module-service-3.5.7.0.jar:cn/herodotus/engine/web/service/properties/ServiceProperties.class */
public class ServiceProperties {
    private Scan scan = new Scan();

    public Scan getScan() {
        return this.scan;
    }

    public void setScan(Scan scan) {
        this.scan = scan;
    }

    /* loaded from: web-module-service-3.5.7.0.jar:cn/herodotus/engine/web/service/properties/ServiceProperties$Scan.class */
    public static class Scan {
        private List<String> scanGroupIds;
        private Boolean enabled = true;
        private Boolean justScanRestController = false;

        public Boolean getEnabled() {
            return this.enabled;
        }

        public void setEnabled(Boolean enabled) {
            this.enabled = enabled;
        }

        public List<String> getScanGroupIds() {
            List<String> defaultGroupIds = Stream.of((Object[]) new String[]{SystemConstants.PACKAGE_NAME, "org.dromara"}).toList();
            if (CollectionUtils.isEmpty(this.scanGroupIds)) {
                this.scanGroupIds = new ArrayList();
            }
            this.scanGroupIds.addAll(defaultGroupIds);
            return this.scanGroupIds;
        }

        public void setScanGroupIds(List<String> scanGroupIds) {
            this.scanGroupIds = scanGroupIds;
        }

        public Boolean getJustScanRestController() {
            return this.justScanRestController;
        }

        public void setJustScanRestController(Boolean justScanRestController) {
            this.justScanRestController = justScanRestController;
        }

        public String toString() {
            return MoreObjects.toStringHelper(this).add(BaseConstants.PROPERTY_NAME_ENABLED, this.enabled).add("justScanRestController", this.justScanRestController).toString();
        }
    }

    public String toString() {
        return MoreObjects.toStringHelper(this).add("scan", this.scan).toString();
    }
}
