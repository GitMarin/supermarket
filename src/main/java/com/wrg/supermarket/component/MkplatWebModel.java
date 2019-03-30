package com.wrg.supermarket.component;


import com.wrg.supermarket.utils.enums.PlatErrorCode;
import com.wrg.supermarket.utils.exception.MkplatException;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * Created by bigsm on 2017/9/19.
 */
public class MkplatWebModel<T> implements Serializable {

    private static final long serialVersionUID = 8745446362620948874L;
    /**
     * 统一返回码
     */
    public String rtnFlag;

    /**
     * 统一错误消息
     */
    public String rtnMsg;

    /**
     * 总条数
     */
    public Long total;
    /**
     * 结果对象
     */
    public T rtnData;

    public static <T extends Object> MkplatWebModel<T> convertMetroPayWebModel(T t) {
        MkplatWebModel<T> result = new MkplatWebModel<T>();

        result.setErrCode(PlatErrorCode.SUCCESS.getCode());

        result.setErrMsg(PlatErrorCode.SUCCESS.getDesc());

        result.setData(t);

        return result;
    }

    public static <T extends Object> MkplatWebModel<T> convertMetroPayWebModel(Long total, T t) {
        MkplatWebModel<T> result = new MkplatWebModel<T>();

        result.setErrCode(PlatErrorCode.SUCCESS.getCode());

        result.setErrMsg(PlatErrorCode.SUCCESS.getDesc());

        result.setData(t);

        result.setTotal(total);

        return result;
    }

    public static MkplatWebModel success() {
        MkplatWebModel result = new MkplatWebModel();

        result.setErrCode(PlatErrorCode.SUCCESS.getCode());

        result.setErrMsg(PlatErrorCode.SUCCESS.getDesc());

        return result;
    }

    public static <T extends Object> MkplatWebModel<T> convertMetroPayWebModel(MkplatException pe) {
        MkplatWebModel<T> result = new MkplatWebModel<T>();

        result.setErrCode(pe.getErrorCode().getCode());

        result.setErrMsg(StringUtils.isEmpty(pe.getMessage()) ? pe.getErrorCode().getDesc() : pe.getMessage());

        return result;
    }

    public static <T extends Object> MkplatWebModel<T> convertMetroPayWebModel(Exception pe) {
        return convertMetroPayWebModel(new MkplatException(PlatErrorCode.SYSTEM_ERROR));
    }


    public String getErrCode() {
        return rtnFlag;
    }

    public void setErrCode(String errCode) {
        this.rtnFlag = errCode;
    }

    public String getErrMsg() {
        return rtnMsg;
    }

    public void setErrMsg(String errMsg) {
        this.rtnMsg = errMsg;
    }

    public T getData() {
        return rtnData;
    }

    public void setData(T data) {
        this.rtnData = data;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
}
