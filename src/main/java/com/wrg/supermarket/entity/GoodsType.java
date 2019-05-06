package com.wrg.supermarket.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @ClassName GoodsType
 * @Description TODO
 * @Author Wang Rengang
 * @Date 2019/4/12 15:08
 * @Version 1.0
 **/
@TableName("goods_type")
public class GoodsType implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 主键
     */
    private String id;

    /**
     * 上级类型id
     */
    private String pId;

    /**
     * 类型名称
     */
    private String name;

    /**
     * 类型标识(英文)
     */
    private String value;

    /**
     * 类型简介
     */
    private String summary;

    /**
     * 类型等级，1最大，之后依次
     */
    private Integer level;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 最后修改时间
     */
    private LocalDateTime modifyTime;

    private Integer orderNumber;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(LocalDateTime modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }
}
