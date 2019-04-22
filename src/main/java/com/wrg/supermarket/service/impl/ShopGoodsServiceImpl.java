package com.wrg.supermarket.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wrg.supermarket.component.MkplatWebModel;
import com.wrg.supermarket.entity.Goods;
import com.wrg.supermarket.entity.GoodsType;
import com.wrg.supermarket.entity.ShopGoods;
import com.wrg.supermarket.mapper.GoodsMapper;
import com.wrg.supermarket.mapper.GoodsTypeMapper;
import com.wrg.supermarket.mapper.ShopGoodsMapper;
import com.wrg.supermarket.service.IShopGoodsService;
import com.wrg.supermarket.utils.enums.PlatErrorCode;
import com.wrg.supermarket.utils.exception.MkplatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName ShopGoodsServiceImpl
 * @Description TODO
 * @Author Wang Rengang
 * @Date 2019/4/18 11:47
 * @Version 1.0
 **/
@Service
public class ShopGoodsServiceImpl extends ServiceImpl<ShopGoodsMapper, ShopGoods> implements IShopGoodsService {
    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private GoodsTypeMapper goodsTypeMapper;
    @Override
    public MkplatWebModel getShopGoodsPage(Map<String,Object> map){
        int current=Integer.parseInt(map.get("current").toString());
        int size=Integer.parseInt(map.get("size").toString());
        QueryWrapper<ShopGoods> queryWrapper= Wrappers.query();

        if(map.get("name")!=null) queryWrapper.like("name",map.get("name"));
        if(map.get("status")!=null) queryWrapper.like("status",map.get("status"));
        queryWrapper.eq("shop_id",map.get("shopId"));

        if(map.get("typeId")!=null) {
            QueryWrapper<Goods> goodsQueryWrapper= Wrappers.query();
            queryTypeId(map.get("typeId").toString(),goodsQueryWrapper);
            List<Goods> goods = goodsMapper.selectList(goodsQueryWrapper);
            for(int i= 0;i<goods.size();i++){
                queryWrapper.eq("goods_id",goods.get(i).getId()).or();
            }
        }

        //分页处理
        IPage<ShopGoods> pageData=page(new com.baomidou.mybatisplus.extension.plugins.pagination.Page<ShopGoods>(current,size),queryWrapper);
        //处理查询后数据
        List<ShopGoods> pageList=pageData.getRecords();

        //用于存放商品信息的数组
        List<Map<String,Object>> resultList=new ArrayList<>();
        for(int i=0;i<pageList.size();i++) {
            //用于存放一条商品信息
            Map<String, Object> resultMap = new HashMap<>(1);
            ShopGoods shopGoods = pageList.get(i);
            Goods goods = goodsMapper.selectById(shopGoods.getGoodsId());
            resultMap.put("name",goods.getName());
            resultMap.put("price",goods.getPrice());
            resultMap.put("brandName",goods.getBrandName());
            resultMap.put("marque",goods.getMarque());
            resultMap.put("goodsId",goods.getId());

            resultMap.put("typeName",goodsTypeMapper.selectById(goods.getTypeId()).getName());

            resultMap.put("createTime",shopGoods.getCreateTime().toString().replaceAll("T"," "));
            resultMap.put("depict",shopGoods.getDepict());
            resultMap.put("status",shopGoods.getStatus());
            resultMap.put("currentPrice",shopGoods.getPrice());
            resultMap.put("number",shopGoods.getNumber());
            resultMap.put("dealNumber",shopGoods.getDealNumber());
            resultList.add(resultMap);
        }
        Map<String,Object> resultMap=new HashMap<>();
        resultMap.put("dataList",resultList);
        //放入tab的数据
        resultMap.put("dataStatistics",dataStatistics());
        return MkplatWebModel.convertMetroPayWebModel(pageData.getTotal(),resultMap);
    }


    @Override
    public MkplatWebModel addShopGoods(Map<String,Object> map){
        String shopId = map.get("shopId").toString();
        String goodsId = map.get("id").toString();
        QueryWrapper<ShopGoods> queryWrapper= Wrappers.query();
        queryWrapper.eq("shop_id",shopId).eq("goods_id",goodsId);
        if(count(queryWrapper)>=1)  return MkplatWebModel.convertMetroPayWebModel(new MkplatException(PlatErrorCode.PARAM_INVAILD));
        ShopGoods shopGoods = new ShopGoods();
        shopGoods.setShopId(shopId);
        shopGoods.setGoodsId(goodsId);
        shopGoods.setStatus("prepared");
        shopGoods.setPrice(new BigDecimal(map.get("currentPrice").toString()));
        shopGoods.setNumber((Integer) map.get("number"));
        shopGoods.setDepict(map.get("depict").toString());
        shopGoods.setDealNumber(0);
        shopGoods.setCommentNumber(0);
        shopGoods.setFavoritesNumber(0);
        shopGoods.setLastCommentTime(null);
        shopGoods.setLastDealTime(null);
        shopGoods.setLastFavoritesTime(null);
        LocalDateTime currentDate=LocalDateTime.now();
        shopGoods.setLastOnlineTime(currentDate);
        shopGoods.setCreateTime(currentDate);
        save(shopGoods);

        return MkplatWebModel.success();
    }

