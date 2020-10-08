package io.kid1999.esystem.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author kid1999
 * @create 2020-10-01 9:21
 * @description 用户信息
 **/

@ApiModel
@Entity
@Data
@Table(name = "auth_user")
@Accessors(chain = true)
//逻辑删除注解，删除sql变成了update
@SQLDelete(sql = "update users set deleted = 1 where id = ?")
//where条件带上了逻辑删除条件
@Where(clause = "deleted = 0")
public class User {
    @Id
    @GeneratedValue
    @ApiModelProperty("user id")
    private Long id;
    @Column(length = 32)
    @ApiModelProperty("user name")
    private String name;
    @Column(length = 32)
    @ApiModelProperty("user password")
    private String pwd;
    @ApiModelProperty("user address")
    private Long addressId;
    @ApiModelProperty("user contactWay")
    private Long contactWayId;
    @ApiModelProperty("user avatar")
    private String avatarUrl;
    @ApiModelProperty("user signature")
    private String signature;
    @ApiModelProperty("user createTime")
    private LocalDateTime createTime;
    @ApiModelProperty("user last login time")
    private LocalDateTime lastLoginTime;
    @ApiModelProperty("user login times")
    private Long loginTimes;
    //逻辑删除（0 未删除、1 删除）
    private Integer deleted = 0;


    // OnToOne 维护端
    @OneToOne
    @JoinColumn(name = "addressId",insertable = false,updatable = false,unique = true) // 查询唯一
    private Address address;

    // OnToOne 维护端
    @OneToOne
    @JoinColumn(name = "contactWayId",insertable = false,updatable = false,unique = true) // 查询唯一
    private ContactWay contactWay;

}