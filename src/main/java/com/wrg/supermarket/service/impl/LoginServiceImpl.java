package com.wrg.supermarket.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.injector.methods.SelectOne;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wrg.supermarket.component.MkplatWebModel;
import com.wrg.supermarket.entity.User;
import com.wrg.supermarket.mapper.UserMapper;
import com.wrg.supermarket.service.ILoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName LoginServiceImpl
 * @Description TODO
 * @Author Wang Rengang
 * @Date 2019/3/28 23:23
 * @Version 1.0
 **/
@Service
public class LoginServiceImpl extends ServiceImpl<UserMapper, User> implements ILoginService {

    @Autowired
    private UserMapper mapper;
    @Override
    public MkplatWebModel login(String username,String password){
        QueryWrapper<User> queryWrapper=Wrappers.query();
        queryWrapper.eq("username",username).eq("password",password);
        User data = mapper.selectOne(queryWrapper);
        return MkplatWebModel.convertMetroPayWebModel(data);
    }
}
