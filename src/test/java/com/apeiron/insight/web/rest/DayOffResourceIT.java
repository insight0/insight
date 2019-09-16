package com.apeiron.insight.web.rest;

import com.apeiron.insight.InsightApp;
import com.apeiron.insight.domain.DayOff;
import com.apeiron.insight.repository.DayOffRepository;
import com.apeiron.insight.repository.search.DayOffSearchRepository;
import com.apeiron.insight.service.DayOffService;
import com.apeiron.insight.service.dto.DayOffDTO;
import com.apeiron.insight.service.mapper.DayOffMapper;
import com.apeiron.insight.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.Validator;


import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;

import static com.apeiron.insight.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.apeiron.insight.domain.enumeration.DayOffStatus;
/**
 * Integration tests for the {@link DayOffResource} REST controller.
 */
@SpringBootTest(classes = InsightApp.class)
public class DayOffResourceIT {

    private static final Instant DEFAULT_START_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);
    private static final Instant SMALLER_START_DATE = Instant.ofEpochMilli(-1L);

    private static final Instant DEFAULT_END_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);
    private static final Instant SMALLER_END_DATE = Instant.ofEpochMilli(-1L);

    private static final String DEFAULT_DAY_OFF_OBJECT = "AAAAAAAAAA";
    private static final String UPDATED_DAY_OFF_OBJECT = "BBBBBBBBBB";

    private static final DayOffStatus DEFAULT_STATUS = DayOffStatus.NEW;
    private static final DayOffStatus UPDATED_STATUS = DayOffStatus.DECLINED;

    private static final Boolean DEFAULT_FORCED = false;
    private static final Boolean UPDATED_FORCED = true;

    private static final String DEFAULT_EMPLOYE_ID = "AAAAAAAAAA";
    private static final String UPDATED_EMPLOYE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_VALIDATOR_ID = "AAAAAAAAAA";
    private static final String UPDATED_VALIDATOR_ID = "BBBBBBBBBB";

    private static final Float DEFAULT_DAYS = 1F;
    private static final Float UPDATED_DAYS = 2F;
    private static final Float SMALLER_DAYS = 1F - 1F;

    @Autowired
    private DayOffRepository dayOffRepository;

    @Autowired
    private DayOffMapper dayOffMapper;

    @Autowired
    private DayOffService dayOffService;

    /**
     * This repository is mocked in the com.apeiron.insight.repository.search test package.
     *
     * @see com.apeiron.insight.repository.search.DayOffSearchRepositoryMockConfiguration
     */
    @Autowired
    private DayOffSearchRepository mockDayOffSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restDayOffMockMvc;

    private DayOff dayOff;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DayOffResource dayOffResource = new DayOffResource(dayOffService);
        this.restDayOffMockMvc = MockMvcBuilders.standaloneSetup(dayOffResource)
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
    public static DayOff createEntity() {
        DayOff dayOff = new DayOff()
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .dayOffObject(DEFAULT_DAY_OFF_OBJECT)
            .status(DEFAULT_STATUS)
            .forced(DEFAULT_FORCED)
            .employeId(DEFAULT_EMPLOYE_ID)
            .validatorId(DEFAULT_VALIDATOR_ID)
            .days(DEFAULT_DAYS);
        return dayOff;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DayOff createUpdatedEntity() {
        DayOff dayOff = new DayOff()
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .dayOffObject(UPDATED_DAY_OFF_OBJECT)
            .status(UPDATED_STATUS)
            .forced(UPDATED_FORCED)
            .employeId(UPDATED_EMPLOYE_ID)
            .validatorId(UPDATED_VALIDATOR_ID)
            .days(UPDATED_DAYS);
        return dayOff;
    }

    @BeforeEach
    public void initTest() {
        dayOffRepository.deleteAll();
        dayOff = createEntity();
    }

    @Test
    public void createDayOff() throws Exception {
        int databaseSizeBeforeCreate = dayOffRepository.findAll().size();

        // Create the DayOff
        DayOffDTO dayOffDTO = dayOffMapper.toDto(dayOff);
        restDayOffMockMvc.perform(post("/api/day-offs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dayOffDTO)))
            .andExpect(status().isCreated());

        // Validate the DayOff in the database
        List<DayOff> dayOffList = dayOffRepository.findAll();
        assertThat(dayOffList).hasSize(databaseSizeBeforeCreate + 1);
        DayOff testDayOff = dayOffList.get(dayOffList.size() - 1);
        assertThat(testDayOff.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testDayOff.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testDayOff.getDayOffObject()).isEqualTo(DEFAULT_DAY_OFF_OBJECT);
        assertThat(testDayOff.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testDayOff.isForced()).isEqualTo(DEFAULT_FORCED);
        assertThat(testDayOff.getEmployeId()).isEqualTo(DEFAULT_EMPLOYE_ID);
        assertThat(testDayOff.getValidatorId()).isEqualTo(DEFAULT_VALIDATOR_ID);
        assertThat(testDayOff.getDays()).isEqualTo(DEFAULT_DAYS);

        // Validate the DayOff in Elasticsearch
        verify(mockDayOffSearchRepository, times(1)).save(testDayOff);
    }

    @Test
    public void createDayOffWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dayOffRepository.findAll().size();

        // Create the DayOff with an existing ID
        dayOff.setId("existing_id");
        DayOffDTO dayOffDTO = dayOffMapper.toDto(dayOff);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDayOffMockMvc.perform(post("/api/day-offs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dayOffDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DayOff in the database
        List<DayOff> dayOffList = dayOffRepository.findAll();
        assertThat(dayOffList).hasSize(databaseSizeBeforeCreate);

        // Validate the DayOff in Elasticsearch
        verify(mockDayOffSearchRepository, times(0)).save(dayOff);
    }


    @Test
    public void checkStartDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = dayOffRepository.findAll().size();
        // set the field null
        dayOff.setStartDate(null);

        // Create the DayOff, which fails.
        DayOffDTO dayOffDTO = dayOffMapper.toDto(dayOff);

        restDayOffMockMvc.perform(post("/api/day-offs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dayOffDTO)))
            .andExpect(status().isBadRequest());

        List<DayOff> dayOffList = dayOffRepository.findAll();
        assertThat(dayOffList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkEndDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = dayOffRepository.findAll().size();
        // set the field null
        dayOff.setEndDate(null);

        // Create the DayOff, which fails.
        DayOffDTO dayOffDTO = dayOffMapper.toDto(dayOff);

        restDayOffMockMvc.perform(post("/api/day-offs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dayOffDTO)))
            .andExpect(status().isBadRequest());

        List<DayOff> dayOffList = dayOffRepository.findAll();
        assertThat(dayOffList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkDayOffObjectIsRequired() throws Exception {
        int databaseSizeBeforeTest = dayOffRepository.findAll().size();
        // set the field null
        dayOff.setDayOffObject(null);

        // Create the DayOff, which fails.
        DayOffDTO dayOffDTO = dayOffMapper.toDto(dayOff);

        restDayOffMockMvc.perform(post("/api/day-offs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dayOffDTO)))
            .andExpect(status().isBadRequest());

        List<DayOff> dayOffList = dayOffRepository.findAll();
        assertThat(dayOffList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkForcedIsRequired() throws Exception {
        int databaseSizeBeforeTest = dayOffRepository.findAll().size();
        // set the field null
        dayOff.setForced(null);

        // Create the DayOff, which fails.
        DayOffDTO dayOffDTO = dayOffMapper.toDto(dayOff);

        restDayOffMockMvc.perform(post("/api/day-offs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dayOffDTO)))
            .andExpect(status().isBadRequest());

        List<DayOff> dayOffList = dayOffRepository.findAll();
        assertThat(dayOffList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkEmployeIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = dayOffRepository.findAll().size();
        // set the field null
        dayOff.setEmployeId(null);

        // Create the DayOff, which fails.
        DayOffDTO dayOffDTO = dayOffMapper.toDto(dayOff);

        restDayOffMockMvc.perform(post("/api/day-offs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dayOffDTO)))
            .andExpect(status().isBadRequest());

        List<DayOff> dayOffList = dayOffRepository.findAll();
        assertThat(dayOffList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkValidatorIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = dayOffRepository.findAll().size();
        // set the field null
        dayOff.setValidatorId(null);

        // Create the DayOff, which fails.
        DayOffDTO dayOffDTO = dayOffMapper.toDto(dayOff);

        restDayOffMockMvc.perform(post("/api/day-offs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dayOffDTO)))
            .andExpect(status().isBadRequest());

        List<DayOff> dayOffList = dayOffRepository.findAll();
        assertThat(dayOffList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkDaysIsRequired() throws Exception {
        int databaseSizeBeforeTest = dayOffRepository.findAll().size();
        // set the field null
        dayOff.setDays(null);

        // Create the DayOff, which fails.
        DayOffDTO dayOffDTO = dayOffMapper.toDto(dayOff);

        restDayOffMockMvc.perform(post("/api/day-offs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dayOffDTO)))
            .andExpect(status().isBadRequest());

        List<DayOff> dayOffList = dayOffRepository.findAll();
        assertThat(dayOffList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllDayOffs() throws Exception {
        // Initialize the database
        dayOffRepository.save(dayOff);

        // Get all the dayOffList
        restDayOffMockMvc.perform(get("/api/day-offs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dayOff.getId())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].dayOffObject").value(hasItem(DEFAULT_DAY_OFF_OBJECT.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].forced").value(hasItem(DEFAULT_FORCED.booleanValue())))
            .andExpect(jsonPath("$.[*].employeId").value(hasItem(DEFAULT_EMPLOYE_ID.toString())))
            .andExpect(jsonPath("$.[*].validatorId").value(hasItem(DEFAULT_VALIDATOR_ID.toString())))
            .andExpect(jsonPath("$.[*].days").value(hasItem(DEFAULT_DAYS.doubleValue())));
    }
    
    @Test
    public void getDayOff() throws Exception {
        // Initialize the database
        dayOffRepository.save(dayOff);

        // Get the dayOff
        restDayOffMockMvc.perform(get("/api/day-offs/{id}", dayOff.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(dayOff.getId()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.dayOffObject").value(DEFAULT_DAY_OFF_OBJECT.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.forced").value(DEFAULT_FORCED.booleanValue()))
            .andExpect(jsonPath("$.employeId").value(DEFAULT_EMPLOYE_ID.toString()))
            .andExpect(jsonPath("$.validatorId").value(DEFAULT_VALIDATOR_ID.toString()))
            .andExpect(jsonPath("$.days").value(DEFAULT_DAYS.doubleValue()));
    }

    @Test
    public void getNonExistingDayOff() throws Exception {
        // Get the dayOff
        restDayOffMockMvc.perform(get("/api/day-offs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateDayOff() throws Exception {
        // Initialize the database
        dayOffRepository.save(dayOff);

        int databaseSizeBeforeUpdate = dayOffRepository.findAll().size();

        // Update the dayOff
        DayOff updatedDayOff = dayOffRepository.findById(dayOff.getId()).get();
        updatedDayOff
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .dayOffObject(UPDATED_DAY_OFF_OBJECT)
            .status(UPDATED_STATUS)
            .forced(UPDATED_FORCED)
            .employeId(UPDATED_EMPLOYE_ID)
            .validatorId(UPDATED_VALIDATOR_ID)
            .days(UPDATED_DAYS);
        DayOffDTO dayOffDTO = dayOffMapper.toDto(updatedDayOff);

        restDayOffMockMvc.perform(put("/api/day-offs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dayOffDTO)))
            .andExpect(status().isOk());

        // Validate the DayOff in the database
        List<DayOff> dayOffList = dayOffRepository.findAll();
        assertThat(dayOffList).hasSize(databaseSizeBeforeUpdate);
        DayOff testDayOff = dayOffList.get(dayOffList.size() - 1);
        assertThat(testDayOff.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testDayOff.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testDayOff.getDayOffObject()).isEqualTo(UPDATED_DAY_OFF_OBJECT);
        assertThat(testDayOff.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testDayOff.isForced()).isEqualTo(UPDATED_FORCED);
        assertThat(testDayOff.getEmployeId()).isEqualTo(UPDATED_EMPLOYE_ID);
        assertThat(testDayOff.getValidatorId()).isEqualTo(UPDATED_VALIDATOR_ID);
        assertThat(testDayOff.getDays()).isEqualTo(UPDATED_DAYS);

        // Validate the DayOff in Elasticsearch
        verify(mockDayOffSearchRepository, times(1)).save(testDayOff);
    }

    @Test
    public void updateNonExistingDayOff() throws Exception {
        int databaseSizeBeforeUpdate = dayOffRepository.findAll().size();

        // Create the DayOff
        DayOffDTO dayOffDTO = dayOffMapper.toDto(dayOff);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDayOffMockMvc.perform(put("/api/day-offs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dayOffDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DayOff in the database
        List<DayOff> dayOffList = dayOffRepository.findAll();
        assertThat(dayOffList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DayOff in Elasticsearch
        verify(mockDayOffSearchRepository, times(0)).save(dayOff);
    }

    @Test
    public void deleteDayOff() throws Exception {
        // Initialize the database
        dayOffRepository.save(dayOff);

        int databaseSizeBeforeDelete = dayOffRepository.findAll().size();

        // Delete the dayOff
        restDayOffMockMvc.perform(delete("/api/day-offs/{id}", dayOff.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DayOff> dayOffList = dayOffRepository.findAll();
        assertThat(dayOffList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the DayOff in Elasticsearch
        verify(mockDayOffSearchRepository, times(1)).deleteById(dayOff.getId());
    }

    @Test
    public void searchDayOff() throws Exception {
        // Initialize the database
        dayOffRepository.save(dayOff);
        when(mockDayOffSearchRepository.search(queryStringQuery("id:" + dayOff.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(dayOff), PageRequest.of(0, 1), 1));
        // Search the dayOff
        restDayOffMockMvc.perform(get("/api/_search/day-offs?query=id:" + dayOff.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dayOff.getId())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].dayOffObject").value(hasItem(DEFAULT_DAY_OFF_OBJECT)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].forced").value(hasItem(DEFAULT_FORCED.booleanValue())))
            .andExpect(jsonPath("$.[*].employeId").value(hasItem(DEFAULT_EMPLOYE_ID)))
            .andExpect(jsonPath("$.[*].validatorId").value(hasItem(DEFAULT_VALIDATOR_ID)))
            .andExpect(jsonPath("$.[*].days").value(hasItem(DEFAULT_DAYS.doubleValue())));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DayOff.class);
        DayOff dayOff1 = new DayOff();
        dayOff1.setId("id1");
        DayOff dayOff2 = new DayOff();
        dayOff2.setId(dayOff1.getId());
        assertThat(dayOff1).isEqualTo(dayOff2);
        dayOff2.setId("id2");
        assertThat(dayOff1).isNotEqualTo(dayOff2);
        dayOff1.setId(null);
        assertThat(dayOff1).isNotEqualTo(dayOff2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DayOffDTO.class);
        DayOffDTO dayOffDTO1 = new DayOffDTO();
        dayOffDTO1.setId("id1");
        DayOffDTO dayOffDTO2 = new DayOffDTO();
        assertThat(dayOffDTO1).isNotEqualTo(dayOffDTO2);
        dayOffDTO2.setId(dayOffDTO1.getId());
        assertThat(dayOffDTO1).isEqualTo(dayOffDTO2);
        dayOffDTO2.setId("id2");
        assertThat(dayOffDTO1).isNotEqualTo(dayOffDTO2);
        dayOffDTO1.setId(null);
        assertThat(dayOffDTO1).isNotEqualTo(dayOffDTO2);
    }
}
