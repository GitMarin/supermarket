package com.wrg.supermarket.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.injector.methods.SelectById;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wrg.supermarket.component.JavaBeanUtil;
import com.wrg.supermarket.component.MkplatWebModel;
import com.wrg.supermarket.entity.Goods;
import com.wrg.supermarket.entity.GoodsType;
import com.wrg.supermarket.mapper.GoodsTypeMapper;
import com.wrg.supermarket.service.IGoodsTypeService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

/**
 * @ClassName GoodsTypeServiceImpl
 * @Description TODO
 * @Author Wang Rengang
 * @Date 2019/4/17 11:47
 * @Version 1.0
 **/
@Service
public class GoodsTypeServiceImpl extends ServiceImpl<GoodsTypeMapper, GoodsType> implements IGoodsTypeService {

    @Override
    public MkplatWebModel getGoodsType(Map<String,Object> map){
        //获取分页处理所需的当前页和尺寸
        int current=Integer.parseInt(map.get("current").toString());
        int size=Integer.parseInt(map.get("size").toString());
        //分页处理
        QueryWrapper<GoodsType> queryWrapper= Wrappers.query();
        queryWrapper.orderByDesc("order_number");
        IPage<GoodsType> pageData=page(new com.baomidou.mybatisplus.extension.plugins.pagination.Page<GoodsType>(current,size),queryWrapper);
        //处理分页后的数据
        List<GoodsType> pageList=pageData.getRecords();
        List<Map<String,Object>> resultList=new ArrayList<>();
        for(int i=0;i<pageList.size();i++) {
            GoodsType entity = pageList.get(i);
            Map<String, Object> resultMap = JavaBeanUtil.transBean2Map(entity);
            //获取上级类型名
            if(entity.getpId()!=null) resultMap.put("parentName",getById(entity.getpId()).getName());
            //获取上级类型的名字数组，用于html级联选择器上显示
            LinkedList<String> pName = new LinkedList<String>();
            String pId=entity.getpId();
            while(!(pId == null) && !pId.equals("")){
                GoodsType pClassify=getById(pId);
                //每次将上级类型id插到数组的第一个
                pName.addFirst(pId);
                pId=pClassify.getpId();
            }
            resultMap.put("pName",pName);
            resultList.add(resultMap);
        }
        return MkplatWebModel.convertMetroPayWebModel(pageData.getTotal(),resultList);
    }

    @Override
    public MkplatWebModel addGoodsType(Map<String,Object> map){
        //获取数据总数，将其新数据作为排列序号
        QueryWrapper<GoodsType> QueryWrapper=Wrappers.query();
        Integer orderNumber=count(QueryWrapper)+1;
        //将map转换为实体
        GoodsType goodsType=new GoodsType();

        JavaBeanUtil.transMap2Bean(map,goodsType);
        //放入排列序号
        goodsType.setOrderNumber(orderNumber);
        //获取当前时间
        goodsType.setCreateTime(LocalDateTime.now());
        goodsType.setModifyTime(LocalDateTime.now());

        QueryWrapper<GoodsType> queryWrapper=Wrappers.query();

        int id;
        //获取类型等级以及计算出id
        if(goodsType.getpId()==null)    {
            goodsType.setLevel(1);
            queryWrapper.lt("id",100);
            id=count(queryWrapper)+1;
        }
        else {
            String pId = goodsType.getpId();
            goodsType.setLevel(getById(pId).getLevel()+1);
            queryWrapper.eq("p_id",pId);
            id=Integer.parseInt(pId)*100+count(queryWrapper)+1;
        }
        goodsType.setId(String.valueOf(id));
        save(goodsType);

        //返回信息 类型的id
        Map<String,Object> result=new HashMap<>();
        result.put("id",id);
        return MkplatWebModel.convertMetroPayWebModel(result);
    }

    @Override
    public MkplatWebModel modifyGoodsType(Map<String,Object> map){
        //将map转化为实体类
        GoodsType goodsType=new GoodsType();
        JavaBeanUtil.transMap2Bean(map,goodsType);
        //获取修改时间
        goodsType.setModifyTime(LocalDateTime.now());
        //插入该条数据
        updateById(goodsType);
        return MkplatWebModel.success();
    }
    @Override
    public MkplatWebModel getParentGoodsType(){
        //获取所有数据
        List<GoodsType> entityList=list();
        //获取栏目数据，并根据上下级关系，组成相应的数组形式
        List<Map<String,Object>> data=getClassifyName(entityList);
        return MkplatWebModel.convertMetroPayWebModel(data);
    }

    //获取pid为null的栏目，并将其作为上级栏目，为他寻找子栏目
    private List<Map<String,Object>> getClassifyName(List<GoodsType> entityList){
        List<Map<String,Object>> data= new ArrayList<>();
        for(int i=0;i<entityList.size();i++){
            if(entityList.get(i).getpId()==null){
                String id=entityList.get(i).getId();
                Map<String,Object> map=new HashMap<>(1);
                map.put("value",id);
                map.put("label",entityList.get(i).getName());
                List<Map<String,Object>> childrenData=getClassifyName(id,entityList);
                if(childrenData.size()>0)  map.put("children",childrenData);
                data.add(map);
            }
        }
        return data;
    }

    //获取pid为参数的栏目，并将其作为上级栏目，为他寻找子栏目
    private List<Map<String,Object>> getClassifyName(String pId,List<GoodsType> entityList){
        List<Map<String,Object>> data= new ArrayList<>();
        for(int i=0;i<entityList.size();i++){
            if(pId.equals(entityList.get(i).getpId())){
                String id=entityList.get(i).getId();
                Map<String,Object> map=new HashMap<>(1);
                map.put("value",id);
                map.put("label",entityList.get(i).getName());
                List<Map<String,Object>> childrenData=getClassifyName(id,entityList);
                if(childrenData.size()>0)  map.put("children",childrenData);
                data.add(map);
            }

        }
        return data;
    }


}
