package com.candelalabs.fire.service;

import com.candelalabs.fire.domain.DSCorrespondence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing DSCorrespondence.
 */
public interface DSCorrespondenceService {

    /**
     * Save a dSCorrespondence.
     *
     * @param dSCorrespondence the entity to save
     * @return the persisted entity
     */
    DSCorrespondence save(DSCorrespondence dSCorrespondence);

    /**
     * Get all the dSCorrespondences.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<DSCorrespondence> findAll(Pageable pageable);


    /**
     * Get the "id" dSCorrespondence.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<DSCorrespondence> findOne(Long id);

    /**
     * Delete the "id" dSCorrespondence.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
