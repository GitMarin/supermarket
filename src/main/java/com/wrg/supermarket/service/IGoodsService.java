package com.wrg.supermarket.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wrg.supermarket.component.MkplatWebModel;
import com.wrg.supermarket.entity.Goods;

import java.util.Map;

public interface IGoodsService extends IService<Goods> {
    MkplatWebModel getData(Map<String,Object> map);
}
