package com.duyen.myapp.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A ValueStreamElement.
 */
@Entity
@Table(name = "value_stream_element")
public class ValueStreamElement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "element_name")
    private String elementName;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getElementName() {
        return elementName;
    }

    public ValueStreamElement elementName(String elementName) {
        this.elementName = elementName;
        return this;
    }

    public void setElementName(String elementName) {
        this.elementName = elementName;
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
        ValueStreamElement valueStreamElement = (ValueStreamElement) o;
        if (valueStreamElement.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), valueStreamElement.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ValueStreamElement{" +
            "id=" + getId() +
            ", elementName='" + getElementName() + "'" +
            "}";
    }
}
