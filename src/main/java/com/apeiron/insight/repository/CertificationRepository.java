package com.apeiron.insight.repository;

import com.apeiron.insight.domain.Certification;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the Certification entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CertificationRepository extends MongoRepository<Certification, String> {

}
