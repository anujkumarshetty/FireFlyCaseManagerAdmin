package com.candelalabs.fire.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.candelalabs.fire.domain.Dsheadertemplate;
import com.candelalabs.fire.service.DsheadertemplateService;
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
 * REST controller for managing Dsheadertemplate.
 */
@RestController
@RequestMapping("/api")
public class DsheadertemplateResource {

    private final Logger log = LoggerFactory.getLogger(DsheadertemplateResource.class);

    private static final String ENTITY_NAME = "dsheadertemplate";

    private final DsheadertemplateService dsheadertemplateService;

    public DsheadertemplateResource(DsheadertemplateService dsheadertemplateService) {
        this.dsheadertemplateService = dsheadertemplateService;
    }

    /**
     * POST  /dsheadertemplates : Create a new dsheadertemplate.
     *
     * @param dsheadertemplate the dsheadertemplate to create
     * @return the ResponseEntity with status 201 (Created) and with body the new dsheadertemplate, or with status 400 (Bad Request) if the dsheadertemplate has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/dsheadertemplates")
    @Timed
    public ResponseEntity<Dsheadertemplate> createDsheadertemplate(@Valid @RequestBody Dsheadertemplate dsheadertemplate) throws URISyntaxException {
        log.debug("REST request to save Dsheadertemplate : {}", dsheadertemplate);
        if (dsheadertemplate.getId() != null) {
            throw new BadRequestAlertException("A new dsheadertemplate cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Dsheadertemplate result = dsheadertemplateService.save(dsheadertemplate);
        return ResponseEntity.created(new URI("/api/dsheadertemplates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /dsheadertemplates : Updates an existing dsheadertemplate.
     *
     * @param dsheadertemplate the dsheadertemplate to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated dsheadertemplate,
     * or with status 400 (Bad Request) if the dsheadertemplate is not valid,
     * or with status 500 (Internal Server Error) if the dsheadertemplate couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/dsheadertemplates")
    @Timed
    public ResponseEntity<Dsheadertemplate> updateDsheadertemplate(@Valid @RequestBody Dsheadertemplate dsheadertemplate) throws URISyntaxException {
        log.debug("REST request to update Dsheadertemplate : {}", dsheadertemplate);
        if (dsheadertemplate.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Dsheadertemplate result = dsheadertemplateService.save(dsheadertemplate);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, dsheadertemplate.getId().toString()))
            .body(result);
    }

    /**
     * GET  /dsheadertemplates : get all the dsheadertemplates.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of dsheadertemplates in body
     */
    @GetMapping("/dsheadertemplates")
    @Timed
    public ResponseEntity<List<Dsheadertemplate>> getAllDsheadertemplates(Pageable pageable) {
        log.debug("REST request to get a page of Dsheadertemplates");
        Page<Dsheadertemplate> page = dsheadertemplateService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/dsheadertemplates");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /dsheadertemplates/:id : get the "id" dsheadertemplate.
     *
     * @param id the id of the dsheadertemplate to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the dsheadertemplate, or with status 404 (Not Found)
     */
    @GetMapping("/dsheadertemplates/{id}")
    @Timed
    public ResponseEntity<Dsheadertemplate> getDsheadertemplate(@PathVariable Long id) {
        log.debug("REST request to get Dsheadertemplate : {}", id);
        Optional<Dsheadertemplate> dsheadertemplate = dsheadertemplateService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dsheadertemplate);
    }

    /**
     * DELETE  /dsheadertemplates/:id : delete the "id" dsheadertemplate.
     *
     * @param id the id of the dsheadertemplate to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/dsheadertemplates/{id}")
    @Timed
    public ResponseEntity<Void> deleteDsheadertemplate(@PathVariable Long id) {
        log.debug("REST request to delete Dsheadertemplate : {}", id);
        dsheadertemplateService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
