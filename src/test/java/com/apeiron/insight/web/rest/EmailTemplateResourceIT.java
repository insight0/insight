package com.apeiron.insight.web.rest;

import com.apeiron.insight.InsightApp;
import com.apeiron.insight.domain.EmailTemplate;
import com.apeiron.insight.repository.EmailTemplateRepository;
import com.apeiron.insight.repository.search.EmailTemplateSearchRepository;
import com.apeiron.insight.service.EmailTemplateService;
import com.apeiron.insight.service.dto.EmailTemplateDTO;
import com.apeiron.insight.service.mapper.EmailTemplateMapper;
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
 * Integration tests for the {@link EmailTemplateResource} REST controller.
 */
@SpringBootTest(classes = InsightApp.class)
public class EmailTemplateResourceIT {

    private static final String DEFAULT_HOLIDAY_EMAIL_TEMPLATE = "AAAAAAAAAA";
    private static final String UPDATED_HOLIDAY_EMAIL_TEMPLATE = "BBBBBBBBBB";

    @Autowired
    private EmailTemplateRepository emailTemplateRepository;

    @Autowired
    private EmailTemplateMapper emailTemplateMapper;

    @Autowired
    private EmailTemplateService emailTemplateService;

    /**
     * This repository is mocked in the com.apeiron.insight.repository.search test package.
     *
     * @see com.apeiron.insight.repository.search.EmailTemplateSearchRepositoryMockConfiguration
     */
    @Autowired
    private EmailTemplateSearchRepository mockEmailTemplateSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restEmailTemplateMockMvc;

