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
    @RequestMapping("/getGoodsPage")
    public MkplatWebModel getGoodsPage(@RequestBody Map<String,Object> map){
        return service.getGoodsPage(map);
    }

    @ResponseBody
    @RequestMapping("/getOneData")
    public MkplatWebModel getOneData(@RequestParam("id")String id){
        return service.getOneData(id);
    }

    @ResponseBody
    @RequestMapping("/addGoods")
    public MkplatWebModel addGoods(@RequestBody Map<String,Object> map){
        Map<String,Object> form = (Map<String, Object>) map.get("form");
        form.put("status",map.get("status"));
        return service.addGoods(form);
    }

    @ResponseBody
    @RequestMapping("/modifyGoods")
    public MkplatWebModel modifyGoods(@RequestBody Map<String,Object> map){
        return service.modifyGoods(map);
    }

    @ResponseBody
    @RequestMapping("/modifyGoodsStatus")
    public MkplatWebModel modifyGoodsStatus(@RequestBody Map<String,Object> map){
        return service.modifyGoodsStatus(map);
    }


}
