package com.candelalabs.fire.service.impl;

import com.candelalabs.fire.service.DSFooterTemplateService;
import com.candelalabs.fire.domain.DSFooterTemplate;
import com.candelalabs.fire.repository.DSFooterTemplateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing DSFooterTemplate.
 */
@Service
@Transactional
public class DSFooterTemplateServiceImpl implements DSFooterTemplateService {

    private final Logger log = LoggerFactory.getLogger(DSFooterTemplateServiceImpl.class);

    private final DSFooterTemplateRepository dSFooterTemplateRepository;

    public DSFooterTemplateServiceImpl(DSFooterTemplateRepository dSFooterTemplateRepository) {
        this.dSFooterTemplateRepository = dSFooterTemplateRepository;
    }

    /**
     * Save a dSFooterTemplate.
     *
     * @param dSFooterTemplate the entity to save
     * @return the persisted entity
     */
    @Override
    public DSFooterTemplate save(DSFooterTemplate dSFooterTemplate) {
        log.debug("Request to save DSFooterTemplate : {}", dSFooterTemplate);
        return dSFooterTemplateRepository.save(dSFooterTemplate);
    }

    /**
     * Get all the dSFooterTemplates.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DSFooterTemplate> findAll(Pageable pageable) {
        log.debug("Request to get all DSFooterTemplates");
        return dSFooterTemplateRepository.findAll(pageable);
    }


    /**
     * Get one dSFooterTemplate by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<DSFooterTemplate> findOne(Long id) {
        log.debug("Request to get DSFooterTemplate : {}", id);
        return dSFooterTemplateRepository.findById(id);
    }

    /**
     * Delete the dSFooterTemplate by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete DSFooterTemplate : {}", id);
        dSFooterTemplateRepository.deleteById(id);
    }
}
