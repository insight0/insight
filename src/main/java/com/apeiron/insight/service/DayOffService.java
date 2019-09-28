package com.apeiron.insight.service;

import com.apeiron.insight.service.dto.DayOffDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.apeiron.insight.domain.DayOff}.
 */
public interface DayOffService {




    List<DayOffDTO> findByUserLogin(String login);

    List<DayOffDTO> findNonClosed();
    /**
     * Save a dayOff.
     *
     * @param dayOffDTO the entity to save.
     * @return the persisted entity.
     */
    DayOffDTO save(DayOffDTO dayOffDTO);

    /**
     * Get all the dayOffs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DayOffDTO> findAll(Pageable pageable);


    /**
     * Get the "id" dayOff.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DayOffDTO> findOne(String id);

    /**
     * Delete the "id" dayOff.
     *
     * @param id the id of the entity.
     */
    void delete(String id);

    /**
     * Search for the dayOff corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DayOffDTO> search(String query, Pageable pageable);
}
