package com.hzit.repay.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 支付服务的接口
 */
@FeignClient(value = "hzit-pay")
public interface IPayClient {

    /**
     * 支付服务支付
     */
    @RequestMapping("/api/pay/create_pay")
    public String topay(@RequestParam String params);

}
