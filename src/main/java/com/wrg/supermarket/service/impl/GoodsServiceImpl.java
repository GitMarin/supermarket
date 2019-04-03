package com.wrg.supermarket.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wrg.supermarket.component.MkplatWebModel;
import com.wrg.supermarket.entity.Goods;
import com.wrg.supermarket.mapper.GoodsMapper;
import com.wrg.supermarket.service.IGoodsService;
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

    @Override
    public MkplatWebModel getData(Map<String,Object> map) {
        int current=Integer.parseInt(map.get("current").toString());
        int size=Integer.parseInt(map.get("size").toString());
        QueryWrapper<Goods> queryWrapper=Wrappers.query();
        if(map.get("name")!=null){
            queryWrapper.eq("name",map.get("name"));
        }
        //分页处理
        IPage<Goods> pageData=page(new com.baomidou.mybatisplus.extension.plugins.pagination.Page<Goods>(current,size),queryWrapper);
        //分页处理之后，pageData total为0，但records有接受到值，但是并没有根据size获取相应数量的值，分页不成功
        //处理查询后数据
        List<Goods> pageList=pageData.getRecords();

        //手动分页
        int total=pageList.size();
        pageData.setTotal(total);
        List<Goods> list= new ArrayList<>();
        //根据size和current，从pageList上截取 需要展示到屏幕上的数据
        for(int i=size*(current-1);i<size*current && i<total;i++){
            list.add(pageList.get(i));
        }
        return MkplatWebModel.convertMetroPayWebModel(pageData.getTotal(),list);
    }
}
