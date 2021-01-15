package io.kid1999.esystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author kid1999
 * @TableName trans_record
 */
@Data
public class TransRecord implements Serializable {
    /**
     * 
     *
     * @mbg.generated Thu Jan 07 13:09:51 CST 2021
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 交易状态
0 申请
1 接受申请
2 完成交易

11 拒绝申请
12 交易失败

20 客服介入
     *
     * @mbg.generated Thu Jan 07 13:09:51 CST 2021
     */
    private Byte status;

    /**
     * 备注
     *
     * @mbg.generated Thu Jan 07 13:09:51 CST 2021
     */
    private String remark;

    /**
     * 卖家
     *
     * @mbg.generated Thu Jan 07 13:09:51 CST 2021
     */
    private Long user1Id;

    /**
     * 买家
     *
     * @mbg.generated Thu Jan 07 13:09:51 CST 2021
     */
    private Long user2Id;

    /**
     * 被交换的物品
     *
     * @mbg.generated Thu Jan 07 13:09:51 CST 2021
     */
    private Long goodsId;



    /**
     * 交换的物品
     *
     * @mbg.generated Thu Jan 07 13:09:51 CST 2021
     */
    private Long exchangeGoodsId;

    /**
     * 
     *
     * @mbg.generated Thu Jan 07 13:09:51 CST 2021
     */
    private LocalDateTime createTime;

    /**
     * 
     *
     * @mbg.generated Thu Jan 07 13:09:51 CST 2021
     */
    private LocalDateTime endTime;

    /**
     * 
     *
     * @mbg.generated Thu Jan 07 13:09:51 CST 2021
     */
    private String detailedAddress;

    /**
     * 
     *
     * @mbg.generated Thu Jan 07 13:09:51 CST 2021
     */
    private LocalDateTime detailedDatetime;

    /**
     * 
     *
     * @mbg.generated Thu Jan 07 13:09:51 CST 2021
     */
    @TableLogic
    private Byte deleted;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table trans_record
     *
     * @mbg.generated Thu Jan 07 13:09:51 CST 2021
     */
    private static final long serialVersionUID = 1L;
}