package com.apeiron.insight.web.rest;

import com.apeiron.insight.domain.Workshop;
import com.apeiron.insight.service.WorkshopService;

import com.apeiron.insight.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.apeiron.insight.domain.Workshop}.
 */
@RestController
@RequestMapping("/api")
public class WorkshopResource {

    private final Logger log = LoggerFactory.getLogger(Workshop.class);

    @Autowired
    private WorkshopService workshopService;

    private static final String ENTITY_NAME = "workshop";


    /**
     * {@code GET /workshops } : get all workshops .
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of all workshops .
     */
    @GetMapping("/workshops")
    public ResponseEntity<List<Workshop>> getAllWorkshops() {
        log.debug("REST request to get all workshops");
        List<Workshop> workshops = workshopService.findAll();
        return new ResponseEntity<List<Workshop>>(workshops, HttpStatus.OK);
    }


    /**
     * { @code GET/workshops/id } : get a specefic workshop .
     *
     * @param id the id of the entity to fetch .
     * @return the {@link ResponseEntity } with status {@code 200 (ok) } and the workshop entity .
     */
    @GetMapping("/workshops/{id}")
    public ResponseEntity<Workshop> getWorkshopById(String id) {
        log.debug("Request to get Workshop by its id :", id);
        Optional<Workshop> result = workshopService.findById(id);
        return new ResponseEntity<Workshop>(result.get(), HttpStatus.OK);
    }

    /**
     * {@code POST/ workshop } : Create  workshop .
     *
     * @param workshop the entity to presist .
     * @return the {@link ResponseEntity} with status {@code 200 (ok)} and the entity's body .
     */
    @PostMapping("/workshops")
    public ResponseEntity<Workshop> saveWorkshop(@Valid @RequestBody Workshop workshop) {
        log.debug("Resuest to save Workshop : {workshop} :", workshop);
        if (workshop.getId() != null) {
            throw new BadRequestAlertException("A new workshop cannot already have an ID ", ENTITY_NAME, "id exists ");
        }
        Workshop workshop1 = workshopService.save(workshop);
        return new ResponseEntity<Workshop>(workshop1, HttpStatus.CREATED);
    }

    /**
     * {@code PUT /workshop } : Updates an existent workshop .
     *
     * @param workshop the entity to update .
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the updated workshop,
     * or with status {@code 400 (bad request) } if the workshop param is not valid,
     * or with status {@code 500 (Internal Server Error) } if the workshop cannot be updated .
     * @throws java.net.URISyntaxException if the the location URI is incorrect .
     */
    @PutMapping("/workshops")
    public ResponseEntity<Workshop> updateWorkshop(@Valid @RequestBody Workshop workshop) {
        log.debug("Request to update Workshhop {workshop} :", workshop);
        if (workshop.getId() == null) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "ID null ");
        }
        Workshop result = workshopService.update(workshop);
        return new ResponseEntity<Workshop>(workshop, HttpStatus.OK);
    }

    /**
     * { @code DELETE/ workshop } : delete workshop .
     *
     * @param id the workshop's id to delete
     */
    @DeleteMapping("/workshops/{id}")
    public void deleteWorkshop(String id) {
        if (workshopService.findById(id).isPresent()) {
            workshopService.delete(id);
        } else
            throw new BadRequestAlertException(" invalid id", ENTITY_NAME, "No matching workshop found for the giving ID ");
    }


}
