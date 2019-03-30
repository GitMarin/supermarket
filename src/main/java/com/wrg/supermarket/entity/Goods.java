package com.wrg.supermarket.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import javax.print.DocFlavor;
import java.io.Serializable;
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
    private String id;
    private Float price;
    private String name;
    private String categoryName;
    private String brandName;
    //商品型号
    private String marque;
    private String depict;
    private String condensePicLink;
    private Integer picNumber;
    private Integer browseNumber;
    private Integer commentNumber;
    private Integer favoritesNumber;
    private LocalDateTime lastOnlineTime;
    private LocalDateTime lastCommentTime;
    private LocalDateTime lastRecommendTime;
    private LocalDateTime lastFavoritesTime;
    private LocalDateTime lastDealTime;
    private LocalDateTime createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
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

    public String getDepict() {
        return depict;
    }

    public void setDepict(String depict) {
        this.depict = depict;
    }

    public String getCondensePicLink() {
        return condensePicLink;
    }

    public void setCondensePicLink(String condensePicLink) {
        this.condensePicLink = condensePicLink;
    }

    public Integer getPicNumber() {
        return picNumber;
    }

    public void setPicNumber(Integer picNumber) {
        this.picNumber = picNumber;
    }

    public Integer getBrowseNumber() {
        return browseNumber;
    }

    public void setBrowseNumber(Integer browseNumber) {
        this.browseNumber = browseNumber;
    }

    public Integer getCommentNumber() {
        return commentNumber;
    }

    public void setCommentNumber(Integer commentNumber) {
        this.commentNumber = commentNumber;
    }

    public Integer getFavoritesNumber() {
        return favoritesNumber;
    }

    public void setFavoritesNumber(Integer favoritesNumber) {
        this.favoritesNumber = favoritesNumber;
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

    public LocalDateTime getLastRecommendTime() {
        return lastRecommendTime;
    }

    public void setLastRecommendTime(LocalDateTime lastRecommendTime) {
        this.lastRecommendTime = lastRecommendTime;
    }

    public LocalDateTime getLastFavoritesTime() {
        return lastFavoritesTime;
    }

    public void setLastFavoritesTime(LocalDateTime lastFavoritesTime) {
        this.lastFavoritesTime = lastFavoritesTime;
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



}
