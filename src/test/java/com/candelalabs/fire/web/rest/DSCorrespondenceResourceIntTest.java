package com.candelalabs.fire.web.rest;

import com.candelalabs.fire.FireFlyCaseManagerAdminApp;

import com.candelalabs.fire.domain.DSCorrespondence;
import com.candelalabs.fire.repository.DSCorrespondenceRepository;
import com.candelalabs.fire.service.DSCorrespondenceService;
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
 * Test class for the DSCorrespondenceResource REST controller.
 *
 * @see DSCorrespondenceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FireFlyCaseManagerAdminApp.class)
public class DSCorrespondenceResourceIntTest {

    private static final Integer DEFAULT_TEMPLATEID = 1;
    private static final Integer UPDATED_TEMPLATEID = 2;

    private static final String DEFAULT_LETTERTYPE = "AAAAAAAAAA";
    private static final String UPDATED_LETTERTYPE = "BBBBBBBBBB";

    private static final String DEFAULT_CATEGORY = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY = "BBBBBBBBBB";

    private static final String DEFAULT_SUBCATEGORY = "AAAAAAAAAA";
    private static final String UPDATED_SUBCATEGORY = "BBBBBBBBBB";

    private static final byte[] DEFAULT_LETTERTEMPLATE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_LETTERTEMPLATE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_LETTERTEMPLATE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_LETTERTEMPLATE_CONTENT_TYPE = "image/png";

    private static final Integer DEFAULT_ISACTIVE = 1;
    private static final Integer UPDATED_ISACTIVE = 2;

    private static final Integer DEFAULT_PARENTID = 1;
    private static final Integer UPDATED_PARENTID = 2;

    private static final String DEFAULT_TEMPLATETYPE = "AAAAAAAAAA";
    private static final String UPDATED_TEMPLATETYPE = "BBBBBBBBBB";

    @Autowired
    private DSCorrespondenceRepository dSCorrespondenceRepository;

    @Autowired
    private DSCorrespondenceService dSCorrespondenceService;

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

    private MockMvc restDSCorrespondenceMockMvc;

    private DSCorrespondence dSCorrespondence;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DSCorrespondenceResource dSCorrespondenceResource = new DSCorrespondenceResource(dSCorrespondenceService);
        this.restDSCorrespondenceMockMvc = MockMvcBuilders.standaloneSetup(dSCorrespondenceResource)
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
    public static DSCorrespondence createEntity(EntityManager em) {
        DSCorrespondence dSCorrespondence = new DSCorrespondence()
            .templateid(DEFAULT_TEMPLATEID)
            .lettertype(DEFAULT_LETTERTYPE)
            .category(DEFAULT_CATEGORY)
            .subcategory(DEFAULT_SUBCATEGORY)
            .lettertemplate(DEFAULT_LETTERTEMPLATE)
            .lettertemplateContentType(DEFAULT_LETTERTEMPLATE_CONTENT_TYPE)
            .isactive(DEFAULT_ISACTIVE)
            .parentid(DEFAULT_PARENTID)
            .templatetype(DEFAULT_TEMPLATETYPE);
        return dSCorrespondence;
    }

    @Before
    public void initTest() {
        dSCorrespondence = createEntity(em);
    }

