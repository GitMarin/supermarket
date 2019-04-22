package com.wrg.supermarket.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wrg.supermarket.component.MkplatWebModel;
import com.wrg.supermarket.entity.Goods;

import java.util.Map;

public interface IGoodsService extends IService<Goods> {

    MkplatWebModel getGoodsPage(Map<String,Object> map);

    MkplatWebModel getOneData(String id);

    MkplatWebModel addGoods(Map<String,Object> map);

    MkplatWebModel modifyGoods(Map<String,Object> map);

    MkplatWebModel modifyGoodsStatus(Map<String,Object> map);


}
