package com.apeiron.insight.repository.search;

import com.apeiron.insight.domain.EmailTemplate;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link EmailTemplate} entity.
 */
public interface EmailTemplateSearchRepository extends ElasticsearchRepository<EmailTemplate, String> {
}
