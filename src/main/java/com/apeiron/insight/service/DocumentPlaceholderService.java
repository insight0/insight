package com.apeiron.insight.service;

import com.apeiron.insight.service.dto.DocumentPlaceholderDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.apeiron.insight.domain.DocumentPlaceholder}.
 */
public interface DocumentPlaceholderService {

    /**
     * Save a documentPlaceholder.
     *
     * @param documentPlaceholderDTO the entity to save.
     * @return the persisted entity.
     */
    DocumentPlaceholderDTO save(DocumentPlaceholderDTO documentPlaceholderDTO);

    /**
     * Get all the documentPlaceholders.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DocumentPlaceholderDTO> findAll(Pageable pageable);


    /**
     * Get the "id" documentPlaceholder.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DocumentPlaceholderDTO> findOne(String id);

    /**
     * Delete the "id" documentPlaceholder.
     *
     * @param id the id of the entity.
     */
    void delete(String id);

    /**
     * Search for the documentPlaceholder corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DocumentPlaceholderDTO> search(String query, Pageable pageable);
}
