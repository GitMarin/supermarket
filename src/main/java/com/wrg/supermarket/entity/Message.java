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
@TableName("message")
public class Message implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String formerId;
    private String latterId;
    private String relatedId;
    private String context;
    private String type;
    private String status;
    private LocalDateTime createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFormerId() {
        return formerId;
    }

    public void setFormerId(String formerId) {
        this.formerId = formerId;
    }

    public String getLatterId() {
        return latterId;
    }

    public void setLatterId(String latterId) {
        this.latterId = latterId;
    }

    public String getRelatedId() {
        return relatedId;
    }

    public void setRelatedId(String relatedId) {
        this.relatedId = relatedId;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
}
