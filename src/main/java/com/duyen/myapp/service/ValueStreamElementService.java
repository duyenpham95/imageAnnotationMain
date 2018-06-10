package com.duyen.myapp.service;

import com.duyen.myapp.domain.ValueStreamElement;
import java.util.List;

/**
 * Service Interface for managing ValueStreamElement.
 */
public interface ValueStreamElementService {

    /**
     * Save a valueStreamElement.
     *
     * @param valueStreamElement the entity to save
     * @return the persisted entity
     */
    ValueStreamElement save(ValueStreamElement valueStreamElement);

    /**
     * Get all the valueStreamElements.
     *
     * @return the list of entities
     */
    List<ValueStreamElement> findAll();

    /**
     * Get the "id" valueStreamElement.
     *
     * @param id the id of the entity
     * @return the entity
     */
    ValueStreamElement findOne(Long id);

    /**
     * Delete the "id" valueStreamElement.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
