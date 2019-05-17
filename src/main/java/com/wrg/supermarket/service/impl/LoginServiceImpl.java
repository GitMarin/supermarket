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
import com.wrg.supermarket.entity.Account;
import com.wrg.supermarket.entity.Shop;
import com.wrg.supermarket.entity.User;
import com.wrg.supermarket.mapper.AccountMapper;
import com.wrg.supermarket.mapper.ShopMapper;
import com.wrg.supermarket.mapper.UserMapper;
import com.wrg.supermarket.service.ILoginService;
import com.wrg.supermarket.utils.exception.MkplatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
    @Autowired
    private AccountMapper accountMapper;

    @Override
    public MkplatWebModel login(String username, String password,String type){
        QueryWrapper<Account> accountQueryWrapper = Wrappers.query();
        accountQueryWrapper.eq("username",username).eq("password",password);
        if(type.equals("shop"))accountQueryWrapper.eq("type",type);
        else if(type.equals("user"))accountQueryWrapper.ne("type","shop");
        Account account = accountMapper.selectOne(accountQueryWrapper);

        if(account==null) return MkplatWebModel.convertMetroPayWebModel(new MkplatException(PARAM_INVAILD));
        else {
            Map<String,Object> result=new HashMap<>();
            result.put("id",account.getId());
            result.put("type",account.getType());
            return MkplatWebModel.convertMetroPayWebModel(result);
        }
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
        user.setShoppingCartNumber(0);
        user.setId(key);
        //user.setType("asf");
        //将年转化为年龄
        user.setAge(LocalDateTime.now().getYear()-Integer.parseInt(splitYear((String) map.get("year")))-1);
        //获取当前时间
        LocalDateTime currentDate=LocalDateTime.now();
        user.setRegisterTime(currentDate);
        user.setAvatar("img/user01.jpg");
        save(user);

        Account account = new Account();
        account.setId(key);
        account.setUsername(map.get("username").toString());
        account.setPassword(map.get("password").toString());
        account.setWallet(new BigDecimal(10000));
        account.setType("user");
        accountMapper.insert(account);

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
        shop.setId(key);
        //获取当前时间
        LocalDateTime currentDate=LocalDateTime.now();
        shop.setRegisterTime(currentDate);
        shop.setPicLink("img/user01.jpg");
        shopMapper.insert(shop);

        Account account = new Account();
        account.setId(key);
        account.setUsername(map.get("username").toString());
        account.setPassword(map.get("password").toString());
        account.setType("shop");
        accountMapper.insert(account);

        Map<String,Object> result = new HashMap<>();
        result.put("id",key);
        return MkplatWebModel.convertMetroPayWebModel(result);
    }

    @Override
    public MkplatWebModel testName(String username,String type){
        Map<String,Object> result = new HashMap<>();
        QueryWrapper<Account> queryWrapper=Wrappers.query();
        queryWrapper.eq("username",username);
        if(type.equals("shop")) queryWrapper.eq("type","shop");
        else if(type.equals("user")) queryWrapper.ne("type","shop");

        if(accountMapper.selectCount(queryWrapper)>0)   result.put("result",false);
        else result.put("result",true);
        return MkplatWebModel.convertMetroPayWebModel(result);
    }

    private String splitYear(String date){
        return date.split("-")[0];
    }
}
