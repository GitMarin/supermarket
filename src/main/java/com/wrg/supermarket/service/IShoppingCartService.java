package com.wrg.supermarket.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wrg.supermarket.component.MkplatWebModel;
import com.wrg.supermarket.entity.ShoppingCart;

import java.util.Map;

public interface IShoppingCartService extends IService<ShoppingCart> {

    MkplatWebModel addShoppingCart(Map<String,Object> map);

    MkplatWebModel getShoppingCartCount(String id);

    MkplatWebModel getShoppingCartData(String id);

    MkplatWebModel deleteShoppingCart(Map<String,Object> map);

}
