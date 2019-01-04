package com.candelalabs.fire.service.impl;

import com.candelalabs.fire.service.DsheadertemplateService;
import com.candelalabs.fire.domain.Dsheadertemplate;
import com.candelalabs.fire.repository.DsheadertemplateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Dsheadertemplate.
 */
@Service
@Transactional
public class DsheadertemplateServiceImpl implements DsheadertemplateService {

    private final Logger log = LoggerFactory.getLogger(DsheadertemplateServiceImpl.class);

    private final DsheadertemplateRepository dsheadertemplateRepository;

    public DsheadertemplateServiceImpl(DsheadertemplateRepository dsheadertemplateRepository) {
        this.dsheadertemplateRepository = dsheadertemplateRepository;
    }

    /**
     * Save a dsheadertemplate.
     *
     * @param dsheadertemplate the entity to save
     * @return the persisted entity
     */
    @Override
    public Dsheadertemplate save(Dsheadertemplate dsheadertemplate) {
        log.debug("Request to save Dsheadertemplate : {}", dsheadertemplate);
        return dsheadertemplateRepository.save(dsheadertemplate);
    }

    /**
     * Get all the dsheadertemplates.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Dsheadertemplate> findAll(Pageable pageable) {
        log.debug("Request to get all Dsheadertemplates");
        return dsheadertemplateRepository.findAll(pageable);
    }


    /**
     * Get one dsheadertemplate by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Dsheadertemplate> findOne(Long id) {
        log.debug("Request to get Dsheadertemplate : {}", id);
        return dsheadertemplateRepository.findById(id);
    }

    /**
     * Delete the dsheadertemplate by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Dsheadertemplate : {}", id);
        dsheadertemplateRepository.deleteById(id);
    }
}
