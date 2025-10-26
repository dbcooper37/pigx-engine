package com.pigx.engine.core.definition.domain.captcha;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import java.util.List;

/* loaded from: core-definition-3.5.7.0.jar:cn/herodotus/engine/core/definition/domain/captcha/Verification.class */
public class Verification extends Captcha {
    private Coordinate coordinate;
    private List<Coordinate> coordinates;
    private String characters;

    public Coordinate getCoordinate() {
        return this.coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public List<Coordinate> getCoordinates() {
        return this.coordinates;
    }

    public void setCoordinates(List<Coordinate> coordinates) {
        this.coordinates = coordinates;
    }

    public String getCharacters() {
        return this.characters;
    }

    public void setCharacters(String characters) {
        this.characters = characters;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Verification that = (Verification) o;
        return Objects.equal(this.characters, that.characters);
    }

    public int hashCode() {
        return Objects.hashCode(new Object[]{this.characters});
    }

    public String toString() {
        return MoreObjects.toStringHelper(this).add("characters", this.characters).toString();
    }
}
