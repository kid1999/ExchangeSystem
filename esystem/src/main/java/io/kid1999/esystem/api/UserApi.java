package io.kid1999.esystem.api;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.kid1999.esystem.dao.UserDao;
import io.kid1999.esystem.entity.Address;
import io.kid1999.esystem.entity.User;
import io.kid1999.esystem.utils.AddressAndContactWayUtil;
import io.kid1999.esystem.utils.RedisUtil;
import io.kid1999.esystem.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static io.kid1999.esystem.common.Constants.DEFAULT_AVATAR_URL;

/**
 * @author kid1999
 * @create 2020-10-01 10:23
 * @description 用户管理操作
 **/

@RestController @RequestMapping("/user")
@Api(tags = "用户管理操作")
public class UserApi {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private AddressAndContactWayUtil addressUtil;

    @PostMapping("/register")
    @ApiOperation("注册新用户")
    public Result register(@RequestBody HashMap<String,String> map) {
        System.out.println(map);
        Long addressId = addressUtil.checkAndSaveAddress(map);
        Long contactWayId = addressUtil.saveContactWay(map);
        String avatarUrl = map.get("avatarUrl");
        if(StrUtil.isEmpty(avatarUrl)){
            avatarUrl = DEFAULT_AVATAR_URL;
        }
        User user = new User();
        user.setAddressId(addressId);
        user.setContactWayId(contactWayId);
        user.setAvatarUrl(avatarUrl);
        user.setUsername(map.get("username"));
        user.setPassword(map.get("password"));
        user.setSignature(map.get("signature"));
        user.setCreateTime(LocalDateTime.now());
        user.setLastLoginTime(LocalDateTime.now());
        user.setLoginTimes(0L);
        userDao.insert(user);
        return new Result<>().success("注册成功！");
    }


    @Deprecated
    @PostMapping("/login")
    @ApiOperation("登录")
    public Result login(@RequestBody Map<String,String> map) {
        String userName = map.get("username");
        String userPwd = map.get("password");
        QueryWrapper<User> wrapper = new QueryWrapper();
        wrapper.eq("username",userName);
        User user = userDao.selectOne(wrapper);
        if(user != null && StrUtil.equals(userPwd,user.getPassword())){
            user.setPassword("");
            redisUtil.incr("userLoginTimes::" + user.getId());
            return new Result(200,"登录成功！",user);
        }else {
            return new Result().failed("账号密码不正确!");
        }
    }


    @PutMapping("")
    @ApiOperation("修改个人信息")
    public Result updateUserInfo(@RequestBody Map<String,String> map) {
        String name = map.get("name");
        String pwd = map.get("password");
        User user = new User();
        Address address = new Address();
        userDao.updateById(user);
        return new Result(200,"修改成功！");
    }

    @GetMapping("/{id}")
    @ApiOperation("获取个人信息")
    public Result<User> getUser(@PathVariable long id) {
        User user = userDao.selectById(id);
        if(user == null) {
            return new Result<>().failed("用户不存在！");
        } else {
            user.setPassword("");
            return new Result<>(200,"获取数据成功！",user);
        }
    }

    @GetMapping("/name/{username}")
    @ApiOperation("获取个人信息")
    public Result<User> getUser(@PathVariable String username) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username",username);
        User user = userDao.selectOne(wrapper);
        if(user != null){
            user.setPassword("");
            return new Result<>(200,"获取数据成功！",user);
        }else {
            return new Result<>().failed("获取数据失败！");
        }
    }


    @DeleteMapping("/{id}")
    @ApiOperation("删除用户")
    public Result deleteUser(@PathVariable int id) {
        userDao.deleteById(id);
        return new Result().success("删除成功！");
    }

    @GetMapping("/loginTimes/{id}")
    @ApiOperation("获取用户登录次数")
    @Cacheable(value="userLoginTimes",key="#id")
    public Result getUserLoginTimes(@PathVariable int id) {
        User user = userDao.selectById(id);
        if(user == null) {
            return new Result().failed("用户不存在！");
        } else {
            return new Result(200,"获取数据成功！",user.getLoginTimes());
        }
    }


    @PostMapping("/checkName")
    @ApiOperation("检查用户名是否使用")
    Result checkNameUsed(@RequestBody HashMap<String,String> map) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username",map.get("userName"));
        User user = userDao.selectOne(wrapper);
        if(user == null) {
            return new Result().success();
        }
        else{
            return new Result().failed("该用户名已被占用");
        }
    }


}