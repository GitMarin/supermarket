package com.wrg.supermarket;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

//@EnableEurekaServer
@SpringBootApplication//(exclude = DataSourceAutoConfiguration.class)
@MapperScan("com.wrg.supermarket.mapper")
//@ComponentScan(basePackages = {"com.wrg.supermarket.mapper"})
public class SupermarketApplication {

    public static void main(String[] args) {
        SpringApplication.run(SupermarketApplication.class, args);
    }

}
