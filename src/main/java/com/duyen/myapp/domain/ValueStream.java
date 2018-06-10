package com.duyen.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A ValueStream.
 */
@Entity
@Table(name = "value_stream")
public class ValueStream implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Lob
    @Column(name = "image")
    private byte[] image;

    @Column(name = "image_content_type")
    private String imageContentType;

    @OneToMany(cascade=CascadeType.ALL,mappedBy = "valueStream")
    @JsonIgnore
    private Set<ValueStreamTag> valueStreamTags = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {                          
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public ValueStream name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getImage() {
        return image;
    }

    public ValueStream image(byte[] image) {
        this.image = image;
        return this;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public ValueStream imageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
        return this;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public Set<ValueStreamTag> getValueStreamTags() {
        return valueStreamTags;
    }

    public ValueStream valueStreamTags(Set<ValueStreamTag> valueStreamTags) {
        this.valueStreamTags = valueStreamTags;
        return this;
    }

    public ValueStream addValueStreamTag(ValueStreamTag valueStreamTag) {
        this.valueStreamTags.add(valueStreamTag);
        valueStreamTag.setValueStream(this);
        return this;
    }

    public ValueStream removeValueStreamTag(ValueStreamTag valueStreamTag) {
        this.valueStreamTags.remove(valueStreamTag);
        valueStreamTag.setValueStream(null);
        return this;
    }

    public void setValueStreamTags(Set<ValueStreamTag> valueStreamTags) {
        this.valueStreamTags = valueStreamTags;
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
        ValueStream valueStream = (ValueStream) o;
        if (valueStream.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), valueStream.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ValueStream{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", image='" + getImage() + "'" +
            ", imageContentType='" + getImageContentType() + "'" +
            "}";
    }
}
