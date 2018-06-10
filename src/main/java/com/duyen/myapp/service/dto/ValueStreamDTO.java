package com.duyen.myapp.service.dto;


import javax.validation.constraints.*;

import com.duyen.myapp.domain.ValueStreamTag;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the ValueStream entity.
 */
public class ValueStreamDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @Lob
    private byte[] image;
    private String imageContentType;
    
    public Set<ValueStreamTag> getValueStreamTags() {
		return valueStreamTags;
	}

	public void setValueStreamTags(Set<ValueStreamTag> valueStreamTags) {
		this.valueStreamTags = valueStreamTags;
	}

	private Set<ValueStreamTag> valueStreamTags = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ValueStreamDTO valueStreamDTO = (ValueStreamDTO) o;
        if(valueStreamDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), valueStreamDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ValueStreamDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", image='" + getImage() + "'" +
            "}";
    }
}
