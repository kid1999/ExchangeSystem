package io.kid1999.esystem;

import cn.hutool.core.util.RandomUtil;
import io.kid1999.esystem.dao.UserDao;
import io.kid1999.esystem.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author kid1999
 * @create 2021-02-09 10:13
 * @description 随机生成user
 **/

@Component
public class MakeRandomUser {

    @Autowired
    private UserDao userDao;

    void makeUser(int number){
        for (int i = 0; i <number ; i++) {
            User user = new User();
            user.setUsername("user-" + RandomUtil.randomString(6));
            user.setPassword("$2a$10$LXlVXynPlzXc9Jicn6B1Iu1xMNaG.yTZD5BpcyK/2ryHZqXNUkgJu");
            user.setContactWayId(1L);
            user.setSignature("signature-" + RandomUtil.randomString(6));
            user.setAvatarUrl("http://kid1999.top:9000/default/avatar.png");
            user.setAddressId(RandomUtil.randomLong(15));
            user.setLoginTimes(1L);
            user.setCreateTime(LocalDateTime.now());
            user.setLastLoginTime(LocalDateTime.now());
            user.setRole("ROLE_user");
            userDao.insert(user);
        }
    }

}