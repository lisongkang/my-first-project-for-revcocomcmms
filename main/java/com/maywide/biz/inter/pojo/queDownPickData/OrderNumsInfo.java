package com.maywide.biz.inter.pojo.queDownPickData;

import java.io.Serializable;

/**
 * Created by lisongkang on 2019/6/28 0001.
 */
public class OrderNumsInfo implements Serializable {
    private String redisenceName;//小区名称
    private Long installNumber;//安装数量

    public String getRedisenceName() {
        return redisenceName;
    }

    public void setRedisenceName(String redisenceName) {
        this.redisenceName = redisenceName;
    }

    public Long getInstallNumber() {
        return installNumber;
    }

    public void setInstallNumber(Long installNumber) {
        this.installNumber = installNumber;
    }
}
