package com.apeiron.insight.service;

import com.apeiron.insight.domain.Workshop;


import java.util.List;
import java.util.Optional;

public interface WorkshopService {

    /**
     * Get all Workshops .
     *
     * @return All workshops
     */
    List<Workshop> findAll();

    /**
     * Get workshop by its id
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Workshop> findById(String id);

    /**
     * SAVE a workshop
     *
     * @param workshop the entity to save
     * @return the persisted entity
     */
    Workshop save(Workshop workshop);

    /**
     * UPDATE workshop
     *
     * @param workshop the entity to update
     * @return the updated entity
     */
    Workshop update(Workshop workshop);

    /**
     * DELETE workshop
     *
     * @param id the entity's id to delete
     * @return void
     */
    void delete(String id);

}
