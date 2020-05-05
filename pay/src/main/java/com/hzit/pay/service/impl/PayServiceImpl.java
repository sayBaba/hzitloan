package com.hzit.pay.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hzit.pay.mapper.PayOrderMapper;
import com.hzit.pay.model.MchInfo;
import com.hzit.pay.model.PayChannel;
import com.hzit.pay.model.PayOrder;
import com.hzit.pay.service.IPayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 支付接口
 */
@Service
public class PayServiceImpl implements IPayService {

    @Autowired
    private PayOrderMapper payOrderMapper;

    private Logger logger = LoggerFactory.getLogger(PayServiceImpl.class);

    @Override
    public MchInfo queryById(String mchId) {

        MchInfo m = new MchInfo(); //TODO
        m.setMchId("1000");
        m.setReqKey("abcd123456");
        m.setResKey("abcd123456");
        return m;
    }


    @Override
    public PayChannel querychannelMchId(String mchId, String channelId) {

        PayChannel payChannel = new PayChannel();  //TODO
        payChannel.setChannelId("Alipy");
        payChannel.setMchId("1000");
        payChannel.setParam("{}");
        return payChannel;
    }

    @Override
    public PayOrder createPayOrder(JSONObject po) {

        PayOrder payOrder = new PayOrder();

        payOrder.setMchOrderNo(po.getString("mchOrderNo"));
        payOrder.setAmount(po.getLong("amount")); //金额：单位为分
        payOrder.setBody("测试商品");
        payOrder.setPayOrderId(String.valueOf(System.currentTimeMillis())); // TODO 流水号，怎么生成全局唯一id  uuid, 雪花算法，分布式的唯一id
        payOrder.setMchId("1000");
        payOrder.setChannelId(po.getString("channelId"));
        payOrder.setChannelMchId("test");
        payOrder.setCurrency("ccy");
        payOrder.setStatus((byte)0);
        payOrder.setNotifyUrl(po.getString("notifyUrl"));
        payOrder.setNotifyCount((byte) 0);
        Date date = new Date();

        payOrder.setCreateTime(date);
        payOrder.setUpdateTime(date);

        payOrderMapper.insertSelective(payOrder);

        return payOrder;
    }
}
