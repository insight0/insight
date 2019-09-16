package com.apeiron.insight.repository.search;

import com.apeiron.insight.domain.Holiday;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Holiday} entity.
 */
public interface HolidaySearchRepository extends ElasticsearchRepository<Holiday, String> {
}
