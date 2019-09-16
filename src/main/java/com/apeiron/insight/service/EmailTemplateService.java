package com.apeiron.insight.service;

import com.apeiron.insight.service.dto.EmailTemplateDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.apeiron.insight.domain.EmailTemplate}.
 */
public interface EmailTemplateService {

    /**
     * Save a emailTemplate.
     *
     * @param emailTemplateDTO the entity to save.
     * @return the persisted entity.
     */
    EmailTemplateDTO save(EmailTemplateDTO emailTemplateDTO);

    /**
     * Get all the emailTemplates.
     *
     * @return the list of entities.
     */
    List<EmailTemplateDTO> findAll();


    /**
     * Get the "id" emailTemplate.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EmailTemplateDTO> findOne(String id);

    /**
     * Delete the "id" emailTemplate.
     *
     * @param id the id of the entity.
     */
    void delete(String id);

    /**
     * Search for the emailTemplate corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<EmailTemplateDTO> search(String query);
}
