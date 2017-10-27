package com.mooc.course.controller;

import com.mooc.course.bean.Product;
import com.mooc.course.bean.User;
import com.mooc.course.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.List;


@Controller
public class ProductController {

    @Autowired
    private ProductService productService;
    
    @RequestMapping(value = {"/index"})
    public String index(ModelMap modelMap,
    					@SessionAttribute(name = "user", required = false) User user,
            			@SessionAttribute(name = "username", required = false) String userName,
                        @SessionAttribute(name = "usertype", required = false) String usertype,
                        @RequestParam(name = "type", required = false) String type) {
        //传入userName和type获取商品列表
    	//同时进行筛选
        List<Product> list = productService.listProducts(type, userName);
        modelMap.addAttribute("productList", list);
        return "index";
    }
    
    @RequestMapping(value = {"/show"})
    public String show(ModelMap modelMap,
                       @SessionAttribute(name = "user", required = false) User user,
                       @SessionAttribute(name = "username", required = false) String userName,
                       @SessionAttribute(name = "usertype", required = false) String usertype,
                       @RequestParam("id") int id) {
        Product p = productService.get(id, userName);
        if (p != null) {
            modelMap.addAttribute(p);
        }
        return "show";
    }
    
    //跳转到发布的页面
    @RequestMapping(value = {"/public"})
    public String publicSome(
            @SessionAttribute(name = "user") User user,
            @SessionAttribute(name = "username") String userName,
            @SessionAttribute(name = "usertype") String usertype) {
    	if ("0".equals(usertype)) {
            throw new IllegalStateException("买家无法访问");
        }
        return "public";
    }


    //请求编辑的页面
    @RequestMapping(value = {"/edit"})
    public String edit(ModelMap modelMap,
                       @SessionAttribute(name = "user") User user,
                       @SessionAttribute(name = "username") String userName,
                       @SessionAttribute(name = "usertype") String usertype,
                       @RequestParam("id") int id) {
        if ("0".equals(usertype)) {
            throw new IllegalStateException("买家无法访问");
        }
        Product p = productService.get(id, userName);
        modelMap.addAttribute(p);
        return "edit";
    }

    
    //发布产品
    @RequestMapping(value = "/publicSubmit", method = RequestMethod.POST)
    public String publicSubmit(ModelMap modelMap,
                               @SessionAttribute(name = "user") User user,
                               @SessionAttribute(name = "username") String userName,
                               @SessionAttribute(name = "usertype") String usertype,
                               @Validated Product data, Errors errors) {
        if ("0".equals(usertype)) {
            throw new IllegalStateException("买家无法访问");
        }
        int count = productService.getCount();
        if (!errors.hasErrors() && count < 1000) {
            productService.submitProduct(data);
            modelMap.addAttribute("product", data);
        } else {
            modelMap.addAttribute("product", null);
        }
        return "publicSubmit";
    }
    
    
    //编辑内容
    @RequestMapping(value = {"/editSubmit"}, method = RequestMethod.POST)
    public String editSubmit(ModelMap modelMap,
                             @SessionAttribute(name = "user") User user,
                             @SessionAttribute(name = "username") String userName,
                             @SessionAttribute(name = "usertype") String usertype,
                             @Validated Product data, Errors errors, 
                             @RequestParam("id") int id) {
        if ("0".equals(usertype)) {
            throw new IllegalStateException("买家无法访问");
        }
        int count = productService.getCount();
        if (!errors.hasErrors() && count < 1000
                && productService.updateProduct(data)) {
            modelMap.addAttribute("product", data);
        } else {
            modelMap.addAttribute("product", null);
        }
        return "editSubmit";
    }
}