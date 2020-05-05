package com.hzit.repay.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 支付异步通知
 */
@RestController
public class NotifyController {

    /**
     * 支付异步回调
     */
    @RequestMapping("/pay/notify")
    public void payNotify(){

        //获取支付结果


    }



}
