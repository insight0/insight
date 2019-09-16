package com.apeiron.insight.service.impl;

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
        emailTemplateSearchRepository.save(emailTemplate);
        return result;
    }

    /**
     * Get all the emailTemplates.
     *
     * @return the list of entities.
     */
    @Override
    public List<EmailTemplateDTO> findAll() {
        log.debug("Request to get all EmailTemplates");
        return emailTemplateRepository.findAll().stream()
            .map(emailTemplateMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one emailTemplate by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    public Optional<EmailTemplateDTO> findOne(String id) {
        log.debug("Request to get EmailTemplate : {}", id);
        return emailTemplateRepository.findById(id)
            .map(emailTemplateMapper::toDto);
    }

    /**
     * Delete the emailTemplate by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete EmailTemplate : {}", id);
        emailTemplateRepository.deleteById(id);
        emailTemplateSearchRepository.deleteById(id);
    }

    /**
     * Search for the emailTemplate corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    public List<EmailTemplateDTO> search(String query) {
        log.debug("Request to search EmailTemplates for query {}", query);
        return StreamSupport
            .stream(emailTemplateSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(emailTemplateMapper::toDto)
            .collect(Collectors.toList());
    }
}
