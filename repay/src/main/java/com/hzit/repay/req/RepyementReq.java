package com.hzit.repay.req;

import lombok.Data;
import lombok.ToString;

/**
 * 还款请求参数
 */
@Data
@ToString
public class RepyementReq {

    private String userId; //用户id

    private String peid; //期数

    private String amount; //还款金额

    private String planId; //还款计划id

    private String channelId; //还款方式


}
