package com.apeiron.insight.web.rest;

import com.apeiron.insight.domain.Workshop;
import com.apeiron.insight.service.WorkshopService;
import com.apeiron.insight.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;


/**
 * REST controller for managing {@link com.apeiron.insight.domain.Workshop}.
 */
@RestController
@RequestMapping("/api")
public class WorkshopResource {

    private final Logger log = LoggerFactory.getLogger(Workshop.class);

    private static final String ENTITY_NAME = "workshop";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private WorkshopService workshopService;


    public WorkshopResource(WorkshopService workshopService) {
        this.workshopService = workshopService;
    }

    @GetMapping("/workshops/all")
    public @ResponseBody List<Workshop> getAlls(){
        return workshopService.findAll();
    }


}
