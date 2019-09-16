package com.apeiron.insight.service.impl;

import com.apeiron.insight.service.DayOffService;
import com.apeiron.insight.domain.DayOff;
import com.apeiron.insight.repository.DayOffRepository;
import com.apeiron.insight.repository.search.DayOffSearchRepository;
import com.apeiron.insight.service.dto.DayOffDTO;
import com.apeiron.insight.service.mapper.DayOffMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link DayOff}.
 */
@Service
public class DayOffServiceImpl implements DayOffService {

    private final Logger log = LoggerFactory.getLogger(DayOffServiceImpl.class);

    private final DayOffRepository dayOffRepository;

    private final DayOffMapper dayOffMapper;

    private final DayOffSearchRepository dayOffSearchRepository;

    public DayOffServiceImpl(DayOffRepository dayOffRepository, DayOffMapper dayOffMapper, DayOffSearchRepository dayOffSearchRepository) {
        this.dayOffRepository = dayOffRepository;
        this.dayOffMapper = dayOffMapper;
        this.dayOffSearchRepository = dayOffSearchRepository;
    }

    /**
     * Save a dayOff.
     *
     * @param dayOffDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public DayOffDTO save(DayOffDTO dayOffDTO) {
        log.debug("Request to save DayOff : {}", dayOffDTO);
        DayOff dayOff = dayOffMapper.toEntity(dayOffDTO);
        dayOff = dayOffRepository.save(dayOff);
        DayOffDTO result = dayOffMapper.toDto(dayOff);
        dayOffSearchRepository.save(dayOff);
        return result;
    }

    /**
     * Get all the dayOffs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    public Page<DayOffDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DayOffs");
        return dayOffRepository.findAll(pageable)
            .map(dayOffMapper::toDto);
    }


    /**
     * Get one dayOff by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    public Optional<DayOffDTO> findOne(String id) {
        log.debug("Request to get DayOff : {}", id);
        return dayOffRepository.findById(id)
            .map(dayOffMapper::toDto);
    }

    /**
     * Delete the dayOff by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete DayOff : {}", id);
        dayOffRepository.deleteById(id);
        dayOffSearchRepository.deleteById(id);
    }

    /**
     * Search for the dayOff corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    public Page<DayOffDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of DayOffs for query {}", query);
        return dayOffSearchRepository.search(queryStringQuery(query), pageable)
            .map(dayOffMapper::toDto);
    }
}
