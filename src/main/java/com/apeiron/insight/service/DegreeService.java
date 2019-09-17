package com.apeiron.insight.service;

import com.apeiron.insight.domain.Degree;
import com.apeiron.insight.repository.DegreeRepository;
import com.apeiron.insight.repository.search.DegreeSearchRepository;
import com.apeiron.insight.service.dto.DegreeDTO;
import com.apeiron.insight.service.mapper.DegreeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Degree}.
 */
@Service
public class DegreeService {

    private final Logger log = LoggerFactory.getLogger(DegreeService.class);

    private final DegreeRepository degreeRepository;

    private final DegreeMapper degreeMapper;

    private final DegreeSearchRepository degreeSearchRepository;

    public DegreeService(DegreeRepository degreeRepository, DegreeMapper degreeMapper, DegreeSearchRepository degreeSearchRepository) {
        this.degreeRepository = degreeRepository;
        this.degreeMapper = degreeMapper;
        this.degreeSearchRepository = degreeSearchRepository;
    }

    /**
     * Save a degree.
     *
     * @param degreeDTO the entity to save.
     * @return the persisted entity.
     */
    public DegreeDTO save(DegreeDTO degreeDTO) {
        log.debug("Request to save Degree : {}", degreeDTO);
        Degree degree = degreeMapper.toEntity(degreeDTO);
        degree = degreeRepository.save(degree);
        DegreeDTO result = degreeMapper.toDto(degree);
        degreeSearchRepository.save(degree);
        return result;
    }

    /**
     * Get all the degrees.
     *
     * @return the list of entities.
     */
    public List<DegreeDTO> findAll() {
        log.debug("Request to get all Degrees");
        return degreeRepository.findAll().stream()
            .map(degreeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one degree by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<DegreeDTO> findOne(String id) {
        log.debug("Request to get Degree : {}", id);
        return degreeRepository.findById(id)
            .map(degreeMapper::toDto);
    }

    /**
     * Delete the degree by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete Degree : {}", id);
        degreeRepository.deleteById(id);
        degreeSearchRepository.deleteById(id);
    }

    /**
     * Search for the degree corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    public List<DegreeDTO> search(String query) {
        log.debug("Request to search Degrees for query {}", query);
        return StreamSupport
            .stream(degreeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(degreeMapper::toDto)
            .collect(Collectors.toList());
    }
}
