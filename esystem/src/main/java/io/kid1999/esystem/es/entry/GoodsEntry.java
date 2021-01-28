package io.kid1999.esystem.es.entry;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * @author kid1999
 * @create 2021-01-28 11:12
 * @description TODO
 **/
@Data
@Document( indexName = "test")
public class GoodsEntry {
    @Id
    private Long id;
    private String name;
    private String password;
}