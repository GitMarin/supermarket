package com.wrg.supermarket.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wrg.supermarket.component.MkplatWebModel;
import com.wrg.supermarket.entity.User;

/**
 * @ClassName ILoginService
 * @Description TODO
 * @Author Wang Rengang
 * @Date 2019/3/28 23:20
 * @Version 1.0
 **/
public interface ILoginService extends IService<User> {

    MkplatWebModel login(String username, String password);
}
