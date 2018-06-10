package com.duyen.myapp.service;

import com.duyen.myapp.domain.ValueStreamTag;
import java.util.List;

/**
 * Service Interface for managing ValueStreamTag.
 */
public interface ValueStreamTagService {

    /**
     * Save a valueStreamTag.
     *
     * @param valueStreamTag the entity to save
     * @return the persisted entity
     */
    ValueStreamTag save(ValueStreamTag valueStreamTag);

    /**
     * Get all the valueStreamTags.
     *
     * @return the list of entities
     */
    List<ValueStreamTag> findAll();

    /**
     * Get the "id" valueStreamTag.
     *
     * @param id the id of the entity
     * @return the entity
     */
    ValueStreamTag findOne(Long id);

    /**
     * Delete the "id" valueStreamTag.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
