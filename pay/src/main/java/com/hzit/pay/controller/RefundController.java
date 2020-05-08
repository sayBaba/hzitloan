package com.hzit.pay.controller;

import com.hzit.pay.req.RefundReq;
import com.hzit.pay.utils.AlipayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RefundController {


    @Autowired
    private AlipayUtils alipayUtils;

    /**
     *
     * @param refundReq
     */
    @RequestMapping("/pay/refund")
    public String createRefund(@RequestBody RefundReq refundReq){
        //避免重复退款
        //1.参数判断，签名判断
        //2.检查退款金额有没有问题
        //3.订单有没有存在

        //4.生成退款流水

        //5.返回业务系统，退款受理中
        return null;

    }

    /**
     * 执行退款
     * @return
     */
    public String toRefund(){

        //1.查出退款的流水


        //2.根据支付渠道调用对应的退款接口

//      alipayUtils.getAlipayRefund();


        //3.更新退款流水状态

        //4.异步通知业务系统，更新退款状

        return null;
    }

}
