package com.wrg.supermarket.service.impl;

import com.baomidou.mybatisplus.core.injector.methods.SelectById;
import com.wrg.supermarket.component.MkplatWebModel;
import com.wrg.supermarket.entity.Shop;
import com.wrg.supermarket.mapper.ShopMapper;
import com.wrg.supermarket.service.IShopService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

    @Override
    public MkplatWebModel getShop(String id){
        return MkplatWebModel.convertMetroPayWebModel(getById(id));
    }
}
