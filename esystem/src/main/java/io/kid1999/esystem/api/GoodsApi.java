package io.kid1999.esystem.api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.kid1999.esystem.dao.GoodsDao;
import io.kid1999.esystem.entity.Goods;
import io.kid1999.esystem.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author kid1999
 * @create 2021-01-03 16:12
 * @description 货物接口
 **/

@RestController
@RequestMapping("/goods")
@Api(tags = "货物管理操作")
public class GoodsApi {

    @Autowired
    private GoodsDao goodsDao;

    @PostMapping("")
    @ApiOperation("创建货物信息")
    Result insertGoods(@RequestBody Goods goods){
        System.out.println(goods);
        int status = goodsDao.insert(goods);
        return new Result(status,"创建成功！");
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除货物信息")
    Result deleteGoods(@PathVariable Long id){
        int status = goodsDao.deleteById(id);
        return new Result(200,"删除成功！");
    }

    @PutMapping("")
    @ApiOperation("修改货物信息")
    Result updateGoods(@RequestBody Goods goods){
        int status = goodsDao.insert(goods);
        return new Result(status,"");
    }

    @GetMapping("/{id}")
    @ApiOperation("获取货物信息")
    Result getGoods(@PathVariable Long id){
        Goods data = goodsDao.selectById(id);
        return new Result(200,"获取数据成功！",data);
    }

    @GetMapping("")
    @ApiOperation("获取所有货物信息")
    Result getAllGoods(@RequestParam(value = "page_size") int pageSize,
                       @RequestParam(value = "current_page") int currentPage){
        Page<Goods> page = new Page<>(currentPage,pageSize);
        QueryWrapper<Goods> wrapper = new QueryWrapper();
        wrapper.select("*");
        Page<Goods> data = goodsDao.selectPage(page,wrapper);
        return new Result(200,"获取数据成功！",data);
    }


}