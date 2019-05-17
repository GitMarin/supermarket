package com.wrg.supermarket.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @ClassName User
 * @Description TODO
 * @Author Wang Rengang
 * @Date 2019/3/27 19:17
 * @Version 1.0
 **/
@TableName("user")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String avatar;
    private String nickname;
    private String sign;
    private String gender;
    //默认地址
    private String defaultAddress;
    private LocalDateTime registerTime;
    private Integer age;
    private Integer dealNumber;
    private Integer commentNumber;
    private Integer shoppingCartNumber;

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDefaultAddress() {
        return defaultAddress;
    }

    public void setDefaultAddress(String defaultAddress) {
        this.defaultAddress = defaultAddress;
    }

    public LocalDateTime getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(LocalDateTime registerTime) {
        this.registerTime = registerTime;
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

    public Integer getShoppingCartNumber() {
        return shoppingCartNumber;
    }

    public void setShoppingCartNumber(Integer shoppingCartNumber) {
        this.shoppingCartNumber = shoppingCartNumber;
    }

}
