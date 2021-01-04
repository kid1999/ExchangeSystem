package io.kid1999.esystem.api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.kid1999.esystem.dao.DemandDao;
import io.kid1999.esystem.entity.Demand;
import io.kid1999.esystem.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author kid1999
 * @create 2021-01-04 16:03
 * @description 需求商品管理
 **/
@RestController
@RequestMapping("/demand")
@Api(tags = "需求商品管理")
public class DemandApi {

    @Autowired
    private DemandDao demandDao;

    @PostMapping("")
    @ApiOperation("新建需求")
    Result insertDemand(@RequestBody Demand demand){
        demand.setCreateTime(LocalDateTime.now());
        demandDao.insert(demand);
        return new Result().success();
    }

    @PutMapping("")
    @ApiOperation("修改需求")
    Result updateDemand(@RequestBody Demand demand){
        demand.setCreateTime(LocalDateTime.now());
        demandDao.updateById(demand);
        return new Result().success();
    }


    @GetMapping("/{id}")
    @ApiOperation("查询需求")
    Result getDemand(@PathVariable int id){
        Demand demand = demandDao.selectById(id);
        return new Result(200,"获取需求成功！",demand);
    }

    @GetMapping("")
    @ApiOperation("查询所有需求")
    Result getAllDemand(){
        QueryWrapper<Demand> wrapper = new QueryWrapper<>();
        wrapper.select("*");
        List<Demand> demands = demandDao.selectList(wrapper);
        return new Result(200,"获取需求成功！",demands);
    }

    @GetMapping("/user/{id}")
    @ApiOperation("查询user需求")
    Result getUserDemand(@PathVariable int id){
        QueryWrapper<Demand> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",id);
        List<Demand> demands = demandDao.selectList(wrapper);
        return new Result(200,"获取需求成功！",demands);
    }


    @DeleteMapping("/{id}")
    @ApiOperation("删除需求")
    Result deleteDemand(@PathVariable int id){
        demandDao.deleteById(id);
        return new Result().success("删除数据成功！");
    }
}