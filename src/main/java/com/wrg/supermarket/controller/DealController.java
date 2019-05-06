package com.wrg.supermarket.controller;

import com.wrg.supermarket.component.MkplatWebModel;
import com.wrg.supermarket.service.IDealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @ResponseBody
    @RequestMapping("/addDeal")
    public MkplatWebModel addDeal(@RequestBody Map<String,Object> map){
        return dealService.addDeal(map);
    }


    @ResponseBody
    @RequestMapping("/changeDealStatus")
    public MkplatWebModel changeDealStatus(@RequestBody Map<String,Object> map){
        return dealService.changeDealStatus(map);
    }

    @ResponseBody
    @RequestMapping("/deleteDeal")
    public MkplatWebModel deleteDeal(@RequestParam("dealId")String dealId){
        return dealService.deleteDeal(dealId);
    }

    @ResponseBody
    @RequestMapping("/addDealBatch")
    public MkplatWebModel addDealBatch(@RequestBody Map<String,Object> map){
        return dealService.addDealBatch(map);
    }

}
