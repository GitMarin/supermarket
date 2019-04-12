package com.wrg.supermarket.controller;

import com.wrg.supermarket.component.MkplatWebModel;
import com.wrg.supermarket.entity.User;
import com.wrg.supermarket.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName UserController
 * @Description TODO
 * @Author Wang Rengang
 * @Date 2019/4/3 19:30
 * @Version 1.0
 **/
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    IUserService service;

    /*
     *@Param userId  用户id，用于查询
     */
    @ResponseBody
    @RequestMapping("/getUserData")
    public MkplatWebModel getUserData(@RequestParam("id")String id){
        return service.getUserData(id);
    }

    @ResponseBody
    @RequestMapping("/modifyUser")
    public MkplatWebModel modify(@RequestBody User entity) {
        return service.modify(entity);
    }
}
