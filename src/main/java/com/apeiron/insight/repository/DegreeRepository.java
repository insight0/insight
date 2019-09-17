package com.apeiron.insight.repository;

import com.apeiron.insight.domain.Degree;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the Degree entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DegreeRepository extends MongoRepository<Degree, String> {

}
