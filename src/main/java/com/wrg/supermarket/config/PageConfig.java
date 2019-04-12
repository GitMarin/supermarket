package com.wrg.supermarket.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @ClassName PageConfig
 * @Description TODO
 * @Author Wang Rengang
 * @Date 2019/4/11 9:26
 * @Version 1.0
 **/
@EnableTransactionManagement
@Configuration
@MapperScan("com.wrg.supermarket.mapper")
public class PageConfig {
    /*
     *分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor(){
        return new PaginationInterceptor();
    }
}
