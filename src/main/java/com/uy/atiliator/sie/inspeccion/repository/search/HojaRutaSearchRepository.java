package com.uy.atiliator.sie.inspeccion.repository.search;

import com.uy.atiliator.sie.inspeccion.domain.HojaRuta;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link HojaRuta} entity.
 */
public interface HojaRutaSearchRepository extends ElasticsearchRepository<HojaRuta, Long> {
}
