package com.apeiron.insight.web.rest;

import com.apeiron.insight.service.FunctionalRoleService;
import com.apeiron.insight.web.rest.errors.BadRequestAlertException;
import com.apeiron.insight.service.dto.FunctionalRoleDTO;

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
 * REST controller for managing {@link com.apeiron.insight.domain.FunctionalRole}.
 */
@RestController
@RequestMapping("/api")
public class FunctionalRoleResource {

    private final Logger log = LoggerFactory.getLogger(FunctionalRoleResource.class);

    private static final String ENTITY_NAME = "functionalRole";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FunctionalRoleService functionalRoleService;

    public FunctionalRoleResource(FunctionalRoleService functionalRoleService) {
        this.functionalRoleService = functionalRoleService;
    }

    /**
     * {@code POST  /functional-roles} : Create a new functionalRole.
     *
     * @param functionalRoleDTO the functionalRoleDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new functionalRoleDTO, or with status {@code 400 (Bad Request)} if the functionalRole has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/functional-roles")
    public ResponseEntity<FunctionalRoleDTO> createFunctionalRole(@Valid @RequestBody FunctionalRoleDTO functionalRoleDTO) throws URISyntaxException {
        log.debug("REST request to save FunctionalRole : {}", functionalRoleDTO);
        if (functionalRoleDTO.getId() != null) {
            throw new BadRequestAlertException("A new functionalRole cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FunctionalRoleDTO result = functionalRoleService.save(functionalRoleDTO);
        return ResponseEntity.created(new URI("/api/functional-roles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /functional-roles} : Updates an existing functionalRole.
     *
     * @param functionalRoleDTO the functionalRoleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated functionalRoleDTO,
     * or with status {@code 400 (Bad Request)} if the functionalRoleDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the functionalRoleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/functional-roles")
    public ResponseEntity<FunctionalRoleDTO> updateFunctionalRole(@Valid @RequestBody FunctionalRoleDTO functionalRoleDTO) throws URISyntaxException {
        log.debug("REST request to update FunctionalRole : {}", functionalRoleDTO);
        if (functionalRoleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FunctionalRoleDTO result = functionalRoleService.save(functionalRoleDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, functionalRoleDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /functional-roles} : get all the functionalRoles.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of functionalRoles in body.
     */
    @GetMapping("/functional-roles")
    public ResponseEntity<List<FunctionalRoleDTO>> getAllFunctionalRoles(Pageable pageable) {
        log.debug("REST request to get a page of FunctionalRoles");
        Page<FunctionalRoleDTO> page = functionalRoleService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /functional-roles/:id} : get the "id" functionalRole.
     *
     * @param id the id of the functionalRoleDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the functionalRoleDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/functional-roles/{id}")
    public ResponseEntity<FunctionalRoleDTO> getFunctionalRole(@PathVariable String id) {
        log.debug("REST request to get FunctionalRole : {}", id);
        Optional<FunctionalRoleDTO> functionalRoleDTO = functionalRoleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(functionalRoleDTO);
    }

    /**
     * {@code DELETE  /functional-roles/:id} : delete the "id" functionalRole.
     *
     * @param id the id of the functionalRoleDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/functional-roles/{id}")
    public ResponseEntity<Void> deleteFunctionalRole(@PathVariable String id) {
        log.debug("REST request to delete FunctionalRole : {}", id);
        functionalRoleService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }


    /**
     * {@code GET  /functional-roles} : get all the functionalRoles.
     ** @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of functionalRole's titles in body.
     */
    @GetMapping("/functional-roles/titles")
    public ResponseEntity<List<String>> getRolesTitles() {
        log.debug("REST request to get a page of FunctionalRole's titles");
        List<String> titles  = functionalRoleService.findRoleTitles();
        return new ResponseEntity<List<String>>(titles,HttpStatus.OK);
    }


}
