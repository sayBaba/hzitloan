package com.hzit.repay.client.impl;

import com.alibaba.fastjson.JSONObject;
import com.hzit.repay.client.IPayClient;
import org.springframework.stereotype.Component;

/**
 * 服务容错
 */
@Component
public class PayClientImpl implements IPayClient {


    @Override
    public String topay(String params) {

        JSONObject obj = new JSONObject();
        obj.put("code","-1");
        obj.put("msg","服务调用失败");
        obj.put("data",new JSONObject());
        return obj.toJSONString();
    }
}
