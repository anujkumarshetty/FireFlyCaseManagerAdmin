package com.candelalabs.fire.web.rest;

import com.candelalabs.fire.FireFlyCaseManagerAdminApp;

import com.candelalabs.fire.domain.Dsheadertemplate;
import com.candelalabs.fire.repository.DsheadertemplateRepository;
import com.candelalabs.fire.service.DsheadertemplateService;
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
 * Test class for the DsheadertemplateResource REST controller.
 *
 * @see DsheadertemplateResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FireFlyCaseManagerAdminApp.class)
public class DsheadertemplateResourceIntTest {

    private static final String DEFAULT_HEADERCONTENT = "AAAAAAAAAA";
    private static final String UPDATED_HEADERCONTENT = "BBBBBBBBBB";

    private static final String DEFAULT_HEADERTEMPLATENAME = "AAAAAAAAAA";
    private static final String UPDATED_HEADERTEMPLATENAME = "BBBBBBBBBB";

    @Autowired
    private DsheadertemplateRepository dsheadertemplateRepository;

    @Autowired
    private DsheadertemplateService dsheadertemplateService;

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

    private MockMvc restDsheadertemplateMockMvc;

    private Dsheadertemplate dsheadertemplate;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DsheadertemplateResource dsheadertemplateResource = new DsheadertemplateResource(dsheadertemplateService);
        this.restDsheadertemplateMockMvc = MockMvcBuilders.standaloneSetup(dsheadertemplateResource)
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
    public static Dsheadertemplate createEntity(EntityManager em) {
        Dsheadertemplate dsheadertemplate = new Dsheadertemplate()
            .headercontent(DEFAULT_HEADERCONTENT)
            .headertemplatename(DEFAULT_HEADERTEMPLATENAME);
        return dsheadertemplate;
    }

    @Before
    public void initTest() {
        dsheadertemplate = createEntity(em);
    }

