package com.apeiron.insight.web.rest;

import com.apeiron.insight.service.CertificationService;
import com.apeiron.insight.web.rest.errors.BadRequestAlertException;
import com.apeiron.insight.service.dto.CertificationDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
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
 * REST controller for managing {@link com.apeiron.insight.domain.Certification}.
 */
@RestController
@RequestMapping("/api")
public class CertificationResource {

    private final Logger log = LoggerFactory.getLogger(CertificationResource.class);

    private static final String ENTITY_NAME = "certification";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CertificationService certificationService;

    public CertificationResource(CertificationService certificationService) {
        this.certificationService = certificationService;
    }

    /**
     * {@code POST  /certifications} : Create a new certification.
     *
     * @param certificationDTO the certificationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new certificationDTO, or with status {@code 400 (Bad Request)} if the certification has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/certifications")
    public ResponseEntity<CertificationDTO> createCertification(@Valid @RequestBody CertificationDTO certificationDTO) throws URISyntaxException {
        log.debug("REST request to save Certification : {}", certificationDTO);
        if (certificationDTO.getId() != null) {
            throw new BadRequestAlertException("A new certification cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CertificationDTO result = certificationService.save(certificationDTO);
        return ResponseEntity.created(new URI("/api/certifications/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /certifications} : Updates an existing certification.
     *
     * @param certificationDTO the certificationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated certificationDTO,
     * or with status {@code 400 (Bad Request)} if the certificationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the certificationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/certifications")
    public ResponseEntity<CertificationDTO> updateCertification(@Valid @RequestBody CertificationDTO certificationDTO) throws URISyntaxException {
        log.debug("REST request to update Certification : {}", certificationDTO);
        if (certificationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CertificationDTO result = certificationService.save(certificationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, certificationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /certifications} : get all the certifications.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of certifications in body.
     */
    @GetMapping("/certifications")
    public List<CertificationDTO> getAllCertifications() {
        log.debug("REST request to get all Certifications");
        return certificationService.findAll();
    }

    /**
     * {@code GET  /certifications/:id} : get the "id" certification.
     *
     * @param id the id of the certificationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the certificationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/certifications/{id}")
    public ResponseEntity<CertificationDTO> getCertification(@PathVariable String id) {
        log.debug("REST request to get Certification : {}", id);
        Optional<CertificationDTO> certificationDTO = certificationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(certificationDTO);
    }

    /**
     * {@code DELETE  /certifications/:id} : delete the "id" certification.
     *
     * @param id the id of the certificationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/certifications/{id}")
    public ResponseEntity<Void> deleteCertification(@PathVariable String id) {
        log.debug("REST request to delete Certification : {}", id);
        certificationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }

    /**
     * {@code SEARCH  /_search/certifications?query=:query} : search for the certification corresponding
     * to the query.
     *
     * @param query the query of the certification search.
     * @return the result of the search.
     */
    @GetMapping("/_search/certifications")
    public List<CertificationDTO> searchCertifications(@RequestParam String query) {
        log.debug("REST request to search Certifications for query {}", query);
        return certificationService.search(query);
    }

}
