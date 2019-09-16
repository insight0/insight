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
     * Get all the configs.
     *
     * @return the list of entities.
     */
    List<ConfigDTO> findAll();


    /**
     * Get the "id" config.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ConfigDTO> findOne(String id);

    /**
     * Delete the "id" config.
     *
     * @param id the id of the entity.
     */
    void delete(String id);

    /**
     * Search for the config corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<ConfigDTO> search(String query);
}
