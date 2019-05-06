package com.wrg.supermarket.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.injector.methods.SelectById;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wrg.supermarket.component.MkplatWebModel;
import com.wrg.supermarket.component.UUIDGenerator;
import com.wrg.supermarket.entity.*;
import com.wrg.supermarket.mapper.*;
import com.wrg.supermarket.service.IDealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
    @Autowired
    private ShopGoodsMapper shopGoodsMapper;
    @Autowired
    private  UserMapper userMapper;
    @Autowired
    private ShoppingCartMapper shoppingCartMapper;

    @Override
    public MkplatWebModel getDealPage(Map<String,Object> map){
        //获取分页处理所需的当前页和尺寸
        int current=Integer.parseInt(map.get("current").toString());
        int size=Integer.parseInt(map.get("size").toString());
        //分页处理
        QueryWrapper<Deal> queryWrapper=Wrappers.query();

        if(map.get("userId")!=null){
            String userId=map.get("userId").toString();
            queryWrapper.eq("user_id",userId);
        }
        else if(map.get("shopId")!=null){
            String shopId=map.get("shopId").toString();
            queryWrapper.eq("shop_id",shopId);
        }


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
            resultMap.put("dealPrice",deal.getDealPrice());

            if(map.get("userId")!=null){
                //将deal中的shopId在shop表中进行转换，然后存放
                String shopName=shopMapper.selectById(deal.getShopId()).getName();
                resultMap.put("shopName",shopName);
            }
            else if(map.get("shopId")!=null){
                String userName=userMapper.selectById(deal.getUserId()).getNickname();
                resultMap.put("userName",userName);
            }

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
                dealGoodsMap.put("goodsPrice",dealDetails.getGoodsPrice());
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


    public MkplatWebModel addDeal(Map<String,Object> map){
        //生成主键
        String key= UUIDGenerator.generate();
        String userId = map.get("userId").toString();
        String shopId = map.get("shopId").toString();
        String goodsId = map.get("goodsId").toString();
        Integer number = (Integer) map.get("number");
        BigDecimal price = new BigDecimal(map.get("price").toString());
        Deal deal = new Deal();
        deal.setDealPrice(price.multiply(BigDecimal.valueOf(number)));
        deal.setId(key);
        deal.setShopId(shopId);
        deal.setUserId(userId);
        deal.setStatus("waitPay");
        LocalDateTime currentDate=LocalDateTime.now();
        deal.setCreateTime(currentDate);
        save(deal);

        DealDetails dealDetails = new DealDetails();
        dealDetails.setId(key);
        dealDetails.setGoodsNumber(number);
        dealDetails.setGoodsId(goodsId);
        dealDetails.setGoodsPrice(price);
        dealDetailsMapper.insert(dealDetails);

        QueryWrapper<ShopGoods> queryWrapper = Wrappers.query();
        queryWrapper.eq("goods_id",goodsId).eq("shop_id",shopId);
        ShopGoods shopGoods = shopGoodsMapper.selectOne(queryWrapper);
        shopGoods.setNumber(shopGoods.getNumber()-number);
        if(shopGoods.getNumber()<=0) {
            shopGoods.setStatus("soldOut");
            QueryWrapper<ShoppingCart> shoppingCartqueryWrapper = Wrappers.query();
            shoppingCartqueryWrapper.eq("shop_id",shopId).eq("goods_id",goodsId);
            List<ShoppingCart>shoppingCartList = shoppingCartMapper.selectList(shoppingCartqueryWrapper);
            for(int i=0;i<shoppingCartList.size();i++){
                ShoppingCart shoppingCart = shoppingCartList.get(i);
                shoppingCart.setStatus("disabled");
                UpdateWrapper<ShoppingCart> shoppingCartUpdateWrapper = Wrappers.update();
                shoppingCartMapper.update(shoppingCart,shoppingCartUpdateWrapper);
            }
        }
        UpdateWrapper<ShopGoods> updateWrapper = Wrappers.update();
        updateWrapper.eq("goods_id",goodsId).eq("shop_id",shopId);
        shopGoodsMapper.update(shopGoods,updateWrapper);

        Goods goods = goodsMapper.selectById(goodsId);
        goods.setNumber(goods.getNumber()-number);
        goodsMapper.updateById(goods);
        return MkplatWebModel.convertMetroPayWebModel(key);
    }

    public MkplatWebModel changeDealStatus(Map<String,Object> map){
        String dealId = map.get("dealId").toString();
        String status = map.get("status").toString();
        Deal deal = getById(dealId);
        if(!status.equals("waitSend")&&!status.equals("waitConfirm")){
            User user = userMapper.selectById(deal.getUserId());
            Shop shop = shopMapper.selectById(deal.getShopId());

            QueryWrapper<DealDetails> queryWrapper= Wrappers.query();
            queryWrapper.eq("deal_id",dealId);
            List<DealDetails> dealDetailsList = dealDetailsMapper.selectList(queryWrapper);
            //收货
            if(status.equals("waitComment")) {
                //更改user、shop、shopGoods的dealnumber
                user.setDealNumber(user.getDealNumber()+1);

                shop.setDealNumber(shop.getDealNumber()+1);

                for(int i=0;i<dealDetailsList.size();i++){
                    String goodsId = dealDetailsList.get(i).getGoodsId();
                    QueryWrapper<ShopGoods> queryWrapper1 = Wrappers.query();
                    queryWrapper1.eq("shop_id",deal.getShopId()).eq("goods_id",goodsId);
                    ShopGoods shopGoods = shopGoodsMapper.selectOne(queryWrapper1);
                    UpdateWrapper<ShopGoods> updateWrapper = Wrappers.update();
                    updateWrapper.eq("shop_id",deal.getShopId()).eq("goods_id",goodsId);
                    shopGoods.setDealNumber(shopGoods.getDealNumber()+1);
                    shopGoodsMapper.update(shopGoods,updateWrapper);
                }
            }
            else if(status.equals("over") && map.get("comment")!=null){
                user.setCommentNumber(user.getCommentNumber()+1);

                shop.setCommentNumber(shop.getCommentNumber()+1);

                for(int i=0;i<dealDetailsList.size();i++){
                    String goodsId = dealDetailsList.get(i).getGoodsId();
                    QueryWrapper<ShopGoods> queryWrapper1 = Wrappers.query();
                    queryWrapper1.eq("shop_id",deal.getShopId()).eq("goods_id",goodsId);
                    ShopGoods shopGoods = shopGoodsMapper.selectOne(queryWrapper1);
                    UpdateWrapper<ShopGoods> updateWrapper = Wrappers.update();
                    updateWrapper.eq("shop_id",deal.getShopId()).eq("goods_id",goodsId);
                    shopGoods.setCommentNumber(shopGoods.getCommentNumber()+1);
                    shopGoodsMapper.update(shopGoods,updateWrapper);
                }
            }
            userMapper.updateById(user);
            shopMapper.updateById(shop);
        }
        deal.setStatus(status);
        updateById(deal);
        return MkplatWebModel.success();
    }

    public MkplatWebModel deleteDeal(String dealId){
        Deal deal = getById(dealId);
        String shopId = deal.getShopId();
        QueryWrapper<DealDetails> dealDetailsQueryWrapper = Wrappers.query();
        dealDetailsQueryWrapper.eq("id",dealId);
        List<DealDetails> dealDetailsList = dealDetailsMapper.selectList(dealDetailsQueryWrapper);

        for(int i=0;i<dealDetailsList.size();i++){
            String goodsId = dealDetailsList.get(i).getGoodsId();
            Integer number = dealDetailsList.get(i).getGoodsNumber();
            Goods goods = goodsMapper.selectById(goodsId);
            goods.setNumber(goods.getNumber()+number);
            goodsMapper.updateById(goods);

            QueryWrapper<ShopGoods> shopGoodsQueryWrapper = Wrappers.query();
            shopGoodsQueryWrapper.eq("goods_id",goodsId).eq("shop_id",shopId);
            ShopGoods shopGoods = shopGoodsMapper.selectOne(shopGoodsQueryWrapper);
            shopGoods.setNumber(shopGoods.getNumber()+number);
            UpdateWrapper<ShopGoods> shopGoodsUpdateWrapper = Wrappers.update();
            shopGoodsUpdateWrapper.eq("goods_id",goodsId).eq("shop_id",shopId);
            shopGoodsMapper.update(shopGoods,shopGoodsUpdateWrapper);
        }

        removeById(dealId);
        dealDetailsMapper.delete(dealDetailsQueryWrapper);
        return MkplatWebModel.success();
    }

    public MkplatWebModel addDealBatch(Map<String,Object> map){
        String userId = map.get("userId").toString();
        List<Map<String,Object>> dealList= (List<Map<String, Object>>) map.get("data");
        for(int i=0;i<dealList.size();i++){
            Deal deal = new Deal();
            String dealId= UUIDGenerator.generate();
            List<Map<String,Object>> dealDetailsList = (List<Map<String, Object>>) dealList.get(i);
            BigDecimal dealPrice = new BigDecimal(0);
            for(int j=0;j<dealDetailsList.size();j++){
                String goodsId = dealDetailsList.get(j).get("goodsId").toString();
                String shopId = dealDetailsList.get(j).get("shopId").toString();
                Integer number = (Integer) dealDetailsList.get(j).get("num");
                BigDecimal price = new BigDecimal(dealDetailsList.get(j).get("price").toString());

                Goods goods = goodsMapper.selectById(goodsId);
                goods.setNumber(goods.getNumber()-number);
                goodsMapper.updateById(goods);

                QueryWrapper<ShopGoods> shopGoodsQueryWrapper = Wrappers.query();
                shopGoodsQueryWrapper.eq("shop_id",shopId).eq("goods_id",goodsId);
                ShopGoods shopGoods = shopGoodsMapper.selectOne(shopGoodsQueryWrapper);
                shopGoods.setNumber(shopGoods.getNumber()-number);
                int t = shopGoodsMapper.updateById(shopGoods);
                System.out.println(t);

                DealDetails dealDetails = new DealDetails();
                dealDetails.setGoodsPrice(price);
                dealDetails.setId(dealId);
                dealDetails.setGoodsId(goodsId);
                dealDetails.setGoodsNumber(number);
                dealDetailsMapper.insert(dealDetails);
                dealPrice = dealPrice.add(price.multiply(BigDecimal.valueOf(number)));
                deal.setShopId(shopId);

                QueryWrapper<ShoppingCart> queryWrapper = Wrappers.query();
                queryWrapper.eq("user_id",userId).eq("goods_id",goodsId).eq("shop_id",shopId);
                shoppingCartMapper.delete(queryWrapper);
            }
            deal.setStatus("waitPay");
            deal.setUserId(userId);
            deal.setId(dealId);
            deal.setDealPrice(dealPrice);
            deal.setCreateTime(LocalDateTime.now());
            save(deal);

        }
        return MkplatWebModel.success();
    }

}
