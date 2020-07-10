package com.apeiron.insight.service.impl;

import com.apeiron.insight.domain.Workshop;
import com.apeiron.insight.repository.WorkshopRepository;
import com.apeiron.insight.service.WorkshopService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WorkshopServiceImpl implements WorkshopService {

    private final Logger log = LoggerFactory.getLogger(WorkshopServiceImpl.class);

    @Autowired
    WorkshopRepository repository;

    @Override
    public Workshop save(Workshop workshop) {
        log.debug("Request to save Workshop : {}", workshop);
        return repository.insert(workshop);
    }

    @Override
    public Workshop update(Workshop workshop) {
        log.debug("Request to update workshop : {}", workshop);
        return repository.save(workshop);
    }


    @Override
    public Optional<Workshop> findOne(String id) {
        log.debug("Request to get workshop from its id {} :", id);
        Optional<Workshop> workshop = repository.findById(id);
        return workshop;
    }

    @Override
    public List<Workshop> findAll() {
        log.debug("Request to get all Workshops");
        return repository.findAll();
    }


    @Override
    public void delete(String id) {
        log.debug("Request to delete formation using its id {} :", id);
        Optional<Workshop> workshop = repository.findById(id);
        Workshop result = workshop.get();
        repository.delete(result);
    }
}
