package com.pigx.engine.web.service.properties;

import com.google.common.base.MoreObjects;
import com.pigx.engine.core.definition.constant.SystemConstants;
import com.pigx.engine.web.core.constant.WebConstants;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;


@ConfigurationProperties(prefix = WebConstants.PROPERTY_PREFIX_SERVICE)
public class ServiceProperties {

    /**
     * 服务接口扫描配置
     */
    private Scan scan = new Scan();

    public Scan getScan() {
        return scan;
    }

    public void setScan(Scan scan) {
        this.scan = scan;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("scan", scan)
                .toString();
    }

    public static class Scan {

        /**
         * 是否开启接口扫描
         */
        private Boolean enabled = true;
        /**
         * 指定扫描的命名空间。未指定的命名空间中，即使包含RequestMapping，也不会被添加进来。
         */
        private List<String> scanGroupIds;
        /**
         * Spring 中会包含 Controller和 RestController，
         * 如果该配置设置为True，那么就只扫描RestController
         * 如果该配置设置为False，那么Controller和 RestController斗扫描。
         */
        private Boolean justScanRestController = false;

        public Boolean getEnabled() {
            return enabled;
        }

        public void setEnabled(Boolean enabled) {
            this.enabled = enabled;
        }

        public List<String> getScanGroupIds() {
            List<String> defaultGroupIds = Stream.of(SystemConstants.PACKAGE_NAME, "org.dromara").toList();

            if (CollectionUtils.isEmpty(this.scanGroupIds)) {
                this.scanGroupIds = new ArrayList<>();
            }

            this.scanGroupIds.addAll(defaultGroupIds);
            return scanGroupIds;
        }

        public void setScanGroupIds(List<String> scanGroupIds) {
            this.scanGroupIds = scanGroupIds;
        }

        public Boolean getJustScanRestController() {
            return justScanRestController;
        }

        public void setJustScanRestController(Boolean justScanRestController) {
            this.justScanRestController = justScanRestController;
        }

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this)
                    .add("enabled", enabled)
                    .add("justScanRestController", justScanRestController)
                    .toString();
        }
    }
}
