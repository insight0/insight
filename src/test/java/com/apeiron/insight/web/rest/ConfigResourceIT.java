package com.apeiron.insight.web.rest;

import com.apeiron.insight.InsightApp;
import com.apeiron.insight.domain.Config;
import com.apeiron.insight.repository.ConfigRepository;
import com.apeiron.insight.repository.search.ConfigSearchRepository;
import com.apeiron.insight.service.ConfigService;
import com.apeiron.insight.service.dto.ConfigDTO;
import com.apeiron.insight.service.mapper.ConfigMapper;
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
 * Integration tests for the {@link ConfigResource} REST controller.
 */
@SpringBootTest(classes = InsightApp.class)
public class ConfigResourceIT {

    private static final Integer DEFAULT_HOLIDAY_EMAIL_SEND_DATE = 1;
    private static final Integer UPDATED_HOLIDAY_EMAIL_SEND_DATE = 2;
    private static final Integer SMALLER_HOLIDAY_EMAIL_SEND_DATE = 1 - 1;

    private static final Boolean DEFAULT_HOLIDAY_EMAIL_NOTIFICATION = false;
    private static final Boolean UPDATED_HOLIDAY_EMAIL_NOTIFICATION = true;

    private static final Boolean DEFAULT_WELCOMING_EMAIL_NOTIFICATION = false;
    private static final Boolean UPDATED_WELCOMING_EMAIL_NOTIFICATION = true;

    private static final Boolean DEFAULT_BIRTHDAY_EMAIL_NOTIFICATION = false;
    private static final Boolean UPDATED_BIRTHDAY_EMAIL_NOTIFICATION = true;

    @Autowired
    private ConfigRepository configRepository;

    @Autowired
    private ConfigMapper configMapper;

    @Autowired
    private ConfigService configService;

    /**
     * This repository is mocked in the com.apeiron.insight.repository.search test package.
     *
     * @see com.apeiron.insight.repository.search.ConfigSearchRepositoryMockConfiguration
     */
    @Autowired
    private ConfigSearchRepository mockConfigSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restConfigMockMvc;

