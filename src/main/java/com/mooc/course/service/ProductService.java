package com.mooc.course.service;

import java.util.List;

import com.mooc.course.bean.Product;


public interface ProductService {

    /**
     * 获取到所有商
     *
     * @param type  type为1时，筛选用户未购买的商品
     * @param userName  以空值来判断区分用户是否登录
     * @return
     */
    List<Product> listProducts(String type, String userName);

    /**
     * 获取单个商品信息
     *
     * @param id 商品的id
     * @return
     */
    Product get(int id);

    /**
     * 获取商品的信息
     *
     * @param id       商品的id
     * @param userName 当前用户的username，用于区分是否有用户登录
     * @return
     */
    Product get(int id, String userName);

    /**
     * 发布新的商品
     *
     * @param data 提交的商品信息
     */
    void submitProduct(Product data);

    /**
     * 更新商品的数据
     *
     * @param data 提交的商品信息
     * @return 是否成功
     */
    boolean updateProduct(Product data);

    /**
     * 删除商品信息
     *
     * @param id 商品id
     * @return 是否成功
     */
    boolean delete(int id);

    /**
     * 获取商品的总数
     *
     * @return 商品的总数
     */
    int getCount();
}
