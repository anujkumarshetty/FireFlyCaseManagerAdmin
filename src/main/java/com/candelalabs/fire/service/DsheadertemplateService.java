package com.candelalabs.fire.service;

import com.candelalabs.fire.domain.Dsheadertemplate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Dsheadertemplate.
 */
public interface DsheadertemplateService {

    /**
     * Save a dsheadertemplate.
     *
     * @param dsheadertemplate the entity to save
     * @return the persisted entity
     */
    Dsheadertemplate save(Dsheadertemplate dsheadertemplate);

    /**
     * Get all the dsheadertemplates.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Dsheadertemplate> findAll(Pageable pageable);


    /**
     * Get the "id" dsheadertemplate.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Dsheadertemplate> findOne(Long id);

    /**
     * Delete the "id" dsheadertemplate.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
