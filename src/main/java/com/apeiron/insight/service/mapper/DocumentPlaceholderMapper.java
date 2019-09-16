package com.apeiron.insight.service.mapper;

import com.apeiron.insight.domain.*;
import com.apeiron.insight.service.dto.DocumentPlaceholderDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link DocumentPlaceholder} and its DTO {@link DocumentPlaceholderDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DocumentPlaceholderMapper extends EntityMapper<DocumentPlaceholderDTO, DocumentPlaceholder> {



    default DocumentPlaceholder fromId(String id) {
        if (id == null) {
            return null;
        }
        DocumentPlaceholder documentPlaceholder = new DocumentPlaceholder();
        return documentPlaceholder;
    }
}
