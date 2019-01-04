package com.candelalabs.fire.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.candelalabs.fire.domain.DSCorrespondence;
import com.candelalabs.fire.service.DSCorrespondenceService;
import com.candelalabs.fire.web.rest.errors.BadRequestAlertException;
import com.candelalabs.fire.web.rest.util.HeaderUtil;
import com.candelalabs.fire.web.rest.util.PaginationUtil;
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
 * REST controller for managing DSCorrespondence.
 */
@RestController
@RequestMapping("/api")
public class DSCorrespondenceResource {

    private final Logger log = LoggerFactory.getLogger(DSCorrespondenceResource.class);

    private static final String ENTITY_NAME = "dSCorrespondence";

    private final DSCorrespondenceService dSCorrespondenceService;

    public DSCorrespondenceResource(DSCorrespondenceService dSCorrespondenceService) {
        this.dSCorrespondenceService = dSCorrespondenceService;
    }

    /**
     * POST  /ds-correspondences : Create a new dSCorrespondence.
     *
     * @param dSCorrespondence the dSCorrespondence to create
     * @return the ResponseEntity with status 201 (Created) and with body the new dSCorrespondence, or with status 400 (Bad Request) if the dSCorrespondence has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ds-correspondences")
    @Timed
    public ResponseEntity<DSCorrespondence> createDSCorrespondence(@Valid @RequestBody DSCorrespondence dSCorrespondence) throws URISyntaxException {
        log.debug("REST request to save DSCorrespondence : {}", dSCorrespondence);
        if (dSCorrespondence.getId() != null) {
            throw new BadRequestAlertException("A new dSCorrespondence cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DSCorrespondence result = dSCorrespondenceService.save(dSCorrespondence);
        return ResponseEntity.created(new URI("/api/ds-correspondences/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ds-correspondences : Updates an existing dSCorrespondence.
     *
     * @param dSCorrespondence the dSCorrespondence to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated dSCorrespondence,
     * or with status 400 (Bad Request) if the dSCorrespondence is not valid,
     * or with status 500 (Internal Server Error) if the dSCorrespondence couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ds-correspondences")
    @Timed
    public ResponseEntity<DSCorrespondence> updateDSCorrespondence(@Valid @RequestBody DSCorrespondence dSCorrespondence) throws URISyntaxException {
        log.debug("REST request to update DSCorrespondence : {}", dSCorrespondence);
        if (dSCorrespondence.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DSCorrespondence result = dSCorrespondenceService.save(dSCorrespondence);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, dSCorrespondence.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ds-correspondences : get all the dSCorrespondences.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of dSCorrespondences in body
     */
    @GetMapping("/ds-correspondences")
    @Timed
    public ResponseEntity<List<DSCorrespondence>> getAllDSCorrespondences(Pageable pageable) {
        log.debug("REST request to get a page of DSCorrespondences");
        Page<DSCorrespondence> page = dSCorrespondenceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/ds-correspondences");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /ds-correspondences/:id : get the "id" dSCorrespondence.
     *
     * @param id the id of the dSCorrespondence to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the dSCorrespondence, or with status 404 (Not Found)
     */
    @GetMapping("/ds-correspondences/{id}")
    @Timed
    public ResponseEntity<DSCorrespondence> getDSCorrespondence(@PathVariable Long id) {
        log.debug("REST request to get DSCorrespondence : {}", id);
        Optional<DSCorrespondence> dSCorrespondence = dSCorrespondenceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dSCorrespondence);
    }

    /**
     * DELETE  /ds-correspondences/:id : delete the "id" dSCorrespondence.
     *
     * @param id the id of the dSCorrespondence to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ds-correspondences/{id}")
    @Timed
    public ResponseEntity<Void> deleteDSCorrespondence(@PathVariable Long id) {
        log.debug("REST request to delete DSCorrespondence : {}", id);
        dSCorrespondenceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
