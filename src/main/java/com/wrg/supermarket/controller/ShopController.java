package com.wrg.supermarket.controller;


import com.wrg.supermarket.component.MkplatWebModel;
import com.wrg.supermarket.service.IShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
    @RequestMapping("/getOneShop")
    public MkplatWebModel getOneShop(@RequestParam("id")String id){
        return iShopService.getOneShop(id);
    }

    @ResponseBody
    @RequestMapping("/getShopPage")
    public MkplatWebModel getShopPage(@RequestBody Map<String,Object> map){
        return iShopService.getShopPage(map);
    }


    @ResponseBody
    @RequestMapping("/modifyShopStatus")
    public MkplatWebModel modifyShopStatus(@RequestBody Map<String,Object> map){
        return iShopService.modifyShopStatus(map);
    }

    @ResponseBody
    @RequestMapping("/modifyShop")
    public MkplatWebModel modifyShop(@RequestBody Map<String,Object> map){
        return iShopService.modifyShop(map);
    }

}

