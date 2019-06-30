package com.duyen.inheritancemapping.entities.tableperclass;

import javax.persistence.Entity;

@Entity
public class Rectangle extends Shape {

    public double width;

    public double height;

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }
}
