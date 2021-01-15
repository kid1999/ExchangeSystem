package io.kid1999.esystem.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.kid1999.esystem.entity.TransRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

/**
 * @Entity io.kid1999.esystem.entity.TransRecord
 */
@Mapper
@Repository
public interface TransRecordDao extends BaseMapper<TransRecord> {


    /**
     * 查询所有用户的交易申请记录
     * @param userId
     * @return
     */
    @Select("SELECT\n" +
            "t.id,\n" +
            "t.`status`,\n" +
            "t.remark,\n" +
            "t.user1_id,\n" +
            "t.user2_id,\n" +
            "t.goods_id,\n" +
            "t.create_time,\n" +
            "t.end_time,\n" +
            "t.detailed_address,\n" +
            "t.detailed_datetime,\n" +
            "t.deleted,\n" +
            "t.phone,\n" +
            "t.exchange_goods_Id,\n" +
            "t.price,\n" +
            "u.user_name,\n" +
            "g.goods_name,\n" +
            "(select goods_name FROM goods WHERE goods.id = exchange_goods_Id) as exchange_goods_name\n" +
            "FROM\n" +
            "trans_record AS t ,\n" +
            "`user` AS u ,\n" +
            "goods AS g\n" +
            "WHERE\n" +
            "t.user2_id = u.id AND\n" +
            "t.goods_id = g.id AND\n" +
            "t.user1_id = #{userId}\n")
    List<HashMap<String,String>> findAllByUser1Id(Long userId);


    @Select("SELECT\n" +
            "t.id,\n" +
            "t.`status`,\n" +
            "t.remark,\n" +
            "t.user1_id,\n" +
            "t.user2_id,\n" +
            "t.goods_id,\n" +
            "t.create_time,\n" +
            "t.end_time,\n" +
            "t.detailed_address,\n" +
            "t.detailed_datetime,\n" +
            "t.deleted,\n" +
            "t.exchange_goods_Id,\n" +
            "t.price,\n" +
            "t.phone,\n" +
            "u.user_name,\n" +
            "g.goods_name,\n" +
            "(select goods_name FROM goods WHERE goods.id = exchange_goods_Id) as exchange_goods_name\n" +
            "FROM\n" +
            "trans_record AS t ,\n" +
            "`user` AS u ,\n" +
            "goods AS g\n" +
            "WHERE\n" +
            "t.user1_id = u.id AND\n" +
            "t.goods_id = g.id AND\n" +
            "t.user2_id = #{userId}\n")
    List<HashMap<String,String>> findAllByUser2Id(Long userId);
}
