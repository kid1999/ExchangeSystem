package io.kid1999.esystem.es.repository;

import io.kid1999.esystem.es.entry.GoodsEntry;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoodsRepository extends ElasticsearchRepository<GoodsEntry, Long> {

    List<GoodsEntry> findAllByNameLike(String name);


}
