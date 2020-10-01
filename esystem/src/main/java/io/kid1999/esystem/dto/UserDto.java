package io.kid1999.esystem.dto;

import io.kid1999.esystem.entity.Address;
import io.kid1999.esystem.entity.ContactWay;
import io.kid1999.esystem.entity.User;
import lombok.Data;

/**
 * @author kid1999
 * @create 2020-10-01 10:39
 * @description 前端用户信息
 **/
@Data
public class UserDto {
    private int id;
    private User user;
    private Address address;
    private ContactWay contactWay;
}