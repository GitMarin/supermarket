package com.wrg.supermarket.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.injector.methods.Insert;
import com.baomidou.mybatisplus.core.injector.methods.SelectOne;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wrg.supermarket.component.JavaBeanUtil;
import com.wrg.supermarket.component.MkplatWebModel;
import com.wrg.supermarket.component.UUIDGenerator;
import com.wrg.supermarket.entity.Shop;
import com.wrg.supermarket.entity.User;
import com.wrg.supermarket.mapper.ShopMapper;
import com.wrg.supermarket.mapper.UserMapper;
import com.wrg.supermarket.service.ILoginService;
import com.wrg.supermarket.utils.exception.MkplatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.wrg.supermarket.utils.enums.PlatErrorCode.PARAM_INVAILD;

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
    @Autowired
    private ShopMapper shopMapper;
    @Override
    public MkplatWebModel loginUser(String username,String password){
        QueryWrapper<User> queryWrapper=Wrappers.query();
        queryWrapper.eq("username",username).eq("password",password);
        User data = mapper.selectOne(queryWrapper);
        if(data==null) return MkplatWebModel.convertMetroPayWebModel(new MkplatException(PARAM_INVAILD));
        else return MkplatWebModel.convertMetroPayWebModel(data);
    }

    @Override
    public MkplatWebModel loginShop(String username,String password){
        QueryWrapper<Shop> queryWrapper=Wrappers.query();
        queryWrapper.eq("username",username).eq("password",password);
        Shop data = shopMapper.selectOne(queryWrapper);
        if(data==null) return MkplatWebModel.convertMetroPayWebModel(new MkplatException(PARAM_INVAILD));
        else return MkplatWebModel.convertMetroPayWebModel(data);
    }


    @Override
    public MkplatWebModel registerUser(Map<String,Object> map){
        //生成主键
        String key= UUIDGenerator.generate();
        //map转为实体类bean
        User user = new User();
        JavaBeanUtil.transMap2Bean(map,user);
        user.setDealNumber(0);
        user.setCommentNumber(0);
        user.setFavoritesNumber(0);
        user.setShoppingCartNumber(0);
        user.setId(key);
        user.setType("asf");
        //将年转化为年龄
        user.setAge(LocalDateTime.now().getYear()-Integer.parseInt(splitYear((String) map.get("year")))-1);
        //获取当前时间
        LocalDateTime currentDate=LocalDateTime.now();
        user.setLastAccessTime(currentDate);
        user.setRegisterTime(currentDate);
        user.setAvatar("img/user01.jpg");
        save(user);
        Map<String,Object> result = new HashMap<>();
        result.put("id",key);
        return MkplatWebModel.convertMetroPayWebModel(result);
    }

    @Override
    public MkplatWebModel registerShop(Map<String,Object> map){
        //生成主键
        String key= UUIDGenerator.generate();
        //map转为实体类bean
        Shop shop = new Shop();
        JavaBeanUtil.transMap2Bean(map,shop);
        shop.setCommentNumber(0);
        shop.setDealNumber(0);
        shop.setGoodsNumber(0);
        shop.setId(key);
        //获取当前时间
        LocalDateTime currentDate=LocalDateTime.now();
        shop.setLastAccessTime(currentDate);
        shop.setRegisterTime(currentDate);
        shop.setPicLink("img/user01.jpg");
        shopMapper.insert(shop);
        Map<String,Object> result = new HashMap<>();
        result.put("id",key);
        return MkplatWebModel.convertMetroPayWebModel(result);
    }

    @Override
    public MkplatWebModel testUsername(String username){
        Map<String,Object> result = new HashMap<>();
        QueryWrapper<User> queryWrapper=Wrappers.query();
        queryWrapper.eq("username",username);
        if(count(queryWrapper)>0)   result.put("result",false);
        else result.put("result",true);
        return MkplatWebModel.convertMetroPayWebModel(result);
    }

    @Override
    public MkplatWebModel testShopName(String username){
        Map<String,Object> result = new HashMap<>();
        QueryWrapper<Shop> queryWrapper=Wrappers.query();
        queryWrapper.eq("username",username);
        if(shopMapper.selectCount(queryWrapper)>0)   result.put("result",false);
        else result.put("result",true);
        return MkplatWebModel.convertMetroPayWebModel(result);
    }

    private String splitYear(String date){
        return date.split("-")[0];
    }
}
