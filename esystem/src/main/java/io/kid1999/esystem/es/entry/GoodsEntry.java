package io.kid1999.esystem.es.entry;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author kid1999
 * @create 2021-01-28 11:12
 * @description TODO
 **/
@Data
@Document( indexName = "goods")
public class GoodsEntry implements Serializable {
    @Id
    private Long id;
    private String username;
    private Byte goodsStatus;
    private String goodsName;
    private String address;
    private String wantGoodsName;
    private Long wantGoodsId;
    private Long numberOfClicked;
    private Long addressId;
    private String description;
    private Byte deleted;
    private Long userId;
    private String imgUrl;
    private BigDecimal price;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date createDate;
    private String remarks;
    private String goodsCondition;


    private static final long serialVersionUID = -1;
}