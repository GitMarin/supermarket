package com.wrg.supermarket.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wrg.supermarket.component.MkplatWebModel;
import com.wrg.supermarket.entity.Deal;
import com.wrg.supermarket.entity.DealDetails;
import com.wrg.supermarket.mapper.DealDetailsMapper;
import com.wrg.supermarket.mapper.DealMapper;
import com.wrg.supermarket.mapper.GoodsMapper;
import com.wrg.supermarket.mapper.ShopMapper;
import com.wrg.supermarket.service.IDealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wrg.supermarket.component.JavaBeanUtil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName DealServiceImpl
 * @Description TODO
 * @Author Wang Rengang
 * @Date 2019/4/10 22:10
 * @Version 1.0
 **/
@Service
public class DealServiceImpl extends ServiceImpl<DealMapper, Deal> implements IDealService {
    @Autowired
    private DealDetailsMapper dealDetailsMapper;
    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private ShopMapper shopMapper;

    @Override
    public MkplatWebModel getDealPage(Map<String,Object> map){
        //获取分页处理所需的当前页和尺寸
        int current=Integer.parseInt(map.get("current").toString());
        int size=Integer.parseInt(map.get("size").toString());
        String id=map.get("id").toString();

        //分页处理
        QueryWrapper<Deal> queryWrapper=Wrappers.query();
        queryWrapper.eq("user_id",id);
        if(map.get("status")!=null) queryWrapper.eq("status",map.get("status").toString());
        IPage<Deal> pageData=page(new com.baomidou.mybatisplus.extension.plugins.pagination.Page<Deal>(current,size),queryWrapper);
        //处理分页后的数据
        List<Deal> pageList=pageData.getRecords();
        //用于存放交易信息的数组
        List<Map<String,Object>> resultList=new ArrayList<>();
        for(int i=0;i<pageList.size();i++) {
            //用于存放一条交易信息
            Map<String, Object> resultMap = new HashMap<>(1);
            //获取一条deal信息
            Deal deal = pageList.get(i);
            //存放 创建时间、交易状态和交易金额 等deal本身存放的数据
            resultMap.put("createTime",deal.getCreateTime().toString().replaceAll("T"," "));
            resultMap.put("status",deal.getStatus());
            resultMap.put("dealPrice",deal.getdealPrice());
            //将deal中的shopId在shop表中进行转换，然后存放
            String shopName=shopMapper.selectById(deal.getShopId()).getName();
            resultMap.put("shopName",shopName);
            //获取交易ID，用于存放以及在dealDetails表中查询详细信息
            String dealId=deal.getId();
            resultMap.put("dealId",dealId);
            QueryWrapper<DealDetails> queryWrapperDD=Wrappers.query();
            queryWrapperDD.eq("id",dealId);
            List<DealDetails> dealDetailsList=dealDetailsMapper.selectList(queryWrapperDD);
            //将dealDetails中的信息取出进行打包，存放所有商品信息
            List<Map<String,Object>> dealGoodsList = new ArrayList<>();
            for(int j=0;j<dealDetailsList.size();j++){
                //用于存放每个商品信息
                Map<String, Object> dealGoodsMap = new HashMap<>(1);
                DealDetails dealDetails=dealDetailsList.get(j);
                //存放商品价格和商品数量等信息
                dealGoodsMap.put("goodsPrice",dealDetails.getPrice());
                dealGoodsMap.put("goodsNumber",dealDetails.getGoodsNumber());
                //将dealDetails中的goodsId在goods表中转换成goodsName，然后存放
                String goodsName=goodsMapper.selectById(dealDetails.getGoodsId()).getName();
                dealGoodsMap.put("goodsName",goodsName);
                //打包信息
                dealGoodsList.add(dealGoodsMap);
            }
            resultMap.put("cardData",dealGoodsList);
            resultList.add(resultMap);
        }
        return MkplatWebModel.convertMetroPayWebModel(pageData.getTotal(),resultList);
    }
}
