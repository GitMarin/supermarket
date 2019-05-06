package com.wrg.supermarket.controller;


import com.wrg.supermarket.component.MkplatWebModel;
import com.wrg.supermarket.service.IShopGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Wang Rengang
 * @since 2019-04-18
 */
@RestController
@RequestMapping("/shopGoods")
public class ShopGoodsController {
    @Autowired
    IShopGoodsService iShopGoodsService;
    
    @ResponseBody
    @RequestMapping("/getShopGoodsPage")
    public MkplatWebModel getShopGoodsPage(@RequestBody Map<String,Object> map){
        Map<String,Object> form = (Map<String, Object>) map.get("form");
        form.put("shopId",map.get("shopId"));
        return iShopGoodsService.getShopGoodsPage(form);
    }
    @ResponseBody
    @RequestMapping("/getOneShopGoods")
    public MkplatWebModel getOneShopGoods(@RequestBody Map<String,Object> map){
        return iShopGoodsService.getOneShopGoods(map);
    }

    @ResponseBody
    @RequestMapping("/addShopGoods")
    public MkplatWebModel addShopGoods(@RequestBody Map<String,Object> map){
        Map<String,Object> form = (Map<String, Object>) map.get("form");
        form.put("shopId",map.get("shopId"));
        return iShopGoodsService.addShopGoods(form);
    }

    @ResponseBody
    @RequestMapping("/modifyShopGoods")
    public MkplatWebModel modifyShopGoods(@RequestBody Map<String,Object> map){
        Map<String,Object> form = (Map<String, Object>) map.get("form");
        form.put("shopId",map.get("shopId"));
        return iShopGoodsService.modifyShopGoods(form);
    }

    @ResponseBody
    @RequestMapping("/onlineShopGoods")
    public MkplatWebModel onlineShopGoods(@RequestBody Map<String,Object> map){
        Map<String,Object> form = (Map<String, Object>) map.get("form");
        form.put("shopId",map.get("shopId"));
        return iShopGoodsService.onlineShopGoods(form);
    }

    @ResponseBody
    @RequestMapping("/offlineShopGoods")
    public MkplatWebModel offlineShopGoods(@RequestBody Map<String,Object> map){
        Map<String,Object> form = (Map<String, Object>) map.get("form");
        form.put("shopId",map.get("shopId"));
        return iShopGoodsService.offlineShopGoods(form);
    }

    @ResponseBody
    @RequestMapping("/modifyShopGoodsStatus")
    public MkplatWebModel modifyShopGoodsStatus(@RequestBody Map<String,Object> map){
        return iShopGoodsService.modifyShopGoodsStatus(map);
    }




}
