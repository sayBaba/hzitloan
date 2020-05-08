package com.hzit.pay.req;

import lombok.Data;

@Data
public class RefundReq {



    private String userId; //用户id

    private String MchOrderNo; //商户订单号

    private String amount; //退款金额

    private String mchRefundNo; //退款金额




}
