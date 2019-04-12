package com.wrg.supermarket.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wrg.supermarket.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @ClassName UserMapper
 * @Description TODO
 * @Author Wang Rengang
 * @Date 2019/3/28 23:27
 * @Version 1.0
 **/
@Repository
public interface UserMapper extends BaseMapper<User>{
}
