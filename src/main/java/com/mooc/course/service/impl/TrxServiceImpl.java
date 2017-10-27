package com.mooc.course.service.impl;

import com.mooc.course.bean.Product;
import com.mooc.course.dao.ProductDao;
import com.mooc.course.dao.TrxDao;
import com.mooc.course.service.TrxService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TrxServiceImpl implements TrxService {
    @Autowired
    private TrxDao trxDao;
    @Autowired
    private ProductDao productDao;

    /**
     * 返回用户id为id的用户购买的商品列表
     *
     * @param userId 用户的id
     * @return 购买的商品列表
     */
    @Override
    public List<Product> getBuyList(String userId) {
        return trxDao.getBuyList(userId);
    }

    /**
     * 将用户购买商品的行为存入交易表
     *
     * @param userId            用户的id
     * @param productId         首要购买的商品id
     * @param currentTimeMillis 购买的时间
     * @return 是否购买成功
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public boolean buy(String userId, int productId, long currentTimeMillis) {
        Product product = productDao.get(productId);
        boolean buy = false;
        if (!trxDao.isSell(product)) {
            buy = trxDao.buy(userId, product, System.currentTimeMillis());
        }
        return buy;
    }
}
