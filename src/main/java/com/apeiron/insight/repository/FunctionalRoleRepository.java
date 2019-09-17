package com.apeiron.insight.repository;

import com.apeiron.insight.domain.FunctionalRole;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the FunctionalRole entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FunctionalRoleRepository extends MongoRepository<FunctionalRole, String> {

}
