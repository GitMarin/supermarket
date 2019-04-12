package com.wrg.supermarket.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.wrg.supermarket.component.MkplatWebModel;
import com.wrg.supermarket.entity.User;
import com.wrg.supermarket.mapper.UserMapper;
import com.wrg.supermarket.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wrg.supermarket.utils.enums.PlatErrorCode;
import com.wrg.supermarket.utils.exception.MkplatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author Wang Rengang
 * @since 2019-04-03
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    UserMapper mapper;

    @Override
    public MkplatWebModel getUserData(String id){
        QueryWrapper<User> queryWrapper=Wrappers.query();
        queryWrapper.eq("id",id);
        User Data = mapper.selectOne(queryWrapper);
        return MkplatWebModel.convertMetroPayWebModel(Data);
    }

    @Override
    public MkplatWebModel modify(User entity){
        /* 不需要更改登录名
        if (StringUtils.isNullOrEmpty(entity.getLoginName())) {
            return MkplatWebModel.errInfo("登录名不能为空");
        }
        User user = mapper.getAccountByLoginNameOrId(entity..getLoginName(), null);
        if ((user != null) && (!user.getId().equals(entity.getId()))) {
            return ResponseData.errInfo("登录名已存在");
        }*/
        //保存修改人信息
        //entity.setModifyId(AccountUtil.getCurrentAccount().getId());
        //entity.setModifyTime(LocalDateTime.now());
        if (entity.getId() == null) {
            return MkplatWebModel.convertMetroPayWebModel(new MkplatException(PlatErrorCode.PARAM_INVAILD,"主键ID不能为空"));
        }
        UpdateWrapper<User> updateWrapper= Wrappers.update();
        updateWrapper.eq("id",entity.getId());
        mapper.update(entity,updateWrapper);
        return MkplatWebModel.success();
    }

}
