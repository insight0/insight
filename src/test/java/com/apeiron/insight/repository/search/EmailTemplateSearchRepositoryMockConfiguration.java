package com.apeiron.insight.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link EmailTemplateSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class EmailTemplateSearchRepositoryMockConfiguration {

    @MockBean
    private EmailTemplateSearchRepository mockEmailTemplateSearchRepository;

}
