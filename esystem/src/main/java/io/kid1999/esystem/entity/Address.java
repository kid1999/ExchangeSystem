package io.kid1999.esystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author kid1999
 * @create 2020-10-01 9:49
 * @description 地址信息
 **/

@Data
@TableName("address")
public class Address {

    @TableId(type = IdType.AUTO) // 指定主键生成策略，默认自增长
    private Long id;
    
    private String province;
    private String city;
    private String area;
    private String addr;

}