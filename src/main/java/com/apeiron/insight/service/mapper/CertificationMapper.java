package com.apeiron.insight.service.mapper;

import com.apeiron.insight.domain.*;
import com.apeiron.insight.service.dto.CertificationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Certification} and its DTO {@link CertificationDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CertificationMapper extends EntityMapper<CertificationDTO, Certification> {



    default Certification fromId(String id) {
        if (id == null) {
            return null;
        }
        Certification certification = new Certification();
        certification.setId(id);
        return certification;
    }
}
