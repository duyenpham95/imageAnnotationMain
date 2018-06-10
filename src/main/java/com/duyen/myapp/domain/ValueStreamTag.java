package com.duyen.myapp.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A ValueStreamTag.
 */
@Entity
@Table(name = "value_stream_tag")
public class ValueStreamTag implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tag_name")
    private String tagName;

    @Column(name = "x")
    private Integer x;

    @Column(name = "y")
    private Integer y;

    @Column(name = "height")
    private Integer height;

    @Column(name = "width")
    private Integer width;

    @ManyToOne
    private ValueStream valueStream;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTagName() {
        return tagName;
    }

    public ValueStreamTag tagName(String tagName) {
        this.tagName = tagName;
        return this;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public Integer getX() {
        return x;
    }

    public ValueStreamTag x(Integer x) {
        this.x = x;
        return this;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public ValueStreamTag y(Integer y) {
        this.y = y;
        return this;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public Integer getHeight() {
        return height;
    }

    public ValueStreamTag height(Integer height) {
        this.height = height;
        return this;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWidth() {
        return width;
    }

    public ValueStreamTag width(Integer width) {
        this.width = width;
        return this;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public ValueStream getValueStream() {
        return valueStream;
    }

    public ValueStreamTag valueStream(ValueStream valueStream) {
        this.valueStream = valueStream;
        return this;
    }

    public void setValueStream(ValueStream valueStream) {
        this.valueStream = valueStream;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ValueStreamTag valueStreamTag = (ValueStreamTag) o;
        if (valueStreamTag.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), valueStreamTag.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ValueStreamTag{" +
            "id=" + getId() +
            ", tagName='" + getTagName() + "'" +
            ", x=" + getX() +
            ", y=" + getY() +
            ", height=" + getHeight() +
            ", width=" + getWidth() +
            "}";
    }
}
