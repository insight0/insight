package com.apeiron.insight.service.impl;

import com.apeiron.insight.domain.Workshop;
import com.apeiron.insight.repository.WorkshopRepository;
import com.apeiron.insight.service.WorkshopService;
import com.sun.corba.se.spi.orbutil.threadpool.Work;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class WorkshopServiceImpl implements WorkshopService {

    private final Logger log = LoggerFactory.getLogger(WorkshopServiceImpl.class);

    @Autowired
    WorkshopRepository workshopRepository;


    @Override
    public List<Workshop> findAll() {
        List<Workshop> workshops = workshopRepository.findAll();
        return workshops;
    }

    @Override
    public Optional<Workshop> findById(String id) {
        Optional<Workshop> result = workshopRepository.findById(id);
        return result;
    }

    @Override
    public Workshop save(Workshop workshop) {
        Workshop result = workshopRepository.insert(workshop);
        return result;
    }

    @Override
    public Workshop update(Workshop workshop) {
        Workshop result = workshopRepository.save(workshop);
        return result;
    }

    @Override
    public void delete(String id) {
        Optional<Workshop> result = workshopRepository.findById(id);
            workshopRepository.delete(result.get());
    }
}
