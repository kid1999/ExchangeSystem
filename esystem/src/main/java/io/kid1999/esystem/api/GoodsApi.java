package io.kid1999.esystem.api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.kid1999.esystem.dao.GoodsDao;
import io.kid1999.esystem.entity.Goods;
import io.kid1999.esystem.utils.RedisUtil;
import io.kid1999.esystem.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

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

    @Autowired
    private RedisUtil redisUtil;

    @PostMapping("")
    @ApiOperation("创建货物信息")
    Result insertGoods(@RequestBody Goods goods){
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
        int status = goodsDao.updateById(goods);
        return new Result(status,"");
    }


    @GetMapping("/{id}")
    @ApiOperation("获取货物信息")
    Result getGoods(@PathVariable Long id){
        // 点击量 + 定时刷新到数据库
        redisUtil.incr("goodsView::" + id);
        HashMap<String,String> data = goodsDao.findGoodsById(id);
        return new Result(200,"获取数据成功！",data);
    }


    @GetMapping("/search")
    @ApiOperation("通过name搜索货物信息")
    Result getGoodsByGoodsName(@RequestParam(value = "goodsName") String goodsName,
                               @RequestParam(value = "page_size") int pageSize,
                               @RequestParam(value = "current_page") int currentPage){
        Page<HashMap<String,String>> page = new Page<>();
        page.setSize(pageSize);
        page.setCurrent(currentPage);
        IPage<HashMap<String, String>> goods = goodsDao.findGoodsByNameAndPage(page,goodsName);
        return new Result(200,"查询成功！",goods);
    }

    @GetMapping("/user/{userId}")
    @ApiOperation("通过userId查找货物信息")
    Result getGoodsByUserId(@PathVariable long userId){
        QueryWrapper<Goods> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userId);
        List<Goods> goods = goodsDao.selectList(wrapper);
        return new Result(200,"查询成功！",goods);
    }

    @GetMapping("/collection")
    @ApiOperation("查询user收藏的goods")
    Result getGoodsByGoodsName(@RequestParam(value = "page_size") int pageSize,
                               @RequestParam(value = "current_page") int currentPage,
                               @RequestParam(value = "userId") Long userId){
        Page<HashMap<String,String>> page = new Page<>();
        page.setSize(pageSize);
        page.setCurrent(currentPage);
        IPage<HashMap<String, String>> data = goodsDao.findGoodsAndCollectionByUserId(page, userId);
        return new Result(200,"查询成功！",data);
    }


    @GetMapping("")
    @ApiOperation("获取所有货物信息")
//    @Cacheable(value="goodsList",key="#currentPage-#userId")
    public Result getAllGoods(@RequestParam(value = "page_size") int pageSize,
                              @RequestParam(value = "current_page") int currentPage,
                              @RequestParam(value = "userId") Long userId){
        Page<HashMap<String,String>> page = new Page<>();
        page.setSize(pageSize);
        page.setCurrent(currentPage);
        IPage<HashMap<String, String>> data = goodsDao.findAllByPage(page,userId);
        return new Result(200,"获取数据成功！",data);
    }



}