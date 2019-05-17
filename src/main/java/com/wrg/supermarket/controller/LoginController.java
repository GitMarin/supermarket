package com.wrg.supermarket.controller;

import com.wrg.supermarket.component.MkplatWebModel;
import com.wrg.supermarket.service.ILoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

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
                                    @RequestParam("type") String type,
                                    HttpServletRequest request){
        MkplatWebModel mkplatWebModel = iLoginService.login(username,password,type);
        if(mkplatWebModel.rtnFlag=="9999"){
            request.getSession().setAttribute("data",mkplatWebModel.rtnData);
        }
        return mkplatWebModel;
    }

    @ResponseBody
    @RequestMapping("/logout")
    public MkplatWebModel logout(HttpServletRequest request){
        request.getSession().removeAttribute("data");
        return MkplatWebModel.success();
    }


    @ResponseBody
    @RequestMapping("/registerUser")
    public MkplatWebModel registerUser(@RequestBody Map<String,Object> map){
        return iLoginService.registerUser(map);
    }

    @ResponseBody
    @RequestMapping("/registerShop")
    public MkplatWebModel registerShop(@RequestBody Map<String,Object> map){
        return iLoginService.registerShop(map);
    }

    @ResponseBody
    @RequestMapping("/testUsername")
    public MkplatWebModel testUsername(@RequestBody Map<String,Object> map){
        String name= map.get("username").toString();
        return iLoginService.testName(name,"user");
    }

    @ResponseBody
    @RequestMapping("/testShopName")
    public MkplatWebModel testShopName(@RequestBody Map<String,Object> map){
        String name= map.get("username").toString();
        return iLoginService.testName(name,"shop");
    }



}
