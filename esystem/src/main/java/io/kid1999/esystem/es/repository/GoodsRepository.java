package io.kid1999.esystem.es.repository;

import io.kid1999.esystem.es.entry.GoodsEntry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @author kid1999
 */
@Repository
public interface GoodsRepository extends ElasticsearchRepository<GoodsEntry, Long> {

    Page<GoodsEntry> findAllByGoodsNameLike(String name, Pageable pageable);

}
