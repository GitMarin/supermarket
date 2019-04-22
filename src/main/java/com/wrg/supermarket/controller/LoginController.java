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
    @RequestMapping("/loginUser")
    public MkplatWebModel loginUser(@RequestParam("username")String username,
                                @RequestParam("password")String password,
                                HttpSession session){
        MkplatWebModel mkplatWebModel = iLoginService.loginUser(username,password);
//        if(mkplatWebModel.rtnFlag=="9999"){
//            session.setAttribute("loginUser",username);
//        }
        return mkplatWebModel;
    }

    @ResponseBody
    @RequestMapping("/loginShop")
    public MkplatWebModel loginShop(@RequestParam("username")String username,
                                    @RequestParam("password")String password,
                                    HttpSession session){
        MkplatWebModel mkplatWebModel = iLoginService.loginShop(username,password);
//        if(mkplatWebModel.rtnFlag=="9999"){
//            session.setAttribute("loginUser",username);
//        }
        return mkplatWebModel;
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
        String username= (String) map.get("username");
        return iLoginService.testUsername(username);
    }

    @ResponseBody
    @RequestMapping("/testShopName")
    public MkplatWebModel testShopName(@RequestBody Map<String,Object> map){
        String username= (String) map.get("username");
        return iLoginService.testShopName(username);
    }


}
