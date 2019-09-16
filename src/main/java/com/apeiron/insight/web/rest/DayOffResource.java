package com.apeiron.insight.web.rest;

import com.apeiron.insight.service.DayOffService;
import com.apeiron.insight.web.rest.errors.BadRequestAlertException;
import com.apeiron.insight.service.dto.DayOffDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link com.apeiron.insight.domain.DayOff}.
 */
@RestController
@RequestMapping("/api")
public class DayOffResource {

    private final Logger log = LoggerFactory.getLogger(DayOffResource.class);

    private static final String ENTITY_NAME = "dayOff";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DayOffService dayOffService;

    public DayOffResource(DayOffService dayOffService) {
        this.dayOffService = dayOffService;
    }

    /**
     * {@code POST  /day-offs} : Create a new dayOff.
     *
     * @param dayOffDTO the dayOffDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dayOffDTO, or with status {@code 400 (Bad Request)} if the dayOff has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/day-offs")
    public ResponseEntity<DayOffDTO> createDayOff(@Valid @RequestBody DayOffDTO dayOffDTO) throws URISyntaxException {
        log.debug("REST request to save DayOff : {}", dayOffDTO);
        if (dayOffDTO.getId() != null) {
            throw new BadRequestAlertException("A new dayOff cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DayOffDTO result = dayOffService.save(dayOffDTO);
        return ResponseEntity.created(new URI("/api/day-offs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /day-offs} : Updates an existing dayOff.
     *
     * @param dayOffDTO the dayOffDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dayOffDTO,
     * or with status {@code 400 (Bad Request)} if the dayOffDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dayOffDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/day-offs")
    public ResponseEntity<DayOffDTO> updateDayOff(@Valid @RequestBody DayOffDTO dayOffDTO) throws URISyntaxException {
        log.debug("REST request to update DayOff : {}", dayOffDTO);
        if (dayOffDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DayOffDTO result = dayOffService.save(dayOffDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dayOffDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /day-offs} : get all the dayOffs.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dayOffs in body.
     */
    @GetMapping("/day-offs")
    public ResponseEntity<List<DayOffDTO>> getAllDayOffs(Pageable pageable) {
        log.debug("REST request to get a page of DayOffs");
        Page<DayOffDTO> page = dayOffService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /day-offs/:id} : get the "id" dayOff.
     *
     * @param id the id of the dayOffDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dayOffDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/day-offs/{id}")
    public ResponseEntity<DayOffDTO> getDayOff(@PathVariable String id) {
        log.debug("REST request to get DayOff : {}", id);
        Optional<DayOffDTO> dayOffDTO = dayOffService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dayOffDTO);
    }

    /**
     * {@code DELETE  /day-offs/:id} : delete the "id" dayOff.
     *
     * @param id the id of the dayOffDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/day-offs/{id}")
    public ResponseEntity<Void> deleteDayOff(@PathVariable String id) {
        log.debug("REST request to delete DayOff : {}", id);
        dayOffService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }

    /**
     * {@code SEARCH  /_search/day-offs?query=:query} : search for the dayOff corresponding
     * to the query.
     *
     * @param query the query of the dayOff search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/day-offs")
    public ResponseEntity<List<DayOffDTO>> searchDayOffs(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of DayOffs for query {}", query);
        Page<DayOffDTO> page = dayOffService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
