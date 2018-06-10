package com.duyen.myapp.service.impl;

import com.duyen.myapp.service.ValueStreamElementService;
import com.duyen.myapp.domain.ValueStreamElement;
import com.duyen.myapp.repository.ValueStreamElementRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing ValueStreamElement.
 */
@Service
@Transactional
public class ValueStreamElementServiceImpl implements ValueStreamElementService {

    private final Logger log = LoggerFactory.getLogger(ValueStreamElementServiceImpl.class);

    private final ValueStreamElementRepository valueStreamElementRepository;

    public ValueStreamElementServiceImpl(ValueStreamElementRepository valueStreamElementRepository) {
        this.valueStreamElementRepository = valueStreamElementRepository;
    }

    /**
     * Save a valueStreamElement.
     *
     * @param valueStreamElement the entity to save
     * @return the persisted entity
     */
    @Override
    public ValueStreamElement save(ValueStreamElement valueStreamElement) {
        log.debug("Request to save ValueStreamElement : {}", valueStreamElement);
        return valueStreamElementRepository.save(valueStreamElement);
    }

    /**
     * Get all the valueStreamElements.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ValueStreamElement> findAll() {
        log.debug("Request to get all ValueStreamElements");
        return valueStreamElementRepository.findAll();
    }

    /**
     * Get one valueStreamElement by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ValueStreamElement findOne(Long id) {
        log.debug("Request to get ValueStreamElement : {}", id);
        return valueStreamElementRepository.findOne(id);
    }

    /**
     * Delete the valueStreamElement by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ValueStreamElement : {}", id);
        valueStreamElementRepository.delete(id);
    }
}
