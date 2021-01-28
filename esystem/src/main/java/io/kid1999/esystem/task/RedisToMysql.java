package io.kid1999.esystem.task;

import io.kid1999.esystem.dao.UserDao;
import io.kid1999.esystem.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author kid1999
 * @create 2021-01-28 17:12
 * @description 设置redis定时持久化到Mysql
 **/

@Component
public class RedisToMysql {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private UserDao userDao;

    public void saveUserLoginTimesToSQL(){

    }

}