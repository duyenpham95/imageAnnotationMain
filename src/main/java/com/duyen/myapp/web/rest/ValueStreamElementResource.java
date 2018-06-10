package com.duyen.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.duyen.myapp.domain.ValueStreamElement;
import com.duyen.myapp.service.ValueStreamElementService;
import com.duyen.myapp.web.rest.errors.BadRequestAlertException;
import com.duyen.myapp.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ValueStreamElement.
 */
@RestController
@RequestMapping("/api")
public class ValueStreamElementResource {

    private final Logger log = LoggerFactory.getLogger(ValueStreamElementResource.class);

    private static final String ENTITY_NAME = "valueStreamElement";

    private final ValueStreamElementService valueStreamElementService;

    public ValueStreamElementResource(ValueStreamElementService valueStreamElementService) {
        this.valueStreamElementService = valueStreamElementService;
    }

    /**
     * POST  /value-stream-elements : Create a new valueStreamElement.
     *
     * @param valueStreamElement the valueStreamElement to create
     * @return the ResponseEntity with status 201 (Created) and with body the new valueStreamElement, or with status 400 (Bad Request) if the valueStreamElement has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/value-stream-elements")
    @Timed
    public ResponseEntity<ValueStreamElement> createValueStreamElement(@RequestBody ValueStreamElement valueStreamElement) throws URISyntaxException {
        log.debug("REST request to save ValueStreamElement : {}", valueStreamElement);
        if (valueStreamElement.getId() != null) {
            throw new BadRequestAlertException("A new valueStreamElement cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ValueStreamElement result = valueStreamElementService.save(valueStreamElement);
        return ResponseEntity.created(new URI("/api/value-stream-elements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /value-stream-elements : Updates an existing valueStreamElement.
     *
     * @param valueStreamElement the valueStreamElement to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated valueStreamElement,
     * or with status 400 (Bad Request) if the valueStreamElement is not valid,
     * or with status 500 (Internal Server Error) if the valueStreamElement couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/value-stream-elements")
    @Timed
    public ResponseEntity<ValueStreamElement> updateValueStreamElement(@RequestBody ValueStreamElement valueStreamElement) throws URISyntaxException {
        log.debug("REST request to update ValueStreamElement : {}", valueStreamElement);
        if (valueStreamElement.getId() == null) {
            return createValueStreamElement(valueStreamElement);
        }
        ValueStreamElement result = valueStreamElementService.save(valueStreamElement);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, valueStreamElement.getId().toString()))
            .body(result);
    }

    /**
     * GET  /value-stream-elements : get all the valueStreamElements.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of valueStreamElements in body
     */
    @GetMapping("/value-stream-elements")
    @Timed
    public List<ValueStreamElement> getAllValueStreamElements() {
        log.debug("REST request to get all ValueStreamElements");
        return valueStreamElementService.findAll();
        }

    /**
     * GET  /value-stream-elements/:id : get the "id" valueStreamElement.
     *
     * @param id the id of the valueStreamElement to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the valueStreamElement, or with status 404 (Not Found)
     */
    @GetMapping("/value-stream-elements/{id}")
    @Timed
    public ResponseEntity<ValueStreamElement> getValueStreamElement(@PathVariable Long id) {
        log.debug("REST request to get ValueStreamElement : {}", id);
        ValueStreamElement valueStreamElement = valueStreamElementService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(valueStreamElement));
    }

    /**
     * DELETE  /value-stream-elements/:id : delete the "id" valueStreamElement.
     *
     * @param id the id of the valueStreamElement to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/value-stream-elements/{id}")
    @Timed
    public ResponseEntity<Void> deleteValueStreamElement(@PathVariable Long id) {
        log.debug("REST request to delete ValueStreamElement : {}", id);
        valueStreamElementService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
