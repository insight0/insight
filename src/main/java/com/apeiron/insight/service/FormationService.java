package com.apeiron.insight.service;

import com.apeiron.insight.domain.Formation;
import com.apeiron.insight.repository.FormationRepository;
import com.apeiron.insight.service.dto.EmailTemplateDTO;
import com.apeiron.insight.service.dto.FunctionalRoleDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.apeiron.insight.domain.Formation}.
 */
public interface FormationService {

    /**
     * Save a formation
     *
     * @param formation the entity to save.
     * @return the persisted entity.
     */
    Formation save(Formation formation);

    /**
     * Update a formation
     *
     * @param formation the entity to update.
     * @return the persisted entity.
     */
    Formation update(Formation formation);

    /**
     * Get the "id" formation.
     *
     * @return the entity.
     */
    Optional<Formation> findOne(String id);

    /**
     * Get all the formations .
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Formation> findAll(Pageable pageable);


    /**
     * Delete the "id" formation.
     *
     * @param id the id of the entity.
     */
    void delete(String id);

}
