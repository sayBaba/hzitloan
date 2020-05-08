package com.hzit.pay.resp;

import lombok.Data;
import lombok.ToString;

/**
 * 接口返回对象
 */
@Data
@ToString
public class RespVo<T> {

    private int code;  // 0-成功，-1失败

    private String  msg;  //提示信息

    private T data;


}
