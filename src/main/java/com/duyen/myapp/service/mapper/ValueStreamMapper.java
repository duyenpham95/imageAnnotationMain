package com.duyen.myapp.service.mapper;

import com.duyen.myapp.domain.*;
import com.duyen.myapp.service.dto.ValueStreamDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ValueStream and its DTO ValueStreamDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ValueStreamMapper extends EntityMapper<ValueStreamDTO, ValueStream> {


    @Mapping(target = "valueStreamTags", ignore = true)
    ValueStream toEntity(ValueStreamDTO valueStreamDTO);

    default ValueStream fromId(Long id) {
        if (id == null) {
            return null;
        }
        ValueStream valueStream = new ValueStream();
        valueStream.setId(id);
        return valueStream;
    }
}
