package com.wrg.supermarket.controller;

import com.wrg.supermarket.component.MkplatWebModel;
import com.wrg.supermarket.service.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * @ClassName MessageController
 * @Description TODO
 * @Author Wang Rengang
 * @Date 2019/5/11 21:56
 * @Version 1.0
 **/
@Controller
@RequestMapping("/message")
public class MessageController {

    @Autowired
    IMessageService iMessageService;

    @ResponseBody
    @RequestMapping("/addMessage")
    public MkplatWebModel addMessage(@RequestBody Map<String,Object> map){
        return iMessageService.addMessage(map);
    }


    @ResponseBody
    @RequestMapping("/getNewMessageNumber")
    public MkplatWebModel getNewMessageNumber(@RequestBody Map<String,Object> map){
        return iMessageService.getNewMessageNumber(map);
    }

    @ResponseBody
    @RequestMapping("/getMessage")
    public MkplatWebModel getMessage(@RequestBody Map<String,Object> map){
        return iMessageService.getMessage(map);
    }

    @ResponseBody
    @RequestMapping("/deleteMessage")
    public MkplatWebModel deleteMessage(@RequestBody Map<String,Object> map){
        return iMessageService.deleteMessage(map);
    }



}
