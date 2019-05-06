package com.wrg.supermarket.controller;


import com.wrg.supermarket.component.MkplatWebModel;
import com.wrg.supermarket.service.IShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Wang Rengang
 * @since 2019-04-25
 */
@RestController
@RequestMapping("/shoppingCart")
public class ShoppingCartController {

    @Autowired
    private IShoppingCartService iShoppingCartService;
    @ResponseBody
    @RequestMapping("/addShoppingCart")
    public MkplatWebModel addShoppingCart(@RequestBody Map<String,Object> map){
        return iShoppingCartService.addShoppingCart(map);
    }

    @ResponseBody
    @RequestMapping("/getShoppingCartCount")
    public MkplatWebModel getShoppingCartCount(@RequestParam("id")String id){
        return iShoppingCartService.getShoppingCartCount(id);
    }
    @ResponseBody
    @RequestMapping("/getShoppingCartData")
    public MkplatWebModel getShoppingCartData(@RequestParam("id")String id){
        return iShoppingCartService.getShoppingCartData(id);
    }

    @ResponseBody
    @RequestMapping("/deleteShoppingCart")
    public MkplatWebModel deleteShoppingCart(@RequestBody Map<String,Object> map){
        return iShoppingCartService.deleteShoppingCart(map);
    }

}
