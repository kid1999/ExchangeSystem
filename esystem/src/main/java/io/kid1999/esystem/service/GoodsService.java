package io.kid1999.esystem.service;

import io.kid1999.esystem.dao.GoodsDao;
import io.kid1999.esystem.entity.Goods;
import io.kid1999.esystem.es.entry.GoodsEntry;
import io.kid1999.esystem.es.repository.GoodsRepository;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static io.kid1999.esystem.common.Constants.ES_GOODS_INDEX;

/**
 * @author kid1999
 * @create 2021-01-28 19:21
 * @description 货物服务层
 **/
@Slf4j
@Service
public class GoodsService {

    @Resource
    private GoodsRepository goodsRepository;

    @Resource
    private GoodsDao goodsDao;

    @Resource
    private RestHighLevelClient client;

    @Resource
    private RedisTemplate<String,Long> redisTemplate;

    private final static String GOODS_VIEW = "goodsView";

    /**
     * 搜索
     */
    public List<GoodsEntry> searchGoods(String goodsName){
        return goodsRepository.findAllByGoodsNameLike(goodsName);
    }

    /**
     * 删除
     */
    public void deleteGoods(Long id){
        goodsDao.deleteById(id);
        goodsRepository.deleteById(id);
    }


    /**
     * 更新
     */
    public void updateGoods(Goods goods){
        goodsDao.updateById(goods);
        UpdateRequest request = new UpdateRequest(ES_GOODS_INDEX, goods.getId() + "");
        request.timeout("1s");
        request.doc(goods);
        try {
            UpdateResponse update = client.update(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
    }

    /**
     *  根据id 获取信息
     */
    public GoodsEntry getGoods(Long id){
        Optional<GoodsEntry> entry = goodsRepository.findById(id);
        redisTemplate.opsForHash().increment(GOODS_VIEW,id + "",1L);
        return entry.get();
    }


}