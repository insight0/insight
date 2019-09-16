package com.apeiron.insight.repository;

import com.apeiron.insight.domain.EmailTemplate;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the EmailTemplate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmailTemplateRepository extends MongoRepository<EmailTemplate, String> {

}
