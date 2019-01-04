package com.candelalabs.fire.service;

import com.candelalabs.fire.domain.DSFooterTemplate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing DSFooterTemplate.
 */
public interface DSFooterTemplateService {

    /**
     * Save a dSFooterTemplate.
     *
     * @param dSFooterTemplate the entity to save
     * @return the persisted entity
     */
    DSFooterTemplate save(DSFooterTemplate dSFooterTemplate);

    /**
     * Get all the dSFooterTemplates.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<DSFooterTemplate> findAll(Pageable pageable);


    /**
     * Get the "id" dSFooterTemplate.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<DSFooterTemplate> findOne(Long id);

    /**
     * Delete the "id" dSFooterTemplate.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
