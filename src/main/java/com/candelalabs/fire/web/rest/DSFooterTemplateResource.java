package com.candelalabs.fire.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.candelalabs.fire.domain.DSFooterTemplate;
import com.candelalabs.fire.service.DSFooterTemplateService;
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
 * REST controller for managing DSFooterTemplate.
 */
@RestController
@RequestMapping("/api")
public class DSFooterTemplateResource {

    private final Logger log = LoggerFactory.getLogger(DSFooterTemplateResource.class);

    private static final String ENTITY_NAME = "dSFooterTemplate";

    private final DSFooterTemplateService dSFooterTemplateService;

    public DSFooterTemplateResource(DSFooterTemplateService dSFooterTemplateService) {
        this.dSFooterTemplateService = dSFooterTemplateService;
    }

    /**
     * POST  /ds-footer-templates : Create a new dSFooterTemplate.
     *
     * @param dSFooterTemplate the dSFooterTemplate to create
     * @return the ResponseEntity with status 201 (Created) and with body the new dSFooterTemplate, or with status 400 (Bad Request) if the dSFooterTemplate has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ds-footer-templates")
    @Timed
    public ResponseEntity<DSFooterTemplate> createDSFooterTemplate(@Valid @RequestBody DSFooterTemplate dSFooterTemplate) throws URISyntaxException {
        log.debug("REST request to save DSFooterTemplate : {}", dSFooterTemplate);
        if (dSFooterTemplate.getId() != null) {
            throw new BadRequestAlertException("A new dSFooterTemplate cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DSFooterTemplate result = dSFooterTemplateService.save(dSFooterTemplate);
        return ResponseEntity.created(new URI("/api/ds-footer-templates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ds-footer-templates : Updates an existing dSFooterTemplate.
     *
     * @param dSFooterTemplate the dSFooterTemplate to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated dSFooterTemplate,
     * or with status 400 (Bad Request) if the dSFooterTemplate is not valid,
     * or with status 500 (Internal Server Error) if the dSFooterTemplate couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ds-footer-templates")
    @Timed
    public ResponseEntity<DSFooterTemplate> updateDSFooterTemplate(@Valid @RequestBody DSFooterTemplate dSFooterTemplate) throws URISyntaxException {
        log.debug("REST request to update DSFooterTemplate : {}", dSFooterTemplate);
        if (dSFooterTemplate.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DSFooterTemplate result = dSFooterTemplateService.save(dSFooterTemplate);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, dSFooterTemplate.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ds-footer-templates : get all the dSFooterTemplates.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of dSFooterTemplates in body
     */
    @GetMapping("/ds-footer-templates")
    @Timed
    public ResponseEntity<List<DSFooterTemplate>> getAllDSFooterTemplates(Pageable pageable) {
        log.debug("REST request to get a page of DSFooterTemplates");
        Page<DSFooterTemplate> page = dSFooterTemplateService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/ds-footer-templates");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /ds-footer-templates/:id : get the "id" dSFooterTemplate.
     *
     * @param id the id of the dSFooterTemplate to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the dSFooterTemplate, or with status 404 (Not Found)
     */
    @GetMapping("/ds-footer-templates/{id}")
    @Timed
    public ResponseEntity<DSFooterTemplate> getDSFooterTemplate(@PathVariable Long id) {
        log.debug("REST request to get DSFooterTemplate : {}", id);
        Optional<DSFooterTemplate> dSFooterTemplate = dSFooterTemplateService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dSFooterTemplate);
    }

    /**
     * DELETE  /ds-footer-templates/:id : delete the "id" dSFooterTemplate.
     *
     * @param id the id of the dSFooterTemplate to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ds-footer-templates/{id}")
    @Timed
    public ResponseEntity<Void> deleteDSFooterTemplate(@PathVariable Long id) {
        log.debug("REST request to delete DSFooterTemplate : {}", id);
        dSFooterTemplateService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
