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
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

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
    private RedisTemplate redisTemplate;

    @Resource
    private KafkaUtil kafkaUtil;



    private final static String GOODS_VIEW = "goodsView";
    private final static String HOT_WORD = "hotWord";

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
    public Page<GoodsEntry> getGoodsByGoodsName(String goodsName,int currentPage,int pageSize) {
        Pageable pageable = PageRequest.of(currentPage, pageSize);
        Page<GoodsEntry> entry = goodsRepository.findAllByGoodsNameLike(goodsName, pageable);
        addHotWord(goodsName);
        return entry;
    }


    /**
     * 设置缓存失效时间，统一为凌晨零点
     */
    public void addHotWord(String hotWord) {
        if(hotWord.length() < 2) {
            return;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        //晚上十二点与当前时间的毫秒差
        Long timeOut = (calendar.getTimeInMillis() - System.currentTimeMillis()) / 1000;
        redisTemplate.expire(HOT_WORD, timeOut, TimeUnit.SECONDS);
        redisTemplate.opsForZSet().incrementScore(HOT_WORD, hotWord, 1); // 加入排序set
    }

    /**
     * 获取热词前五位
     */
    public List<String> getHotWord() {
        List<String> hotWordList = new ArrayList<>();
        Set<ZSetOperations.TypedTuple<Object>> typedTupleSet = redisTemplate.opsForZSet().reverseRangeByScoreWithScores(HOT_WORD,1,100);
        Iterator<ZSetOperations.TypedTuple<Object>> iterator = typedTupleSet.iterator();
        int flag = 0;
        while (iterator.hasNext()){
            flag++;
            ZSetOperations.TypedTuple<Object> typedTuple = iterator.next();
            String hotWord = (String)typedTuple.getValue();
            hotWordList.add(hotWord);
            if ( flag >= 5 ) {
                break;
            }
        }
        return hotWordList;
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