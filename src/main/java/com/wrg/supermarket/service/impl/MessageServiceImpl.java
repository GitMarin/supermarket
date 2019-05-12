package com.wrg.supermarket.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wrg.supermarket.component.MkplatWebModel;
import com.wrg.supermarket.component.UUIDGenerator;
import com.wrg.supermarket.entity.Message;
import com.wrg.supermarket.mapper.GoodsMapper;
import com.wrg.supermarket.mapper.MessageMapper;
import com.wrg.supermarket.mapper.ShopMapper;
import com.wrg.supermarket.mapper.UserMapper;
import com.wrg.supermarket.service.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

/**
 * @ClassName MessageServiceImpl
 * @Description TODO
 * @Author Wang Rengang
 * @Date 2019/5/11 22:10
 * @Version 1.0
 **/
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements IMessageService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ShopMapper shopMapper;
    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public MkplatWebModel addMessage(Map<String,Object> map){
        Message message = new Message();
        message.setContext(map.get("context").toString());
        message.setFormerId(map.get("formerId").toString());
        message.setLatterId(map.get("latterId").toString());
        message.setRelatedId(map.get("relatedId").toString());
        message.setType(map.get("type").toString());
        String key= UUIDGenerator.generate();
        message.setId(key);
        LocalDateTime currentDate=LocalDateTime.now();
        message.setCreateTime(currentDate);
        message.setStatus("new");
        save(message);
        return MkplatWebModel.success();
    }

    @Override
    public MkplatWebModel getNewMessageNumber(Map<String,Object> map){
        String latterId = map.get("latterId").toString();
        QueryWrapper<Message> messageQueryWrapper = Wrappers.query();
        messageQueryWrapper.eq("latter_id",latterId).eq("status","new");
        return MkplatWebModel.convertMetroPayWebModel(count(messageQueryWrapper));
    }

    @Override
    public MkplatWebModel getMessage(Map<String,Object> map){
        String type = map.get("type").toString();
        String latterId = map.get("latterId").toString();
        QueryWrapper<Message> messageQueryWrapper = Wrappers.query();
        messageQueryWrapper.eq("latter_id",latterId).orderByDesc("id");
        List<Message> messageList = list(messageQueryWrapper);
        List<Map<String,Object>> resultList = new ArrayList<>();
        for(int i=0;i<messageList.size();i++){
            Message message = messageList.get(i);
            Map<String,Object> resultMap = new HashMap<>();
            StringBuffer resultMessage = new StringBuffer();
            if(message.getStatus().equals("new")){
                message.setStatus("old");
                updateById(message);
                resultMap.put("status","new");
            }else{
                resultMap.put("status","old");
            }
            String formerId = message.getFormerId();
            String relatedId = message.getRelatedId();
            String context = message.getContext();
            String createTime = message.getCreateTime().toString().replaceAll("T"," ");

            String messageType = message.getType();
            if(type.equals("admin")){
                if(messageType.equals("adminRefund")){
                    String userName = userMapper.selectById(formerId).getNickname();
                    resultMessage.append(createTime +" 用户 "+userName+": 请求订单 "+relatedId+" 强制退款。理由:"+context);
                }else if(messageType.equals("auditGoods")){
                    String shopName = shopMapper.selectById(formerId).getName();
                    String goodsName = goodsMapper.selectById(relatedId).getName();
                    resultMessage.append(createTime +" 超市 "+shopName+": 请求上架新商品 "+goodsName+"。"+context);
                }else if(messageType.equals("enabledShopGoods")){
                    String shopName = shopMapper.selectById(formerId).getName();
                    String goodsName = goodsMapper.selectById(relatedId).getName();
                    resultMessage.append(createTime +" 超市 "+shopName+": 请求解冻该超市的商品 "+goodsName+"。"+context);
                }
            }else if(type.equals("shop")){
                if(messageType.equals("disabledShopGoods")){
                    String goodsName = goodsMapper.selectById(relatedId).getName();
                    resultMessage.append(createTime +" 系统管理员: 已冻结贵超市的商品 "+goodsName+"。理由:"+context);
                }else if(messageType.equals("deliverGoods")){
                    String userName = userMapper.selectById(formerId).getNickname();
                    resultMessage.append(createTime +" 用户 "+userName+": 请求订单 "+relatedId+" 发货。"+context);
                }else if(messageType.equals("refund")){
                    String userName = userMapper.selectById(formerId).getNickname();
                    resultMessage.append(createTime +" 用户 "+userName+": 请求订单 "+relatedId+" 退款。理由:"+context);
                }else if(messageType.equals("enabledShopGoods")){
                    String goodsName = goodsMapper.selectById(relatedId).getName();
                    resultMessage.append(createTime +" 系统管理员: 已解冻贵超市的商品 "+goodsName+"。"+context);
                }else if(messageType.equals("adminRefund")){
                    resultMessage.append(createTime +" 系统管理员：订单 "+relatedId+" 已强制退款。"+context);
                }else if(messageType.equals("dealPay")){
                    String userName = userMapper.selectById(formerId).getNickname();
                    resultMessage.append(createTime +" 用户: "+userName+" 已付款。请求订单 "+relatedId+" 发货。"+context);
                }
            }else if(type.equals("user")){
                if(messageType.equals("adminRefund")){
                    resultMessage.append(createTime +" 系统管理员: 订单 "+relatedId+" 已强制退款。"+context);
                }else if(messageType.equals("shopDeliverGoods")){
                    String shopName = shopMapper.selectById(formerId).getName();
                    resultMessage.append(createTime +" 超市 "+shopName+": 订单 "+relatedId+" 已发货。"+context);
                }else if(messageType.equals("shopRefund")){
                    String shopName = shopMapper.selectById(formerId).getName();
                    resultMessage.append(createTime +" 超市 "+shopName+": 订单 "+relatedId+" 已退款。"+context);
                }
            }


            resultMap.put("id",message.getId());
            resultMap.put("message",resultMessage);
            resultList.add(resultMap);
        }
        return MkplatWebModel.convertMetroPayWebModel(resultList);
    }



    @Override
    public MkplatWebModel deleteMessage(Map<String,Object> map){
        removeById(map.get("id").toString());
        return MkplatWebModel.success();
    }

}
