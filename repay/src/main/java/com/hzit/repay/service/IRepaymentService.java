package com.hzit.repay.service;

import com.hzit.repay.req.RepyementReq;

public interface IRepaymentService {

    /**
     * 创建还款流水
     * @param repyementReq
     */
    public String createRepayment(RepyementReq repyementReq);




}
