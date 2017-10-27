package com.mooc.course.controller;

import com.mooc.course.bean.User;
import com.mooc.course.service.ProductService;
import com.mooc.course.service.TrxService;
import com.mooc.course.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/api")
public class ApiController {
    @Autowired
    private UserService userService;
    @Autowired
    private TrxService trxService;
    @Autowired
    private ProductService productService;

    
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView login(HttpServletRequest request,
                              @RequestParam("userName") String username,
                              @RequestParam("password") String password) {
        User user = userService.login(username, password);
        
        boolean login = false;
        if(user != null){
        	login = true;
        }
        
        if (login) {
            HttpSession session = request.getSession(true);
            session.setAttribute("user", user);
            session.setAttribute("username", user.getUsername());
            session.setAttribute("usertype", String.valueOf(user.getUsertype()));
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("code", (login ? 200 : 100));
        modelAndView.addObject("message", (login ? "登陆成功" : "登陆异常"));
        modelAndView.addObject("result", login);
        return modelAndView;
    }

    //用户购买商品
    @RequestMapping(value = "/buy", method = RequestMethod.POST)
    public ModelAndView buy(@RequestParam("id") int id,
                            @SessionAttribute("user") User user) {
        boolean buy = trxService.buy(user.getId(), id, System.currentTimeMillis());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("code", (buy ? 200 : 100));
        modelAndView.addObject("message", (buy ? "购买成功" : "购买异常"));
        modelAndView.addObject("result", buy);
        return modelAndView;
    }

    //卖家删除商品
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ModelAndView delete(@RequestParam("id") int id) {
        boolean result = productService.delete(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("code", (result ? 200 : 100));
        modelAndView.addObject("message", (result ? "删除成功" : "删除失败"));
        modelAndView.addObject("result", result);
        return modelAndView;
    }
}
