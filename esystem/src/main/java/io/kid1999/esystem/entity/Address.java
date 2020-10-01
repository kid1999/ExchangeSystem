package io.kid1999.esystem.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author kid1999
 * @create 2020-10-01 9:49
 * @description 地址信息
 **/
@Entity
@Data
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue  // 指定主键生成策略，默认自增长
    private Long id;
    
    private String province;
    private String city;
    private String area;
    private String addr;

}