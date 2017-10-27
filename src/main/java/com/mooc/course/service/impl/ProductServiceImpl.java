package com.mooc.course.service.impl;

import com.mooc.course.bean.Product;
import com.mooc.course.dao.ProductDao;
import com.mooc.course.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;
    
    //查询所有商品信息，其中传入userName用来区分
    //当前是否为登录状态
    @Override
    public List<Product> listProducts(String type, String userName) {
    	List<Product> products = productDao.listProducts();
    	
        Iterator<Product> iterator = products.iterator();
        while (iterator.hasNext()) {
            Product p = iterator.next();
            if (userName != null) {
            	if(p.getTrxCount() > 0){
            		p.setBuy(true);
            		p.setSell(true);
            	}
            	else{
            		p.setBuy(false);
            		p.setSell(false);
            	}
                if ("1".equals(type)) {
                    if (p.getIsBuy()) {
                        iterator.remove();
                    }
                }
            }
        }
        return products;
    }
    
    
    //取得商品的信息
    @Override
    public Product get(int id) {
        Product p = productDao.get(id);
        
        return p;
    }

    
    //也是获得商品的信息，传入的userName用来区分是否有用户登录 
    @Override
    public Product get(int id, String userName) {
        Product p = productDao.get(id);
        //当用户登录时设置buy和sell
        if (p != null && userName != null) {
            p.setBuy(p.getTrxCount() > 0);
            p.setSell(p.getTrxCount() > 0);
        }
        return p;
    }
    
    
    //提交要发布的商品
    //采用事务管理，当提交不成功回滚
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void submitProduct(Product data) {
        productDao.submit(data);
    }
    
    
    //更新商品的数据
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public boolean updateProduct(Product data) {
        return productDao.update(data);
    }
    
    
    //删除商品
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public boolean delete(int id) {
        return productDao.delete(id);
    }
    
    
    //得到商品总数
    @Override
    public int getCount() {
        return productDao.getCount();
    }
}
