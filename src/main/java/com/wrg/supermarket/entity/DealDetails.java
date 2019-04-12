package com.wrg.supermarket.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * @ClassName DealDetails
 * @Description TODO
 * @Author Wang Rengang
 * @Date 2019/4/10 22:06
 * @Version 1.0
 **/
@TableName("deal_details")
public class DealDetails implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String goodsId;
    private String goodsNumber;
    private Float goodsPrice;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsNumber() {
        return goodsNumber;
    }

    public void setGoodsNumber(String goodsNumber) {
        this.goodsNumber = goodsNumber;
    }

    public Float getPrice() {
        return goodsPrice;
    }

    public void setPrice(Float price) {
        this.goodsPrice = price;
    }


}
