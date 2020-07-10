package com.apeiron.insight.repository;

import com.apeiron.insight.domain.Formation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FormationRepository extends MongoRepository<Formation , String> {

}
