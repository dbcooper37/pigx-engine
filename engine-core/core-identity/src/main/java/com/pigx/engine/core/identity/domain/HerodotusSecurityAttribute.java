package com.pigx.engine.core.identity.domain;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class HerodotusSecurityAttribute implements Serializable {

    private String attribute;

    public HerodotusSecurityAttribute() {
    }

    public HerodotusSecurityAttribute(String config) {
        Assert.hasText(config, "You must provide a configuration attribute");
        this.attribute = config;
    }

    public static HerodotusSecurityAttribute create(String attribute) {
        Assert.notNull(attribute, "You must supply an array of attribute names");
        return new HerodotusSecurityAttribute(attribute.trim());
    }

    public static List<HerodotusSecurityAttribute> createListFromCommaDelimitedString(String access) {
        return createList(StringUtils.commaDelimitedListToStringArray(access));
    }

    public static List<HerodotusSecurityAttribute> createList(String... attributeNames) {
        Assert.notNull(attributeNames, "You must supply an array of attribute names");
        List<HerodotusSecurityAttribute> attributes = new ArrayList<>(attributeNames.length);
        for (String attribute : attributeNames) {
            attributes.add(new HerodotusSecurityAttribute(attribute.trim()));
        }
        return attributes;
    }

    public String getAttribute() {
        return this.attribute;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HerodotusSecurityAttribute that = (HerodotusSecurityAttribute) o;
        return Objects.equal(attribute, that.attribute);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(attribute);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("attrib", attribute)
                .toString();
    }
}
