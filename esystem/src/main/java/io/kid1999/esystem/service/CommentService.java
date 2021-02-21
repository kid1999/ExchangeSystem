package io.kid1999.esystem.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author kid1999
 * @create 2021-02-21 19:54
 * @description TODO
 **/
@Service
public class CommentService {

    @Resource
    private RedisTemplate redisTemplate;

    private static final String COMMENT_NUM = "commentNum::";

    /**
     * 增加消息个数
     * @param userId
     */
    public void addMsg(Long userId){
        if(redisTemplate.opsForValue().get(COMMENT_NUM + userId) == null){
            redisTemplate.opsForValue().set(COMMENT_NUM + userId,1);
        }else {
            redisTemplate.opsForValue().increment(COMMENT_NUM + userId,1);
        }
    }

    /**
     * 减少消息个数
     * @param userId
     * @param num
     */
    public void reduceMsg(Long userId,int num){
        redisTemplate.opsForValue().decrement(COMMENT_NUM + userId,num);
    }

    /**
     * 获取user的消息数量
     * @param userId
     * @return
     */
    public int getUsersMsg(Long userId){
        return (int) redisTemplate.opsForValue().get(COMMENT_NUM + userId);
    }



}