package com.apeiron.insight.repository.search;

import com.apeiron.insight.domain.Certification;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Certification} entity.
 */
public interface CertificationSearchRepository extends ElasticsearchRepository<Certification, String> {
}
