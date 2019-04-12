package com.wrg.supermarket.service;

import com.wrg.supermarket.component.MkplatWebModel;
import com.wrg.supermarket.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author Wang Rengang
 * @since 2019-04-03
 */
public interface IUserService extends IService<User> {
    //根据id获取user信息
    MkplatWebModel getUserData(String id);
    MkplatWebModel modify(User entity);
}
