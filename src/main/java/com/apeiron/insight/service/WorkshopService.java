package com.apeiron.insight.service;

import com.apeiron.insight.domain.Workshop;
import com.apeiron.insight.service.dto.FunctionalRoleDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface WorkshopService {

    /**
     * Save a workshop.
     *
     * @param workshop the entity to save.
     * @return the persisted entity.
     */
    Workshop save(Workshop workshop);


    /**
     * Update a workshop.
     *
     * @param workshop the entity to save.
     * @return the persisted entity.
     */
    Workshop update(Workshop workshop);

    /**
     * Get all the workshops.
     *
     * @return the list of entities.
     */
    List<Workshop> findAll();


    /**
     * Get the "id" workshop.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Workshop> findOne(String id);

    /**
     * Delete the "id" workshop.
     *
     * @param id the id of the entity.
     */
    void delete(String id);

}
