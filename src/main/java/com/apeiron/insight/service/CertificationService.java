package com.apeiron.insight.service;

import com.apeiron.insight.domain.Certification;
import com.apeiron.insight.repository.CertificationRepository;
import com.apeiron.insight.repository.search.CertificationSearchRepository;
import com.apeiron.insight.service.dto.CertificationDTO;
import com.apeiron.insight.service.mapper.CertificationMapper;
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
 * Service Implementation for managing {@link Certification}.
 */
@Service
public class CertificationService {

    private final Logger log = LoggerFactory.getLogger(CertificationService.class);

    private final CertificationRepository certificationRepository;

    private final CertificationMapper certificationMapper;

    private final CertificationSearchRepository certificationSearchRepository;

    public CertificationService(CertificationRepository certificationRepository, CertificationMapper certificationMapper, CertificationSearchRepository certificationSearchRepository) {
        this.certificationRepository = certificationRepository;
        this.certificationMapper = certificationMapper;
        this.certificationSearchRepository = certificationSearchRepository;
    }

    /**
     * Save a certification.
     *
     * @param certificationDTO the entity to save.
     * @return the persisted entity.
     */
    public CertificationDTO save(CertificationDTO certificationDTO) {
        log.debug("Request to save Certification : {}", certificationDTO);
        Certification certification = certificationMapper.toEntity(certificationDTO);
        certification = certificationRepository.save(certification);
        CertificationDTO result = certificationMapper.toDto(certification);
        certificationSearchRepository.save(certification);
        return result;
    }

    /**
     * Get all the certifications.
     *
     * @return the list of entities.
     */
    public List<CertificationDTO> findAll() {
        log.debug("Request to get all Certifications");
        return certificationRepository.findAll().stream()
            .map(certificationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one certification by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<CertificationDTO> findOne(String id) {
        log.debug("Request to get Certification : {}", id);
        return certificationRepository.findById(id)
            .map(certificationMapper::toDto);
    }

    /**
     * Delete the certification by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete Certification : {}", id);
        certificationRepository.deleteById(id);
        certificationSearchRepository.deleteById(id);
    }

    /**
     * Search for the certification corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    public List<CertificationDTO> search(String query) {
        log.debug("Request to search Certifications for query {}", query);
        return StreamSupport
            .stream(certificationSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(certificationMapper::toDto)
            .collect(Collectors.toList());
    }
}
