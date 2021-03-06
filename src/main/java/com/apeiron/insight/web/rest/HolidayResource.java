package com.apeiron.insight.web.rest;

import com.apeiron.insight.service.HolidayService;
import com.apeiron.insight.web.rest.errors.BadRequestAlertException;
import com.apeiron.insight.service.dto.HolidayDTO;

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
 * REST controller for managing {@link com.apeiron.insight.domain.Holiday}.
 */
@RestController
@RequestMapping("/api")
public class HolidayResource {

    private final Logger log = LoggerFactory.getLogger(HolidayResource.class);

    private static final String ENTITY_NAME = "holiday";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HolidayService holidayService;

    public HolidayResource(HolidayService holidayService) {
        this.holidayService = holidayService;
    }

    /**
     * {@code POST  /holidays} : Create a new holiday.
     *
     * @param holidayDTO the holidayDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new holidayDTO, or with status {@code 400 (Bad Request)} if the holiday has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/holidays")
    public ResponseEntity<HolidayDTO> createHoliday(@Valid @RequestBody HolidayDTO holidayDTO) throws URISyntaxException {
        log.debug("REST request to save Holiday : {}", holidayDTO);
        if (holidayDTO.getId() != null) {
            throw new BadRequestAlertException("A new holiday cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HolidayDTO result = holidayService.save(holidayDTO);
        return ResponseEntity.created(new URI("/api/holidays/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /holidays} : Updates an existing holiday.
     *
     * @param holidayDTO the holidayDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated holidayDTO,
     * or with status {@code 400 (Bad Request)} if the holidayDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the holidayDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/holidays")
    public ResponseEntity<HolidayDTO> updateHoliday(@Valid @RequestBody HolidayDTO holidayDTO) throws URISyntaxException {
        log.debug("REST request to update Holiday : {}", holidayDTO);
        if (holidayDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        HolidayDTO result = holidayService.save(holidayDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, holidayDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /holidays} : get all the holidays.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of holidays in body.
     */
    @GetMapping("/holidays")
    public ResponseEntity<List<HolidayDTO>> getAllHolidays(Pageable pageable) {
        log.debug("REST request to get a page of Holidays");
        Page<HolidayDTO> page = holidayService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/holidays/next")
    public ResponseEntity<List<HolidayDTO>> getNextHolidays() {
        log.debug("REST request to get a page of Holidays");
        List<HolidayDTO> holidayDTOS = holidayService.findNextHoliday();
        return ResponseEntity.ok().body(holidayDTOS);
    }

    /**
     * {@code GET  /holidays/:id} : get the "id" holiday.
     *
     * @param id the id of the holidayDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the holidayDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/holidays/{id}")
    public ResponseEntity<HolidayDTO> getHoliday(@PathVariable String id) {
        log.debug("REST request to get Holiday : {}", id);
        Optional<HolidayDTO> holidayDTO = holidayService.findOne(id);
        return ResponseUtil.wrapOrNotFound(holidayDTO);
    }

    /**
     * {@code DELETE  /holidays/:id} : delete the "id" holiday.
     *
     * @param id the id of the holidayDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/holidays/{id}")
    public ResponseEntity<Void> deleteHoliday(@PathVariable String id) {
        log.debug("REST request to delete Holiday : {}", id);
        holidayService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }

    /**
     * {@code SEARCH  /_search/holidays?query=:query} : search for the holiday corresponding
     * to the query.
     *
     * @param query    the query of the holiday search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/holidays")
    public ResponseEntity<List<HolidayDTO>> searchHolidays(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Holidays for query {}", query);
        Page<HolidayDTO> page = holidayService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
