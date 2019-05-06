package com.wrg.supermarket.service;

import com.wrg.supermarket.component.MkplatWebModel;
import com.wrg.supermarket.entity.Shop;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Wang Rengang
 * @since 2019-04-03
 */
public interface IShopService extends IService<Shop> {

    MkplatWebModel getOneShop(String id);

    MkplatWebModel getShopPage(Map<String,Object> map);

    MkplatWebModel modifyShopStatus(Map<String,Object> map);
}
