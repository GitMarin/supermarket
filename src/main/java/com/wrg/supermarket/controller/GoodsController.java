package com.wrg.supermarket.controller;

import com.wrg.supermarket.component.MkplatWebModel;
import com.wrg.supermarket.service.IGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.Map;

/**
 * @ClassName GoodsController
 * @Description TODO
 * @Author Wang Rengang
 * @Date 2019/4/2 11:28
 * @Version 1.0
 **/
@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private IGoodsService service;

    @ResponseBody
    @RequestMapping("/getData")
    public MkplatWebModel getData(@RequestBody Map<String,Object> map){
        return service.getData(map);
    }

    @ResponseBody
    @RequestMapping("/getOneData")
    public MkplatWebModel getOneData(@RequestParam("id")String id){
        return service.getOneData(id);
    }
}
