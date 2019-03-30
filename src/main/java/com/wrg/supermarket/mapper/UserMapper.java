package com.wrg.supermarket.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wrg.supermarket.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * @ClassName UserMapper
 * @Description TODO
 * @Author Wang Rengang
 * @Date 2019/3/28 23:27
 * @Version 1.0
 **/
@Component
public interface UserMapper extends BaseMapper<User>{
    String login(@Param("username")String username,@Param("password")String password);
}
