package io.kid1999.esystem.api;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import io.kid1999.esystem.dao.UserDao;
import io.kid1999.esystem.entity.User;
import io.kid1999.esystem.utils.AddressAndContactWayUtil;
import io.kid1999.esystem.utils.EmailUtil;
import io.kid1999.esystem.utils.RedisUtil;
import io.kid1999.esystem.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
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

@Slf4j
@RestController @RequestMapping("/user")
@Api(tags = "用户管理操作")
public class UserApi {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private EmailUtil emailUtil;

    @Autowired
    private AddressAndContactWayUtil addressUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;



    @PostMapping("/register")
    @ApiOperation("注册新用户")
    public Result register(@RequestBody HashMap<String,String> map) {
        log.info("注册新用户 " + map.get("username"));
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
        user.setPassword(passwordEncoder.encode(map.get("password")));
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
        log.info("登录 " + map.get("username"));
        String userName = map.get("username");
        String userPwd = map.get("password");
        QueryWrapper<User> wrapper = new QueryWrapper();
        wrapper.eq("username",userName);
        User user = userDao.selectOne(wrapper);
        if(user != null && StrUtil.equals(userPwd,user.getPassword())){
            user.setPassword("");
            return new Result(200,"登录成功！",user);
        }else {
            return new Result().failed("账号密码不正确!");
        }
    }


    @PutMapping("")
    @ApiOperation("修改个人信息")
    public Result updateUserInfo(@RequestBody HashMap<String,String> map) {
        log.info("修改个人信息 " + map.get("name"));
        Long addressId = addressUtil.checkAndSaveAddress(map);
        Long contactWayId = addressUtil.saveContactWay(map);
        User user = new User();
        if(map.get("avatarUrl") != null){
            user.setAvatarUrl(map.get("avatarUrl"));
        }
        user.setId(Long.valueOf(map.get("id")));
        user.setAddressId(addressId);
        user.setContactWayId(contactWayId);
        user.setUsername(map.get("username"));
        user.setSignature(map.get("signature"));
        userDao.updateById(user);
        return new Result(200,"修改成功！");
    }

    @GetMapping("/{id}")
    @ApiOperation("获取个人信息")
    public Result<User> getUser(@PathVariable long id) {
        log.info("获取个人信息 " + id);
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
        log.info("获取个人信息 " + username);
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
    public Result deleteUser(@PathVariable long id) {
        log.info("删除用户 " + id);
        userDao.deleteById(id);
        return new Result().success("删除成功！");
    }

    @GetMapping("/loginTimes/{id}")
    @ApiOperation("获取用户登录次数")
    @Cacheable(value="userLoginTimes",key="#id")
    public Result getUserLoginTimes(@PathVariable long id) {
        log.info("获取用户登录次数 " + id);
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
        log.info("检查用户名是否使用 " + map.get("username"));
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username",map.get("username"));
        User user = userDao.selectOne(wrapper);
        if(user == null) {
            return new Result().success();
        }
        else{
            return new Result().failed("该用户名已被占用");
        }
    }

    // 验证码失效时间
    private final Long CHECKCODE_EXPIRE_DATE = 15*60L;
    private final static String CHECK_CODE = "CheckCode::";

    @PostMapping("/sendCheckCode")
    @ApiOperation("获取邮件验证码")
    Result sendCheckCode(@RequestBody HashMap<String,String> map) {
        log.info("获取邮件验证码 " + map.get("username"));
        String username = map.get("username");
        HashMap<String,String> usermap = userDao.findUserAndContactWayByName(username);
        String email = usermap.get("email");
        if(StrUtil.equals("",email)){
            return new Result().failed("该账户未绑定邮箱，无法修改密码！");
        }else{
            String checkCode = RandomUtil.randomString(6);
            redisUtil.setStringKey(CHECK_CODE + username,checkCode,CHECKCODE_EXPIRE_DATE);
            emailUtil.sendMailCode(email,"Esystem的验证码",checkCode);
            return new Result().success();
        }
    }

    @PostMapping("/changePassword")
    @ApiOperation("修改密码")
    Result changeUserPassword(@RequestBody HashMap<String,String> map) {
        log.info("修改密码 " + map.get("username"));
        String username = map.get("username");
        String checkCode = map.get("checkCode");
        String value = (String) redisUtil.getStringValue(CHECK_CODE + username);
        if(StrUtil.equals("",value)){
            return new Result().failed("验证码已失效");
        }
        if(StrUtil.equals(value,checkCode)){
            UpdateWrapper<User> wrapper = new UpdateWrapper<>();
            wrapper.eq("username",username);
            wrapper.set("password",passwordEncoder.encode(map.get("password")));
            userDao.update(new User(),wrapper);
            redisUtil.deleteKey(username);
            return new Result().success();
        }else{
            return new Result().failed("验证码错误");
        }
    }

}