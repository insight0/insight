package com.apeiron.insight.repository;

import com.apeiron.insight.domain.Skill;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the Skill entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SkillRepository extends MongoRepository<Skill, String> {

}
