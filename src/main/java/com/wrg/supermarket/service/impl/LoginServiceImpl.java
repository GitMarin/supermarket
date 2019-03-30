package com.wrg.supermarket.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wrg.supermarket.component.MkplatWebModel;
import com.wrg.supermarket.entity.User;
import com.wrg.supermarket.mapper.UserMapper;
import com.wrg.supermarket.service.ILoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
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
    private UserMapper userMapper;

    @Override
    public MkplatWebModel login(String username,String password){
        Map<String,Object> Data = new HashMap<>(1);
        Data.put("id",userMapper.login(username,password));
        return MkplatWebModel.convertMetroPayWebModel(Data);
    }
}
