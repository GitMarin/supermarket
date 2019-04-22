package com.wrg.supermarket.controller;


import com.wrg.supermarket.component.MkplatWebModel;
import com.wrg.supermarket.service.IGoodsService;
import com.wrg.supermarket.service.IGoodsTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * @ClassName GoodsTypeController
 * @Description TODO
 * @Author Wang Rengang
 * @Date 2019/4/17 11:28
 * @Version 1.0
 **/
@Controller
@RequestMapping("/goodsType")
public class GoodsTypeController {
    @Autowired
    private IGoodsTypeService iGoodsTypeService;

    @ResponseBody
    @RequestMapping("/getGoodsType")
    public MkplatWebModel getGoodsType(@RequestBody Map<String,Object> map){
        return iGoodsTypeService.getGoodsType(map);
    }

    @ResponseBody
    @RequestMapping("/getParentGoodsType")
    public MkplatWebModel getParentGoodsType(){
        return iGoodsTypeService.getParentGoodsType();
    }

    @ResponseBody
    @RequestMapping("/addGoodsType")
    public MkplatWebModel addGoodsType(@RequestBody Map<String,Object> map){
        return iGoodsTypeService.addGoodsType(map);
    }

    @ResponseBody
    @RequestMapping("/modifyGoodsType")
    public MkplatWebModel modifyGoodsType(@RequestBody Map<String,Object> map){
        return iGoodsTypeService.modifyGoodsType(map);
    }
}
