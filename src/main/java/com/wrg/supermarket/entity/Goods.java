package com.wrg.supermarket.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import javax.print.DocFlavor;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @ClassName Goods
 * @Description TODO
 * @Author Wang Rengang
 * @Date 2019/3/27 19:57
 * @Version 1.0
 **/
@TableName("goods")
public class Goods implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private BigDecimal price;
    private String name;
    private String typeId;
    private String brandName;
    private String status;
    //商品型号
    private String marque;
    private String condensePicLink;
    private Integer number;
    private Integer dealNumber;
    private Integer commentNumber;
    private LocalDateTime lastCommentTime;
    private LocalDateTime lastDealTime;
    private LocalDateTime createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public String getCondensePicLink() {
        return condensePicLink;
    }

    public void setCondensePicLink(String condensePicLink) {
        this.condensePicLink = condensePicLink;
    }

    public Integer getCommentNumber() {
        return commentNumber;
    }

    public void setCommentNumber(Integer commentNumber) {
        this.commentNumber = commentNumber;
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

    public Integer getDealNumber() {
        return dealNumber;
    }

    public void setDealNumber(Integer dealNumber) {
        this.dealNumber = dealNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
}
