package com.mooc.course.service;

import java.util.List;

import com.mooc.course.bean.Product;

public interface TrxService {

    /**
     * 返回用户id为id的用户购买的商品列表
     * @param userId 用户的id
     * @return 用户已经购买的商品列表
     */
    List<Product> getBuyList(String userId);

    /**
     * 用户购买商品服务
     * @param userId 用户的id
     * @param productId 首要购买的商品id
     * @param currentTimeMillis 购买的时间
     * @return 是否购买成功
     */
    boolean buy(String userId, int productId, long currentTimeMillis);
}
