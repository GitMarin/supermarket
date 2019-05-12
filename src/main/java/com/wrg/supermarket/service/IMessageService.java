package com.wrg.supermarket.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wrg.supermarket.component.MkplatWebModel;
import com.wrg.supermarket.entity.Message;

import java.util.Map;

/**
 * @ClassName IMessageService
 * @Description TODO
 * @Author Wang Rengang
 * @Date 2019/5/11 22:00
 * @Version 1.0
 **/
public interface IMessageService extends IService<Message> {

    MkplatWebModel addMessage(Map<String,Object> map);

    MkplatWebModel getNewMessageNumber(Map<String,Object> map);

    MkplatWebModel getMessage(Map<String,Object> map);

    MkplatWebModel deleteMessage(Map<String,Object> map);
}
