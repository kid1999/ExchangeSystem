package io.kid1999.esystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @TableName trans_

record
 */
@Data
public class TransRecord implements Serializable {
    /**
     * 
     *
     * @mbg.generated Mon Jan 04 18:02:29 CST 2021
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 
     *
     * @mbg.generated Mon Jan 04 18:02:29 CST 2021
     */
    private LocalDateTime createDate;

    /**
     * 交易状态
     *
     * @mbg.generated Mon Jan 04 18:02:29 CST 2021
     */
    private Byte status;

    /**
     * 备注
     *
     * @mbg.generated Mon Jan 04 18:02:29 CST 2021
     */
    private String remark;

    /**
     * 卖家
     *
     * @mbg.generated Mon Jan 04 18:02:29 CST 2021
     */
    private Long user1Id;

    /**
     * 买家
     *
     * @mbg.generated Mon Jan 04 18:02:29 CST 2021
     */
    private Long user2Id;

    /**
     * 货物
     *
     * @mbg.generated Mon Jan 04 18:02:29 CST 2021
     */
    private Long goodsId;

    /**
     * 
     *
     * @mbg.generated Mon Jan 04 18:02:29 CST 2021
     */
    @TableLogic
    private Byte deleted;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table trans_

record
     *
     * @mbg.generated Mon Jan 04 18:02:29 CST 2021
     */
    private static final long serialVersionUID = 1L;
}