package com.hzit.repay.model;

import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

@Data
@ToString
public class Repayment {

    private Long id;
    private int repaymentId;
    private String userId;
    private String status;
    private String payOrderId; //
    private String channelId;
    private Date createTime;
    private Date finshedTime;
    private BigDecimal amount;


}
