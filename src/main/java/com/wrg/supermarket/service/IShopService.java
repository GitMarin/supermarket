package com.wrg.supermarket.service;

import com.wrg.supermarket.component.MkplatWebModel;
import com.wrg.supermarket.entity.Shop;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Wang Rengang
 * @since 2019-04-03
 */
public interface IShopService extends IService<Shop> {

    MkplatWebModel getShop(String id);
}
