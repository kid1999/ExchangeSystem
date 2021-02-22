package io.kid1999.esystem.api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import io.kid1999.esystem.dao.CollectionDao;
import io.kid1999.esystem.entity.Collection;
import io.kid1999.esystem.entity.Goods;
import io.kid1999.esystem.service.UserService;
import io.kid1999.esystem.utils.KafkaUtil;
import io.kid1999.esystem.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

/**
 * @author kid1999
 * @create 2021-01-03 17:58
 * @description 商品收藏管理
 **/
@Slf4j
@RestController @RequestMapping("/collection")
@Api(tags = "商品收藏管理")
public class CollectionApi {
    @Autowired
    private CollectionDao collectionDao;

    @Resource
    private KafkaUtil kafkaUtil;

    @Resource
    private UserService userService;

    @Transactional
    @PostMapping("")
    @ApiOperation("新建收藏")
    Result insertCollection(@RequestBody HashMap<String,Long> map,
                            Principal principal){
        log.info("新建收藏");
        Long goodsId = map.get("goodsId");
        Long userId = map.get("userId");
        System.out.println(goodsId + " " + userId);
        if(goodsId == null || userId == null){
            return new Result().failed("收藏失败");
        }
        QueryWrapper<Collection> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userId).eq("goods_id",goodsId);
        Collection one = collectionDao.selectOne(wrapper);
        if(one == null){
            Collection collection = new Collection();
            collection.setUserId(userId);
            collection.setGoodsId(goodsId);
            collection.setCreateDate(LocalDateTime.now());
            collection.setDeleted((byte) 0);
            collectionDao.insert(collection);
            kafkaUtil.send2Spark(userService.findUserIdByName(principal.getName()),goodsId,5.0);
            return new Result(200,"收藏成功");
        }else{
            if(one.getDeleted() != 0){
                one.setDeleted((byte) 0);
                collectionDao.updateById(one);
                return new Result(200,"收藏成功");
            }
        }
        return new Result(200,"收藏成功");
    }

    @DeleteMapping("")
    @ApiOperation("删除收藏")
    Result deleteCollection(@RequestBody HashMap<String,Long> map){
        Long goodsId = map.get("goodsId");
        Long userId = map.get("userId");
        if(goodsId == null || userId == null){
            return new Result().failed("收藏失败");
        }
        log.info("删除收藏 " + goodsId + " " + userId);
        UpdateWrapper<Collection> wrapper = new UpdateWrapper<>();
        wrapper.set("deleted",1).eq("user_id",userId).eq("goods_id",goodsId);
        collectionDao.update(new Collection(),wrapper);
        return new Result(200,"删除成功！");
    }

    @PutMapping("")
    @ApiOperation("更新收藏")
    Result updateCollection(Collection collection){
        log.info("更新收藏 ");
        collectionDao.updateById(collection);
        return new Result(200,"更新成功！");
    }

    @GetMapping("/{userId}")
    @ApiOperation("查询某人的收藏")
    Result getCollection(@PathVariable long userId){
        log.info("查询某人的收藏 " + userId);
        List<Goods> collections = collectionDao.selectCollectionsByUserId(userId);
        return new Result(200,"查找成功！",collections);
    }
}