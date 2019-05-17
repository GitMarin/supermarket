package com.wrg.supermarket.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @ClassName ShopGoods
 * @Description TODO
 * @Author Wang Rengang
 * @Date 2019/4/12 16:21
 * @Version 1.0
 **/
@TableName("shop_goods")
public class ShopGoods implements Serializable {
    private static final long serialVersionUID = 1L;
    private String goodsId;
    private String shopId;
    private BigDecimal price;
    private String depict;
    private String status;
    private Integer number;
    private Integer dealNumber;
    private Integer commentNumber;
    private LocalDateTime lastOnlineTime;
    private LocalDateTime lastCommentTime;
    private LocalDateTime lastDealTime;
    private LocalDateTime createTime;

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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getDealNumber() {
        return dealNumber;
    }

    public void setDealNumber(Integer dealNumber) {
        this.dealNumber = dealNumber;
    }

    public Integer getCommentNumber() {
        return commentNumber;
    }

    public void setCommentNumber(Integer commentNumber) {
        this.commentNumber = commentNumber;
    }

    public LocalDateTime getLastOnlineTime() {
        return lastOnlineTime;
    }

    public void setLastOnlineTime(LocalDateTime lastOnlineTime) {
        this.lastOnlineTime = lastOnlineTime;
    }

    public LocalDateTime getLastCommentTime() {
        return lastCommentTime;
    }

    public void setLastCommentTime(LocalDateTime lastCommentTime) {
        this.lastCommentTime = lastCommentTime;
    }

    public LocalDateTime getLastDealTime() {
        return lastDealTime;
    }

    public void setLastDealTime(LocalDateTime lastDealTime) {
        this.lastDealTime = lastDealTime;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public String getDepict() {
        return depict;
    }

    public void setDepict(String depict) {
        this.depict = depict;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
