package com.duyen.inheritancemapping.entities.tableperclass;

import javax.persistence.Entity;

@Entity
public class Circle extends Shape {

    public double radius;

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }
}
