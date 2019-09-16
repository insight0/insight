package com.apeiron.insight.repository.search;

import com.apeiron.insight.domain.Organisation;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Organisation} entity.
 */
public interface OrganisationSearchRepository extends ElasticsearchRepository<Organisation, String> {
}
