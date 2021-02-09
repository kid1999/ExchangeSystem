package io.kid1999.esystem;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpUtil;
import io.kid1999.esystem.dao.GoodsDao;
import io.kid1999.esystem.entity.Goods;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author kid1999
 * @create 2021-02-09 10:13
 * @description 爬取商品信息
 **/
@Component
public class MakeGoods {


    @Autowired
    private GoodsDao goodsDao;


    void selectAll(String name){
        for (int i = 0; i <15 ; i++) {
            String url = "https://book.douban.com/tag/%E7%AE%97%E6%B3%95?start=" + i*20 + "&type=T";
            getPage(url);
        }
    }

    void getPage(String url){
        String result = HttpUtil.get(url);
        Document doc = Jsoup.parse(result);
        Elements subs = doc.getElementsByClass("subject-item");
        for (Element sub : subs) {
            String imgUrl = sub.getElementsByTag("img").attr("src");
            String title = sub.getElementsByTag("h2").text();
            String desc = sub.getElementsByTag("p").text();
            String remarks = sub.getElementsByClass("buy-info").text();
            Long wantGoodId = RandomUtil.randomLong(100);
            Long addressId = RandomUtil.randomLong(15);
            int viewNumber = RandomUtil.randomInt(3000);
            String goodCondition = "九成新";

            Goods goods = new Goods();
            goods.setCreateDate(LocalDateTime.now());
            goods.setAddressId(addressId);
            goods.setDescription(desc);
            goods.setGoodsCondition(goodCondition);
            goods.setGoodsName(title);
            goods.setImgUrl(imgUrl);
            goods.setRemarks(remarks);
            goods.setNumberOfClicked(viewNumber);
            goods.setPrice(RandomUtil.randomBigDecimal(BigDecimal.valueOf(300)));
            goods.setUserId(RandomUtil.randomLong(100));
            goods.setWantGoodsId(wantGoodId);
            System.out.println(goods);


            goodsDao.insert(goods);

        }

    }



}