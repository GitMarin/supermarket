package com.wrg.supermarket.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wrg.supermarket.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Wang Rengang
 * @since 2019-03-28
 */
@Repository
public interface UserMapper extends BaseMapper<User>{
}
