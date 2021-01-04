package io.kid1999.esystem.api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.kid1999.esystem.dao.ActivityDao;
import io.kid1999.esystem.entity.Activity;
import io.kid1999.esystem.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author kid1999
 * @create 2021-01-04 15:23
 * @description 活动管理操作
 **/
@RestController
@RequestMapping("/activity")
@Api(tags = "活动管理操作")
public class ActivityApi {

    @Autowired
    private ActivityDao activityDao;

    @PostMapping("")
    @ApiOperation("创建活动信息")
    Result insertActivity(Activity activity){
        activity.setCreateTime(LocalDateTime.now());
        activityDao.insert(activity);
        return new Result().success();
    }

    @PutMapping("")
    @ApiOperation("更新活动信息")
    Result updateActivity(Activity activity){
        activity.setCreateTime(LocalDateTime.now());
        activityDao.updateById(activity);
        return new Result().success();
    }

    @GetMapping("/{id}")
    @ApiOperation("获取活动信息")
    Result getActivity(@PathVariable int id){
        Activity activity = activityDao.selectById(id);
        return new Result(200,"获取活动成功！",activity);
    }

    @GetMapping("")
    @ApiOperation("获取所有活动信息")
    Result getAllActivity(){
        QueryWrapper<Activity> wrapper = new QueryWrapper<>();
        wrapper.select("*");
        List<Activity> activitys = activityDao.selectList(wrapper);
        return new Result(200,"获取活动成功！",activitys);
    }

    @GetMapping("/user/{id}")
    @ApiOperation("获取user所有活动信息")
    Result getActivityByUserId(@PathVariable int id){
        QueryWrapper<Activity> wrapper = new QueryWrapper<>();
        wrapper.eq("create_user_id",id);
        List<Activity> activitys = activityDao.selectList(wrapper);
        return new Result(200,"获取活动成功！",activitys);
    }

    @GetMapping("/addr/{id}")
    @ApiOperation("获取address所有活动信息")
    Result getActivityByAddrId(@PathVariable int id){
        QueryWrapper<Activity> wrapper = new QueryWrapper<>();
        wrapper.eq("address_id",id);
        List<Activity> activitys = activityDao.selectList(wrapper);
        return new Result(200,"获取活动成功！",activitys);
    }


    @DeleteMapping("/{id}")
    @ApiOperation("删除活动信息")
    Result deleteActivity(@PathVariable int id){
        activityDao.deleteById(id);
        return new Result().success();
    }
}