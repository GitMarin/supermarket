package com.wrg.supermarket.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wrg.supermarket.component.MkplatWebModel;
import com.wrg.supermarket.entity.ShopGoods;

import java.util.Map;

public interface IShopGoodsService extends IService<ShopGoods> {

    MkplatWebModel getShopGoodsPage(Map<String,Object> map);

    MkplatWebModel addShopGoods(Map<String,Object> map);

    MkplatWebModel modifyShopGoods(Map<String,Object> map);

    MkplatWebModel onlineShopGoods(Map<String,Object> map);

    MkplatWebModel offlineShopGoods(Map<String,Object> map);

    MkplatWebModel getOneShopGoods(Map<String,Object> map);

    MkplatWebModel modifyShopGoodsStatus(Map<String,Object> map);
}
