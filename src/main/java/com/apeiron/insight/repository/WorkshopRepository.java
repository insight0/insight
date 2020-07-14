package com.apeiron.insight.repository;

import com.apeiron.insight.domain.Workshop;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface WorkshopRepository extends MongoRepository< Workshop, String> {
    Optional<Workshop> findById(String id);
}
