package com.hzit.pay.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.hzit.pay.model.PayOrder;
import com.hzit.pay.service.IPayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 支付宝支付异步回调地址
 */
@RestController
public class AlipayNotifyController {

    private Logger logger = LoggerFactory.getLogger(AlipayNotifyController.class);

    @Autowired
    private IPayService iPayService;


    private static final String  alipay_public_key = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDIgHnOn7LLILlKETd6BFRJ0GqgS2Y3mn1wMQmyh9zEyWlz5p1zrahRahbXAfCfSqshSNfqOmAQzSHRVjCqjsAw1jyqrXaPdKBmr90DIpIxmIyKXv4GGAkPyJ/6FTFY99uhpiq0qadD/uSzQsefWo0aTvP/65zi3eof7TcZ32oWpwIDAQAB";
    private static String charset = "utf-8";
    public static String sign_type = "RSA2";


    @Autowired
    private AmqpTemplate amqpTemplate;



    /**
     * 支付宝支付异步通知
     */
    @RequestMapping("/alipay/notify")
    public String alipayNotify(HttpServletRequest request){
        logger.info("---------------接受到支付宝的回调--------------");
        //1.获取回调参数

        Map<String,String> params = new HashMap<String,String>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
            try {
                valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            params.put(name, valueStr);
        }
        logger.info("接受到支付宝的异步回调参数参数:params={}", params);

        //2.解析参数



        //3.验证签名

        //2.判断支付宝返回的参数是否正常
        Map<String, Object> payContext = new HashMap();
        try {
            boolean signVerified = AlipaySignature.rsaCheckV1(params, alipay_public_key, charset, sign_type); //调用SDK验证签名

            if(!signVerified){
                return "fail";
            }
        } catch (AlipayApiException e) {
            logger.error("AlipayApiException",e);
        }

        //4. 流水表中的 金额，流水号是否一直
       // TODO
       PayOrder payOrder =  iPayService.queryPayOrder(null);



        //5.在更新流水
        //TODO
        iPayService.updatePayOrder(payOrder);


        //6.通知 业务系统。 通知业务系统，5次，每隔 15分钟通知一次，用mq来做异步通知

        //问题1.保证mq消息不丢失，持久化
        //问题2.mq重复消息，幂等处理
        amqpTemplate.convertAndSend(payOrder);


        //最后通知业务系统：repay

        return "sucess"; //支付宝总共会通知我们9次，如果你没有响应sucess 他就会一直通知，直到9次
    }

}
