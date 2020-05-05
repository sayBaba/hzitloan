package com.hzit.pay.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 支付宝支付异步回调地址
 */
@RestController
public class AlipayNotifyController {

    /**
     * 支付宝支付异步通知
     */
    @RequestMapping("/alipay/notify")
    public void alipayNotify(){


//        charset :1
//
//        sign_type: 11
//        biz_content:{
//            body:subject
//
//        }

        //最后通知业务系统：repay
    }

}
