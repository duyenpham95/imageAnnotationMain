package com.duyen.myapp.service.impl;

import com.duyen.myapp.service.ValueStreamTagService;
import com.duyen.myapp.domain.ValueStreamTag;
import com.duyen.myapp.repository.ValueStreamTagRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing ValueStreamTag.
 */
@Service
@Transactional
public class ValueStreamTagServiceImpl implements ValueStreamTagService {

    private final Logger log = LoggerFactory.getLogger(ValueStreamTagServiceImpl.class);

    private final ValueStreamTagRepository valueStreamTagRepository;

    public ValueStreamTagServiceImpl(ValueStreamTagRepository valueStreamTagRepository) {
        this.valueStreamTagRepository = valueStreamTagRepository;
    }

    /**
     * Save a valueStreamTag.
     *
     * @param valueStreamTag the entity to save
     * @return the persisted entity
     */
    @Override
    public ValueStreamTag save(ValueStreamTag valueStreamTag) {
        log.debug("Request to save ValueStreamTag : {}", valueStreamTag);
        return valueStreamTagRepository.save(valueStreamTag);
    }

    /**
     * Get all the valueStreamTags.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ValueStreamTag> findAll() {
        log.debug("Request to get all ValueStreamTags");
        return valueStreamTagRepository.findAll();
    }

    /**
     * Get one valueStreamTag by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ValueStreamTag findOne(Long id) {
        log.debug("Request to get ValueStreamTag : {}", id);
        return valueStreamTagRepository.findOne(id);
    }

    /**
     * Delete the valueStreamTag by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ValueStreamTag : {}", id);
        valueStreamTagRepository.delete(id);
    }
}
