package com.mooc.course.controller;

import com.mooc.course.bean.Product;
import com.mooc.course.bean.User;
import com.mooc.course.service.TrxService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.List;

@Controller
public class TrxController {
    @Autowired
    private TrxService trxService;


    @RequestMapping(value = {"/account.html", "/account"})
    public String account(ModelMap modelMap,
                          @SessionAttribute(name = "user", required = false) User user,
                          @SessionAttribute(name = "username", required = false) String userName,
                          @SessionAttribute(name = "usertype", required = false) String usertype) {
        if ("1".equals(usertype)) {
            throw new IllegalStateException("卖家无法访问");
        }
        List<Product> buyProducts = trxService.getBuyList(user.getId());
        modelMap.addAttribute("buyList", buyProducts);
        
        return "account";
    }
}
