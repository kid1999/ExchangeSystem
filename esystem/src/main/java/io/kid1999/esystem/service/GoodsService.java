package io.kid1999.esystem.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.kid1999.esystem.dao.GoodsDao;
import io.kid1999.esystem.entity.Goods;
import io.kid1999.esystem.es.entry.GoodsEntry;
import io.kid1999.esystem.es.entry.RecommendationGoodsEntry;
import io.kid1999.esystem.es.repository.GoodsIdRepository;
import io.kid1999.esystem.es.repository.GoodsRepository;
import io.kid1999.esystem.utils.KafkaUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

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
    private GoodsIdRepository goodsIdRepository;

    @Resource
    private GoodsDao goodsDao;

    @Resource
    private RedisTemplate<String,Long> redisTemplate;

    @Resource
    private KafkaUtil kafkaUtil;



    private final static String GOODS_VIEW = "goodsView";

    /**
     * 当商品点击，收藏时 将数据存储到数据集
     * 当商品搜索时，回传 推荐的商品id列表 猜你喜欢
     */



    /**
     * 删除
     */
    public void deleteGoods(Long goodsId){
        goodsDao.deleteById(goodsId);
        goodsRepository.deleteById(goodsId);
    }


    /**
     * 更新
     */
    public void updateGoods(Goods goods){
        goodsDao.updateById(goods);
        GoodsEntry entry = GoodsEntry.parse2GoodsEntry(goods);
        goodsRepository.save(entry);
    }

    /**
     *  根据id 获取信息
     */
    public GoodsEntry getGoods(Long userId,Long goodsId){
        Optional<GoodsEntry> entry = goodsRepository.findById(goodsId);
        redisTemplate.opsForHash().increment(GOODS_VIEW,goodsId + "",1L);
        kafkaUtil.send2Spark(userId,goodsId,3.0);
        return entry.get();
    }

    /**
     *  搜索
     */
    public Page<GoodsEntry> getGoodsByGoodsName(String goodsName,int currentPage,int pageSize){
        Pageable pageable = PageRequest.of(currentPage, pageSize);
        Page<GoodsEntry> entry = goodsRepository.findAllByGoodsNameLike(goodsName, pageable);
        return entry;
    }

    /**
     * 猜你喜欢,每日推荐50个 ？
     */
    public List<Goods> findYourLove(Long userId){
        Optional<RecommendationGoodsEntry> entryOptional = goodsIdRepository.findById(userId);
        RecommendationGoodsEntry data = entryOptional.get();
        QueryWrapper<Goods> wrapper = new QueryWrapper<>();
        wrapper.in("id",data.getGoodsId());
        List<Goods> goods = goodsDao.selectList(wrapper);
        return goods;
    }



}