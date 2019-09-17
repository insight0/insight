package com.apeiron.insight.web.rest;

import com.apeiron.insight.InsightApp;
import com.apeiron.insight.domain.Degree;
import com.apeiron.insight.repository.DegreeRepository;
import com.apeiron.insight.repository.search.DegreeSearchRepository;
import com.apeiron.insight.service.DegreeService;
import com.apeiron.insight.service.dto.DegreeDTO;
import com.apeiron.insight.service.mapper.DegreeMapper;
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
 * Integration tests for the {@link DegreeResource} REST controller.
 */
@SpringBootTest(classes = InsightApp.class)
public class DegreeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_YEAR = 1;
    private static final Integer UPDATED_YEAR = 2;
    private static final Integer SMALLER_YEAR = 1 - 1;

    private static final Integer DEFAULT_DURATION = 1;
    private static final Integer UPDATED_DURATION = 2;
    private static final Integer SMALLER_DURATION = 1 - 1;

    private static final String DEFAULT_SCHOOL = "AAAAAAAAAA";
    private static final String UPDATED_SCHOOL = "BBBBBBBBBB";

    private static final String DEFAULT_FILE_PATH = "AAAAAAAAAA";
    private static final String UPDATED_FILE_PATH = "BBBBBBBBBB";

    @Autowired
    private DegreeRepository degreeRepository;

    @Autowired
    private DegreeMapper degreeMapper;

    @Autowired
    private DegreeService degreeService;

    /**
     * This repository is mocked in the com.apeiron.insight.repository.search test package.
     *
     * @see com.apeiron.insight.repository.search.DegreeSearchRepositoryMockConfiguration
     */
    @Autowired
    private DegreeSearchRepository mockDegreeSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restDegreeMockMvc;

    private Degree degree;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DegreeResource degreeResource = new DegreeResource(degreeService);
        this.restDegreeMockMvc = MockMvcBuilders.standaloneSetup(degreeResource)
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
    public static Degree createEntity() {
        Degree degree = new Degree()
            .name(DEFAULT_NAME)
            .year(DEFAULT_YEAR)
            .duration(DEFAULT_DURATION)
            .school(DEFAULT_SCHOOL)
            .filePath(DEFAULT_FILE_PATH);
        return degree;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Degree createUpdatedEntity() {
        Degree degree = new Degree()
            .name(UPDATED_NAME)
            .year(UPDATED_YEAR)
            .duration(UPDATED_DURATION)
            .school(UPDATED_SCHOOL)
            .filePath(UPDATED_FILE_PATH);
        return degree;
    }

    @BeforeEach
    public void initTest() {
        degreeRepository.deleteAll();
        degree = createEntity();
    }

    @Test
    public void createDegree() throws Exception {
        int databaseSizeBeforeCreate = degreeRepository.findAll().size();

        // Create the Degree
        DegreeDTO degreeDTO = degreeMapper.toDto(degree);
        restDegreeMockMvc.perform(post("/api/degrees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(degreeDTO)))
            .andExpect(status().isCreated());

        // Validate the Degree in the database
        List<Degree> degreeList = degreeRepository.findAll();
        assertThat(degreeList).hasSize(databaseSizeBeforeCreate + 1);
        Degree testDegree = degreeList.get(degreeList.size() - 1);
        assertThat(testDegree.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDegree.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testDegree.getDuration()).isEqualTo(DEFAULT_DURATION);
        assertThat(testDegree.getSchool()).isEqualTo(DEFAULT_SCHOOL);
        assertThat(testDegree.getFilePath()).isEqualTo(DEFAULT_FILE_PATH);

        // Validate the Degree in Elasticsearch
        verify(mockDegreeSearchRepository, times(1)).save(testDegree);
    }

    @Test
    public void createDegreeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = degreeRepository.findAll().size();

        // Create the Degree with an existing ID
        degree.setId("existing_id");
        DegreeDTO degreeDTO = degreeMapper.toDto(degree);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDegreeMockMvc.perform(post("/api/degrees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(degreeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Degree in the database
        List<Degree> degreeList = degreeRepository.findAll();
        assertThat(degreeList).hasSize(databaseSizeBeforeCreate);

        // Validate the Degree in Elasticsearch
        verify(mockDegreeSearchRepository, times(0)).save(degree);
    }


    @Test
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = degreeRepository.findAll().size();
        // set the field null
        degree.setName(null);

        // Create the Degree, which fails.
        DegreeDTO degreeDTO = degreeMapper.toDto(degree);

        restDegreeMockMvc.perform(post("/api/degrees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(degreeDTO)))
            .andExpect(status().isBadRequest());

        List<Degree> degreeList = degreeRepository.findAll();
        assertThat(degreeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkYearIsRequired() throws Exception {
        int databaseSizeBeforeTest = degreeRepository.findAll().size();
        // set the field null
        degree.setYear(null);

        // Create the Degree, which fails.
        DegreeDTO degreeDTO = degreeMapper.toDto(degree);

        restDegreeMockMvc.perform(post("/api/degrees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(degreeDTO)))
            .andExpect(status().isBadRequest());

        List<Degree> degreeList = degreeRepository.findAll();
        assertThat(degreeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkSchoolIsRequired() throws Exception {
        int databaseSizeBeforeTest = degreeRepository.findAll().size();
        // set the field null
        degree.setSchool(null);

        // Create the Degree, which fails.
        DegreeDTO degreeDTO = degreeMapper.toDto(degree);

        restDegreeMockMvc.perform(post("/api/degrees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(degreeDTO)))
            .andExpect(status().isBadRequest());

        List<Degree> degreeList = degreeRepository.findAll();
        assertThat(degreeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkFilePathIsRequired() throws Exception {
        int databaseSizeBeforeTest = degreeRepository.findAll().size();
        // set the field null
        degree.setFilePath(null);

        // Create the Degree, which fails.
        DegreeDTO degreeDTO = degreeMapper.toDto(degree);

        restDegreeMockMvc.perform(post("/api/degrees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(degreeDTO)))
            .andExpect(status().isBadRequest());

        List<Degree> degreeList = degreeRepository.findAll();
        assertThat(degreeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllDegrees() throws Exception {
        // Initialize the database
        degreeRepository.save(degree);

        // Get all the degreeList
        restDegreeMockMvc.perform(get("/api/degrees?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(degree.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
            .andExpect(jsonPath("$.[*].duration").value(hasItem(DEFAULT_DURATION)))
            .andExpect(jsonPath("$.[*].school").value(hasItem(DEFAULT_SCHOOL.toString())))
            .andExpect(jsonPath("$.[*].filePath").value(hasItem(DEFAULT_FILE_PATH.toString())));
    }
    
    @Test
    public void getDegree() throws Exception {
        // Initialize the database
        degreeRepository.save(degree);

        // Get the degree
        restDegreeMockMvc.perform(get("/api/degrees/{id}", degree.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(degree.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR))
            .andExpect(jsonPath("$.duration").value(DEFAULT_DURATION))
            .andExpect(jsonPath("$.school").value(DEFAULT_SCHOOL.toString()))
            .andExpect(jsonPath("$.filePath").value(DEFAULT_FILE_PATH.toString()));
    }

    @Test
    public void getNonExistingDegree() throws Exception {
        // Get the degree
        restDegreeMockMvc.perform(get("/api/degrees/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateDegree() throws Exception {
        // Initialize the database
        degreeRepository.save(degree);

        int databaseSizeBeforeUpdate = degreeRepository.findAll().size();

        // Update the degree
        Degree updatedDegree = degreeRepository.findById(degree.getId()).get();
        updatedDegree
            .name(UPDATED_NAME)
            .year(UPDATED_YEAR)
            .duration(UPDATED_DURATION)
            .school(UPDATED_SCHOOL)
            .filePath(UPDATED_FILE_PATH);
        DegreeDTO degreeDTO = degreeMapper.toDto(updatedDegree);

        restDegreeMockMvc.perform(put("/api/degrees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(degreeDTO)))
            .andExpect(status().isOk());

        // Validate the Degree in the database
        List<Degree> degreeList = degreeRepository.findAll();
        assertThat(degreeList).hasSize(databaseSizeBeforeUpdate);
        Degree testDegree = degreeList.get(degreeList.size() - 1);
        assertThat(testDegree.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDegree.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testDegree.getDuration()).isEqualTo(UPDATED_DURATION);
        assertThat(testDegree.getSchool()).isEqualTo(UPDATED_SCHOOL);
        assertThat(testDegree.getFilePath()).isEqualTo(UPDATED_FILE_PATH);

        // Validate the Degree in Elasticsearch
        verify(mockDegreeSearchRepository, times(1)).save(testDegree);
    }

    @Test
    public void updateNonExistingDegree() throws Exception {
        int databaseSizeBeforeUpdate = degreeRepository.findAll().size();

        // Create the Degree
        DegreeDTO degreeDTO = degreeMapper.toDto(degree);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDegreeMockMvc.perform(put("/api/degrees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(degreeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Degree in the database
        List<Degree> degreeList = degreeRepository.findAll();
        assertThat(degreeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Degree in Elasticsearch
        verify(mockDegreeSearchRepository, times(0)).save(degree);
    }

    @Test
    public void deleteDegree() throws Exception {
        // Initialize the database
        degreeRepository.save(degree);

        int databaseSizeBeforeDelete = degreeRepository.findAll().size();

        // Delete the degree
        restDegreeMockMvc.perform(delete("/api/degrees/{id}", degree.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Degree> degreeList = degreeRepository.findAll();
        assertThat(degreeList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Degree in Elasticsearch
        verify(mockDegreeSearchRepository, times(1)).deleteById(degree.getId());
    }

    @Test
    public void searchDegree() throws Exception {
        // Initialize the database
        degreeRepository.save(degree);
        when(mockDegreeSearchRepository.search(queryStringQuery("id:" + degree.getId())))
            .thenReturn(Collections.singletonList(degree));
        // Search the degree
        restDegreeMockMvc.perform(get("/api/_search/degrees?query=id:" + degree.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(degree.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
            .andExpect(jsonPath("$.[*].duration").value(hasItem(DEFAULT_DURATION)))
            .andExpect(jsonPath("$.[*].school").value(hasItem(DEFAULT_SCHOOL)))
            .andExpect(jsonPath("$.[*].filePath").value(hasItem(DEFAULT_FILE_PATH)));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Degree.class);
        Degree degree1 = new Degree();
        degree1.setId("id1");
        Degree degree2 = new Degree();
        degree2.setId(degree1.getId());
        assertThat(degree1).isEqualTo(degree2);
        degree2.setId("id2");
        assertThat(degree1).isNotEqualTo(degree2);
        degree1.setId(null);
        assertThat(degree1).isNotEqualTo(degree2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DegreeDTO.class);
        DegreeDTO degreeDTO1 = new DegreeDTO();
        degreeDTO1.setId("id1");
        DegreeDTO degreeDTO2 = new DegreeDTO();
        assertThat(degreeDTO1).isNotEqualTo(degreeDTO2);
        degreeDTO2.setId(degreeDTO1.getId());
        assertThat(degreeDTO1).isEqualTo(degreeDTO2);
        degreeDTO2.setId("id2");
        assertThat(degreeDTO1).isNotEqualTo(degreeDTO2);
        degreeDTO1.setId(null);
        assertThat(degreeDTO1).isNotEqualTo(degreeDTO2);
    }
}
