package io.kid1999.esystem.utils;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.kid1999.esystem.dao.AddressDao;
import io.kid1999.esystem.dao.ContactWayDao;
import io.kid1999.esystem.entity.Address;
import io.kid1999.esystem.entity.ContactWay;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * @author kid1999
 * @create 2021-01-18 21:50
 * @description 地址处理工具
 **/
@Log
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
        String address = map.get("address");
        String province = map.get("province");
        String city = map.get("city");
        String area = map.get("area");
        QueryWrapper<Address> wrapper = new QueryWrapper<>();
        wrapper.eq("address",address);
        Address one = addressDao.selectOne(wrapper);
        if(StrUtil.isEmpty(address)){
            return 0L;
        }else if(one != null){
            return one.getId();
        }else {
            Address obj = new Address();
            obj.setAddress(address);
            obj.setProvince(province);
            obj.setCity(city);
            obj.setArea(area);
            addressDao.insert(obj);
            return obj.getId();
        }
    }

    /**
     * 保存联系方式
     */
    public Long saveContactWay(HashMap<String,String> map){
        String email = map.get("email");
        String phone = map.get("phone");
        String qq = map.get("email");
        if(StrUtil.isEmpty(phone)){
            return 0L;
        }{
            ContactWay obj = new ContactWay();
            obj.setEmail(email);
            obj.setPhone(phone);
            obj.setQq(qq);
            contactWayDao.insert(obj);
            return obj.getId();
        }
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