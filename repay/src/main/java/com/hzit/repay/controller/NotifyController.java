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
    @RequestMapping("/repay/notify")
    public String payNotify(){

        //1.获取支付结果

        //2.验证签名

        //3.更新还款流水状态

        //4.返回success

        return "返回success";

    }



}
