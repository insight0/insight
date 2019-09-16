package com.apeiron.insight.service.mapper;

import com.apeiron.insight.domain.*;
import com.apeiron.insight.service.dto.FunctionalRoleDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link FunctionalRole} and its DTO {@link FunctionalRoleDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface FunctionalRoleMapper extends EntityMapper<FunctionalRoleDTO, FunctionalRole> {



    default FunctionalRole fromId(String id) {
        if (id == null) {
            return null;
        }
        FunctionalRole functionalRole = new FunctionalRole();
        functionalRole.setId(id);
        return functionalRole;
    }
}
