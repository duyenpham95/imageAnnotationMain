package com.duyen.myapp.service;

import com.duyen.myapp.service.dto.ValueStreamDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing ValueStream.
 */
public interface ValueStreamService {

    /**
     * Save a valueStream.
     *
     * @param valueStreamDTO the entity to save
     * @return the persisted entity
     */
    ValueStreamDTO save(ValueStreamDTO valueStreamDTO);

    /**
     * Get all the valueStreams.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ValueStreamDTO> findAll(Pageable pageable);

    /**
     * Get the "id" valueStream.
     *
     * @param id the id of the entity
     * @return the entity
     */
    ValueStreamDTO findOne(Long id);

    /**
     * Delete the "id" valueStream.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
