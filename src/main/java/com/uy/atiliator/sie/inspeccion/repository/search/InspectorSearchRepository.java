package com.uy.atiliator.sie.inspeccion.repository.search;

import com.uy.atiliator.sie.inspeccion.domain.Inspector;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Inspector} entity.
 */
public interface InspectorSearchRepository extends ElasticsearchRepository<Inspector, Long> {
}
