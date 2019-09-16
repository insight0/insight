package com.apeiron.insight.repository;

import com.apeiron.insight.domain.DayOff;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the DayOff entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DayOffRepository extends MongoRepository<DayOff, String> {

}
