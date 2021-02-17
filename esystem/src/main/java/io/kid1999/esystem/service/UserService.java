package io.kid1999.esystem.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.kid1999.esystem.dao.UserDao;
import io.kid1999.esystem.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author kid1999
 * @create 2021-01-19 17:59
 * @description 比对密码是否匹配
 **/
@Slf4j
@Component
public class UserService implements UserDetailsService {

    @Resource
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("login  " + username);
        User user = findUserByName(username);
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        // 全部赋予user角色
        if(user == null){
            throw new UsernameNotFoundException("用户不存在！");
        }else{
            authorities.add(new SimpleGrantedAuthority(user.getRole()));
            String password = user.getPassword();
            return new org.springframework.security.core.userdetails.User(username,password,authorities);
        }
    }


    public User findUserByName(String username){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username",username);
        return userDao.selectOne(wrapper);
    }


    @Cacheable(value="user",key="#username")
    public Long findUserIdByName(String username){
        User user = findUserByName(username);
        return user.getId();
    }

}