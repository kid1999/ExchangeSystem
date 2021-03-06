package io.kid1999.esystem.task;

import io.kid1999.esystem.dao.GoodsDao;
import io.kid1999.esystem.es.entry.GoodsEntry;
import io.kid1999.esystem.es.repository.GoodsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author kid1999
 * @create 2021-01-28 20:38
 * @description mysql 到 es
 **/
@Slf4j
@Component
public class MysqlToES {

    public static final String LAST_GOODS_ID = "lastSaveId";

    @Resource
    private GoodsDao goodsDao;

    @Resource
    private GoodsRepository goodsRepository;

    @Resource
    private RedisTemplate redisTemplate;


    // 定时30s 增量迁移到ES
    @Scheduled(fixedRate = 300000)
    public void transDataToEs(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startTime = LocalDateTime.now();
        log.info("time:{}，开始执行Mysql数据持久化到ES任务", startTime.format(formatter));
        long lastTransGoodsId = 1L;
        if(redisTemplate.opsForValue().get(LAST_GOODS_ID) != null){
            String o = redisTemplate.opsForValue().get(LAST_GOODS_ID).toString();
            lastTransGoodsId = Long.parseLong(o);
        }
        List<GoodsEntry> goodsEntryList = goodsDao.findGoodsSinceId(lastTransGoodsId);
        if(goodsEntryList.size() == 0){
            log.info("此次未有数据更新操作！");
        }else{
            // 分批次提交
            for (int i = 0; i < goodsEntryList.size() ; i+= 100) {
                List<GoodsEntry> data = goodsEntryList.subList(i,Math.min(i+100,goodsEntryList.size()));
                goodsRepository.saveAll(data);
            }
            lastTransGoodsId = goodsEntryList.get(goodsEntryList.size()-1).getId();
            redisTemplate.opsForValue().set(LAST_GOODS_ID,lastTransGoodsId+1);
        }
        LocalDateTime endTime = LocalDateTime.now();
        log.info("time:{}，执行结束Mysql数据持久化到ES任务, 共转移{}条数据，耗时: {}s",
                endTime.format(formatter),goodsEntryList.size(),endTime.getSecond()-startTime.getSecond());
    }

}