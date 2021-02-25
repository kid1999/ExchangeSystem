package io.kid1999.esystem.utils;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import io.kid1999.esystem.dao.AddressDao;
import io.kid1999.esystem.dao.ContactWayDao;
import io.kid1999.esystem.entity.Address;
import io.kid1999.esystem.entity.ContactWay;
import io.kid1999.esystem.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author kid1999
 * @create 2021-01-18 21:50
 * @description 地址处理工具
 **/
@Slf4j
@Service
public class AddressAndContactWayUtil {

    @Autowired
    private AddressDao addressDao;

    @Autowired
    private ContactWayDao contactWayDao;


    /**
     * 核查 保存 地址信息
     */
    public Long checkAndSaveAddress(HashMap<String,String> map){
        log.info("核查保存地址信息");
        String address = map.get("address");
        String province = map.get("province");
        String city = map.get("city");
        String area = map.get("area");
        Address obj = new Address();
        obj.setAddress(address);
        obj.setProvince(province);
        obj.setCity(city);
        obj.setArea(area);
        QueryWrapper<Address> wrapper = new QueryWrapper<>();
        wrapper.eq("address",address);
        Address one = addressDao.selectOne(wrapper);
        if(map.get("addressId") != null && one == null){
            obj.setId(Long.valueOf(map.get("addressId")));
            addressDao.updateById(obj);
            return obj.getId();
        } else {
            addressDao.insert(obj);
            return obj.getId();
        }
    }

    /**
     * 保存联系方式
     */
    public Long saveContactWay(HashMap<String,String> map){
        log.info("保存联系方式");
        String email = map.get("email");
        String phone = map.get("phone");
        String qq = map.get("qq");
        if(map.get("contactWayId") != null){
            Long contactWayId = Long.valueOf(map.get("contactWayId"));
            ContactWay obj = new ContactWay();
            obj.setEmail(email);
            obj.setPhone(phone);
            obj.setQq(qq);
            obj.setId(contactWayId);
            contactWayDao.updateById(obj);
            return contactWayId;
        }else {
            ContactWay obj = new ContactWay();
            obj.setEmail(email);
            obj.setPhone(phone);
            obj.setQq(qq);
            contactWayDao.insert(obj);
            return obj.getId();
        }
    }

    /**
     * 获取用户的其他信息
     * @param user
     * @return
     */
    public List<Object> getContactWayAndAddress(User user){
        Address address = addressDao.selectById(user.getAddressId());
        ContactWay contactWay = contactWayDao.selectById(user.getContactWayId());
        List<Object> data = new ArrayList<>();
        data.add(user);
        data.add(address);
        data.add(contactWay);
        return data;
    }


    /**
     *
     */
    public Long findGithubOrSave(JSONObject jsonObject){
        System.out.println(jsonObject.get("avatar"));
        System.out.println(jsonObject.get("uuid"));
        return 0L;
    }


}