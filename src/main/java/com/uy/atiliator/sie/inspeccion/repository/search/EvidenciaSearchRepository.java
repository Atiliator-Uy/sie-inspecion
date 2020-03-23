package com.uy.atiliator.sie.inspeccion.repository.search;

import com.uy.atiliator.sie.inspeccion.domain.Evidencia;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Evidencia} entity.
 */
public interface EvidenciaSearchRepository extends ElasticsearchRepository<Evidencia, Long> {
}
