package io.kid1999.esystem.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author kid1999
 * @create 2020-10-01 9:42
 * @description 联系方式
 **/

@Data
@Table(name = "contact_way")
@Entity
public class ContactWay {
    @Id
    @GeneratedValue  // 指定主键生成策略，默认自增长
    private Long id;

    private String phone;
    private String qq;
    private String email;
    private String wechat;
    private String other;
}