package com.apeiron.insight.service.mapper;

import com.apeiron.insight.domain.*;
import com.apeiron.insight.service.dto.DayOffDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link DayOff} and its DTO {@link DayOffDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DayOffMapper extends EntityMapper<DayOffDTO, DayOff> {



    default DayOff fromId(String id) {
        if (id == null) {
            return null;
        }
        DayOff dayOff = new DayOff();
        dayOff.setId(id);
        return dayOff;
    }
}
