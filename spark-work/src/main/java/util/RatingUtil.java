package util;

import org.apache.spark.mllib.recommendation.Rating;

/**
 * @author kid1999
 * @create 2021-02-10 12:52
 * @description Rating 工具类
 **/

public class RatingUtil {

    public static Rating parseRating(String s){
        String[] fields = s.split(",");
        if(fields.length != 3){
            throw new IllegalArgumentException("Each line must contain 3 fields");
        }
        int userId = Integer.parseInt(fields[0]);
        int goodsId = Integer.parseInt(fields[1]);
        double rating = Double.parseDouble(fields[2]);
        return new Rating(userId,goodsId,rating);
    }
}