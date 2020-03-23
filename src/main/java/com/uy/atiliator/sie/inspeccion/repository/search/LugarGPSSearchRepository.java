package com.uy.atiliator.sie.inspeccion.repository.search;

import com.uy.atiliator.sie.inspeccion.domain.LugarGPS;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link LugarGPS} entity.
 */
public interface LugarGPSSearchRepository extends ElasticsearchRepository<LugarGPS, Long> {
}
