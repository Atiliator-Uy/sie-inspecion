package com.uy.atiliator.sie.inspeccion.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link LugarGPSSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class LugarGPSSearchRepositoryMockConfiguration {

    @MockBean
    private LugarGPSSearchRepository mockLugarGPSSearchRepository;

}
