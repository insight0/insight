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
     * Get the "id" emailTemplate.
     *
     * @return the entity.
     */
    Optional<EmailTemplateDTO> findOne();

}
