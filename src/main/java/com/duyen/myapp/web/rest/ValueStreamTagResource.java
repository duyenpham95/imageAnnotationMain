package com.duyen.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.duyen.myapp.domain.ValueStreamTag;
import com.duyen.myapp.service.ValueStreamTagService;
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
 * REST controller for managing ValueStreamTag.
 */
@RestController
@RequestMapping("/api")
public class ValueStreamTagResource {

    private final Logger log = LoggerFactory.getLogger(ValueStreamTagResource.class);

    private static final String ENTITY_NAME = "valueStreamTag";

    private final ValueStreamTagService valueStreamTagService;

    public ValueStreamTagResource(ValueStreamTagService valueStreamTagService) {
        this.valueStreamTagService = valueStreamTagService;
    }

    /**
     * POST  /value-stream-tags : Create a new valueStreamTag.
     *
     * @param valueStreamTag the valueStreamTag to create
     * @return the ResponseEntity with status 201 (Created) and with body the new valueStreamTag, or with status 400 (Bad Request) if the valueStreamTag has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/value-stream-tags")
    @Timed
    public ResponseEntity<ValueStreamTag> createValueStreamTag(@RequestBody ValueStreamTag valueStreamTag) throws URISyntaxException {
        log.debug("REST request to save ValueStreamTag : {}", valueStreamTag);
        if (valueStreamTag.getId() != null) {
            throw new BadRequestAlertException("A new valueStreamTag cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ValueStreamTag result = valueStreamTagService.save(valueStreamTag);
        return ResponseEntity.created(new URI("/api/value-stream-tags/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /value-stream-tags : Updates an existing valueStreamTag.
     *
     * @param valueStreamTag the valueStreamTag to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated valueStreamTag,
     * or with status 400 (Bad Request) if the valueStreamTag is not valid,
     * or with status 500 (Internal Server Error) if the valueStreamTag couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/value-stream-tags")
    @Timed
    public ResponseEntity<ValueStreamTag> updateValueStreamTag(@RequestBody ValueStreamTag valueStreamTag) throws URISyntaxException {
        log.debug("REST request to update ValueStreamTag : {}", valueStreamTag);
        if (valueStreamTag.getId() == null) {
            return createValueStreamTag(valueStreamTag);
        }
        ValueStreamTag result = valueStreamTagService.save(valueStreamTag);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, valueStreamTag.getId().toString()))
            .body(result);
    }

    /**
     * GET  /value-stream-tags : get all the valueStreamTags.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of valueStreamTags in body
     */
    @GetMapping("/value-stream-tags")
    @Timed
    public List<ValueStreamTag> getAllValueStreamTags() {
        log.debug("REST request to get all ValueStreamTags");
        return valueStreamTagService.findAll();
        }

    /**
     * GET  /value-stream-tags/:id : get the "id" valueStreamTag.
     *
     * @param id the id of the valueStreamTag to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the valueStreamTag, or with status 404 (Not Found)
     */
    @GetMapping("/value-stream-tags/{id}")
    @Timed
    public ResponseEntity<ValueStreamTag> getValueStreamTag(@PathVariable Long id) {
        log.debug("REST request to get ValueStreamTag : {}", id);
        ValueStreamTag valueStreamTag = valueStreamTagService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(valueStreamTag));
    }

    /**
     * DELETE  /value-stream-tags/:id : delete the "id" valueStreamTag.
     *
     * @param id the id of the valueStreamTag to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/value-stream-tags/{id}")
    @Timed
    public ResponseEntity<Void> deleteValueStreamTag(@PathVariable Long id) {
        log.debug("REST request to delete ValueStreamTag : {}", id);
        valueStreamTagService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
