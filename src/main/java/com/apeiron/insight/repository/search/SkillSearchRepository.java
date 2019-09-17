package com.apeiron.insight.repository.search;

import com.apeiron.insight.domain.Skill;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Skill} entity.
 */
public interface SkillSearchRepository extends ElasticsearchRepository<Skill, String> {
}
