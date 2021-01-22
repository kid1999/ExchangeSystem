package io.kid1999.esystem.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.kid1999.esystem.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

/**
 * @author kid1999
 * @Entity io.kid1999.esystem.entity.User
 */
@Mapper
@Repository
public interface UserDao extends BaseMapper<User> {

    @Select("SELECT\n" +
            "contact_way.email,\n" +
            "contact_way.other,\n" +
            "contact_way.phone,\n" +
            "contact_way.qq,\n" +
            "contact_way.wechat,\n" +
            "`user`.id,\n" +
            "`user`.address_id,\n" +
            "`user`.avatar_url,\n" +
            "`user`.contact_way_id,\n" +
            "`user`.create_time,\n" +
            "`user`.last_login_time,\n" +
            "`user`.login_times,\n" +
            "`user`.username,\n" +
            "`user`.`password`,\n" +
            "`user`.signature,\n" +
            "`user`.deleted\n" +
            "FROM\n" +
            "`user` ,\n" +
            "contact_way\n" +
            "WHERE\n" +
            "contact_way.id = `user`.contact_way_id AND\n" +
            "`user`.username = #{name}\n" +
            "AND `user`.deleted = 0\n")
    HashMap<String,String> findUserAndContactWayByName(String name);

}
