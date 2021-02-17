package io.kid1999.esystem.es.entry;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;
import java.util.List;

/**
 * @author kid1999
 * @create 2021-02-17 11:04
 * @description 推荐商品列表
 **/
@Data
@Document( indexName = "recommendation-goods")
public class RecommendationGoodsEntry implements Serializable {

    @Id
    private Long id;

    private int userId;

    private List<Integer> goodsId;

    private static final long serialVersionUID = -1;

}