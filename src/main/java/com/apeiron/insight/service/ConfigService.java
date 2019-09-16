package com.apeiron.insight.service;

import com.apeiron.insight.service.dto.ConfigDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.apeiron.insight.domain.Config}.
 */
public interface ConfigService {

    /**
     * Save a config.
     *
     * @param configDTO the entity to save.
     * @return the persisted entity.
     */
    ConfigDTO save(ConfigDTO configDTO);


    /**
     * Get the "id" config.
     *
     * @return the entity.
     */
    Optional<ConfigDTO> findOne();

}
