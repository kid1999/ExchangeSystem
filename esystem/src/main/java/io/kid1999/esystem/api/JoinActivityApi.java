package io.kid1999.esystem.api;

import io.kid1999.esystem.dao.JoinActivityDao;
import io.kid1999.esystem.entity.JoinActivity;
import io.kid1999.esystem.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashMap;

/**
 * @author kid1999
 * @create 2021-01-18 18:14
 * @description TODO
 **/
@Slf4j
@RestController
@RequestMapping("/joinActivity")
@Api(tags = "活动管理操作")
public class JoinActivityApi {
    @Resource
    private JoinActivityDao joinActivityDao;

    @Resource
    private RedisTemplate redisTemplate;

    @PostMapping("")
    @ApiOperation("加入活动")
    Result insertActivity(@RequestBody HashMap<String,Long> map){
        log.info("加入活动");
        JoinActivity joinActivity = new JoinActivity();
        joinActivity.setJoinTime(LocalDateTime.now());
        joinActivity.setUserId(map.get("userId"));
        joinActivity.setActivityId(map.get("activityId"));
        joinActivityDao.insert(joinActivity);
        return new Result().success();
    }

    @DeleteMapping("/{id}")
    @ApiOperation("退出活动")
    Result insertActivity(@PathVariable Long id){
        log.info("退出活动 " + id);
        joinActivityDao.deleteById(id);
        return new Result().success();
    }
}