package io.kid1999.esystem.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.kid1999.esystem.entity.Collection;
import io.kid1999.esystem.entity.Goods;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

/**
 * @Entity io.kid1999.esystem.entity.Collection
 */
@Mapper
@Repository
public interface CollectionDao extends BaseMapper<Collection> {

    @Select("select goods.id, goods.user_id, goods_name, img_url, goods_condition, description, want, exchange_addr, remarks, goods_status, number_of_clicked, goods.create_date, goods.deleted, price " +
            "from goods left join collection on collection.user_id = goods.user_id where collection.user_id = #{userId} and collection.deleted = 0 and goods.deleted = 0")
    List<Goods> selectCollectionsByUserId(long userId);


    @Select("SELECT\n" +
            "g.id,\n" +
            "g.user_id,\n" +
            "g.goods_name,\n" +
            "g.img_url,\n" +
            "g.goods_condition,\n" +
            "g.description,\n" +
            "g.want_goods_id,\n" +
            "g.address_id,\n" +
            "g.remarks,\n" +
            "g.goods_status,\n" +
            "g.number_of_clicked,\n" +
            "g.create_date,\n" +
            "g2.goods_name AS want_goods_name,\n" +
            "g.deleted,\n" +
            "g.price,\n" +
            "c.goods_id,\n" +
            "c.id as cid,\n" +
            "c.deleted as cdeleted,\n" +
            "u.username,\n" +
            "u.address_id,\n" +
            "a.address\n" +
            "FROM\n" +
            "collection as c LEFT JOIN goods as g ON c.goods_id = g.id \n" +
            "LEFT JOIN `user` as u ON u.id = g.user_id \n" +
            "JOIN address as a ON a.id = g.address_id\n" +
            "LEFT JOIN goods AS g2 ON g.want_goods_id = g2.id\n" +
            "WHERE c.user_id = #{userId} and c.deleted = 0 \n" +
            "and g.goods_name like  CONCAT('%',#{goodsName},'%')\n")
    IPage<HashMap<String,String>> findCollectionGoodsByUserIdAndGoodsName
            (Page<?> page, Long userId,String goodsName);
}