    private Config config;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ConfigResource configResource = new ConfigResource(configService);
        this.restConfigMockMvc = MockMvcBuilders.standaloneSetup(configResource)
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
    public static Config createEntity() {
        Config config = new Config()
            .holidayEmailSendDate(DEFAULT_HOLIDAY_EMAIL_SEND_DATE)
            .holidayEmailNotification(DEFAULT_HOLIDAY_EMAIL_NOTIFICATION)
            .welcomingEmailNotification(DEFAULT_WELCOMING_EMAIL_NOTIFICATION)
            .birthdayEmailNotification(DEFAULT_BIRTHDAY_EMAIL_NOTIFICATION);
        return config;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Config createUpdatedEntity() {
        Config config = new Config()
            .holidayEmailSendDate(UPDATED_HOLIDAY_EMAIL_SEND_DATE)
            .holidayEmailNotification(UPDATED_HOLIDAY_EMAIL_NOTIFICATION)
            .welcomingEmailNotification(UPDATED_WELCOMING_EMAIL_NOTIFICATION)
            .birthdayEmailNotification(UPDATED_BIRTHDAY_EMAIL_NOTIFICATION);
        return config;
    }

    @BeforeEach
    public void initTest() {
        configRepository.deleteAll();
        config = createEntity();
    }

    @Test
    public void createConfig() throws Exception {
        int databaseSizeBeforeCreate = configRepository.findAll().size();

        // Create the Config
        ConfigDTO configDTO = configMapper.toDto(config);
        restConfigMockMvc.perform(post("/api/configs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(configDTO)))
            .andExpect(status().isCreated());

        // Validate the Config in the database
        List<Config> configList = configRepository.findAll();
        assertThat(configList).hasSize(databaseSizeBeforeCreate + 1);
        Config testConfig = configList.get(configList.size() - 1);
        assertThat(testConfig.getHolidayEmailSendDate()).isEqualTo(DEFAULT_HOLIDAY_EMAIL_SEND_DATE);
        assertThat(testConfig.isHolidayEmailNotification()).isEqualTo(DEFAULT_HOLIDAY_EMAIL_NOTIFICATION);
        assertThat(testConfig.isWelcomingEmailNotification()).isEqualTo(DEFAULT_WELCOMING_EMAIL_NOTIFICATION);
        assertThat(testConfig.isBirthdayEmailNotification()).isEqualTo(DEFAULT_BIRTHDAY_EMAIL_NOTIFICATION);

        // Validate the Config in Elasticsearch
        verify(mockConfigSearchRepository, times(1)).save(testConfig);
    }

    @Test
    public void createConfigWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = configRepository.findAll().size();

        // Create the Config with an existing ID
        config.setId("existing_id");
        ConfigDTO configDTO = configMapper.toDto(config);

        // An entity with an existing ID cannot be created, so this API call must fail
        restConfigMockMvc.perform(post("/api/configs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(configDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Config in the database
        List<Config> configList = configRepository.findAll();
        assertThat(configList).hasSize(databaseSizeBeforeCreate);

        // Validate the Config in Elasticsearch
        verify(mockConfigSearchRepository, times(0)).save(config);
    }


    @Test
    public void checkHolidayEmailNotificationIsRequired() throws Exception {
        int databaseSizeBeforeTest = configRepository.findAll().size();
        // set the field null
        config.setHolidayEmailNotification(null);

        // Create the Config, which fails.
        ConfigDTO configDTO = configMapper.toDto(config);

        restConfigMockMvc.perform(post("/api/configs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(configDTO)))
            .andExpect(status().isBadRequest());

        List<Config> configList = configRepository.findAll();
        assertThat(configList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkWelcomingEmailNotificationIsRequired() throws Exception {
        int databaseSizeBeforeTest = configRepository.findAll().size();
        // set the field null
        config.setWelcomingEmailNotification(null);

        // Create the Config, which fails.
        ConfigDTO configDTO = configMapper.toDto(config);

        restConfigMockMvc.perform(post("/api/configs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(configDTO)))
            .andExpect(status().isBadRequest());

        List<Config> configList = configRepository.findAll();
        assertThat(configList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkBirthdayEmailNotificationIsRequired() throws Exception {
        int databaseSizeBeforeTest = configRepository.findAll().size();
        // set the field null
        config.setBirthdayEmailNotification(null);

        // Create the Config, which fails.
        ConfigDTO configDTO = configMapper.toDto(config);

        restConfigMockMvc.perform(post("/api/configs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(configDTO)))
            .andExpect(status().isBadRequest());

        List<Config> configList = configRepository.findAll();
        assertThat(configList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllConfigs() throws Exception {
        // Initialize the database
        configRepository.save(config);

        // Get all the configList
        restConfigMockMvc.perform(get("/api/configs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(config.getId())))
            .andExpect(jsonPath("$.[*].holidayEmailSendDate").value(hasItem(DEFAULT_HOLIDAY_EMAIL_SEND_DATE)))
            .andExpect(jsonPath("$.[*].holidayEmailNotification").value(hasItem(DEFAULT_HOLIDAY_EMAIL_NOTIFICATION.booleanValue())))
            .andExpect(jsonPath("$.[*].welcomingEmailNotification").value(hasItem(DEFAULT_WELCOMING_EMAIL_NOTIFICATION.booleanValue())))
            .andExpect(jsonPath("$.[*].birthdayEmailNotification").value(hasItem(DEFAULT_BIRTHDAY_EMAIL_NOTIFICATION.booleanValue())));
    }
    
    @Test
    public void getConfig() throws Exception {
        // Initialize the database
        configRepository.save(config);

        // Get the config
        restConfigMockMvc.perform(get("/api/configs/{id}", config.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(config.getId()))
            .andExpect(jsonPath("$.holidayEmailSendDate").value(DEFAULT_HOLIDAY_EMAIL_SEND_DATE))
            .andExpect(jsonPath("$.holidayEmailNotification").value(DEFAULT_HOLIDAY_EMAIL_NOTIFICATION.booleanValue()))
            .andExpect(jsonPath("$.welcomingEmailNotification").value(DEFAULT_WELCOMING_EMAIL_NOTIFICATION.booleanValue()))
            .andExpect(jsonPath("$.birthdayEmailNotification").value(DEFAULT_BIRTHDAY_EMAIL_NOTIFICATION.booleanValue()));
    }

    @Test
    public void getNonExistingConfig() throws Exception {
        // Get the config
        restConfigMockMvc.perform(get("/api/configs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateConfig() throws Exception {
        // Initialize the database
        configRepository.save(config);

        int databaseSizeBeforeUpdate = configRepository.findAll().size();

        // Update the config
        Config updatedConfig = configRepository.findById(config.getId()).get();
        updatedConfig
            .holidayEmailSendDate(UPDATED_HOLIDAY_EMAIL_SEND_DATE)
            .holidayEmailNotification(UPDATED_HOLIDAY_EMAIL_NOTIFICATION)
            .welcomingEmailNotification(UPDATED_WELCOMING_EMAIL_NOTIFICATION)
            .birthdayEmailNotification(UPDATED_BIRTHDAY_EMAIL_NOTIFICATION);
        ConfigDTO configDTO = configMapper.toDto(updatedConfig);

        restConfigMockMvc.perform(put("/api/configs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(configDTO)))
            .andExpect(status().isOk());

        // Validate the Config in the database
        List<Config> configList = configRepository.findAll();
        assertThat(configList).hasSize(databaseSizeBeforeUpdate);
        Config testConfig = configList.get(configList.size() - 1);
        assertThat(testConfig.getHolidayEmailSendDate()).isEqualTo(UPDATED_HOLIDAY_EMAIL_SEND_DATE);
        assertThat(testConfig.isHolidayEmailNotification()).isEqualTo(UPDATED_HOLIDAY_EMAIL_NOTIFICATION);
        assertThat(testConfig.isWelcomingEmailNotification()).isEqualTo(UPDATED_WELCOMING_EMAIL_NOTIFICATION);
        assertThat(testConfig.isBirthdayEmailNotification()).isEqualTo(UPDATED_BIRTHDAY_EMAIL_NOTIFICATION);

        // Validate the Config in Elasticsearch
        verify(mockConfigSearchRepository, times(1)).save(testConfig);
    }

    @Test
    public void updateNonExistingConfig() throws Exception {
        int databaseSizeBeforeUpdate = configRepository.findAll().size();

        // Create the Config
        ConfigDTO configDTO = configMapper.toDto(config);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConfigMockMvc.perform(put("/api/configs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(configDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Config in the database
        List<Config> configList = configRepository.findAll();
        assertThat(configList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Config in Elasticsearch
        verify(mockConfigSearchRepository, times(0)).save(config);
    }

    @Test
    public void deleteConfig() throws Exception {
        // Initialize the database
        configRepository.save(config);

        int databaseSizeBeforeDelete = configRepository.findAll().size();

        // Delete the config
        restConfigMockMvc.perform(delete("/api/configs/{id}", config.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Config> configList = configRepository.findAll();
        assertThat(configList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Config in Elasticsearch
        verify(mockConfigSearchRepository, times(1)).deleteById(config.getId());
    }

    @Test
    public void searchConfig() throws Exception {
        // Initialize the database
        configRepository.save(config);
        when(mockConfigSearchRepository.search(queryStringQuery("id:" + config.getId())))
            .thenReturn(Collections.singletonList(config));
        // Search the config
        restConfigMockMvc.perform(get("/api/_search/configs?query=id:" + config.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(config.getId())))
            .andExpect(jsonPath("$.[*].holidayEmailSendDate").value(hasItem(DEFAULT_HOLIDAY_EMAIL_SEND_DATE)))
            .andExpect(jsonPath("$.[*].holidayEmailNotification").value(hasItem(DEFAULT_HOLIDAY_EMAIL_NOTIFICATION.booleanValue())))
            .andExpect(jsonPath("$.[*].welcomingEmailNotification").value(hasItem(DEFAULT_WELCOMING_EMAIL_NOTIFICATION.booleanValue())))
            .andExpect(jsonPath("$.[*].birthdayEmailNotification").value(hasItem(DEFAULT_BIRTHDAY_EMAIL_NOTIFICATION.booleanValue())));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Config.class);
        Config config1 = new Config();
        config1.setId("id1");
        Config config2 = new Config();
        config2.setId(config1.getId());
        assertThat(config1).isEqualTo(config2);
        config2.setId("id2");
        assertThat(config1).isNotEqualTo(config2);
        config1.setId(null);
        assertThat(config1).isNotEqualTo(config2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConfigDTO.class);
        ConfigDTO configDTO1 = new ConfigDTO();
        configDTO1.setId("id1");
        ConfigDTO configDTO2 = new ConfigDTO();
        assertThat(configDTO1).isNotEqualTo(configDTO2);
        configDTO2.setId(configDTO1.getId());
        assertThat(configDTO1).isEqualTo(configDTO2);
        configDTO2.setId("id2");
        assertThat(configDTO1).isNotEqualTo(configDTO2);
        configDTO1.setId(null);
        assertThat(configDTO1).isNotEqualTo(configDTO2);
    }
}
