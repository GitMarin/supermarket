package com.wrg.supermarket.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.wrg.supermarket.component.JavaBeanUtil;
import com.wrg.supermarket.component.MkplatWebModel;
import com.wrg.supermarket.entity.Goods;
import com.wrg.supermarket.entity.Shop;
import com.wrg.supermarket.entity.ShopGoods;
import com.wrg.supermarket.mapper.GoodsMapper;
import com.wrg.supermarket.mapper.ShopGoodsMapper;
import com.wrg.supermarket.mapper.ShopMapper;
import com.wrg.supermarket.service.IShopService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Wang Rengang
 * @since 2019-04-03
 */
@Service
public class ShopServiceImpl extends ServiceImpl<ShopMapper, Shop> implements IShopService {

    @Autowired
    private ShopGoodsMapper shopGoodsMapper;
    @Autowired
    private GoodsMapper goodsMapper;
    @Override
    public MkplatWebModel getOneShop(String id){
        return MkplatWebModel.convertMetroPayWebModel(getById(id));
    }

    @Override
    public MkplatWebModel getShopPage(Map<String,Object> map){
        int current=Integer.parseInt(map.get("current").toString());
        int size=Integer.parseInt(map.get("size").toString());
        QueryWrapper<Shop> queryWrapper= Wrappers.query();
        if(map.get("shopName")!=null) queryWrapper.like("name",map.get("shopName").toString());
        if( map.get("goodsId")!=null) {
            String goodsId = map.get("goodsId").toString();
            QueryWrapper<ShopGoods> shopGoodsQueryWrapper= Wrappers.query();
            shopGoodsQueryWrapper.eq("goods_id",goodsId).eq("status","online");
            List<ShopGoods> shopGoodsList = shopGoodsMapper.selectList(shopGoodsQueryWrapper);
            for(int i=0;i<shopGoodsList.size();i++) queryWrapper.eq("id",shopGoodsList.get(i).getShopId()).or();
        }
        IPage<Shop> pageData=page(new com.baomidou.mybatisplus.extension.plugins.pagination.Page<Shop>(current,size),queryWrapper);
        //处理查询后数据
        List<Shop> pageList=pageData.getRecords();
        //存放最后的结果
        List<Map<String,Object>> resultList=new ArrayList<>();
        for(int j=0;j<pageList.size();j++){
            Map<String,Object> resultMap = JavaBeanUtil.transBean2Map(pageList.get(j));
            if( map.get("goodsId")!=null){
                String goodsId = map.get("goodsId").toString();
                QueryWrapper<ShopGoods> shopGoodsQueryWrapper= Wrappers.query();
                shopGoodsQueryWrapper.eq("goods_id",goodsId).eq("shop_id",pageList.get(j).getId());
                ShopGoods shopGoods=shopGoodsMapper.selectOne(shopGoodsQueryWrapper);
                resultMap.put("depict",shopGoods.getDepict());
                resultMap.put("price",shopGoods.getPrice());
                resultMap.put("number",shopGoods.getNumber());
            }
            resultList.add(resultMap);
        }
        return MkplatWebModel.convertMetroPayWebModel(pageData.getTotal(),resultList);
    }


    @Override
    public MkplatWebModel modifyShopStatus(Map<String, Object> map){
        Shop shop = new Shop();
        String shopId = map.get("id").toString();
        String status = map.get("status").toString();
        shop.setId(shopId);
        shop.setStatus(status);
        if(status.equals("disabled")){
            QueryWrapper<ShopGoods> shopGoodsQueryWrapper = Wrappers.query();
            shopGoodsQueryWrapper.eq("shop_id",shopId).eq("status","online");
            List<ShopGoods> shopGoodsList = shopGoodsMapper.selectList(shopGoodsQueryWrapper);

            UpdateWrapper<ShopGoods> shopGoodsUpdateWrapper = Wrappers.update();
            shopGoodsUpdateWrapper.eq("shop_id",shopId).set("status","disabled");
            shopGoodsMapper.update(new ShopGoods(),shopGoodsUpdateWrapper);

            for(int i = 0;i<shopGoodsList.size();i++){
                ShopGoods shopGoods = shopGoodsList.get(i);
                String goodsId = shopGoods.getGoodsId();
                UpdateWrapper<Goods> goodsUpdateWrapper = Wrappers.update();
                goodsUpdateWrapper.eq("id",goodsId).set("number",goodsMapper.selectById(goodsId).getNumber()-shopGoods.getNumber());
                goodsMapper.update(new Goods(),goodsUpdateWrapper);
            }
        }else if(status.equals("enabledAll")){
            UpdateWrapper<ShopGoods> shopGoodsUpdateWrapper = Wrappers.update();
            shopGoodsUpdateWrapper.eq("shop_id",shopId).set("status","prepared");
            shopGoodsMapper.update(new ShopGoods(),shopGoodsUpdateWrapper);

            shop.setStatus("enabled");
        }
        updateById(shop);
        return MkplatWebModel.success();
    }

    @Override
    public MkplatWebModel modifyShop(Map<String,Object> map){
        Shop shop = new Shop();
        shop.setId(map.get("id").toString());
        shop.setAddress(map.get("address").toString());
        shop.setName(map.get("name").toString());
        shop.setPicLink(map.get("picLink").toString());
        shop.setRemark(map.get("remark").toString());
        updateById(shop);
        return MkplatWebModel.success();
    }
}
