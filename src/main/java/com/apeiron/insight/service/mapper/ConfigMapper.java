package com.apeiron.insight.service.mapper;

import com.apeiron.insight.domain.*;
import com.apeiron.insight.service.dto.ConfigDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Config} and its DTO {@link ConfigDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ConfigMapper extends EntityMapper<ConfigDTO, Config> {



    default Config fromId(String id) {
        if (id == null) {
            return null;
        }
        Config config = new Config();
        config.setId(id);
        return config;
    }
}
