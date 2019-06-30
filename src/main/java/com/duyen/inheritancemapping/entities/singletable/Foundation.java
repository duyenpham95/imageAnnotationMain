package com.duyen.inheritancemapping.entities.singletable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("foundation")
public class Foundation extends BeautyProduct {
    String shade;

    public String getShade() {
        return shade;
    }

    public void setShade(String shade) {
        this.shade = shade;
    }
}
