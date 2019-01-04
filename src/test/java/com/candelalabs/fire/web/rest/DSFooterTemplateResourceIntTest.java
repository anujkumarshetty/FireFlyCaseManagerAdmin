package com.candelalabs.fire.web.rest;

import com.candelalabs.fire.FireFlyCaseManagerAdminApp;

import com.candelalabs.fire.domain.DSFooterTemplate;
import com.candelalabs.fire.repository.DSFooterTemplateRepository;
import com.candelalabs.fire.service.DSFooterTemplateService;
import com.candelalabs.fire.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;


import static com.candelalabs.fire.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the DSFooterTemplateResource REST controller.
 *
 * @see DSFooterTemplateResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FireFlyCaseManagerAdminApp.class)
public class DSFooterTemplateResourceIntTest {

    private static final String DEFAULT_FOOTERCONTENT = "AAAAAAAAAA";
    private static final String UPDATED_FOOTERCONTENT = "BBBBBBBBBB";

    private static final String DEFAULT_FOOTERTEMPLATENAME = "AAAAAAAAAA";
    private static final String UPDATED_FOOTERTEMPLATENAME = "BBBBBBBBBB";

    @Autowired
    private DSFooterTemplateRepository dSFooterTemplateRepository;

    @Autowired
    private DSFooterTemplateService dSFooterTemplateService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restDSFooterTemplateMockMvc;

