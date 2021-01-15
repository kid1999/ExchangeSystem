package io.kid1999.esystem.api;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.kid1999.esystem.dao.AddressDao;
import io.kid1999.esystem.dao.ContactWayDao;
import io.kid1999.esystem.dao.UserDao;
import io.kid1999.esystem.entity.Address;
import io.kid1999.esystem.entity.ContactWay;
import io.kid1999.esystem.entity.User;
import io.kid1999.esystem.utils.RedisUtil;
import io.kid1999.esystem.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
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
    private AddressDao addressDao;

    @Autowired
    private ContactWayDao contactWayDao;

    @Autowired
    private RedisUtil redisUtil;

    @PostMapping("/register")
    @ApiOperation("注册新用户")
    public Result register(@RequestBody HashMap<String,String> map) {
        System.out.println(map);
        Long addressId = checkAndSaveAddress(map);
        Long contactWayId = saveContactWay(map);
        String avatarUrl = map.get("avatarUrl");
        if(StrUtil.isEmpty(avatarUrl)){
            avatarUrl = DEFAULT_AVATAR_URL;
        }
        User user = new User();
        user.setAddressId(addressId);
        user.setContactWayId(contactWayId);
        user.setAvatarUrl(avatarUrl);
        user.setUserName(map.get("userName"));
        user.setUserPwd(map.get("userPwd"));
        user.setSignature(map.get("signature"));
        user.setCreateTime(LocalDateTime.now());
        user.setLastLoginTime(LocalDateTime.now());
        user.setLoginTimes(0L);
        userDao.insert(user);
        return new Result<>().success("注册成功！");
    }


    @PostMapping("/login")
    @ApiOperation("登录")
    public Result login(@RequestBody Map<String,String> map) {
        String userName = map.get("userName");
        String userPwd = map.get("userPwd");
        QueryWrapper<User> wrapper = new QueryWrapper();
        wrapper.eq("user_name",userName);
        User user = userDao.selectOne(wrapper);
        if(user != null && StrUtil.equals(userPwd,user.getUserPwd())){
            user.setUserPwd("");
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
    public Result<User> getUser(@PathVariable int id) {
        User user = userDao.selectById(id);
        if(user == null) {
            return new Result<>().failed("用户不存在！");
        } else {
            user.setUserPwd("");
            return new Result<>(200,"获取数据成功！",user);
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
        wrapper.eq("user_name",map.get("userName"));
        User user = userDao.selectOne(wrapper);
        if(user == null) {
            return new Result().success();
        }
        else{
            return new Result().failed("该用户名已被占用");
        }
    }

    /**
     * 核查 保存 地址信息
     */
    private Long checkAndSaveAddress(HashMap<String,String> map){
        String address = map.get("address");
        String province = map.get("province");
        String city = map.get("city");
        String area = map.get("area");
        QueryWrapper<Address> wrapper = new QueryWrapper<>();
        wrapper.eq("address",address);
        Address one = addressDao.selectOne(wrapper);
        if(StrUtil.isEmpty(address)){
            return 0L;
        }else if(one != null){
            return one.getId();
        }else {
            Address obj = new Address();
            obj.setAddress(address);
            obj.setProvince(province);
            obj.setCity(city);
            obj.setArea(area);
            addressDao.insert(obj);
            return obj.getId();
        }
    }

    /**
     * 保存联系方式
     */
    private Long saveContactWay(HashMap<String,String> map){
        String email = map.get("email");
        String phone = map.get("phone");
        String qq = map.get("email");
        if(StrUtil.isEmpty(phone)){
            return 0L;
        }{
            ContactWay obj = new ContactWay();
            obj.setEmail(email);
            obj.setPhone(phone);
            obj.setQq(qq);
            contactWayDao.insert(obj);
            return obj.getId();
        }
    }

}