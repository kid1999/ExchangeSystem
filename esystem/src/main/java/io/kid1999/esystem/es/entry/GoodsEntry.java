package io.kid1999.esystem.es.entry;

import com.alibaba.fastjson.annotation.JSONField;
import io.kid1999.esystem.entity.Goods;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZoneId;
import java.util.Date;

/**
 * @author kid1999
 * @create 2021-01-28 11:12
 * @description TODO
 **/
@Data
@Document( indexName = "goods")
public class GoodsEntry implements Serializable {
    @Field(type = FieldType.Keyword)
    @Id
    private Long id;
    private String username;
    private Integer goodsStatus;
    private String goodsName;
    private String address;
    private String wantGoodsName;
    private Long wantGoodsId;
    private Integer numberOfClicked;
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


    public static GoodsEntry parse2GoodsEntry(Goods goods,String username){
        GoodsEntry entry = new GoodsEntry();
        entry.id = goods.getId();
        entry.addressId = goods.getAddressId();
        entry.deleted = goods.getDeleted();
        entry.description = goods.getDescription();
        entry.goodsCondition = goods.getGoodsCondition();
        entry.goodsName = goods.getGoodsName();
        entry.goodsStatus = goods.getGoodsStatus();
        entry.imgUrl = goods.getImgUrl();
        entry.price = goods.getPrice();
        entry.numberOfClicked = goods.getNumberOfClicked();
        entry.remarks = goods.getRemarks();
        entry.wantGoodsId = goods.getWantGoodsId();
        entry.userId = goods.getUserId();
        entry.username = username;
        return entry;
    }


    public static Goods parse2Goods(GoodsEntry goodsEntry){
        Goods goods = new Goods();
        goods.setWantGoodsId(goodsEntry.wantGoodsId);
        goods.setId(goodsEntry.id);
        goods.setUserId(goodsEntry.userId);
        goods.setPrice(goodsEntry.price);
        goods.setNumberOfClicked(goodsEntry.numberOfClicked);
        goods.setRemarks(goodsEntry.remarks);
        goods.setImgUrl(goodsEntry.imgUrl);
        goods.setGoodsCondition(goodsEntry.goodsCondition);
        goods.setAddressId(goodsEntry.addressId);
        goods.setGoodsStatus(goodsEntry.goodsStatus);
        goods.setDescription(goodsEntry.description);
        return goods;
    }
}