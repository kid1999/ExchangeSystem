package io.kid1999.esystem.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.kid1999.esystem.entity.Goods;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

/**
 * @author kid1999
 * @Entity io.kid1999.esystem.entity.Goods
 */


@Mapper
@Repository
public interface GoodsDao extends BaseMapper<Goods> {

    /**
     * 查找所有goods
     * @return
     */
    @Select("SELECT\n" +
            "g.id,\n" +
            "g.user_id,\n" +
            "u.user_name,\n" +
            "g.goods_name,\n" +
            "g.img_url,\n" +
            "g.want_goods_id,\n" +
            "g.remarks,\n" +
            "g.goods_status,\n" +
            "g.number_of_clicked,\n" +
            "g.create_date,\n" +
            "g.deleted,\n" +
            "g.price,\n" +
            "g2.goods_name AS want_goods_name,\n" +
            "g.goods_condition,\n" +
            "g.description,\n" +
            "g.address_id,\n" +
            "a.address\n" +
            "FROM\n" +
            "goods AS g\n" +
            "LEFT JOIN `user` AS u ON u.id = g.user_id\n" +
            "LEFT JOIN goods AS g2 ON g.want_goods_id = g2.id\n" +
            "LEFT JOIN address AS a ON g.address_id = a.id\n" +
            "WHERE g.deleted = 0;")
    List<HashMap<String,String>> findAll();


    /**
     * 查找所有goods by page
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Select("SELECT\n" +
            "g.id,\n" +
            "g.user_id,\n" +
            "u.user_name,\n" +
            "g.goods_name,\n" +
            "g.img_url,\n" +
            "g.want_goods_id,\n" +
            "g.remarks,\n" +
            "g.goods_status,\n" +
            "g.number_of_clicked,\n" +
            "g.create_date,\n" +
            "g.deleted,\n" +
            "g.price,\n" +
            "g2.goods_name AS want_goods_name,\n" +
            "g.goods_condition,\n" +
            "g.description,\n" +
            "g.address_id,\n" +
            "a.address\n" +
            "FROM\n" +
            "goods AS g\n" +
            "LEFT JOIN `user` AS u ON u.id = g.user_id\n" +
            "LEFT JOIN goods AS g2 ON g.want_goods_id = g2.id\n" +
            "LEFT JOIN address AS a ON g.address_id = a.id\n" +
            "WHERE g.deleted = 0\n")
    IPage<HashMap<String,String>> findAllByPage(Page<?> page);


    /**
     * 通过name查找goods by page
     * @param goodsName
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Select("SELECT\n" +
            "g.id,\n" +
            "g.user_id,\n" +
            "u.user_name,\n" +
            "g.goods_name,\n" +
            "g.img_url,\n" +
            "g.want_goods_id,\n" +
            "g.remarks,\n" +
            "g.goods_status,\n" +
            "g.number_of_clicked,\n" +
            "g.create_date,\n" +
            "g.deleted,\n" +
            "g.price,\n" +
            "g2.goods_name AS want_goods_name,\n" +
            "g.goods_condition,\n" +
            "g.description,\n" +
            "g.address_id,\n" +
            "a.address\n" +
            "FROM\n" +
            "goods AS g\n" +
            "LEFT JOIN `user` AS u ON u.id = g.user_id\n" +
            "LEFT JOIN goods AS g2 ON g.want_goods_id = g2.id\n" +
            "LEFT JOIN address AS a ON g.address_id = a.id\n" +
            "WHERE g.deleted = 0 and g.goods_name like  CONCAT('%',#{goodsName},'%') \n")
    IPage<HashMap<String,String>> findGoodsByNameAndPage(Page<?> page,String goodsName);
}
