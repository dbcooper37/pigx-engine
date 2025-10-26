package com.pigx.engine.core.definition.domain.captcha;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/* loaded from: core-definition-3.5.7.0.jar:cn/herodotus/engine/core/definition/domain/captcha/GraphicCaptcha.class */
public class GraphicCaptcha extends Captcha {
    private String graphicImageBase64;

    public String getGraphicImageBase64() {
        return this.graphicImageBase64;
    }

    public void setGraphicImageBase64(String graphicImageBase64) {
        this.graphicImageBase64 = graphicImageBase64;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GraphicCaptcha that = (GraphicCaptcha) o;
        return Objects.equal(this.graphicImageBase64, that.graphicImageBase64);
    }

    public int hashCode() {
        return Objects.hashCode(new Object[]{this.graphicImageBase64});
    }

    public String toString() {
        return MoreObjects.toStringHelper(this).add("graphicImageBase64", this.graphicImageBase64).toString();
    }
}
