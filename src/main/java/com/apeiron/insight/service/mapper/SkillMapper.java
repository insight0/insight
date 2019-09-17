package com.apeiron.insight.service.mapper;

import com.apeiron.insight.domain.*;
import com.apeiron.insight.service.dto.SkillDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Skill} and its DTO {@link SkillDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SkillMapper extends EntityMapper<SkillDTO, Skill> {



    default Skill fromId(String id) {
        if (id == null) {
            return null;
        }
        Skill skill = new Skill();
        skill.setId(id);
        return skill;
    }
}
