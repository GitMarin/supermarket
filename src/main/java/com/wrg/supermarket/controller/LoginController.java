package com.wrg.supermarket.controller;

import com.wrg.supermarket.component.MkplatWebModel;
import com.wrg.supermarket.service.ILoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @PostMapping("/login")
    public MkplatWebModel login(@RequestParam("username")String username,
                                @RequestParam("password")String password){
        MkplatWebModel mkplatWebModel=iLoginService.login(username,password);
        return mkplatWebModel;
    }
}
