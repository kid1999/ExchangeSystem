package io.kid1999.esystem.api;

import io.kid1999.esystem.dao.CollectionDao;
import io.kid1999.esystem.entity.Collection;
import io.kid1999.esystem.entity.Goods;
import io.kid1999.esystem.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

/**
 * @author kid1999
 * @create 2021-01-03 17:58
 * @description 商品收藏管理
 **/
@RestController @RequestMapping("/collection")
@Api(tags = "商品收藏管理")
public class CollectionApi {
    @Autowired
    private CollectionDao collectionDao;

    @PostMapping("")
    @ApiOperation("新建收藏")
    Result insertCollection(@RequestBody HashMap<String,Long> map){
        Collection collection = new Collection();
        collection.setUserId( map.get("userId"));
        collection.setGoodsId( map.get("goodsId"));
        collection.setCreateDate(LocalDateTime.now());
        return new Result(200,"创建成功！",collectionDao.insert(collection));
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除收藏")
    Result deleteCollection(@PathVariable int id){
        collectionDao.deleteById(id);
        return new Result(200,"删除成功！");
    }

    @PutMapping("")
    @ApiOperation("更新收藏")
    Result updateCollection(Collection collection){
        collectionDao.updateById(collection);
        return new Result(200,"更新成功！");
    }

    @GetMapping("/{userId}")
    @ApiOperation("查询某人的收藏")
    Result getCollection(@PathVariable int userId){
        List<Goods> collections = collectionDao.selectCollectionsByUserId(userId);
        return new Result(200,"查找成功！",collections);
    }
}