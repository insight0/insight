package com.apeiron.insight.repository;

import com.apeiron.insight.domain.Workshop;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface WorkshopRepository extends MongoRepository< Workshop, String> {
}
