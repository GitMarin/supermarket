package com.wrg.supermarket.controller;

import com.wrg.supermarket.component.MkplatWebModel;
import com.wrg.supermarket.service.ILoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * @ClassName LoginController
 * @Description TODO
 * @Author Wang Rengang
 * @Date 2019/3/28 23:15
 * @Version 1.0
 **/
@Controller
public class LoginController {
    @Autowired
    private ILoginService iLoginService;

    @ResponseBody
    @RequestMapping("/login")
    public MkplatWebModel login(@RequestParam("username")String username,
                                @RequestParam("password")String password,
                                HttpSession session){
        MkplatWebModel mkplatWebModel = iLoginService.login(username,password);
        if(mkplatWebModel.rtnFlag=="9999"){
            session.setAttribute("loginUser",username);
        }
        return mkplatWebModel;

    }
}
