package com.apeiron.insight.service.impl;
import com.apeiron.insight.domain.Formation;
import com.apeiron.insight.repository.FormationRepository;
import com.apeiron.insight.service.FormationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Service
public class FormationServiceImpl implements FormationService {

    private final Logger log = LoggerFactory.getLogger(FormationServiceImpl.class);

    @Autowired
    FormationRepository repository;

    @Override
    public Formation save(Formation formation) {
        log.debug("Request to save Formation : {}", formation);
        return repository.insert(formation);
    }

    @Override
    public Formation update(Formation formation) {
        log.debug("Request to update Formation : {}", formation);
        return repository.save(formation);
    }




    @Override
    public Optional<Formation> findOne(String id) {
        log.debug("Request to get formation from its id {} :", id);
        Optional<Formation> formation = repository.findById(id);
        return formation;
    }

    @Override
    public Page<Formation> findAll(Pageable pageable) {
        log.debug("Request to get all Formations");
        return repository.findAll(pageable);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete formation using its id {} :", id);
        Optional<Formation> formation = repository.findById(id);
        Formation result = formation.get();
        repository.delete(result);
    }
}