    private EmailTemplate emailTemplate;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EmailTemplateResource emailTemplateResource = new EmailTemplateResource(emailTemplateService);
        this.restEmailTemplateMockMvc = MockMvcBuilders.standaloneSetup(emailTemplateResource)
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
    public static EmailTemplate createEntity() {
        EmailTemplate emailTemplate = new EmailTemplate()
            .holidayEmailTemplate(DEFAULT_HOLIDAY_EMAIL_TEMPLATE);
        return emailTemplate;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmailTemplate createUpdatedEntity() {
        EmailTemplate emailTemplate = new EmailTemplate()
            .holidayEmailTemplate(UPDATED_HOLIDAY_EMAIL_TEMPLATE);
        return emailTemplate;
    }

    @BeforeEach
    public void initTest() {
        emailTemplateRepository.deleteAll();
        emailTemplate = createEntity();
    }

    @Test
    public void createEmailTemplate() throws Exception {
        int databaseSizeBeforeCreate = emailTemplateRepository.findAll().size();

        // Create the EmailTemplate
        EmailTemplateDTO emailTemplateDTO = emailTemplateMapper.toDto(emailTemplate);
        restEmailTemplateMockMvc.perform(post("/api/email-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(emailTemplateDTO)))
            .andExpect(status().isCreated());

        // Validate the EmailTemplate in the database
        List<EmailTemplate> emailTemplateList = emailTemplateRepository.findAll();
        assertThat(emailTemplateList).hasSize(databaseSizeBeforeCreate + 1);
        EmailTemplate testEmailTemplate = emailTemplateList.get(emailTemplateList.size() - 1);
        assertThat(testEmailTemplate.getHolidayEmailTemplate()).isEqualTo(DEFAULT_HOLIDAY_EMAIL_TEMPLATE);

        // Validate the EmailTemplate in Elasticsearch
        verify(mockEmailTemplateSearchRepository, times(1)).save(testEmailTemplate);
    }

    @Test
    public void createEmailTemplateWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = emailTemplateRepository.findAll().size();

        // Create the EmailTemplate with an existing ID
        emailTemplate.setId("existing_id");
        EmailTemplateDTO emailTemplateDTO = emailTemplateMapper.toDto(emailTemplate);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmailTemplateMockMvc.perform(post("/api/email-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(emailTemplateDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EmailTemplate in the database
        List<EmailTemplate> emailTemplateList = emailTemplateRepository.findAll();
        assertThat(emailTemplateList).hasSize(databaseSizeBeforeCreate);

        // Validate the EmailTemplate in Elasticsearch
        verify(mockEmailTemplateSearchRepository, times(0)).save(emailTemplate);
    }


    @Test
    public void getAllEmailTemplates() throws Exception {
        // Initialize the database
        emailTemplateRepository.save(emailTemplate);

        // Get all the emailTemplateList
        restEmailTemplateMockMvc.perform(get("/api/email-templates?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(emailTemplate.getId())))
            .andExpect(jsonPath("$.[*].holidayEmailTemplate").value(hasItem(DEFAULT_HOLIDAY_EMAIL_TEMPLATE.toString())));
    }
    
    @Test
    public void getEmailTemplate() throws Exception {
        // Initialize the database
        emailTemplateRepository.save(emailTemplate);

        // Get the emailTemplate
        restEmailTemplateMockMvc.perform(get("/api/email-templates/{id}", emailTemplate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(emailTemplate.getId()))
            .andExpect(jsonPath("$.holidayEmailTemplate").value(DEFAULT_HOLIDAY_EMAIL_TEMPLATE.toString()));
    }

    @Test
    public void getNonExistingEmailTemplate() throws Exception {
        // Get the emailTemplate
        restEmailTemplateMockMvc.perform(get("/api/email-templates/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateEmailTemplate() throws Exception {
        // Initialize the database
        emailTemplateRepository.save(emailTemplate);

        int databaseSizeBeforeUpdate = emailTemplateRepository.findAll().size();

        // Update the emailTemplate
        EmailTemplate updatedEmailTemplate = emailTemplateRepository.findById(emailTemplate.getId()).get();
        updatedEmailTemplate
            .holidayEmailTemplate(UPDATED_HOLIDAY_EMAIL_TEMPLATE);
        EmailTemplateDTO emailTemplateDTO = emailTemplateMapper.toDto(updatedEmailTemplate);

        restEmailTemplateMockMvc.perform(put("/api/email-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(emailTemplateDTO)))
            .andExpect(status().isOk());

        // Validate the EmailTemplate in the database
        List<EmailTemplate> emailTemplateList = emailTemplateRepository.findAll();
        assertThat(emailTemplateList).hasSize(databaseSizeBeforeUpdate);
        EmailTemplate testEmailTemplate = emailTemplateList.get(emailTemplateList.size() - 1);
        assertThat(testEmailTemplate.getHolidayEmailTemplate()).isEqualTo(UPDATED_HOLIDAY_EMAIL_TEMPLATE);

        // Validate the EmailTemplate in Elasticsearch
        verify(mockEmailTemplateSearchRepository, times(1)).save(testEmailTemplate);
    }

    @Test
    public void updateNonExistingEmailTemplate() throws Exception {
        int databaseSizeBeforeUpdate = emailTemplateRepository.findAll().size();

        // Create the EmailTemplate
        EmailTemplateDTO emailTemplateDTO = emailTemplateMapper.toDto(emailTemplate);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmailTemplateMockMvc.perform(put("/api/email-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(emailTemplateDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EmailTemplate in the database
        List<EmailTemplate> emailTemplateList = emailTemplateRepository.findAll();
        assertThat(emailTemplateList).hasSize(databaseSizeBeforeUpdate);

        // Validate the EmailTemplate in Elasticsearch
        verify(mockEmailTemplateSearchRepository, times(0)).save(emailTemplate);
    }

    @Test
    public void deleteEmailTemplate() throws Exception {
        // Initialize the database
        emailTemplateRepository.save(emailTemplate);

        int databaseSizeBeforeDelete = emailTemplateRepository.findAll().size();

        // Delete the emailTemplate
        restEmailTemplateMockMvc.perform(delete("/api/email-templates/{id}", emailTemplate.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EmailTemplate> emailTemplateList = emailTemplateRepository.findAll();
        assertThat(emailTemplateList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the EmailTemplate in Elasticsearch
        verify(mockEmailTemplateSearchRepository, times(1)).deleteById(emailTemplate.getId());
    }

    @Test
    public void searchEmailTemplate() throws Exception {
        // Initialize the database
        emailTemplateRepository.save(emailTemplate);
        when(mockEmailTemplateSearchRepository.search(queryStringQuery("id:" + emailTemplate.getId())))
            .thenReturn(Collections.singletonList(emailTemplate));
        // Search the emailTemplate
        restEmailTemplateMockMvc.perform(get("/api/_search/email-templates?query=id:" + emailTemplate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(emailTemplate.getId())))
            .andExpect(jsonPath("$.[*].holidayEmailTemplate").value(hasItem(DEFAULT_HOLIDAY_EMAIL_TEMPLATE)));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmailTemplate.class);
        EmailTemplate emailTemplate1 = new EmailTemplate();
        emailTemplate1.setId("id1");
        EmailTemplate emailTemplate2 = new EmailTemplate();
        emailTemplate2.setId(emailTemplate1.getId());
        assertThat(emailTemplate1).isEqualTo(emailTemplate2);
        emailTemplate2.setId("id2");
        assertThat(emailTemplate1).isNotEqualTo(emailTemplate2);
        emailTemplate1.setId(null);
        assertThat(emailTemplate1).isNotEqualTo(emailTemplate2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmailTemplateDTO.class);
        EmailTemplateDTO emailTemplateDTO1 = new EmailTemplateDTO();
        emailTemplateDTO1.setId("id1");
        EmailTemplateDTO emailTemplateDTO2 = new EmailTemplateDTO();
        assertThat(emailTemplateDTO1).isNotEqualTo(emailTemplateDTO2);
        emailTemplateDTO2.setId(emailTemplateDTO1.getId());
        assertThat(emailTemplateDTO1).isEqualTo(emailTemplateDTO2);
        emailTemplateDTO2.setId("id2");
        assertThat(emailTemplateDTO1).isNotEqualTo(emailTemplateDTO2);
        emailTemplateDTO1.setId(null);
        assertThat(emailTemplateDTO1).isNotEqualTo(emailTemplateDTO2);
    }
}
