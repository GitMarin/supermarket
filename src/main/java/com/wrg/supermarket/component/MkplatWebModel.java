package com.wrg.supermarket.component;


import com.wrg.supermarket.utils.enums.PlatErrorCode;
import com.wrg.supermarket.utils.exception.MkplatException;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

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

        result.setRtnFlag(PlatErrorCode.SUCCESS.getCode());

        result.setRtnMsg(PlatErrorCode.SUCCESS.getDesc());

        result.setRtnData(t);

        return result;
    }

    public static <T extends Object> MkplatWebModel<T> convertMetroPayWebModel(Long total, T t) {
        MkplatWebModel<T> result = new MkplatWebModel<T>();

        result.setRtnFlag(PlatErrorCode.SUCCESS.getCode());

        result.setRtnMsg(PlatErrorCode.SUCCESS.getDesc());

        result.setRtnData(t);

        result.setTotal(total);

        return result;
    }

    public static MkplatWebModel success() {
        MkplatWebModel result = new MkplatWebModel();

        result.setRtnFlag(PlatErrorCode.SUCCESS.getCode());

        result.setRtnMsg(PlatErrorCode.SUCCESS.getDesc());

        return result;
    }

    public static MkplatWebModel success(String rtnMsg) {
        MkplatWebModel result = new MkplatWebModel();

        result.setRtnFlag(PlatErrorCode.SUCCESS.getCode());

        result.setRtnMsg(rtnMsg);

        return result;
    }

    public static MkplatWebModel errMsg (String rtnMsg){
        MkplatWebModel result = new MkplatWebModel();
        result.setRtnFlag(ResponseRtnFlag.SYSTEM_ERROR);
        result.setRtnMsg(rtnMsg);

        return result;
    }

    public static MkplatWebModel error (){
        MkplatWebModel result = new MkplatWebModel();
        result.setRtnFlag(ResponseRtnFlag.SYSTEM_ERROR);
        return result;
    }

    public static <T extends Object> MkplatWebModel<T> convertMetroPayWebModel(MkplatException pe) {
        MkplatWebModel<T> result = new MkplatWebModel<T>();

        result.setRtnFlag(pe.getErrorCode().getCode());

        result.setRtnMsg(StringUtils.isEmpty(pe.getMessage()) ? pe.getErrorCode().getDesc() : pe.getMessage());

        return result;
    }

    public static <T extends Object> MkplatWebModel<T> convertMetroPayWebModel(Exception pe) {
        return convertMetroPayWebModel(new MkplatException(PlatErrorCode.SYSTEM_ERROR));
    }


    public String getRtnFlag() {
        return rtnFlag;
    }

    public String getRtnMsg() {
        return rtnMsg;
    }

    public Long getTotal() {
        return total;
    }

    public T getRtnData() {
        return rtnData;
    }

    public void setRtnFlag(String rtnFlag) {
        this.rtnFlag = rtnFlag;
    }

    public void setRtnMsg(String rtnMsg) {
        this.rtnMsg = rtnMsg;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public void setRtnData(T rtnData) {
        this.rtnData = rtnData;
    }
}
