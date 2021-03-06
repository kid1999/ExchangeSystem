package io.kid1999.esystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @TableName goods
 */
@Data
public class Goods implements Serializable {
    /**
     * 
     *
     * @mbg.generated Sun Jan 03 17:10:59 CST 2021
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 
     *
     * @mbg.generated Sun Jan 03 17:10:59 CST 2021
     */
    private Long userId;

    /**
     * 
     *
     * @mbg.generated Sun Jan 03 17:10:59 CST 2021
     */
    private String goodsName;

    /**
     * 
     *
     * @mbg.generated Sun Jan 03 17:10:59 CST 2021
     */
    private String imgUrl;

    /**
     * 成色
     *
     * @mbg.generated Sun Jan 03 17:10:59 CST 2021
     */
    private String goodsCondition;

    /**
     * 描述
     *
     * @mbg.generated Sun Jan 03 17:10:59 CST 2021
     */
    private String description;

    /**
     * 期望交换物品id
     *
     * @mbg.generated Sun Jan 03 17:10:59 CST 2021
     */
    private Long wantGoodsId;

    /**
     * 交易地点
     *
     * @mbg.generated Sun Jan 03 17:10:59 CST 2021
     */
    private Long addressId;

    /**
     * 备注
     *
     * @mbg.generated Sun Jan 03 17:10:59 CST 2021
     */
    private String remarks;

    /**
     * 状态
     *
     * @mbg.generated Sun Jan 03 17:10:59 CST 2021
     */
    private Integer goodsStatus;

    /**
     * 被点击次数
     *
     * @mbg.generated Sun Jan 03 17:10:59 CST 2021
     */
    private Integer numberOfClicked;

    /**
     * 
     *
     * @mbg.generated Sun Jan 03 17:10:59 CST 2021
     */
    private LocalDateTime createDate;

    /**
     * 
     *
     * @mbg.generated Sun Jan 03 17:10:59 CST 2021
     */
    @TableLogic
    private Byte deleted;

    /**
     * 
     *
     * @mbg.generated Sun Jan 03 17:10:59 CST 2021
     */
    private BigDecimal price;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table goods
     *
     * @mbg.generated Sun Jan 03 17:10:59 CST 2021
     */
    private static final long serialVersionUID = 1L;
}