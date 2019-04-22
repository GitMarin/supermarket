package com.wrg.supermarket.controller;


import com.wrg.supermarket.component.MkplatWebModel;
import com.wrg.supermarket.service.IShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Wang Rengang
 * @since 2019-04-03
 */
@RestController
@RequestMapping("/shop")
public class ShopController  {

    @Autowired
    private IShopService iShopService;
    @ResponseBody
    @RequestMapping("/getShop")
    public MkplatWebModel getUserData(@RequestParam("id")String id){
        return iShopService.getShop(id);
    }
}

