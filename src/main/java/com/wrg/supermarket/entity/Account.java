package com.wrg.supermarket.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @ClassName Comment
 * @Description TODO
 * @Author Wang Rengang
 * @Date 2019/5/8 15:40
 * @Version 1.0
 **/
@TableName("account")
public class Account implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String username;
    private String password;
    private String type;
    private BigDecimal wallet;
    private BigDecimal transfer;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getWallet() {
        return wallet;
    }

    public void setWallet(BigDecimal wallet) {
        this.wallet = wallet;
    }

    public BigDecimal getTransfer() {
        return transfer;
    }

    public void setTransfer(BigDecimal transfer) {
        this.transfer = transfer;
    }
}
