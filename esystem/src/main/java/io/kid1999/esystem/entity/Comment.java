package io.kid1999.esystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @TableName comment
 */
@Data
public class Comment implements Serializable {
    /**
     * 
     *
     * @mbg.generated Mon Jan 04 11:17:25 CST 2021
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 
     *
     * @mbg.generated Mon Jan 04 11:17:25 CST 2021
     */
    private Long user1Id;

    /**
     *
     *
     * @mbg.generated Mon Jan 04 11:17:25 CST 2021
     */
    private Long user2Id;


    /**
     * 
     *
     * @mbg.generated Mon Jan 04 11:17:25 CST 2021
     */
    private Long goodsId;

    /**
     * 
     *
     * @mbg.generated Mon Jan 04 11:17:25 CST 2021
     */
    private LocalDateTime date;

    /**
     * 内容
     *
     * @mbg.generated Mon Jan 04 11:17:25 CST 2021
     */
    private String context;

    /**
     * 状态
     *
     * @mbg.generated Mon Jan 04 11:17:25 CST 2021
     */
    private Byte status;

    /**
     * 
     *
     * @mbg.generated Mon Jan 04 11:17:25 CST 2021
     */
    @TableLogic
    private Byte deleted;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table comment
     *
     * @mbg.generated Mon Jan 04 11:17:25 CST 2021
     */
    private static final long serialVersionUID = 1L;
}