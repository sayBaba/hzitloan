package com.hzit.pay.service;

import com.alibaba.fastjson.JSONObject;
import com.hzit.pay.model.MchInfo;
import com.hzit.pay.model.PayChannel;
import com.hzit.pay.model.PayOrder;

public interface IPayService {

    /**
     * 根据商户id查询
     * @param mchId
     * @return
     */
    public MchInfo queryById(String mchId);



    /**
     * 根据商户id查询支付渠道
     * @param mchId
     * @return
     */
    public PayChannel querychannelMchId(String mchId, String channelId);

    /**
     * 查询支付流水
     * @param payOrderId
     * @return
     */
    public PayOrder queryPayOrder(String payOrderId);


    /**
     * 创建支付流水
     * @param payOrder
     */
    public PayOrder createPayOrder(JSONObject payOrder);


    /**
     * 更新支付流水
     * @param payOrder
     */
    public PayOrder updatePayOrder(PayOrder payOrder);


}
