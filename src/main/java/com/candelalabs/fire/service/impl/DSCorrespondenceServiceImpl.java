package com.candelalabs.fire.service.impl;

import com.candelalabs.fire.service.DSCorrespondenceService;
import com.candelalabs.fire.domain.DSCorrespondence;
import com.candelalabs.fire.repository.DSCorrespondenceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing DSCorrespondence.
 */
@Service
@Transactional
public class DSCorrespondenceServiceImpl implements DSCorrespondenceService {

    private final Logger log = LoggerFactory.getLogger(DSCorrespondenceServiceImpl.class);

    private final DSCorrespondenceRepository dSCorrespondenceRepository;

    public DSCorrespondenceServiceImpl(DSCorrespondenceRepository dSCorrespondenceRepository) {
        this.dSCorrespondenceRepository = dSCorrespondenceRepository;
    }

    /**
     * Save a dSCorrespondence.
     *
     * @param dSCorrespondence the entity to save
     * @return the persisted entity
     */
    @Override
    public DSCorrespondence save(DSCorrespondence dSCorrespondence) {
        log.debug("Request to save DSCorrespondence : {}", dSCorrespondence);
        return dSCorrespondenceRepository.save(dSCorrespondence);
    }

    /**
     * Get all the dSCorrespondences.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DSCorrespondence> findAll(Pageable pageable) {
        log.debug("Request to get all DSCorrespondences");
        return dSCorrespondenceRepository.findAll(pageable);
    }


    /**
     * Get one dSCorrespondence by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<DSCorrespondence> findOne(Long id) {
        log.debug("Request to get DSCorrespondence : {}", id);
        return dSCorrespondenceRepository.findById(id);
    }

    /**
     * Delete the dSCorrespondence by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete DSCorrespondence : {}", id);
        dSCorrespondenceRepository.deleteById(id);
    }
}
