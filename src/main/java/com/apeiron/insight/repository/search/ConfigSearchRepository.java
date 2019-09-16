package com.apeiron.insight.repository.search;

import com.apeiron.insight.domain.Config;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Config} entity.
 */
public interface ConfigSearchRepository extends ElasticsearchRepository<Config, String> {
}
