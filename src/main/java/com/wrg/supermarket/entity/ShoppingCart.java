package com.wrg.supermarket.entity;


import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * @ClassName ShoppingCart
 * @Description TODO
 * @Author Wang Rengang
 * @Date 2019/4/24 16:21
 * @Version 1.0
 **/
@TableName("shopping_cart")
public class ShoppingCart implements Serializable {
    private static final long serialVersionUID = 1L;
    private String goodsId;
    private String shopId;
    private String userId;
    private String status;

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
