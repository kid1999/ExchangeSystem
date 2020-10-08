package io.kid1999.esystem.api;

import cn.hutool.core.util.StrUtil;
import io.kid1999.esystem.dao.UserDao;
import io.kid1999.esystem.entity.User;
import io.kid1999.esystem.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author kid1999
 * @create 2020-10-01 10:23
 * @description 用户管理操作
 **/

@RestController @RequestMapping("/user")
@Api(tags = "用户管理操作")
public class UserApi {
    @Resource
    private UserDao userDao;

    @PostMapping("/register")
    @ApiOperation("注册新用户")
    public Result<Integer> register(@RequestParam(value = "id") int integer) {
        System.out.println(integer);
        return new Result<Integer>().success(integer);
    }


    @PostMapping("/login")
    @ApiOperation("登录")
    public Result<Integer> login(@RequestBody Map<String,String> map) {
        String name = map.get("name");
        String pwd = map.get("password");
        return new Result<>().success("login success");
//        User userSql = userDao.findUserByName(name);
//        if(userSql != null && StrUtil.equals(pwd,userSql.getPwd()))
//            return new Result<Integer>().success();
//        else return new Result<Integer>().failed("账号密码不正确!");
    }


    @PutMapping("/info")
    @ApiOperation("修改个人信息")
    public Result<Integer> updateUserInfo(@RequestBody Map<String,String> map) {
        String name = map.get("name");
        String pwd = map.get("password");
        System.out.println(name + " " + pwd);
        return new Result<>().failed("put info");
    }

    @GetMapping("/info")
    @ApiOperation("获取个人信息")
    public Result<Integer> getUserInfo(@RequestParam String name,
                                       @RequestParam String password) {

        System.out.println(name + " " + password);
        return new Result<>().success("success get user info");
    }


    @DeleteMapping("/user")
    @ApiOperation("删除用户")
    public Result<Integer> deleteUser(@RequestBody Map<String,String> map) {
        String name = map.get("name");
        String pwd = map.get("password");
        System.out.println(name + " " + pwd);
        return new Result<>().success();
    }



}