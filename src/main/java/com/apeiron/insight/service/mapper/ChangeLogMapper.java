package com.apeiron.insight.service.mapper;

import com.apeiron.insight.domain.*;
import com.apeiron.insight.service.dto.ChangeLogDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ChangeLog} and its DTO {@link ChangeLogDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ChangeLogMapper extends EntityMapper<ChangeLogDTO, ChangeLog> {



    default ChangeLog fromId(String id) {
        if (id == null) {
            return null;
        }
        ChangeLog changeLog = new ChangeLog();
        return changeLog;
    }
}
