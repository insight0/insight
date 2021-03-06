package com.apeiron.insight.web.rest;

import com.apeiron.insight.service.EmailTemplateService;
import com.apeiron.insight.web.rest.errors.BadRequestAlertException;
import com.apeiron.insight.service.dto.EmailTemplateDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link com.apeiron.insight.domain.EmailTemplate}.
 */
@RestController
@RequestMapping("/api")
public class EmailTemplateResource {

    private final Logger log = LoggerFactory.getLogger(EmailTemplateResource.class);

    private static final String ENTITY_NAME = "emailTemplate";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmailTemplateService emailTemplateService;

    public EmailTemplateResource(EmailTemplateService emailTemplateService) {
        this.emailTemplateService = emailTemplateService;
    }


    /**
     * {@code PUT  /email-templates} : Updates an existing emailTemplate.
     *
     * @param emailTemplateDTO the emailTemplateDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated emailTemplateDTO,
     * or with status {@code 400 (Bad Request)} if the emailTemplateDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the emailTemplateDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/email-templates")
    public ResponseEntity<EmailTemplateDTO> updateEmailTemplate(@RequestBody EmailTemplateDTO emailTemplateDTO) throws URISyntaxException {
        log.debug("REST request to update EmailTemplate : {}", emailTemplateDTO);

        emailTemplateDTO.setId("DEFAULT_CONFIG");
        EmailTemplateDTO result = emailTemplateService.save(emailTemplateDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, emailTemplateDTO.getId().toString()))
            .body(result);
    }



    /**
     * {@code GET  /email-templates/:id} : get the "id" emailTemplate.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the emailTemplateDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/email-templates")
    public ResponseEntity<EmailTemplateDTO> getEmailTemplate() {
        log.debug("REST request to get EmailTemplate : {}", "DEFAULT_CONFIG");
        Optional<EmailTemplateDTO> emailTemplateDTO = emailTemplateService.findOne();
        return ResponseUtil.wrapOrNotFound(emailTemplateDTO);
    }


}