    @Override
    public MkplatWebModel modifyShopGoods(Map<String,Object> map){
        String shopId = map.get("shopId").toString();
        String goodsId = map.get("goodsId").toString();
        UpdateWrapper<ShopGoods> updateWrapper= Wrappers.update();
        updateWrapper.eq("shop_id",shopId).eq("goods_id",goodsId);

        ShopGoods shopGoods = new ShopGoods();
        shopGoods.setStatus("prepared");
        shopGoods.setPrice(new BigDecimal(map.get("currentPrice").toString()));
        shopGoods.setDepict(map.get("depict").toString());
        shopGoods.setNumber((Integer) map.get("number"));
        update(shopGoods,updateWrapper);
        return MkplatWebModel.success();
    }

    public MkplatWebModel onlineShopGoods(Map<String,Object> map){
        //上架商品数量不能小于等于0
        if((Integer) map.get("number")<=0) return MkplatWebModel.convertMetroPayWebModel(new MkplatException(PlatErrorCode.PARAM_INVAILD));
        String goodsId = map.get("goodsId").toString();
        //上架商品已被冻结，不能操作
        if(!goodsMapper.selectById(goodsId).getStatus().equals("enabled"))
            return MkplatWebModel.convertMetroPayWebModel(new MkplatException(PlatErrorCode.DATASTATUS_ERROR));
        String shopId = map.get("shopId").toString();

        UpdateWrapper<ShopGoods> updateWrapper= Wrappers.update();
        updateWrapper.eq("shop_id",shopId).eq("goods_id",goodsId).set("status","online");
        update(updateWrapper);

        //更新相关goods的信息
        Goods goods = goodsMapper.selectById(goodsId);
        int number = goods.getNumber()+(Integer) map.get("number");
        goods.setNumber(number);
        goodsMapper.updateById(goods);
        return MkplatWebModel.success();
    }

    public MkplatWebModel offlineShopGoods(Map<String,Object> map){
        String goodsId = map.get("goodsId").toString();
        String shopId = map.get("shopId").toString();
        UpdateWrapper<ShopGoods> updateWrapper= Wrappers.update();
        updateWrapper.eq("shop_id",shopId).eq("goods_id",goodsId).set("status","offline");
        update(updateWrapper);

        //更新相关goods的信息
        Goods goods = goodsMapper.selectById(goodsId);
        int number = goods.getNumber()-(Integer) map.get("number");
        goods.setNumber(number);
        goodsMapper.updateById(goods);
        return MkplatWebModel.success();
    }
    /**
     * @Author Wang Rengang
     * @Description 商品各状态数据统计
     * @Date 2019/4/18
     * @Param
     * @return
     **/
    private Map<String,Integer>  dataStatistics(){
        Map<String,Integer> dataStatisticsMap=new HashMap<>();
        //全部数据统计
        int all=count();
        dataStatisticsMap.put("all",all);
        //售罄数据统计 soldOut
        QueryWrapper<ShopGoods> queryWrapper1= Wrappers.query();
        queryWrapper1.eq("status","soldOut");
        int soldOut=count(queryWrapper1);
        dataStatisticsMap.put("soldOut",soldOut);
        //上架数据统计 online
        QueryWrapper<ShopGoods> queryWrapper2= Wrappers.query();
        queryWrapper2.eq("status","online");
        int online=count(queryWrapper2);
        dataStatisticsMap.put("online",online);
        //下架数据统计 offline
        QueryWrapper<ShopGoods> queryWrapper3= Wrappers.query();
        queryWrapper3.eq("status","offline");
        int offline=count(queryWrapper3);
        dataStatisticsMap.put("offline",offline);
        //准备中数据统计 prepared
        QueryWrapper<ShopGoods> queryWrapper4= Wrappers.query();
        queryWrapper4.eq("status","prepared");
        int prepared=count(queryWrapper4);
        dataStatisticsMap.put("prepared",prepared);
        return dataStatisticsMap;
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
