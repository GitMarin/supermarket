package com.wrg.supermarket.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName HelloController
 * @Description TODO
 * @Author Wang Rengang
 * @Date 2019/3/27 9:52
 * @Version 1.0
 **/
@Controller
public class HelloController {


    @RequestMapping({"/", "/login.html"})
    public String index(){
        return "login";
    }
    @ResponseBody
    @RequestMapping("/hello")
    public String hello(){
        return "hello world";
    }
}
