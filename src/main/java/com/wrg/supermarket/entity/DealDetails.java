package com.wrg.supermarket.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

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
    private Integer goodsNumber;
    private BigDecimal goodsPrice;


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

    public Integer getGoodsNumber() {
        return goodsNumber;
    }

    public void setGoodsNumber(Integer goodsNumber) {
        this.goodsNumber = goodsNumber;
    }

    public BigDecimal getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(BigDecimal goodsPrice) {
        this.goodsPrice = goodsPrice;
    }
}