    @Test
    @Transactional
    public void createDSCorrespondence() throws Exception {
        int databaseSizeBeforeCreate = dSCorrespondenceRepository.findAll().size();

        // Create the DSCorrespondence
        restDSCorrespondenceMockMvc.perform(post("/api/ds-correspondences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dSCorrespondence)))
            .andExpect(status().isCreated());

        // Validate the DSCorrespondence in the database
        List<DSCorrespondence> dSCorrespondenceList = dSCorrespondenceRepository.findAll();
        assertThat(dSCorrespondenceList).hasSize(databaseSizeBeforeCreate + 1);
        DSCorrespondence testDSCorrespondence = dSCorrespondenceList.get(dSCorrespondenceList.size() - 1);
        assertThat(testDSCorrespondence.getTemplateid()).isEqualTo(DEFAULT_TEMPLATEID);
        assertThat(testDSCorrespondence.getLettertype()).isEqualTo(DEFAULT_LETTERTYPE);
        assertThat(testDSCorrespondence.getCategory()).isEqualTo(DEFAULT_CATEGORY);
        assertThat(testDSCorrespondence.getSubcategory()).isEqualTo(DEFAULT_SUBCATEGORY);
        assertThat(testDSCorrespondence.getLettertemplate()).isEqualTo(DEFAULT_LETTERTEMPLATE);
        assertThat(testDSCorrespondence.getLettertemplateContentType()).isEqualTo(DEFAULT_LETTERTEMPLATE_CONTENT_TYPE);
        assertThat(testDSCorrespondence.getIsactive()).isEqualTo(DEFAULT_ISACTIVE);
        assertThat(testDSCorrespondence.getParentid()).isEqualTo(DEFAULT_PARENTID);
        assertThat(testDSCorrespondence.getTemplatetype()).isEqualTo(DEFAULT_TEMPLATETYPE);
    }

    @Test
    @Transactional
    public void createDSCorrespondenceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dSCorrespondenceRepository.findAll().size();

        // Create the DSCorrespondence with an existing ID
        dSCorrespondence.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDSCorrespondenceMockMvc.perform(post("/api/ds-correspondences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dSCorrespondence)))
            .andExpect(status().isBadRequest());

        // Validate the DSCorrespondence in the database
        List<DSCorrespondence> dSCorrespondenceList = dSCorrespondenceRepository.findAll();
        assertThat(dSCorrespondenceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLettertypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = dSCorrespondenceRepository.findAll().size();
        // set the field null
        dSCorrespondence.setLettertype(null);

        // Create the DSCorrespondence, which fails.

        restDSCorrespondenceMockMvc.perform(post("/api/ds-correspondences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dSCorrespondence)))
            .andExpect(status().isBadRequest());

        List<DSCorrespondence> dSCorrespondenceList = dSCorrespondenceRepository.findAll();
        assertThat(dSCorrespondenceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCategoryIsRequired() throws Exception {
        int databaseSizeBeforeTest = dSCorrespondenceRepository.findAll().size();
        // set the field null
        dSCorrespondence.setCategory(null);

        // Create the DSCorrespondence, which fails.

        restDSCorrespondenceMockMvc.perform(post("/api/ds-correspondences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dSCorrespondence)))
            .andExpect(status().isBadRequest());

        List<DSCorrespondence> dSCorrespondenceList = dSCorrespondenceRepository.findAll();
        assertThat(dSCorrespondenceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSubcategoryIsRequired() throws Exception {
        int databaseSizeBeforeTest = dSCorrespondenceRepository.findAll().size();
        // set the field null
        dSCorrespondence.setSubcategory(null);

        // Create the DSCorrespondence, which fails.

        restDSCorrespondenceMockMvc.perform(post("/api/ds-correspondences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dSCorrespondence)))
            .andExpect(status().isBadRequest());

        List<DSCorrespondence> dSCorrespondenceList = dSCorrespondenceRepository.findAll();
        assertThat(dSCorrespondenceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTemplatetypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = dSCorrespondenceRepository.findAll().size();
        // set the field null
        dSCorrespondence.setTemplatetype(null);

        // Create the DSCorrespondence, which fails.

        restDSCorrespondenceMockMvc.perform(post("/api/ds-correspondences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dSCorrespondence)))
            .andExpect(status().isBadRequest());

        List<DSCorrespondence> dSCorrespondenceList = dSCorrespondenceRepository.findAll();
        assertThat(dSCorrespondenceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDSCorrespondences() throws Exception {
        // Initialize the database
        dSCorrespondenceRepository.saveAndFlush(dSCorrespondence);

        // Get all the dSCorrespondenceList
        restDSCorrespondenceMockMvc.perform(get("/api/ds-correspondences?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dSCorrespondence.getId().intValue())))
            .andExpect(jsonPath("$.[*].templateid").value(hasItem(DEFAULT_TEMPLATEID)))
            .andExpect(jsonPath("$.[*].lettertype").value(hasItem(DEFAULT_LETTERTYPE.toString())))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].subcategory").value(hasItem(DEFAULT_SUBCATEGORY.toString())))
            .andExpect(jsonPath("$.[*].lettertemplateContentType").value(hasItem(DEFAULT_LETTERTEMPLATE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].lettertemplate").value(hasItem(Base64Utils.encodeToString(DEFAULT_LETTERTEMPLATE))))
            .andExpect(jsonPath("$.[*].isactive").value(hasItem(DEFAULT_ISACTIVE)))
            .andExpect(jsonPath("$.[*].parentid").value(hasItem(DEFAULT_PARENTID)))
            .andExpect(jsonPath("$.[*].templatetype").value(hasItem(DEFAULT_TEMPLATETYPE.toString())));
    }
    
    @Test
    @Transactional
    public void getDSCorrespondence() throws Exception {
        // Initialize the database
        dSCorrespondenceRepository.saveAndFlush(dSCorrespondence);

        // Get the dSCorrespondence
        restDSCorrespondenceMockMvc.perform(get("/api/ds-correspondences/{id}", dSCorrespondence.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(dSCorrespondence.getId().intValue()))
            .andExpect(jsonPath("$.templateid").value(DEFAULT_TEMPLATEID))
            .andExpect(jsonPath("$.lettertype").value(DEFAULT_LETTERTYPE.toString()))
            .andExpect(jsonPath("$.category").value(DEFAULT_CATEGORY.toString()))
            .andExpect(jsonPath("$.subcategory").value(DEFAULT_SUBCATEGORY.toString()))
            .andExpect(jsonPath("$.lettertemplateContentType").value(DEFAULT_LETTERTEMPLATE_CONTENT_TYPE))
            .andExpect(jsonPath("$.lettertemplate").value(Base64Utils.encodeToString(DEFAULT_LETTERTEMPLATE)))
            .andExpect(jsonPath("$.isactive").value(DEFAULT_ISACTIVE))
            .andExpect(jsonPath("$.parentid").value(DEFAULT_PARENTID))
            .andExpect(jsonPath("$.templatetype").value(DEFAULT_TEMPLATETYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDSCorrespondence() throws Exception {
        // Get the dSCorrespondence
        restDSCorrespondenceMockMvc.perform(get("/api/ds-correspondences/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDSCorrespondence() throws Exception {
        // Initialize the database
        dSCorrespondenceService.save(dSCorrespondence);

        int databaseSizeBeforeUpdate = dSCorrespondenceRepository.findAll().size();

        // Update the dSCorrespondence
        DSCorrespondence updatedDSCorrespondence = dSCorrespondenceRepository.findById(dSCorrespondence.getId()).get();
        // Disconnect from session so that the updates on updatedDSCorrespondence are not directly saved in db
        em.detach(updatedDSCorrespondence);
        updatedDSCorrespondence
            .templateid(UPDATED_TEMPLATEID)
            .lettertype(UPDATED_LETTERTYPE)
            .category(UPDATED_CATEGORY)
            .subcategory(UPDATED_SUBCATEGORY)
            .lettertemplate(UPDATED_LETTERTEMPLATE)
            .lettertemplateContentType(UPDATED_LETTERTEMPLATE_CONTENT_TYPE)
            .isactive(UPDATED_ISACTIVE)
            .parentid(UPDATED_PARENTID)
            .templatetype(UPDATED_TEMPLATETYPE);

        restDSCorrespondenceMockMvc.perform(put("/api/ds-correspondences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDSCorrespondence)))
            .andExpect(status().isOk());

        // Validate the DSCorrespondence in the database
        List<DSCorrespondence> dSCorrespondenceList = dSCorrespondenceRepository.findAll();
        assertThat(dSCorrespondenceList).hasSize(databaseSizeBeforeUpdate);
        DSCorrespondence testDSCorrespondence = dSCorrespondenceList.get(dSCorrespondenceList.size() - 1);
        assertThat(testDSCorrespondence.getTemplateid()).isEqualTo(UPDATED_TEMPLATEID);
        assertThat(testDSCorrespondence.getLettertype()).isEqualTo(UPDATED_LETTERTYPE);
        assertThat(testDSCorrespondence.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testDSCorrespondence.getSubcategory()).isEqualTo(UPDATED_SUBCATEGORY);
        assertThat(testDSCorrespondence.getLettertemplate()).isEqualTo(UPDATED_LETTERTEMPLATE);
        assertThat(testDSCorrespondence.getLettertemplateContentType()).isEqualTo(UPDATED_LETTERTEMPLATE_CONTENT_TYPE);
        assertThat(testDSCorrespondence.getIsactive()).isEqualTo(UPDATED_ISACTIVE);
        assertThat(testDSCorrespondence.getParentid()).isEqualTo(UPDATED_PARENTID);
        assertThat(testDSCorrespondence.getTemplatetype()).isEqualTo(UPDATED_TEMPLATETYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingDSCorrespondence() throws Exception {
        int databaseSizeBeforeUpdate = dSCorrespondenceRepository.findAll().size();

        // Create the DSCorrespondence

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDSCorrespondenceMockMvc.perform(put("/api/ds-correspondences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dSCorrespondence)))
            .andExpect(status().isBadRequest());

        // Validate the DSCorrespondence in the database
        List<DSCorrespondence> dSCorrespondenceList = dSCorrespondenceRepository.findAll();
        assertThat(dSCorrespondenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDSCorrespondence() throws Exception {
        // Initialize the database
        dSCorrespondenceService.save(dSCorrespondence);

        int databaseSizeBeforeDelete = dSCorrespondenceRepository.findAll().size();

        // Get the dSCorrespondence
        restDSCorrespondenceMockMvc.perform(delete("/api/ds-correspondences/{id}", dSCorrespondence.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DSCorrespondence> dSCorrespondenceList = dSCorrespondenceRepository.findAll();
        assertThat(dSCorrespondenceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DSCorrespondence.class);
        DSCorrespondence dSCorrespondence1 = new DSCorrespondence();
        dSCorrespondence1.setId(1L);
        DSCorrespondence dSCorrespondence2 = new DSCorrespondence();
        dSCorrespondence2.setId(dSCorrespondence1.getId());
        assertThat(dSCorrespondence1).isEqualTo(dSCorrespondence2);
        dSCorrespondence2.setId(2L);
        assertThat(dSCorrespondence1).isNotEqualTo(dSCorrespondence2);
        dSCorrespondence1.setId(null);
        assertThat(dSCorrespondence1).isNotEqualTo(dSCorrespondence2);
    }
}
