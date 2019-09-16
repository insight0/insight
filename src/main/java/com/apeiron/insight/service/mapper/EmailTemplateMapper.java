package com.apeiron.insight.service.mapper;

import com.apeiron.insight.domain.*;
import com.apeiron.insight.service.dto.EmailTemplateDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link EmailTemplate} and its DTO {@link EmailTemplateDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EmailTemplateMapper extends EntityMapper<EmailTemplateDTO, EmailTemplate> {



    default EmailTemplate fromId(String id) {
        if (id == null) {
            return null;
        }
        EmailTemplate emailTemplate = new EmailTemplate();
        emailTemplate.setId(id);
        return emailTemplate;
    }
}
