package com.duyen.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.duyen.myapp.service.ValueStreamService;
import com.duyen.myapp.web.rest.errors.BadRequestAlertException;
import com.duyen.myapp.web.rest.util.HeaderUtil;
import com.duyen.myapp.web.rest.util.PaginationUtil;
import com.duyen.myapp.service.dto.ValueStreamDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ValueStream.
 */
@RestController
@RequestMapping("/api")
public class ValueStreamResource {

    private final Logger log = LoggerFactory.getLogger(ValueStreamResource.class);

    private static final String ENTITY_NAME = "valueStream";

    private final ValueStreamService valueStreamService;

    public ValueStreamResource(ValueStreamService valueStreamService) {
        this.valueStreamService = valueStreamService;
    }

    /**
     * POST  /value-streams : Create a new valueStream.
     *
     * @param valueStreamDTO the valueStreamDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new valueStreamDTO, or with status 400 (Bad Request) if the valueStream has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/value-streams")
    @Timed
    public ResponseEntity<ValueStreamDTO> createValueStream(@Valid @RequestBody ValueStreamDTO valueStreamDTO) throws URISyntaxException {
        log.debug("REST request to save ValueStream : {}", valueStreamDTO);
        if (valueStreamDTO.getId() != null) {
            throw new BadRequestAlertException("A new valueStream cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ValueStreamDTO result = valueStreamService.save(valueStreamDTO);
        return ResponseEntity.created(new URI("/api/value-streams/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /value-streams : Updates an existing valueStream.
     *
     * @param valueStreamDTO the valueStreamDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated valueStreamDTO,
     * or with status 400 (Bad Request) if the valueStreamDTO is not valid,
     * or with status 500 (Internal Server Error) if the valueStreamDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/value-streams")
    @Timed
    public ResponseEntity<ValueStreamDTO> updateValueStream(@Valid @RequestBody ValueStreamDTO valueStreamDTO) throws URISyntaxException {
        log.debug("REST request to update ValueStream : {}", valueStreamDTO);
        if (valueStreamDTO.getId() == null) {
            return createValueStream(valueStreamDTO);
        }
        ValueStreamDTO result = valueStreamService.save(valueStreamDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, valueStreamDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /value-streams : get all the valueStreams.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of valueStreams in body
     */
    @GetMapping("/value-streams")
    @Timed
    public ResponseEntity<List<ValueStreamDTO>> getAllValueStreams(Pageable pageable) {
        log.debug("REST request to get a page of ValueStreams");
        Page<ValueStreamDTO> page = valueStreamService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/value-streams");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /value-streams/:id : get the "id" valueStream.
     *
     * @param id the id of the valueStreamDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the valueStreamDTO, or with status 404 (Not Found)
     */
    @GetMapping("/value-streams/{id}")
    @Timed
    public ResponseEntity<ValueStreamDTO> getValueStream(@PathVariable Long id) {
        log.debug("REST request to get ValueStream : {}", id);
        ValueStreamDTO valueStreamDTO = valueStreamService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(valueStreamDTO));
    }

    /**
     * DELETE  /value-streams/:id : delete the "id" valueStream.
     *
     * @param id the id of the valueStreamDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/value-streams/{id}")
    @Timed
    public ResponseEntity<Void> deleteValueStream(@PathVariable Long id) {
        log.debug("REST request to delete ValueStream : {}", id);
        valueStreamService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
