package com.wrg.supermarket.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.injector.methods.SelectOne;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wrg.supermarket.component.JavaBeanUtil;
import com.wrg.supermarket.component.MkplatWebModel;
import com.wrg.supermarket.component.UUIDGenerator;
import com.wrg.supermarket.entity.Goods;
import com.wrg.supermarket.entity.GoodsType;
import com.wrg.supermarket.mapper.GoodsMapper;
import com.wrg.supermarket.mapper.GoodsTypeMapper;
import com.wrg.supermarket.service.IGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

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
    private GoodsTypeMapper goodsTypeMapper;
    @Override
    public MkplatWebModel getGoodsPage(Map<String,Object> map) {
        int current=Integer.parseInt(map.get("current").toString());
        int size=Integer.parseInt(map.get("size").toString());
        int number=Integer.parseInt(map.get("number").toString());
        QueryWrapper<Goods> queryWrapper=Wrappers.query();
        queryWrapper.ge("number",number);
        if(map.get("name")!=null)   queryWrapper.like("name",map.get("name").toString());
        //搜寻typeId的类型以及包含的商品类型，使用递归调用方法实现
        if(!map.get("status").toString().equals("all")) queryWrapper.eq("status",map.get("status").toString());

        //根据不同用户，选择提供不同状态的商品信息
        if(map.get("typeId")!=null) queryTypeId(map.get("typeId").toString(),queryWrapper);


        //分页处理
        IPage<Goods> pageData=page(new com.baomidou.mybatisplus.extension.plugins.pagination.Page<Goods>(current,size),queryWrapper);
        //处理查询后数据
        List<Goods> pageList=pageData.getRecords();
        //存放最后的结果
        List<Map<String,Object>> resultList=new ArrayList<>();
        for(int i=0;i<pageList.size();i++) {
            Goods entity = pageList.get(i);
            Map<String, Object> resultMap = JavaBeanUtil.transBean2Map(entity);
            //获取类型名称
            resultMap.put("typeName",goodsTypeMapper.selectById(entity.getTypeId()).getName());
            //获取上级类型的名字数组，用于html级联选择器上显示
            LinkedList<String> pName = new LinkedList<String>();
            String typeId=entity.getTypeId();
            while(!(typeId == null) && !typeId.equals("")){
                GoodsType pClassify=goodsTypeMapper.selectById(typeId);
                //每次将上级类型id插到数组的第一个
                pName.addFirst(typeId);
                typeId=pClassify.getpId();
            }
            resultMap.put("pName",pName);
            resultList.add(resultMap);
        }
        return MkplatWebModel.convertMetroPayWebModel(pageData.getTotal(),resultList);
    }

    @Override
    public MkplatWebModel getOneGoods(String id){
        Goods goods = getById(id);
        Map<String,Object> map = JavaBeanUtil.transBean2Map(goods);
        map.put("typeName",goodsTypeMapper.selectById(goods.getTypeId()).getName());
        return MkplatWebModel.convertMetroPayWebModel(map);
    }

    @Override
    public MkplatWebModel addGoods(Map<String,Object> map){
        //生成主键
        String key= UUIDGenerator.generate();
        Goods goods = new Goods();
        goods.setId(key);
        goods.setStatus(map.get("status").toString());
        goods.setTypeId(map.get("typeId").toString());
        goods.setBrandName(map.get("brandName").toString());
        goods.setPrice(new BigDecimal((String) map.get("price")));
        goods.setMarque(map.get("marque").toString());
        goods.setName(map.get("name").toString());
        //图片
        goods.setCondensePicLink(map.get("condensePicLink").toString());

        goods.setLastCommentTime(null);
        goods.setLastDealTime(null);
        goods.setLastFavoritesTime(null);
        goods.setCommentNumber(0);
        goods.setDealNumber(0);
        goods.setFavoritesNumber(0);

        LocalDateTime currentDate=LocalDateTime.now();
        goods.setModifyTime(currentDate);
        goods.setCreateTime(currentDate);

        save(goods);
        Map<String,Object> result = new HashMap<>();
        result.put("id",key);
        return MkplatWebModel.convertMetroPayWebModel(result);
    }

    public MkplatWebModel modifyGoods(Map<String,Object> map){
        Goods goods = new Goods();
        goods.setTypeId(map.get("typeId").toString());
        goods.setBrandName(map.get("brandName").toString());
        goods.setPrice(new BigDecimal((String) map.get("price")));
        goods.setMarque(map.get("marque").toString());
        goods.setName(map.get("name").toString());
        goods.setId(map.get("id").toString());
        LocalDateTime currentDate=LocalDateTime.now();
        goods.setModifyTime(currentDate);
        updateById(goods);
        return MkplatWebModel.success();
    }

    @Override
    public MkplatWebModel modifyGoodsStatus(Map<String,Object> map){
        Goods goods = new Goods();
        goods.setId(map.get("id").toString());
        goods.setStatus(map.get("status").toString());
        updateById(goods);
        return MkplatWebModel.success();
    }

    private void queryTypeId(String typeId,QueryWrapper<Goods> queryWrapper){
        queryWrapper.eq("type_id",typeId).or();
        QueryWrapper<GoodsType> goodsTypeQueryWrapper=Wrappers.query();
        goodsTypeQueryWrapper.eq("p_id",typeId);
        List<GoodsType> goodsTypeList=goodsTypeMapper.selectList(goodsTypeQueryWrapper);
        for(int i= 0;i<goodsTypeList.size();i++){
            queryTypeId(goodsTypeList.get(i).getId(),queryWrapper);
        }
    }
}
