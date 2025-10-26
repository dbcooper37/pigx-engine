package com.pigx.engine.core.definition.domain.captcha;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import java.io.Serializable;

/* loaded from: core-definition-3.5.7.0.jar:cn/herodotus/engine/core/definition/domain/captcha/Coordinate.class */
public class Coordinate implements Serializable {
    private int x;
    private int y;

    public Coordinate() {
    }

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Coordinate that = (Coordinate) o;
        return this.x == that.x && this.y == that.y;
    }

    public int hashCode() {
        return Objects.hashCode(new Object[]{Integer.valueOf(this.x), Integer.valueOf(this.y)});
    }

    public String toString() {
        return MoreObjects.toStringHelper(this).add("x", this.x).add("y", this.y).toString();
    }
}