    private DSFooterTemplate dSFooterTemplate;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DSFooterTemplateResource dSFooterTemplateResource = new DSFooterTemplateResource(dSFooterTemplateService);
        this.restDSFooterTemplateMockMvc = MockMvcBuilders.standaloneSetup(dSFooterTemplateResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DSFooterTemplate createEntity(EntityManager em) {
        DSFooterTemplate dSFooterTemplate = new DSFooterTemplate()
            .footercontent(DEFAULT_FOOTERCONTENT)
            .footertemplatename(DEFAULT_FOOTERTEMPLATENAME);
        return dSFooterTemplate;
    }

    @Before
    public void initTest() {
        dSFooterTemplate = createEntity(em);
    }

    @Test
    @Transactional
    public void createDSFooterTemplate() throws Exception {
        int databaseSizeBeforeCreate = dSFooterTemplateRepository.findAll().size();

        // Create the DSFooterTemplate
        restDSFooterTemplateMockMvc.perform(post("/api/ds-footer-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dSFooterTemplate)))
            .andExpect(status().isCreated());

        // Validate the DSFooterTemplate in the database
        List<DSFooterTemplate> dSFooterTemplateList = dSFooterTemplateRepository.findAll();
        assertThat(dSFooterTemplateList).hasSize(databaseSizeBeforeCreate + 1);
        DSFooterTemplate testDSFooterTemplate = dSFooterTemplateList.get(dSFooterTemplateList.size() - 1);
        assertThat(testDSFooterTemplate.getFootercontent()).isEqualTo(DEFAULT_FOOTERCONTENT);
        assertThat(testDSFooterTemplate.getFootertemplatename()).isEqualTo(DEFAULT_FOOTERTEMPLATENAME);
    }

    @Test
    @Transactional
    public void createDSFooterTemplateWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dSFooterTemplateRepository.findAll().size();

        // Create the DSFooterTemplate with an existing ID
        dSFooterTemplate.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDSFooterTemplateMockMvc.perform(post("/api/ds-footer-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dSFooterTemplate)))
            .andExpect(status().isBadRequest());

        // Validate the DSFooterTemplate in the database
        List<DSFooterTemplate> dSFooterTemplateList = dSFooterTemplateRepository.findAll();
        assertThat(dSFooterTemplateList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkFootertemplatenameIsRequired() throws Exception {
        int databaseSizeBeforeTest = dSFooterTemplateRepository.findAll().size();
        // set the field null
        dSFooterTemplate.setFootertemplatename(null);

        // Create the DSFooterTemplate, which fails.

        restDSFooterTemplateMockMvc.perform(post("/api/ds-footer-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dSFooterTemplate)))
            .andExpect(status().isBadRequest());

        List<DSFooterTemplate> dSFooterTemplateList = dSFooterTemplateRepository.findAll();
        assertThat(dSFooterTemplateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDSFooterTemplates() throws Exception {
        // Initialize the database
        dSFooterTemplateRepository.saveAndFlush(dSFooterTemplate);

        // Get all the dSFooterTemplateList
        restDSFooterTemplateMockMvc.perform(get("/api/ds-footer-templates?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dSFooterTemplate.getId().intValue())))
            .andExpect(jsonPath("$.[*].footercontent").value(hasItem(DEFAULT_FOOTERCONTENT.toString())))
            .andExpect(jsonPath("$.[*].footertemplatename").value(hasItem(DEFAULT_FOOTERTEMPLATENAME.toString())));
    }
    
    @Test
    @Transactional
    public void getDSFooterTemplate() throws Exception {
        // Initialize the database
        dSFooterTemplateRepository.saveAndFlush(dSFooterTemplate);

        // Get the dSFooterTemplate
        restDSFooterTemplateMockMvc.perform(get("/api/ds-footer-templates/{id}", dSFooterTemplate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(dSFooterTemplate.getId().intValue()))
            .andExpect(jsonPath("$.footercontent").value(DEFAULT_FOOTERCONTENT.toString()))
            .andExpect(jsonPath("$.footertemplatename").value(DEFAULT_FOOTERTEMPLATENAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDSFooterTemplate() throws Exception {
        // Get the dSFooterTemplate
        restDSFooterTemplateMockMvc.perform(get("/api/ds-footer-templates/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDSFooterTemplate() throws Exception {
        // Initialize the database
        dSFooterTemplateService.save(dSFooterTemplate);

        int databaseSizeBeforeUpdate = dSFooterTemplateRepository.findAll().size();

        // Update the dSFooterTemplate
        DSFooterTemplate updatedDSFooterTemplate = dSFooterTemplateRepository.findById(dSFooterTemplate.getId()).get();
        // Disconnect from session so that the updates on updatedDSFooterTemplate are not directly saved in db
        em.detach(updatedDSFooterTemplate);
        updatedDSFooterTemplate
            .footercontent(UPDATED_FOOTERCONTENT)
            .footertemplatename(UPDATED_FOOTERTEMPLATENAME);

        restDSFooterTemplateMockMvc.perform(put("/api/ds-footer-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDSFooterTemplate)))
            .andExpect(status().isOk());

        // Validate the DSFooterTemplate in the database
        List<DSFooterTemplate> dSFooterTemplateList = dSFooterTemplateRepository.findAll();
        assertThat(dSFooterTemplateList).hasSize(databaseSizeBeforeUpdate);
        DSFooterTemplate testDSFooterTemplate = dSFooterTemplateList.get(dSFooterTemplateList.size() - 1);
        assertThat(testDSFooterTemplate.getFootercontent()).isEqualTo(UPDATED_FOOTERCONTENT);
        assertThat(testDSFooterTemplate.getFootertemplatename()).isEqualTo(UPDATED_FOOTERTEMPLATENAME);
    }

    @Test
    @Transactional
    public void updateNonExistingDSFooterTemplate() throws Exception {
        int databaseSizeBeforeUpdate = dSFooterTemplateRepository.findAll().size();

        // Create the DSFooterTemplate

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDSFooterTemplateMockMvc.perform(put("/api/ds-footer-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dSFooterTemplate)))
            .andExpect(status().isBadRequest());

        // Validate the DSFooterTemplate in the database
        List<DSFooterTemplate> dSFooterTemplateList = dSFooterTemplateRepository.findAll();
        assertThat(dSFooterTemplateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDSFooterTemplate() throws Exception {
        // Initialize the database
        dSFooterTemplateService.save(dSFooterTemplate);

        int databaseSizeBeforeDelete = dSFooterTemplateRepository.findAll().size();

        // Get the dSFooterTemplate
        restDSFooterTemplateMockMvc.perform(delete("/api/ds-footer-templates/{id}", dSFooterTemplate.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DSFooterTemplate> dSFooterTemplateList = dSFooterTemplateRepository.findAll();
        assertThat(dSFooterTemplateList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DSFooterTemplate.class);
        DSFooterTemplate dSFooterTemplate1 = new DSFooterTemplate();
        dSFooterTemplate1.setId(1L);
        DSFooterTemplate dSFooterTemplate2 = new DSFooterTemplate();
        dSFooterTemplate2.setId(dSFooterTemplate1.getId());
        assertThat(dSFooterTemplate1).isEqualTo(dSFooterTemplate2);
        dSFooterTemplate2.setId(2L);
        assertThat(dSFooterTemplate1).isNotEqualTo(dSFooterTemplate2);
        dSFooterTemplate1.setId(null);
        assertThat(dSFooterTemplate1).isNotEqualTo(dSFooterTemplate2);
    }
}
