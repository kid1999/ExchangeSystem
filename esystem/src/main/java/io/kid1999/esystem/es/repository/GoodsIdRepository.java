package io.kid1999.esystem.es.repository;

import io.kid1999.esystem.es.entry.RecommendationGoodsEntry;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @author kid1999
 */

@Repository
public interface GoodsIdRepository extends ElasticsearchRepository<RecommendationGoodsEntry, Long> {

}
