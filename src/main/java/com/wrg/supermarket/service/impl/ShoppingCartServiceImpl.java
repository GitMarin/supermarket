package com.wrg.supermarket.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wrg.supermarket.component.JavaBeanUtil;
import com.wrg.supermarket.component.MkplatWebModel;
import com.wrg.supermarket.entity.Goods;
import com.wrg.supermarket.entity.ShopGoods;
import com.wrg.supermarket.entity.ShoppingCart;
import com.wrg.supermarket.mapper.*;
import com.wrg.supermarket.service.IShoppingCartService;
import com.wrg.supermarket.utils.enums.PlatErrorCode;
import com.wrg.supermarket.utils.exception.MkplatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName ShoppingCartServiceImpl
 * @Description TODO
 * @Author Wang Rengang
 * @Date 2019/4/25 11:47
 * @Version 1.0
 **/
@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements IShoppingCartService {

    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private ShopGoodsMapper shopGoodsMapper;
    @Autowired
    private GoodsTypeMapper goodsTypeMapper;
    @Autowired
    private ShopMapper shopMapper;
    @Override
    public MkplatWebModel addShoppingCart(Map<String,Object> map){
        ShoppingCart shoppingCart = new ShoppingCart();
        String userId = map.get("userId").toString();
        String shopId = map.get("shopId").toString();
        String goodsId = map.get("goodsId").toString();
        QueryWrapper<ShoppingCart> queryWrapper = Wrappers.query();
        queryWrapper.eq("user_id",userId).eq("goods_id",goodsId).eq("shop_id",shopId);
        if(count(queryWrapper)>0) return MkplatWebModel.convertMetroPayWebModel(new MkplatException(PlatErrorCode.PARAM_INVAILD));
        shoppingCart.setGoodsId(goodsId);
        shoppingCart.setShopId(shopId);
        shoppingCart.setUserId(userId);
        shoppingCart.setStatus("enabled");
        save(shoppingCart);
        return MkplatWebModel.success();
    }

    @Override
    public MkplatWebModel getShoppingCartCount(String id){
        QueryWrapper<ShoppingCart> queryWrapper = Wrappers.query();
        queryWrapper.eq("user_id",id);
        return MkplatWebModel.convertMetroPayWebModel(count(queryWrapper));
    }

    @Override
    public MkplatWebModel getShoppingCartData(String id){
        QueryWrapper<ShoppingCart> queryWrapper = Wrappers.query();
        queryWrapper.eq("user_id",id).groupBy("shop_id");
        List<ShoppingCart> shoppingCartList = list(queryWrapper);

        List<Map<String,Object>> resultList=new ArrayList<>();
        for(int i=0;i<shoppingCartList.size();i++){
            Map<String,Object> resultMap = new HashMap<>();
            ShoppingCart shoppingCart=shoppingCartList.get(i);
            String shopId = shoppingCart.getShopId();
            resultMap.put("shopName",shopMapper.selectById(shopId).getName());
            resultMap.put("shopId",shopId);

            QueryWrapper<ShoppingCart> queryWrapper1 = Wrappers.query();
            queryWrapper1.eq("user_id",id).eq("shop_id",shopId);
            List<ShoppingCart> shoppingCartList1 = list(queryWrapper1);

            List<Map<String,Object>> goodsList=new ArrayList<>();
            for(int j=0;j<shoppingCartList1.size();j++){
                String goodsId = shoppingCartList1.get(j).getGoodsId();

                QueryWrapper<ShopGoods> shopGoodsQueryWrapper = Wrappers.query();
                shopGoodsQueryWrapper.eq("shop_id",shopId).eq("goods_id",goodsId);
                ShopGoods shopGoods = shopGoodsMapper.selectOne(shopGoodsQueryWrapper);

                Map<String,Object> goodsMap=JavaBeanUtil.transBean2Map(shopGoods);

                goodsMap.put("goodsId",goodsId);
                Goods goods = goodsMapper.selectById(goodsId);
                goodsMap.put("goodsName",goods.getName());
                goodsMap.put("picLink",goods.getCondensePicLink());
                goodsMap.put("brandName",goods.getBrandName());
                goodsMap.put("marque",goods.getMarque());
                goodsMap.put("typeName",goodsTypeMapper.selectById(goods.getTypeId()).getName());
                goodsMap.put("num",1);

                goodsList.add(goodsMap);
            }
            resultMap.put("cardData",goodsList);
            resultList.add(resultMap);
        }
        return MkplatWebModel.convertMetroPayWebModel(resultList);
    }

    @Override
    public MkplatWebModel deleteShoppingCart(Map<String,Object> map){
        String userId = map.get("userId").toString();
        String shopId = map.get("shopId").toString();
        String goodsId = map.get("goodsId").toString();
        QueryWrapper<ShoppingCart> queryWrapper = Wrappers.query();
        queryWrapper.eq("shop_id",shopId).eq("user_id",userId).eq("goods_id",goodsId);
        remove(queryWrapper);
        return MkplatWebModel.success();
    }

}
