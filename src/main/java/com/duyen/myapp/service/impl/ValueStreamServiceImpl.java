package com.duyen.myapp.service.impl;

import com.duyen.myapp.service.ValueStreamService;
import com.duyen.myapp.domain.ValueStream;
import com.duyen.myapp.repository.ValueStreamRepository;
import com.duyen.myapp.service.dto.ValueStreamDTO;
import com.duyen.myapp.service.mapper.ValueStreamMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing ValueStream.
 */
@Service
@Transactional
public class ValueStreamServiceImpl implements ValueStreamService {

    private final Logger log = LoggerFactory.getLogger(ValueStreamServiceImpl.class);

    private final ValueStreamRepository valueStreamRepository;

    private final ValueStreamMapper valueStreamMapper;

    public ValueStreamServiceImpl(ValueStreamRepository valueStreamRepository, ValueStreamMapper valueStreamMapper) {
        this.valueStreamRepository = valueStreamRepository;
        this.valueStreamMapper = valueStreamMapper;
    }

    /**
     * Save a valueStream.
     *
     * @param valueStreamDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ValueStreamDTO save(ValueStreamDTO valueStreamDTO) {
        log.debug("Request to save ValueStream : {}", valueStreamDTO);
        ValueStream valueStream = valueStreamMapper.toEntity(valueStreamDTO);
        valueStream = valueStreamRepository.save(valueStream);
        return valueStreamMapper.toDto(valueStream);
    }

    /**
     * Get all the valueStreams.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ValueStreamDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ValueStreams");
        return valueStreamRepository.findAll(pageable)
            .map(valueStreamMapper::toDto);
    }

    /**
     * Get one valueStream by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ValueStreamDTO findOne(Long id) {
        log.debug("Request to get ValueStream : {}", id);
        ValueStream valueStream = valueStreamRepository.findOne(id);
        return valueStreamMapper.toDto(valueStream);
    }

    /**
     * Delete the valueStream by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ValueStream : {}", id);
        valueStreamRepository.delete(id);
    }
}
