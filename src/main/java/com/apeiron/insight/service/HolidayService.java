package com.apeiron.insight.service;

import com.apeiron.insight.service.dto.HolidayDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.apeiron.insight.domain.Holiday}.
 */
public interface HolidayService {

    List<HolidayDTO> findBetween(Instant startDate, Instant endDate);

    /**
     * Save a holiday.
     *
     * @param holidayDTO the entity to save.
     * @return the persisted entity.
     */
    HolidayDTO save(HolidayDTO holidayDTO);

    /**
     * Get all the holidays.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<HolidayDTO> findAll(Pageable pageable);


    /**
     * Get the "id" holiday.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<HolidayDTO> findOne(String id);

    /**
     * Delete the "id" holiday.
     *
     * @param id the id of the entity.
     */
    void delete(String id);

    /**
     * Search for the holiday corresponding to the query.
     *
     * @param query    the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<HolidayDTO> search(String query, Pageable pageable);


    List<HolidayDTO> findNextHoliday();
}
