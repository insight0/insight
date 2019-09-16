package com.apeiron.insight.service.impl;

import com.apeiron.insight.domain.Config;
import com.apeiron.insight.service.EmailTemplateService;
import com.apeiron.insight.domain.EmailTemplate;
import com.apeiron.insight.repository.EmailTemplateRepository;
import com.apeiron.insight.repository.search.EmailTemplateSearchRepository;
import com.apeiron.insight.service.dto.EmailTemplateDTO;
import com.apeiron.insight.service.mapper.EmailTemplateMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link EmailTemplate}.
 */
@Service
public class EmailTemplateServiceImpl implements EmailTemplateService {

    private final Logger log = LoggerFactory.getLogger(EmailTemplateServiceImpl.class);

    private final EmailTemplateRepository emailTemplateRepository;

    private final EmailTemplateMapper emailTemplateMapper;

    private final EmailTemplateSearchRepository emailTemplateSearchRepository;

    public EmailTemplateServiceImpl(EmailTemplateRepository emailTemplateRepository, EmailTemplateMapper emailTemplateMapper, EmailTemplateSearchRepository emailTemplateSearchRepository) {
        this.emailTemplateRepository = emailTemplateRepository;
        this.emailTemplateMapper = emailTemplateMapper;
        this.emailTemplateSearchRepository = emailTemplateSearchRepository;
    }

    /**
     * Save a emailTemplate.
     *
     * @param emailTemplateDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public EmailTemplateDTO save(EmailTemplateDTO emailTemplateDTO) {
        log.debug("Request to save EmailTemplate : {}", emailTemplateDTO);
        EmailTemplate emailTemplate = emailTemplateMapper.toEntity(emailTemplateDTO);
        emailTemplate = emailTemplateRepository.save(emailTemplate);
        EmailTemplateDTO result = emailTemplateMapper.toDto(emailTemplate);
        return result;
    }



    /**
     * Get one emailTemplate by id.
     *
     * @return the entity.
     */
    @Override
    public Optional<EmailTemplateDTO> findOne() {
        log.debug("Request to get EmailTemplate : {}", "DEFAULT_CONFIG");

        Optional<EmailTemplate> default_config = emailTemplateRepository.findById("DEFAULT_CONFIG");
        if(!default_config.isPresent()){
            EmailTemplate config = new EmailTemplate();
            config.setId("DEFAULT_CONFIG");
            emailTemplateRepository.save(config);
        }

        return emailTemplateRepository.findById("DEFAULT_CONFIG")
            .map(emailTemplateMapper::toDto);
    }

}
