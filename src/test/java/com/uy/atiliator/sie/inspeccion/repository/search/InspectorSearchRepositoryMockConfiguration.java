package com.uy.atiliator.sie.inspeccion.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link InspectorSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class InspectorSearchRepositoryMockConfiguration {

    @MockBean
    private InspectorSearchRepository mockInspectorSearchRepository;

}
