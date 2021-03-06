package io.kid1999.esystem.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.kid1999.esystem.entity.Goods;
import io.kid1999.esystem.es.entry.GoodsEntry;
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
    @Deprecated
    @Select("SELECT\n" +
            "g.id,\n" +
            "g.user_id,\n" +
            "u.username,\n" +
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
     * @param page
     * @return
     */
    @Deprecated
    @Select("SELECT\n" +
            "g.id,\n" +
            "g.user_id as userId,\n" +
            "g.goods_name as goodsName,\n" +
            "g.img_url as imgUrl,\n" +
            "g.goods_condition as goodsCondition,\n" +
            "g.description,\n" +
            "g.want_goods_id as wantGoodsId,\n" +
            "g.remarks,\n" +
            "g.goods_status as goodsStatus,\n" +
            "g.number_of_clicked as numberOfClicked,\n" +
            "g.create_date as createDate,\n" +
            "g.deleted,\n" +
            "g.price,\n" +
            "u.username,\n" +
            "a.address,\n" +
            "g.address_id as addressId,\n" +
            "g2.goods_name as wantGoodsName,\n" +
            "(SELECT collection.deleted FROM collection,goods WHERE collection.goods_id = goods.id and collection.user_id = #{userId} and goods.id = g.id LIMIT 1) as cdeleted\n" +
            "FROM\n" +
            "goods AS g\n" +
            "LEFT JOIN `user` AS u ON u.id = g.user_id\n" +
            "LEFT JOIN goods AS g2 ON g.want_goods_id = g2.id\n" +
            "LEFT JOIN address AS a ON a.id = g.address_id")
    IPage<HashMap<String,String>> findAllByPage(Page<?> page,Long userId);



    /**
     * 查找所有goods by page
     * @param page
     * @return
     */
    @Select("SELECT\n" +
            "g.id,\n" +
            "g.user_id as userId,\n" +
            "g.goods_name as goodsName,\n" +
            "g.img_url as imgUrl,\n" +
            "g.goods_condition as goodsCondition,\n" +
            "g.description,\n" +
            "g.want_goods_id as wantGoodsId,\n" +
            "g.remarks,\n" +
            "g.goods_status as goodsStatus,\n" +
            "g.number_of_clicked as numberOfClicked,\n" +
            "g.create_date as createDate,\n" +
            "g.deleted,\n" +
            "g.price,\n" +
            "u.username,\n" +
            "a.address,\n" +
            "g.address_id as addressId,\n" +
            "g2.goods_name as wantGoodsName \n" +
            "FROM\n" +
            "goods AS g\n" +
            "LEFT JOIN `user` AS u ON u.id = g.user_id\n" +
            "LEFT JOIN goods AS g2 ON g.want_goods_id = g2.id\n" +
            "LEFT JOIN address AS a ON a.id = g.address_id")
    IPage<HashMap<String,String>> findAllGoodsByPage(Page<?> page);



    /**
     * 通过name查找goods by page
     * 被ES取代
     * @param goodsName
     * @param page
     * @return
     */
    @Deprecated
    @Select("SELECT\n" +
            "g.id,\n" +
            "g.user_id,\n" +
            "u.username,\n" +
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


    /**
     * 通过id 查询goods的信息
     * @param id
     * @return
     */
    @Select("SELECT\n" +
            "g.id,\n" +
            "g.user_id,\n" +
            "u.username,\n" +
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
            "WHERE g.deleted = 0 and g.id = #{id}\n")
    HashMap<String,String> findGoodsById(Long id);


    /**
     * 通过userid 查询user收藏的所有goods
     * @param page
     * @param id
     * @return
     */
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
            "WHERE c.user_id = #{id} and c.deleted = 0\n")
    IPage<HashMap<String,String>> findGoodsAndCollectionByUserId(Page<?> page,Long id);




    /**
     * 查询 大于id 的goods
     * @param id
     * @return
     */
    @Select("SELECT\n" +
            "g.id,\n" +
            "g.user_id as userId,\n" +
            "u.username,\n" +
            "g.goods_name as goodsName,\n" +
            "g.img_url as imgUrl,\n" +
            "g.want_goods_id as wantGoodsId,\n" +
            "g.remarks,\n" +
            "g.goods_status as goodsStatus,\n" +
            "g.number_of_clicked as numberOfClicked,\n" +
            "g.create_date as createDate,\n" +
            "g.deleted,\n" +
            "g.price,\n" +
            "g2.goods_name AS wantGoodsName,\n" +
            "g.goods_condition as goodsCondition,\n" +
            "g.description,\n" +
            "g.address_id addressId,\n" +
            "a.address\n" +
            "FROM\n" +
            "goods AS g\n" +
            "LEFT JOIN `user` AS u ON u.id = g.user_id\n" +
            "LEFT JOIN goods AS g2 ON g.want_goods_id = g2.id\n" +
            "LEFT JOIN address AS a ON g.address_id = a.id\n" +
            "WHERE g.deleted = 0 and g.id >= #{id}\n" +
            "order by g.id ASC;")
    List<GoodsEntry> findGoodsSinceId(Long id);



}
