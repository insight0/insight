package com.apeiron.insight.web.rest;

import com.apeiron.insight.InsightApp;
import com.apeiron.insight.domain.Certification;
import com.apeiron.insight.repository.CertificationRepository;
import com.apeiron.insight.repository.search.CertificationSearchRepository;
import com.apeiron.insight.service.CertificationService;
import com.apeiron.insight.service.dto.CertificationDTO;
import com.apeiron.insight.service.mapper.CertificationMapper;
import com.apeiron.insight.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.Validator;


import java.util.Collections;
import java.util.List;

import static com.apeiron.insight.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CertificationResource} REST controller.
 */
@SpringBootTest(classes = InsightApp.class)
public class CertificationResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_YEAR = 1;
    private static final Integer UPDATED_YEAR = 2;
    private static final Integer SMALLER_YEAR = 1 - 1;

    private static final String DEFAULT_ORGANIZATION = "AAAAAAAAAA";
    private static final String UPDATED_ORGANIZATION = "BBBBBBBBBB";

    private static final String DEFAULT_FILE_PATH = "AAAAAAAAAA";
    private static final String UPDATED_FILE_PATH = "BBBBBBBBBB";

    @Autowired
    private CertificationRepository certificationRepository;

    @Autowired
    private CertificationMapper certificationMapper;

    @Autowired
    private CertificationService certificationService;

    /**
     * This repository is mocked in the com.apeiron.insight.repository.search test package.
     *
     * @see com.apeiron.insight.repository.search.CertificationSearchRepositoryMockConfiguration
     */
    @Autowired
    private CertificationSearchRepository mockCertificationSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restCertificationMockMvc;

    private Certification certification;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CertificationResource certificationResource = new CertificationResource(certificationService);
        this.restCertificationMockMvc = MockMvcBuilders.standaloneSetup(certificationResource)
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
    public static Certification createEntity() {
        Certification certification = new Certification()
            .name(DEFAULT_NAME)
            .year(DEFAULT_YEAR)
            .organization(DEFAULT_ORGANIZATION)
            .filePath(DEFAULT_FILE_PATH);
        return certification;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Certification createUpdatedEntity() {
        Certification certification = new Certification()
            .name(UPDATED_NAME)
            .year(UPDATED_YEAR)
            .organization(UPDATED_ORGANIZATION)
            .filePath(UPDATED_FILE_PATH);
        return certification;
    }

    @BeforeEach
    public void initTest() {
        certificationRepository.deleteAll();
        certification = createEntity();
    }

    @Test
    public void createCertification() throws Exception {
        int databaseSizeBeforeCreate = certificationRepository.findAll().size();

        // Create the Certification
        CertificationDTO certificationDTO = certificationMapper.toDto(certification);
        restCertificationMockMvc.perform(post("/api/certifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(certificationDTO)))
            .andExpect(status().isCreated());

        // Validate the Certification in the database
        List<Certification> certificationList = certificationRepository.findAll();
        assertThat(certificationList).hasSize(databaseSizeBeforeCreate + 1);
        Certification testCertification = certificationList.get(certificationList.size() - 1);
        assertThat(testCertification.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCertification.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testCertification.getOrganization()).isEqualTo(DEFAULT_ORGANIZATION);
        assertThat(testCertification.getFilePath()).isEqualTo(DEFAULT_FILE_PATH);

        // Validate the Certification in Elasticsearch
        verify(mockCertificationSearchRepository, times(1)).save(testCertification);
    }

    @Test
    public void createCertificationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = certificationRepository.findAll().size();

        // Create the Certification with an existing ID
        certification.setId("existing_id");
        CertificationDTO certificationDTO = certificationMapper.toDto(certification);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCertificationMockMvc.perform(post("/api/certifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(certificationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Certification in the database
        List<Certification> certificationList = certificationRepository.findAll();
        assertThat(certificationList).hasSize(databaseSizeBeforeCreate);

        // Validate the Certification in Elasticsearch
        verify(mockCertificationSearchRepository, times(0)).save(certification);
    }


    @Test
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = certificationRepository.findAll().size();
        // set the field null
        certification.setName(null);

        // Create the Certification, which fails.
        CertificationDTO certificationDTO = certificationMapper.toDto(certification);

        restCertificationMockMvc.perform(post("/api/certifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(certificationDTO)))
            .andExpect(status().isBadRequest());

        List<Certification> certificationList = certificationRepository.findAll();
        assertThat(certificationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkYearIsRequired() throws Exception {
        int databaseSizeBeforeTest = certificationRepository.findAll().size();
        // set the field null
        certification.setYear(null);

        // Create the Certification, which fails.
        CertificationDTO certificationDTO = certificationMapper.toDto(certification);

        restCertificationMockMvc.perform(post("/api/certifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(certificationDTO)))
            .andExpect(status().isBadRequest());

        List<Certification> certificationList = certificationRepository.findAll();
        assertThat(certificationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkOrganizationIsRequired() throws Exception {
        int databaseSizeBeforeTest = certificationRepository.findAll().size();
        // set the field null
        certification.setOrganization(null);

        // Create the Certification, which fails.
        CertificationDTO certificationDTO = certificationMapper.toDto(certification);

        restCertificationMockMvc.perform(post("/api/certifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(certificationDTO)))
            .andExpect(status().isBadRequest());

        List<Certification> certificationList = certificationRepository.findAll();
        assertThat(certificationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllCertifications() throws Exception {
        // Initialize the database
        certificationRepository.save(certification);

        // Get all the certificationList
        restCertificationMockMvc.perform(get("/api/certifications?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(certification.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
            .andExpect(jsonPath("$.[*].organization").value(hasItem(DEFAULT_ORGANIZATION.toString())))
            .andExpect(jsonPath("$.[*].filePath").value(hasItem(DEFAULT_FILE_PATH.toString())));
    }
    
    @Test
    public void getCertification() throws Exception {
        // Initialize the database
        certificationRepository.save(certification);

        // Get the certification
        restCertificationMockMvc.perform(get("/api/certifications/{id}", certification.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(certification.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR))
            .andExpect(jsonPath("$.organization").value(DEFAULT_ORGANIZATION.toString()))
            .andExpect(jsonPath("$.filePath").value(DEFAULT_FILE_PATH.toString()));
    }

    @Test
    public void getNonExistingCertification() throws Exception {
        // Get the certification
        restCertificationMockMvc.perform(get("/api/certifications/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateCertification() throws Exception {
        // Initialize the database
        certificationRepository.save(certification);

        int databaseSizeBeforeUpdate = certificationRepository.findAll().size();

        // Update the certification
        Certification updatedCertification = certificationRepository.findById(certification.getId()).get();
        updatedCertification
            .name(UPDATED_NAME)
            .year(UPDATED_YEAR)
            .organization(UPDATED_ORGANIZATION)
            .filePath(UPDATED_FILE_PATH);
        CertificationDTO certificationDTO = certificationMapper.toDto(updatedCertification);

        restCertificationMockMvc.perform(put("/api/certifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(certificationDTO)))
            .andExpect(status().isOk());

        // Validate the Certification in the database
        List<Certification> certificationList = certificationRepository.findAll();
        assertThat(certificationList).hasSize(databaseSizeBeforeUpdate);
        Certification testCertification = certificationList.get(certificationList.size() - 1);
        assertThat(testCertification.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCertification.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testCertification.getOrganization()).isEqualTo(UPDATED_ORGANIZATION);
        assertThat(testCertification.getFilePath()).isEqualTo(UPDATED_FILE_PATH);

        // Validate the Certification in Elasticsearch
        verify(mockCertificationSearchRepository, times(1)).save(testCertification);
    }

    @Test
    public void updateNonExistingCertification() throws Exception {
        int databaseSizeBeforeUpdate = certificationRepository.findAll().size();

        // Create the Certification
        CertificationDTO certificationDTO = certificationMapper.toDto(certification);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCertificationMockMvc.perform(put("/api/certifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(certificationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Certification in the database
        List<Certification> certificationList = certificationRepository.findAll();
        assertThat(certificationList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Certification in Elasticsearch
        verify(mockCertificationSearchRepository, times(0)).save(certification);
    }

    @Test
    public void deleteCertification() throws Exception {
        // Initialize the database
        certificationRepository.save(certification);

        int databaseSizeBeforeDelete = certificationRepository.findAll().size();

        // Delete the certification
        restCertificationMockMvc.perform(delete("/api/certifications/{id}", certification.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Certification> certificationList = certificationRepository.findAll();
        assertThat(certificationList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Certification in Elasticsearch
        verify(mockCertificationSearchRepository, times(1)).deleteById(certification.getId());
    }

    @Test
    public void searchCertification() throws Exception {
        // Initialize the database
        certificationRepository.save(certification);
        when(mockCertificationSearchRepository.search(queryStringQuery("id:" + certification.getId())))
            .thenReturn(Collections.singletonList(certification));
        // Search the certification
        restCertificationMockMvc.perform(get("/api/_search/certifications?query=id:" + certification.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(certification.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
            .andExpect(jsonPath("$.[*].organization").value(hasItem(DEFAULT_ORGANIZATION)))
            .andExpect(jsonPath("$.[*].filePath").value(hasItem(DEFAULT_FILE_PATH)));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Certification.class);
        Certification certification1 = new Certification();
        certification1.setId("id1");
        Certification certification2 = new Certification();
        certification2.setId(certification1.getId());
        assertThat(certification1).isEqualTo(certification2);
        certification2.setId("id2");
        assertThat(certification1).isNotEqualTo(certification2);
        certification1.setId(null);
        assertThat(certification1).isNotEqualTo(certification2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CertificationDTO.class);
        CertificationDTO certificationDTO1 = new CertificationDTO();
        certificationDTO1.setId("id1");
        CertificationDTO certificationDTO2 = new CertificationDTO();
        assertThat(certificationDTO1).isNotEqualTo(certificationDTO2);
        certificationDTO2.setId(certificationDTO1.getId());
        assertThat(certificationDTO1).isEqualTo(certificationDTO2);
        certificationDTO2.setId("id2");
        assertThat(certificationDTO1).isNotEqualTo(certificationDTO2);
        certificationDTO1.setId(null);
        assertThat(certificationDTO1).isNotEqualTo(certificationDTO2);
    }
}
