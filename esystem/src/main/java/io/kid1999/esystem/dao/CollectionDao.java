package io.kid1999.esystem.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.kid1999.esystem.entity.Collection;
import io.kid1999.esystem.entity.Goods;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Entity io.kid1999.esystem.entity.Collection
 */
@Mapper
@Repository
public interface CollectionDao extends BaseMapper<Collection> {

    @Select("select goods.id, goods.user_id, goods_name, img_url, goods_condition, description, want, exchange_addr, remarks, goods_status, number_of_clicked, goods.create_date, goods.deleted, price " +
            "from goods left join collection on collection.user_id = goods.user_id where collection.user_id = #{userId} and collection.deleted = 0 and goods.deleted = 0")
    List<Goods> selectCollectionsByUserId(int userId);
}