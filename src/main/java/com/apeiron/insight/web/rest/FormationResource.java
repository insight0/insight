package com.apeiron.insight.web.rest;

import com.apeiron.insight.domain.Formation;
import com.apeiron.insight.service.FormationService;
import com.apeiron.insight.service.dto.FunctionalRoleDTO;
import com.apeiron.insight.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;


/**
 * REST controller for managing {@link com.apeiron.insight.domain.Formation}.
 */
@RestController
@RequestMapping("/api")
public class FormationResource {


    private final Logger log = LoggerFactory.getLogger(FormationResource.class);

    private static final String ENTITY_NAME = "Formation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    @Autowired
    private FormationService formationService;

    /**
     * {@code GET  /formations} : get all the formations .
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of formations in body.
     */
    @GetMapping("/formations")
    public ResponseEntity<List<Formation>> getAllFunctionalRoles(Pageable pageable) {
        log.debug("REST request to get a page of Formations");

        Page<Formation> page = formationService.findAll(pageable);
        List<Formation> frs = page.getContent();
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code POST  /formations} : Create a new formation.
     *
     * @param formation the formation to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new formation, or with status {@code 400 (Bad Request)} if the formation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/formations")
    public ResponseEntity<Formation> createFormation(@Valid @RequestBody Formation formation) throws URISyntaxException {
        log.debug("REST request to save formation : {}", formation);
        if (formation.getId() != null) {
            throw new BadRequestAlertException("A new formation cannot already have an ID", ENTITY_NAME, "idexists");
        }

        Formation result = formationService.save(formation);
        return ResponseEntity.created(new URI("/api/formations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /formations} : Updates an existing formation.
     *
     * @param formation the formation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated formation,
     * or with status {@code 400 (Bad Request)} if the formation is not valid,
     * or with status {@code 500 (Internal Server Error)} if the formation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/formations")
    public ResponseEntity<Formation> updateForamtion(@Valid @RequestBody Formation formation) throws URISyntaxException {
        log.debug("REST request to update Formation : {}", formation);
        if (formation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Formation result = formationService.update(formation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, formation.getId().toString()))
            .body(result);
    }



    /**
     * {@code GET  /formations} : fetch an existing formation.
     *
     * @param id of the formation to fetch.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the formation,
     * or with status {@code 400 (Bad Request)} if the formation's id is not valid,
     * or with status {@code 500 (Internal Server Error)} if the formation couldn't be fetch.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @GetMapping("/formations/{id}")
    public ResponseEntity<Formation> findForamtionById(@PathVariable String id) throws URISyntaxException {

        log.debug("REST request to get FunctionalRole : {}", id);
        Optional<Formation> formation = formationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(formation);
    }

    /**
     * {@code DELETE  /formation/:id} : delete the "id" formation.
     *
     * @param id the id of the formation to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/formations/{id}")
    public ResponseEntity<Void> deleteFormation(@PathVariable String id) {
        log.debug("REST request to delete Formation : {}", id);
        formationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
