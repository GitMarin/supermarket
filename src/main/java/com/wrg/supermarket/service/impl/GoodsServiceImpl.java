package com.wrg.supermarket.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.injector.methods.SelectOne;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wrg.supermarket.component.MkplatWebModel;
import com.wrg.supermarket.entity.Goods;
import com.wrg.supermarket.mapper.GoodsMapper;
import com.wrg.supermarket.service.IGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName GoodsServiceImpl
 * @Description TODO
 * @Author Wang Rengang
 * @Date 2019/4/2 11:47
 * @Version 1.0
 **/
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements IGoodsService {

    @Autowired
    private GoodsMapper mapper;
    @Override
    public MkplatWebModel getData(Map<String,Object> map) {
        int current=Integer.parseInt(map.get("current").toString());
        int size=Integer.parseInt(map.get("size").toString());
        QueryWrapper<Goods> queryWrapper=Wrappers.query();

        if(map.get("name")!=null){
            queryWrapper.like("name",map.get("name"));
        }
        //分页处理
        IPage<Goods> pageData=page(new com.baomidou.mybatisplus.extension.plugins.pagination.Page<Goods>(current,size),queryWrapper);
        //处理查询后数据
        List<Goods> pageList=pageData.getRecords();

        return MkplatWebModel.convertMetroPayWebModel(pageData.getTotal(),pageList);
    }

    @Override
    public MkplatWebModel getOneData(String id){
        Goods Data = getById(id);
        return MkplatWebModel.convertMetroPayWebModel(Data);
    }
}
