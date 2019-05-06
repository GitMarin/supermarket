package com.wrg.supermarket.config;

import com.wrg.supermarket.component.LoginHandlerInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

/**
 * @ClassName MyMvcConfig
 * @Description TODO
 * @Author Wang Rengang
 * @Date 2019/3/28 10:16
 * @Version 1.0
 **/

//@EnableWebMvc
@Configuration
public class MyMvcConfig extends WebMvcConfigurationSupport {
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        //super.addInterceptors(registry);
        registry.addInterceptor(new LoginHandlerInterceptor()).addPathPatterns("/**")
                .excludePathPatterns("/login.html","/","/login");
//        ,
//        "/js/vue.js","/js/index.js","/js/axios.min.js","/js/index.js",
//                "/js/vuescroll.js","/js/vuescroll-native.js","/js/vuescroll-slide.js",
//                "/styles/bootstrap.min.css","/styles/index.css","/styles/vuescroll.css"
    }

    @Override
    protected void addViewControllers(ViewControllerRegistry registry) {
        //super.addViewControllers(registry);
        registry.addViewController("/").setViewName("login");
        registry.addViewController("/login.html").setViewName("login");
        registry.addViewController("/shop.html").setViewName("shop");
        registry.addViewController("/details.html").setViewName("details");
        registry.addViewController("/information.html").setViewName("information");
        registry.addViewController("/index.html").setViewName("index");
        registry.addViewController("/manage.html").setViewName("manage");
        registry.addViewController("/shopDetails.html").setViewName("shopDetails");
        registry.addViewController("/buy.html").setViewName("buy");
        registry.addViewController("/shoppingCart.html").setViewName("shoppingCart");


    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/");
//        registry.addResourceHandler("swagger-ui.html")
//                .addResourceLocations("classpath:/META-INF/resources/");
//        registry.addResourceHandler("/webjars/**")
//                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
