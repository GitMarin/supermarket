package com.wrg.supermarket.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wrg.supermarket.component.MkplatWebModel;
import com.wrg.supermarket.entity.GoodsType;

import java.util.Map;

public interface IGoodsTypeService extends IService<GoodsType> {

    MkplatWebModel getGoodsType(Map<String,Object> map);

    MkplatWebModel getParentGoodsType();

    MkplatWebModel addGoodsType(Map<String,Object> map);

    MkplatWebModel modifyGoodsType(Map<String,Object> map);
}
