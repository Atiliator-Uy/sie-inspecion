package com.uy.atiliator.sie.inspeccion.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link InspeccionSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class InspeccionSearchRepositoryMockConfiguration {

    @MockBean
    private InspeccionSearchRepository mockInspeccionSearchRepository;

}
