package com.apeiron.insight.repository.search;

import com.apeiron.insight.domain.Degree;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Degree} entity.
 */
public interface DegreeSearchRepository extends ElasticsearchRepository<Degree, String> {
}
