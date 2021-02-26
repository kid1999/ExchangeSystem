package io.kid1999.esystem.task;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import io.kid1999.esystem.dao.GoodsDao;
import io.kid1999.esystem.entity.Goods;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

/**
 * @author kid1999
 * @create 2021-01-28 17:12
 * @description 设置redis定时持久化到Mysql
 **/
@Slf4j
@Component
public class RedisToMysql {

    // redis中 goods点击 的key
    private final static String GOODS_VIEW = "goodsView";

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private GoodsDao goodsDao;

    @Scheduled(fixedRate = 300000)
    public void saveusergoodsviewtimes2Db(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startTime = LocalDateTime.now();
        log.info("time:{}，开始执行Redis数据持久化到MySQL任务", startTime.format(formatter));
        Goods g = new Goods();
        Map<String, Integer> hashList = redisTemplate.opsForHash().entries(GOODS_VIEW);
        int length = 0;
        if(hashList.size() == 0){
            log.info("time:{}，","redis中么有数据");
        }else{
            for (Map.Entry<String, Integer> entry : hashList.entrySet()) {
                Long id = Long.valueOf(entry.getKey());
                Integer count = entry.getValue();
                UpdateWrapper<Goods> wrapper = new UpdateWrapper<>();
                wrapper.eq("id",id);
                wrapper.set("number_of_clicked",count);
                goodsDao.update(g,wrapper);
                length++;
            }
        }
        LocalDateTime endTime = LocalDateTime.now();
        log.info("time:{}，结束执行Redis数据持久化到MySQL任务, 共转移{}条数据，耗时: {}s",
                endTime.format(formatter),length,endTime.getSecond()-startTime.getSecond());
    }

}