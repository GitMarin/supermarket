package com.wrg.supermarket.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @ClassName Deal
 * @Description TODO
 * @Author Wang Rengang
 * @Date 2019/4/10 22:01
 * @Version 1.0
 **/
@TableName("deal")
public class Deal implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String status;
    private String userId;
    private String shopId;
    private String goodsNumber;
    private Float dealPrice;
    private LocalDateTime createTime;
    private String comment;
    private LocalDateTime commentTime;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getGoodsNumber() {
        return goodsNumber;
    }

    public void setGoodsNumber(String goodsNumber) {
        this.goodsNumber = goodsNumber;
    }

    public Float getdealPrice() {
        return dealPrice;
    }

    public void setdealPrice(Float dealPrice) {
        this.dealPrice = dealPrice;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDateTime getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(LocalDateTime commentTime) {
        this.commentTime = commentTime;
    }


}
