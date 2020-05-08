package com.hzit.repay.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hzit.common.PayUtil;
import com.hzit.repay.client.IPayClient;
import com.hzit.repay.mapper.RepaymentMapper;
import com.hzit.repay.model.Repayment;
import com.hzit.repay.req.RepyementReq;
import com.hzit.repay.service.IRepaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

/**
 * 还款
 */
@Service
public class RepaymentServiceImpl  implements IRepaymentService {

    private Logger logger = LoggerFactory.getLogger(RepaymentServiceImpl.class);


    @Autowired
    private RepaymentMapper repaymentMapper;

    @Autowired
    private IPayClient iPayClient;

    @Override
    public String createRepayment(RepyementReq repyementReq) {


        Integer rpId =  Integer.parseInt(String.valueOf(System.currentTimeMillis()).substring(0,6));

        Repayment repayment = new  Repayment();
        repayment.setAmount(new BigDecimal(repyementReq.getAmount()));
        repayment.setChannelId(repyementReq.getChannelId());

//      LocalDate localDate = LocalDate.now();
        Date d = new Date();
        repayment.setCreateTime(d);
        repayment.setFinshedTime(d);
        repayment.setPayOrderId(null);
        repayment.setRepaymentId(rpId); // 分布式的唯一id，
        repayment.setStatus("0");
        int r =  repaymentMapper.insert(repayment);

       if(r<=0){
           return null;
       }

        //2.调用支付服务的接口


        //2.1 封装参数
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("mchId","1000"); //TODO

        jsonObject.put("mchOrderNo",System.currentTimeMillis()); //分布式的唯一id
        jsonObject.put("channelId",repyementReq.getChannelId());
        jsonObject.put("currency","cny");
        jsonObject.put("amount",repyementReq.getAmount());
        jsonObject.put("clientIp","127.0.0.1");
        jsonObject.put("device","ios");
        jsonObject.put("notifyUrl","http://127.0.0.1:3333/repay/notify");  //TODO 支付服务回调还款服务的地址
        jsonObject.put("subject","还款");
        jsonObject.put("body","还款");

        jsonObject.put("param1",null);
        jsonObject.put("param2",null);


        TreeMap map = JSONObject.parseObject(jsonObject.toJSONString(),TreeMap.class);

        //2.2 生成sign
        String sign =  PayUtil.makeRetData(map,"abcd123456");

        jsonObject.put("sign",sign);

        logger.info("用户id:{},调用支付服务的支付接口，请求参数：{}",repyementReq.getUserId(),jsonObject.toJSONString());

        //2.3 调用支付接口
        String rlt = iPayClient.topay(jsonObject.toJSONString());
        logger.info("用户id:{},调用支付服务的支付接口，返回参数：{}",repyementReq.getUserId(),rlt);

        //3.解析，支付结果。
        if(StringUtils.isEmpty(rlt)){
            return  null;
        }
        //3.1 验签

        JSONObject rltJson = JSONObject.parseObject(rlt);


//      JSONObject data = rltJson.getJSONObject("data");
        System.err.println(rltJson.getInteger("code"));


        if(0 == rltJson.getInteger("code")){

           String dataStr  = rltJson.getString("data");
           logger.info("dataStr = " + dataStr);

           JSONObject data = JSONObject.parseObject(dataStr);


            //3.2 更新还款流水为还款中吧
            repayment = new Repayment();
            repayment.setRepaymentId(rpId);
            repayment.setStatus("-1");
            repayment.setPayOrderId(data.getString("payOrderId"));
            repaymentMapper.update(repayment);

            String url = data.getString("url");

            return url;


        }else{
            return null;
        }







//        return null;

    }


    public static void main(String[] args) {

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("code",91);
        jsonObject.put("msg","fial");


        Map map =  JSONObject.parseObject(jsonObject.toJSONString(),Map.class);

        System.out.println(map);


    }



}