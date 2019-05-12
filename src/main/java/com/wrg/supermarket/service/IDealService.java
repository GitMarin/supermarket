package com.wrg.supermarket.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wrg.supermarket.component.MkplatWebModel;
import com.wrg.supermarket.entity.Deal;

import java.util.Map;

/**
 * @ClassName IDealService
 * @Description TODO
 * @Author Wang Rengang
 * @Date 2019/4/10 22:00
 * @Version 1.0
 **/
public interface IDealService extends IService<Deal> {
    /**
     * @Author Wang Rengang
     * @Description 分页获取交易数据
     * @Date 2019/4/10
     * @Param 需要当前页和尺寸map
     * @return com.wrg.supermarket.component.MkplatWebModel
     **/
    MkplatWebModel getDealPage(Map<String,Object> map);

    MkplatWebModel addDeal(Map<String,Object> map);

    MkplatWebModel changeDealStatus(Map<String,Object> map);

    MkplatWebModel deleteDeal(String dealId);

    MkplatWebModel addDealBatch(Map<String,Object> map);

    MkplatWebModel shopRefundDeal(Map<String,Object> map);

}
