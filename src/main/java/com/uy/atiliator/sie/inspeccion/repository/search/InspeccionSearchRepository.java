package com.uy.atiliator.sie.inspeccion.repository.search;

import com.uy.atiliator.sie.inspeccion.domain.Inspeccion;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Inspeccion} entity.
 */
public interface InspeccionSearchRepository extends ElasticsearchRepository<Inspeccion, Long> {
}
