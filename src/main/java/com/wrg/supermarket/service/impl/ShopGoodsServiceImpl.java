package com.wrg.supermarket.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wrg.supermarket.component.JavaBeanUtil;
import com.wrg.supermarket.component.MkplatWebModel;
import com.wrg.supermarket.component.UUIDGenerator;
import com.wrg.supermarket.entity.*;
import com.wrg.supermarket.mapper.*;
import com.wrg.supermarket.service.IShopGoodsService;
import com.wrg.supermarket.utils.enums.PlatErrorCode;
import com.wrg.supermarket.utils.exception.MkplatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
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
    @Autowired
    private ShopMapper shopMapper;
    @Autowired
    private ImageMapper imageMapper;
    @Override
    public MkplatWebModel getShopGoodsPage(Map<String,Object> map){
        int current=Integer.parseInt(map.get("current").toString());
        int size=Integer.parseInt(map.get("size").toString());
        QueryWrapper<ShopGoods> queryWrapper= Wrappers.query();
        if(map.get("status")!=null) queryWrapper.eq("status",map.get("status"));
        queryWrapper.eq("shop_id",map.get("shopId"));


        //用于存放商品信息的数组
        List<Map<String,Object>> resultList=new ArrayList<>();

        //查询商品名和商品类型 从goods表中 获取goodsId
        QueryWrapper<Goods> goodsQueryWrapper= Wrappers.query();
        if(map.get("name")!=null) goodsQueryWrapper.like("name",map.get("name"));
        if(map.get("typeId")!=null) goodsQueryWrapper.and(i -> getQuery(map.get("typeId").toString(),i));
        if(map.get("name")!=null || map.get("typeId")!=null) {
            List<Goods> goodsList = goodsMapper.selectList(goodsQueryWrapper);
            if(goodsList.size()==0) return MkplatWebModel.convertMetroPayWebModel((long) 0,resultList);
            //for (int i = 0; i < goodsList.size(); i++) queryWrapper.eq("goods_id", goodsList.get(i).getId()).or();
            queryWrapper.and(i ->getFunction(goodsList, i));
        }

        //分页处理
        IPage<ShopGoods> pageData=page(new com.baomidou.mybatisplus.extension.plugins.pagination.Page<ShopGoods>(current,size),queryWrapper);
        //处理查询后数据
        List<ShopGoods> pageList=pageData.getRecords();


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
            resultMap.put("condensePicLink",goods.getCondensePicLink());

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
        resultMap.put("dataStatistics",dataStatistics(map.get("shopId").toString()));
        return MkplatWebModel.convertMetroPayWebModel(pageData.getTotal(),resultMap);
    }


    @Override
    public MkplatWebModel addShopGoods(Map<String,Object> map){
        String shopId = map.get("shopId").toString();
        String goodsId = map.get("id").toString();
        LocalDateTime currentDate=LocalDateTime.now();


        QueryWrapper<ShopGoods> queryWrapper= Wrappers.query();
        queryWrapper.eq("shop_id",shopId).eq("goods_id",goodsId);
        if(count(queryWrapper)>=1)  return MkplatWebModel.convertMetroPayWebModel(new MkplatException(PlatErrorCode.PARAM_INVAILD));
        ShopGoods shopGoods = new ShopGoods();
        shopGoods.setShopId(shopId);
        shopGoods.setGoodsId(goodsId);
        shopGoods.setStatus("prepared");
        shopGoods.setPrice(new BigDecimal(map.get("currentPrice").toString()));
        shopGoods.setNumber((Integer) map.get("num"));
        if(map.get("depict")!=null) shopGoods.setDepict(map.get("depict").toString());
        shopGoods.setDealNumber(0);
        shopGoods.setCommentNumber(0);
        shopGoods.setLastCommentTime(null);
        shopGoods.setLastDealTime(null);

        shopGoods.setLastOnlineTime(currentDate);
        shopGoods.setCreateTime(currentDate);
        save(shopGoods);

        List<String> fileList= (List<String>) map.get("fileList");
        for(int i=0;i<fileList.size();i++){
            String key= UUIDGenerator.generate();
            Image image = new Image();
            image.setId(key);
            image.setGoodsId(goodsId);
            image.setShopId(shopId);
            image.setUrl(fileList.get(i));
            image.setCreateTime(currentDate);
            imageMapper.insert(image);
        }
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

    @Override
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

    @Override
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

    @Override
    public MkplatWebModel getOneShopGoods(Map<String,Object> map){
        String goodsId = map.get("goodsId").toString();
        String shopId = map.get("shopId").toString();
        QueryWrapper<ShopGoods> queryWrapper = Wrappers.query();
        queryWrapper.eq("goods_id",goodsId).eq("shop_id",shopId);
        ShopGoods shopGoods = getOne(queryWrapper);
        if(shopGoods.getNumber()<=0) return MkplatWebModel.convertMetroPayWebModel(new MkplatException(PlatErrorCode.PARAM_INVAILD));
        Map<String,Object> resultMap = JavaBeanUtil.transBean2Map(shopGoods);
        Goods goods = goodsMapper.selectById(goodsId);
        resultMap.put("goodsName",goods.getName());

        resultMap.put("originalPrice",goods.getPrice());
        resultMap.put("brandName",goods.getBrandName());
        resultMap.put("marque",goods.getMarque());
        resultMap.put("shopName",shopMapper.selectById(shopId).getName());
        resultMap.put("typeName",goodsTypeMapper.selectById(goods.getTypeId()).getName());
        resultMap.put("num",1);

        List<String> picList = new ArrayList<>();
        picList.add(goods.getCondensePicLink());

        QueryWrapper<Image> imageQueryWrapper = Wrappers.query();
        imageQueryWrapper.eq("goods_id",goodsId).eq("shop_id",shopId);
        List<Image> imageList = imageMapper.selectList(imageQueryWrapper);
        for(int i= 0;i<imageList.size();i++)    picList.add(imageList.get(i).getUrl());

        resultMap.put("picList",picList);
        return MkplatWebModel.convertMetroPayWebModel(resultMap);
    }

    @Override
    public MkplatWebModel modifyShopGoodsStatus(Map<String,Object> map){
        String status = map.get("status").toString();
        String shopId = map.get("shopId").toString();
        String goodsId = map.get("goodsId").toString();
        if(status.equals("disabled")){
            QueryWrapper<ShopGoods> shopGoodsQueryWrapper = Wrappers.query();
            shopGoodsQueryWrapper.eq("shop_id",shopId).eq("goods_id",goodsId);
            ShopGoods shopGoods = getOne(shopGoodsQueryWrapper);
            String  oldStatus = shopGoods.getStatus();
            if(oldStatus.equals("online")){
                Goods goods = goodsMapper.selectById(goodsId);
                goods.setNumber(goods.getNumber()- shopGoods.getNumber());
                goodsMapper.updateById(goods);
            }
        }
        UpdateWrapper<ShopGoods> updateWrapper = Wrappers.update();
        updateWrapper.eq("shop_id",shopId)
                .eq("goods_id",goodsId)
                .set("status",map.get("status").toString());
        update(updateWrapper);
        return MkplatWebModel.success();
    }

    @Override
    public MkplatWebModel getShopGoodsPic(Map<String,Object> map){
        String goodsId = map.get("goodsId").toString();
        String shopId = map.get("shopId").toString();
        QueryWrapper<Image> imageQueryWrapper = Wrappers.query();
        imageQueryWrapper.eq("goods_id",goodsId).eq("shop_id",shopId);
        List<Image> imageList=imageMapper.selectList(imageQueryWrapper);
        return MkplatWebModel.convertMetroPayWebModel(imageList);
    }

    @Override
    public MkplatWebModel addOneImage(Map<String,Object> map){
        String key= UUIDGenerator.generate();
        LocalDateTime currentDate=LocalDateTime.now();

        Image image = new Image();
        image.setUrl(map.get("url").toString());
        image.setShopId(map.get("shopId").toString());
        image.setGoodsId(map.get("goodsId").toString());
        image.setId(key);
        image.setCreateTime(currentDate);
        imageMapper.insert(image);
        return MkplatWebModel.success();
    }

    @Override
    public MkplatWebModel deleteShopGoodsPic(Map<String,Object> map){
        imageMapper.deleteById(map.get("imageId").toString());
        String url=map.get("url").toString();
        File file=new File("C:/inetpub/wwwroot/image/"+url.substring(url.lastIndexOf("/")+1));
        file.delete();
        return MkplatWebModel.success();
    }



    /**
     * @Author Wang Rengang
     * @Description 商品各状态数据统计
     * @Date 2019/4/18
     * @Param
     * @return
     **/
    private Map<String,Integer>  dataStatistics(String id){
        Map<String,Integer> dataStatisticsMap=new HashMap<>();
        //全部数据统计
        QueryWrapper<ShopGoods> queryWrapper= Wrappers.query();
        queryWrapper.eq("shop_id",id);
        int all=count(queryWrapper);
        dataStatisticsMap.put("all",all);
        //售罄数据统计 soldOut
        QueryWrapper<ShopGoods> queryWrapper1= Wrappers.query();
        queryWrapper1.eq("status","soldOut").eq("shop_id",id);
        int soldOut=count(queryWrapper1);
        dataStatisticsMap.put("soldOut",soldOut);
        //上架数据统计 online
        QueryWrapper<ShopGoods> queryWrapper2= Wrappers.query();
        queryWrapper2.eq("status","online").eq("shop_id",id);
        int online=count(queryWrapper2);
        dataStatisticsMap.put("online",online);
        //下架数据统计 offline
        QueryWrapper<ShopGoods> queryWrapper3= Wrappers.query();
        queryWrapper3.eq("status","offline").eq("shop_id",id);
        int offline=count(queryWrapper3);
        dataStatisticsMap.put("offline",offline);
        //准备中数据统计 prepared
        QueryWrapper<ShopGoods> queryWrapper4= Wrappers.query();
        queryWrapper4.eq("status","prepared").eq("shop_id",id);
        int prepared=count(queryWrapper4);
        dataStatisticsMap.put("prepared",prepared);
        //冻结数据统计
        QueryWrapper<ShopGoods> queryWrapper5= Wrappers.query();
        queryWrapper5.eq("status","disabled").eq("shop_id",id);
        int disabled=count(queryWrapper5);
        dataStatisticsMap.put("disabled",disabled);
        return dataStatisticsMap;
    }

    private QueryWrapper<ShopGoods> getFunction(List<Goods> goodsList, QueryWrapper<ShopGoods> queryWrapper){
        for (int i = 0; i < goodsList.size(); i++) queryWrapper.eq("goods_id", goodsList.get(i).getId()).or();
        return queryWrapper;
    }

    private QueryWrapper<Goods> getQuery(String typeId,QueryWrapper<Goods> queryWrapper){
        queryTypeId(typeId,queryWrapper);
        return queryWrapper;
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