    @Test
    @Transactional
    public void createDsheadertemplate() throws Exception {
        int databaseSizeBeforeCreate = dsheadertemplateRepository.findAll().size();

        // Create the Dsheadertemplate
        restDsheadertemplateMockMvc.perform(post("/api/dsheadertemplates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dsheadertemplate)))
            .andExpect(status().isCreated());

        // Validate the Dsheadertemplate in the database
        List<Dsheadertemplate> dsheadertemplateList = dsheadertemplateRepository.findAll();
        assertThat(dsheadertemplateList).hasSize(databaseSizeBeforeCreate + 1);
        Dsheadertemplate testDsheadertemplate = dsheadertemplateList.get(dsheadertemplateList.size() - 1);
        assertThat(testDsheadertemplate.getHeadercontent()).isEqualTo(DEFAULT_HEADERCONTENT);
        assertThat(testDsheadertemplate.getHeadertemplatename()).isEqualTo(DEFAULT_HEADERTEMPLATENAME);
    }

    @Test
    @Transactional
    public void createDsheadertemplateWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dsheadertemplateRepository.findAll().size();

        // Create the Dsheadertemplate with an existing ID
        dsheadertemplate.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDsheadertemplateMockMvc.perform(post("/api/dsheadertemplates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dsheadertemplate)))
            .andExpect(status().isBadRequest());

        // Validate the Dsheadertemplate in the database
        List<Dsheadertemplate> dsheadertemplateList = dsheadertemplateRepository.findAll();
        assertThat(dsheadertemplateList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkHeadertemplatenameIsRequired() throws Exception {
        int databaseSizeBeforeTest = dsheadertemplateRepository.findAll().size();
        // set the field null
        dsheadertemplate.setHeadertemplatename(null);

        // Create the Dsheadertemplate, which fails.

        restDsheadertemplateMockMvc.perform(post("/api/dsheadertemplates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dsheadertemplate)))
            .andExpect(status().isBadRequest());

        List<Dsheadertemplate> dsheadertemplateList = dsheadertemplateRepository.findAll();
        assertThat(dsheadertemplateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDsheadertemplates() throws Exception {
        // Initialize the database
        dsheadertemplateRepository.saveAndFlush(dsheadertemplate);

        // Get all the dsheadertemplateList
        restDsheadertemplateMockMvc.perform(get("/api/dsheadertemplates?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dsheadertemplate.getId().intValue())))
            .andExpect(jsonPath("$.[*].headercontent").value(hasItem(DEFAULT_HEADERCONTENT.toString())))
            .andExpect(jsonPath("$.[*].headertemplatename").value(hasItem(DEFAULT_HEADERTEMPLATENAME.toString())));
    }
    
    @Test
    @Transactional
    public void getDsheadertemplate() throws Exception {
        // Initialize the database
        dsheadertemplateRepository.saveAndFlush(dsheadertemplate);

        // Get the dsheadertemplate
        restDsheadertemplateMockMvc.perform(get("/api/dsheadertemplates/{id}", dsheadertemplate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(dsheadertemplate.getId().intValue()))
            .andExpect(jsonPath("$.headercontent").value(DEFAULT_HEADERCONTENT.toString()))
            .andExpect(jsonPath("$.headertemplatename").value(DEFAULT_HEADERTEMPLATENAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDsheadertemplate() throws Exception {
        // Get the dsheadertemplate
        restDsheadertemplateMockMvc.perform(get("/api/dsheadertemplates/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDsheadertemplate() throws Exception {
        // Initialize the database
        dsheadertemplateService.save(dsheadertemplate);

        int databaseSizeBeforeUpdate = dsheadertemplateRepository.findAll().size();

        // Update the dsheadertemplate
        Dsheadertemplate updatedDsheadertemplate = dsheadertemplateRepository.findById(dsheadertemplate.getId()).get();
        // Disconnect from session so that the updates on updatedDsheadertemplate are not directly saved in db
        em.detach(updatedDsheadertemplate);
        updatedDsheadertemplate
            .headercontent(UPDATED_HEADERCONTENT)
            .headertemplatename(UPDATED_HEADERTEMPLATENAME);

        restDsheadertemplateMockMvc.perform(put("/api/dsheadertemplates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDsheadertemplate)))
            .andExpect(status().isOk());

        // Validate the Dsheadertemplate in the database
        List<Dsheadertemplate> dsheadertemplateList = dsheadertemplateRepository.findAll();
        assertThat(dsheadertemplateList).hasSize(databaseSizeBeforeUpdate);
        Dsheadertemplate testDsheadertemplate = dsheadertemplateList.get(dsheadertemplateList.size() - 1);
        assertThat(testDsheadertemplate.getHeadercontent()).isEqualTo(UPDATED_HEADERCONTENT);
        assertThat(testDsheadertemplate.getHeadertemplatename()).isEqualTo(UPDATED_HEADERTEMPLATENAME);
    }

    @Test
    @Transactional
    public void updateNonExistingDsheadertemplate() throws Exception {
        int databaseSizeBeforeUpdate = dsheadertemplateRepository.findAll().size();

        // Create the Dsheadertemplate

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDsheadertemplateMockMvc.perform(put("/api/dsheadertemplates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dsheadertemplate)))
            .andExpect(status().isBadRequest());

        // Validate the Dsheadertemplate in the database
        List<Dsheadertemplate> dsheadertemplateList = dsheadertemplateRepository.findAll();
        assertThat(dsheadertemplateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDsheadertemplate() throws Exception {
        // Initialize the database
        dsheadertemplateService.save(dsheadertemplate);

        int databaseSizeBeforeDelete = dsheadertemplateRepository.findAll().size();

        // Get the dsheadertemplate
        restDsheadertemplateMockMvc.perform(delete("/api/dsheadertemplates/{id}", dsheadertemplate.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Dsheadertemplate> dsheadertemplateList = dsheadertemplateRepository.findAll();
        assertThat(dsheadertemplateList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Dsheadertemplate.class);
        Dsheadertemplate dsheadertemplate1 = new Dsheadertemplate();
        dsheadertemplate1.setId(1L);
        Dsheadertemplate dsheadertemplate2 = new Dsheadertemplate();
        dsheadertemplate2.setId(dsheadertemplate1.getId());
        assertThat(dsheadertemplate1).isEqualTo(dsheadertemplate2);
        dsheadertemplate2.setId(2L);
        assertThat(dsheadertemplate1).isNotEqualTo(dsheadertemplate2);
        dsheadertemplate1.setId(null);
        assertThat(dsheadertemplate1).isNotEqualTo(dsheadertemplate2);
    }
}
