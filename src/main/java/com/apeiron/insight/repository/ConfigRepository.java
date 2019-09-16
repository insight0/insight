package com.apeiron.insight.repository;

import com.apeiron.insight.domain.Config;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the Config entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConfigRepository extends MongoRepository<Config, String> {

}
