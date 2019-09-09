package com.maywide.biz.inter.pojo.queDownPickData;

import java.io.Serializable;

/**
 * Created by lisongkang on 2019/6/28 0001.
 */
public class WriteOffInfo implements Serializable {
    private String redisenceName;//小区名称
    private Long publicNumber;//公众用户数
    private Long busenterNumber;//商企用户数
    private Long hotelNumber;//酒店用户数

    public String getRedisenceName() {
        return redisenceName;
    }

    public void setRedisenceName(String redisenceName) {
        this.redisenceName = redisenceName;
    }

    public Long getPublicNumber() {
        return publicNumber;
    }

    public void setPublicNumber(Long publicNumber) {
        this.publicNumber = publicNumber;
    }

    public Long getBusenterNumber() {
        return busenterNumber;
    }

    public void setBusenterNumber(Long busenterNumber) {
        this.busenterNumber = busenterNumber;
    }

    public Long getHotelNumber() {
        return hotelNumber;
    }

    public void setHotelNumber(Long hotelNumber) {
        this.hotelNumber = hotelNumber;
    }
}
