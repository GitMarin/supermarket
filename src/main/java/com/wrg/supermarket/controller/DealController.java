package com.wrg.supermarket.controller;

import com.wrg.supermarket.component.MkplatWebModel;
import com.wrg.supermarket.service.IDealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * @ClassName DealController
 * @Description TODO
 * @Author Wang Rengang
 * @Date 2019/4/10 21:56
 * @Version 1.0
 **/
@Controller
@RequestMapping("/deal")
public class DealController {

    @Autowired
    private IDealService dealService;
    @ResponseBody
    @RequestMapping(value = "/getDealPage", produces = "application/json")
    public MkplatWebModel getDealPage(@RequestBody Map<String,Object> map){

        return dealService.getDealPage(map);
    }
}
