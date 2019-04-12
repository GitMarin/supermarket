package com.wrg.supermarket.entity;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author Wang Rengang
 * @since 2019-04-03
 */
@TableName("shop")
public class Shop  implements Serializable {

    private static final long serialVersionUID = 1L;

    private  String id;
    /**
     * 超市地址
     */
    private String address;

    /**
     * 商品数量
     */
    private Integer goodsNumber;

    /**
     * 评论量
     */
    private Integer commentNumber;

    /**
     * 交易量
     */
    private Integer dealNumber;

    /**
     * 图片链接地址
     */
    private String picLink;

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getGoodsNumber() {
        return goodsNumber;
    }

    public void setGoodsNumber(Integer goodsNumber) {
        this.goodsNumber = goodsNumber;
    }

    public Integer getCommentNumber() {
        return commentNumber;
    }

    public void setCommentNumber(Integer commentNumber) {
        this.commentNumber = commentNumber;
    }

    public Integer getDealNumber() {
        return dealNumber;
    }

    public void setDealNumber(Integer dealNumber) {
        this.dealNumber = dealNumber;
    }

    public String getPicLink() {
        return picLink;
    }

    public void setPicLink(String picLink) {
        this.picLink = picLink;
    }




}
