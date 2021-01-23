package io.kid1999.esystem.handler;

import io.kid1999.esystem.dao.UserDao;
import io.kid1999.esystem.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

/**
 * @author kid1999
 * @create 2021-01-21 18:05
 * @description 认证成功 监听器
 **/
@Slf4j
@Component
public class AuthencationSuccessListener implements ApplicationListener<AuthenticationSuccessEvent> {

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        //用户通过输入用户名和密码登录成功
        Authentication authentication = event.getAuthentication();
        Object o = authentication.getPrincipal();
        if(o instanceof User){
            User auth = (User) o;
            log.info(auth.getUsername() + "  登录成功");
        }else {
            log.info("验证成功");
        }

    }
}