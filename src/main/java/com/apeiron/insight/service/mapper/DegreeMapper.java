package com.apeiron.insight.service.mapper;

import com.apeiron.insight.domain.*;
import com.apeiron.insight.service.dto.DegreeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Degree} and its DTO {@link DegreeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DegreeMapper extends EntityMapper<DegreeDTO, Degree> {



    default Degree fromId(String id) {
        if (id == null) {
            return null;
        }
        Degree degree = new Degree();
        degree.setId(id);
        return degree;
    }
}
