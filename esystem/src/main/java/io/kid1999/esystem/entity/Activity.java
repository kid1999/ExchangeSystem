package io.kid1999.esystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @TableName activity
 */
@Data
public class Activity implements Serializable {
    /**
     * 
     *
     * @mbg.generated Mon Jan 18 10:27:47 CST 2021
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 
     *
     * @mbg.generated Mon Jan 18 10:27:47 CST 2021
     */
    private String activityContext;

    /**
     * 
     *
     * @mbg.generated Mon Jan 18 10:27:47 CST 2021
     */
    private String activityTitle;

    /**
     * 
     *
     * @mbg.generated Mon Jan 18 10:27:47 CST 2021
     */
    private String activityDescription;

    /**
     * 
     *
     * @mbg.generated Mon Jan 18 10:27:47 CST 2021
     */
    private Long addressId;

    /**
     * 
     *
     * @mbg.generated Mon Jan 18 10:27:47 CST 2021
     */
    private String detailAddress;

    /**
     * 
     *
     * @mbg.generated Mon Jan 18 10:27:47 CST 2021
     */
    private Long createUserId;

    /**
     * 
     *
     * @mbg.generated Mon Jan 18 10:27:47 CST 2021
     */
    private LocalDateTime startTime;

    /**
     * 
     *
     * @mbg.generated Mon Jan 18 10:27:47 CST 2021
     */
    private LocalDateTime endTime;

    /**
     * 
     *
     * @mbg.generated Mon Jan 18 10:27:47 CST 2021
     */
    @TableLogic
    private Byte deleted;

    /**
     * 
     *
     * @mbg.generated Mon Jan 18 10:27:47 CST 2021
     */
    private Long numberOfJoin;

    /**
     *
     *
     * @mbg.generated Mon Jan 18 10:27:47 CST 2021
     */
    private Byte status;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table activity
     *
     * @mbg.generated Mon Jan 18 10:27:47 CST 2021
     */
    private static final long serialVersionUID = 1L;
}