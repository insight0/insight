package com.apeiron.insight.service;

import com.apeiron.insight.service.dto.FunctionalRoleDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.apeiron.insight.domain.FunctionalRole}.
 */
public interface FunctionalRoleService {

    /**
     * Save a functionalRole.
     *
     * @param functionalRoleDTO the entity to save.
     * @return the persisted entity.
     */
    FunctionalRoleDTO save(FunctionalRoleDTO functionalRoleDTO);

    /**
     * Get all the functionalRoles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FunctionalRoleDTO> findAll(Pageable pageable);


    /**
     * Get the "id" functionalRole.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FunctionalRoleDTO> findOne(String id);

    /**
     * Delete the "id" functionalRole.
     *
     * @param id the id of the entity.
     */
    void delete(String id);

}
