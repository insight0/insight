package com.apeiron.insight.repository.search;

import com.apeiron.insight.domain.DayOff;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link DayOff} entity.
 */
public interface DayOffSearchRepository extends ElasticsearchRepository<DayOff, String> {
}
