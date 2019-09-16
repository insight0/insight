package com.apeiron.insight.repository;

import com.apeiron.insight.domain.Holiday;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;


/**
 * Spring Data MongoDB repository for the Holiday entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HolidayRepository extends MongoRepository<Holiday, String> {

    List<Holiday> findByDateGreaterThanOrderByDateAsc(Instant date);

}
