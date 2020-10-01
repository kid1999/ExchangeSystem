package io.kid1999.esystem.dao;

import io.kid1999.esystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author kid1999
 * @create 2020-10-01 9:53
 * @description userDao
 **/
@Repository
public interface UserDao extends JpaRepository<User,Long> {

    User findUserByName(String name);

}