package io.kid1999.esystem.api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.kid1999.esystem.dao.GoodsDao;
import io.kid1999.esystem.entity.Goods;
import io.kid1999.esystem.es.entry.GoodsEntry;
import io.kid1999.esystem.service.GoodsService;
import io.kid1999.esystem.service.UserService;
import io.kid1999.esystem.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author kid1999
 * @create 2021-01-03 16:12
 * @description 货物接口
 **/
@Slf4j
@RestController
@RequestMapping("/goods")
@Api(tags = "货物管理操作")
public class GoodsApi {

    @Resource
    private GoodsDao goodsDao;

    @Resource
    private GoodsService goodsService;

    @Resource
    private UserService userService;


    @PostMapping("")
    @ApiOperation("创建货物信息")
    Result insertGoods(@RequestBody Goods goods){
        log.info("创建货物信息");
        goods.setCreateDate(LocalDateTime.now());
        int status = goodsDao.insert(goods);
        return new Result(status,"创建成功！");
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除货物信息")
    Result deleteGoods(@PathVariable Long id){
        log.info("删除货物信息 " + id);
        goodsService.deleteGoods(id);
        return new Result(200,"删除成功！");
    }


    @PutMapping("")
    @ApiOperation("修改货物信息")
    Result updateGoods(@RequestBody GoodsEntry goods){
        log.info("修改货物信息 " + goods.getId());
        goodsService.updateGoods(goods);
        return new Result().success();
    }

    @GetMapping("/{id}")
    @ApiOperation("获取货物信息")
    Result getGoods(@PathVariable Long id,
                    Principal principal){
        log.info("获取货物信息 " + id);
        Long userId = userService.findUserIdByName(principal.getName());
        GoodsEntry data = goodsService.getGoods(userId,id);
        return new Result(200,"获取数据成功！",data);
    }


    @GetMapping("/search")
    @ApiOperation("通过name搜索货物信息")
    Result getGoodsByGoodsName(@RequestParam(value = "goodsName") String goodsName,
                               @RequestParam(value = "page_size") int pageSize,
                               @RequestParam(value = "current_page") int currentPage){
        log.info("通过name搜索货物信息 " + goodsName);
        org.springframework.data.domain.Page<GoodsEntry> data = goodsService.getGoodsByGoodsName(goodsName,currentPage,pageSize);
        return new Result(200,"查询成功！",data);
    }

    @GetMapping("/user/{userId}")
    @ApiOperation("通过userId查找货物信息")
    Result getGoodsByUserId(@PathVariable long userId){
        QueryWrapper<Goods> wrapper = new QueryWrapper<>();
        log.info("通过userId查找货物信息 " + userId);
        wrapper.eq("user_id",userId);
        List<Goods> goods = goodsDao.selectList(wrapper);
        return new Result(200,"查询成功！",goods);
    }

    @GetMapping("/collection")
    @ApiOperation("查询user收藏的goods")
    Result getGoodsByGoodsName(@RequestParam(value = "page_size") int pageSize,
                               @RequestParam(value = "current_page") int currentPage,
                               @RequestParam(value = "userId") Long userId){
        log.info("查询user收藏的goods " + userId);
        Page<HashMap<String,String>> page = new Page<>();
        page.setSize(pageSize);
        page.setCurrent(currentPage);
        IPage<HashMap<String, String>> data = goodsDao.findGoodsAndCollectionByUserId(page, userId);
        return new Result(200,"查询成功！",data);
    }


    @GetMapping("")
    @ApiOperation("获取所有货物信息")
    @Cacheable(cacheNames="goodsList#3600",key="#currentPage")
    public Result getAllGoods(@RequestParam(value = "page_size") int pageSize,
                              @RequestParam(value = "current_page") int currentPage){
        log.info("获取所有货物信息 " + currentPage);
        Page<HashMap<String,String>> page = new Page<>();
        page.setSize(pageSize);
        page.setCurrent(currentPage);
        IPage<HashMap<String, String>> data = goodsDao.findAllGoodsByPage(page);
        return new Result(200,"获取数据成功！",data);
    }


    @GetMapping("/recommendation/{userId}")
    @ApiOperation("实时推荐")
    public Result findYourLove(@PathVariable Long userId){
        log.info("实时推荐 " + userId);
        List<Goods> goods = new ArrayList<>();
        try {
            goods = goodsService.findYourLove(userId);
        }catch (Exception e){
            log.info("实时推荐error");
            return new Result(400,"获取数据失败！");
        }
        return new Result(200,"获取数据成功！",goods);
    }

    @GetMapping("/hotwords")
    @ApiOperation("搜索热词")
    public Result findHotWords(){
        log.info("搜索热词 ");
        List<String> hotWord = goodsService.getHotWord();
        return new Result(200,"获取数据成功！",hotWord);
    }

}