package io.kid1999.esystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;


/**
 * @author kid1999
 * @create 2020-10-01 9:42
 * @description 联系方式
 **/

@Data
@TableName("contact_way")
public class ContactWay {
    @TableId(type = IdType.AUTO)
    private Long id;

    private String phone;
    private String qq;
    private String email;
    private String wechat;
    private String other;
}