package com.hzit.pay.utils;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.alipay.api.response.AlipayTradeWapPayResponse;
import com.hzit.pay.model.PayOrder;
import org.springframework.stereotype.Component;

/**
 * 支付宝接口
 */
@Component
public class AlipayUtils {

    //TODO 写配置文件
    private String alipayUrl = "https://openapi.alipaydev.com/gateway.do";
    private String appId = "2016101200667654";
    private String privateKey =  "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCf4xG+6sIK/ysdoW+9HBtrzGMFw6aaVsrn012rnS0Ji/w49bivxfODnGp7Q32CEtOyuYBmtGJGUMpgYOk8mtq6REaRrC2B5zKgIZOrrimLJhd5nG77AbjS5eD07dSfnlo+KETh2262/q1ussPra7HfVmMXijgUdPEfDL4s53ahrvt3YRGx+10RADg3orZIZh94ksDlEfMAH3voqTblTRcDBWWQoAhr3ywaEQvGWXvwC9eqgl/ZKAVvl1fUUypFSYDrIhYX/59ciZSkug4c0jyI4sxqnvIv+mppXadjkxqJijOmtVlfdADWEeWgNeUWrVstcgrQ9tJvsaunzN0UtbiTAgMBAAECggEAE53/qmYRYtoGBz+sfw54ytQr/iQQR/WINcN4RbsmMHqtequjemWWSDqj0fXo61G2CcK6318Yg7ob6pFZbKcvWRVRd0Qh3UNpsb4M6U6YKEuHK9OmxkNi0vWDkWmw4djDB8dXFEMhJXDABMoIQdVX4Kgvxk6eLNxgTUf5+XZ41sN4ON3UccUqq74JwWfbVvIH8+H0G274SA46FBnj88bo3Yiqu1qkGorll/HJ+rdN6rhQR8tTNasnU05tYH57JniXeGR3VxkJ45qSN7uo3z01BQDOFSyRno5u2zprLD94fou/CVMO3+cUzFpUD9x3/ZLZWfwqF/lbsSyo7ZhaskoRsQKBgQDr+X/Kkk0cNAGOYryhjBL5An8QVv4Wk2NEpeJrygTtc3cyaAFE51tmx4WKlxvOOk+cfLQU0r+NzauBGXtFXahF74GXnBguVSn4mUV3W+X1YsLagoyj+z0UJKKKKoMhocpDNmtSTstNjnMzsgCikfvY8zsNg6fHjypjXQyHLpfDqwKBgQCtdJT8o2bswNQU6qfZHI5GRNwyyYO2gZqfrNuc7AIyRRqfqG7l/bexPQr7U47HVnjdSeYC6jBnIFprD2gQC5TxeOAEXyzYoyLvKToNswH7f3broqV+Zyrf2KLXeqAzjFarCDYnsvbu1SmWAOlFIKrLI5gfA0YTO7KsOD7AFgn2uQKBgElMTcKZoTQif11AFEHF5drNSLTFoNVYYNxg/qzmqpMra5oPrl091Clfzn5XI77yY+pC3hkdwXyFp0FJrf3E8tEObV2vkd9KeugKjAZgWFTZoZcIODFzh+xq2MY8IFFhFRe9nqaJbSMhHW2wW/wjhmxEWVKAU6xsLwmATLTs5x1TAoGAQIaqFgHDfDePrehNYJsa4lEjiVElynMUTmpDmHDDsGe8q8BA4RYti6WlvDEtYEJu3ANtqY/tSa/jcwhcMfvdAKdyzq2JRV4K1OWFTytUhXa5ipcK8ab3cCZrDbX0eriuVRnM65/L9LUfr0nPfYPVhZRMNt1E6ErBAQg7b8HDGyECgYEA2Nbh8KRJwy3wu6auXJz+ygAQmYnrqSpNPekK0D6PgwYoD/ksPJpUeAjQltmR98Kl6IwysSMz+MWQmEih27V1dxykAXXuWjOq3gdMG6BknoDUfQjZr0iIM2IHb+wD/1PZbrD7Z9zsnVdSqbcpw7OVbwalhAVKfLd8OUJ3lsmvZyI=";//"MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCf4xG+6sIK/ysdoW+9HBtrzGMFw6aaVsrn012rnS0Ji/w49bivxfODnGp7Q32CEtOyuYBmtGJGUMpgYOk8mtq6REaRrC2B5zKgIZOrrimLJhd5nG77AbjS5eD07dSfnlo+KETh2262/q1ussPra7HfVmMXijgUdPEfDL4s53ahrvt3YRGx+10RADg3orZIZh94ksDlEfMAH3voqTblTRcDBWWQoAhr3ywaEQvGWXvwC9eqgl/ZKAVvl1fUUypFSYDrIhYX/59ciZSkug4c0jyI4sxqnvIv+mppXadjkxqJijOmtVlfdADWEeWgNeUWrVstcgrQ9tJvsaunzN0UtbiTAgMBAAECggEAE53/qmYRYtoGBz+sfw54ytQr/iQQR/WINcN4RbsmMHqtequjemWWSDqj0fXo61G2CcK6318Yg7ob6pFZbKcvWRVRd0Qh3UNpsb4M6U6YKEuHK9OmxkNi0vWDkWmw4djDB8dXFEMhJXDABMoIQdVX4Kgvxk6eLNxgTUf5+XZ41sN4ON3UccUqq74JwWfbVvIH8+H0G274SA46FBnj88bo3Yiqu1qkGorll/HJ+rdN6rhQR8tTNasnU05tYH57JniXeGR3VxkJ45qSN7uo3z01BQDOFSyRno5u2zprLD94fou/CVMO3+cUzFpUD9x3/ZLZWfwqF/lbsSyo7ZhaskoRsQKBgQDr+X/Kkk0cNAGOYryhjBL5An8QVv4Wk2NEpeJrygTtc3cyaAFE51tmx4WKlxvOOk+cfLQU0r+NzauBGXtFXahF74GXnBguVSn4mUV3W+X1YsLagoyj+z0UJKKKKoMhocpDNmtSTstNjnMzsgCikfvY8zsNg6fHjypjXQyHLpfDqwKBgQCtdJT8o2bswNQU6qfZHI5GRNwyyYO2gZqfrNuc7AIyRRqfqG7l/bexPQr7U47HVnjdSeYC6jBnIFprD2gQC5TxeOAEXyzYoyLvKToNswH7f3broqV+Zyrf2KLXeqAzjFarCDYnsvbu1SmWAOlFIKrLI5gfA0YTO7KsOD7AFgn2uQKBgElMTcKZoTQif11AFEHF5drNSLTFoNVYYNxg/qzmqpMra5oPrl091Clfzn5XI77yY+pC3hkdwXyFp0FJrf3E8tEObV2vkd9KeugKjAZgWFTZoZcIODFzh+xq2MY8IFFhFRe9nqaJbSMhHW2wW/wjhmxEWVKAU6xsLwmATLTs5x1TAoGAQIaqFgHDfDePrehNYJsa4lEjiVElynMUTmpDmHDDsGe8q8BA4RYti6WlvDEtYEJu3ANtqY/tSa/jcwhcMfvdAKdyzq2JRV4K1OWFTytUhXa5ipcK8ab3cCZrDbX0eriuVRnM65/L9LUfr0nPfYPVhZRMNt1E6ErBAQg7b8HDGyECgYEA2Nbh8KRJwy3wu6auXJz+ygAQmYnrqSpNPekK0D6PgwYoD/ksPJpUeAjQltmR98Kl6IwysSMz+MWQmEih27V1dxykAXXuWjOq3gdMG6BknoDUfQjZr0iIM2IHb+wD/1PZbrD7Z9zsnVdSqbcpw7OVbwalhAVKfLd8OUJ3lsmvZyI=";
    private String AlipayPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAnzJnbTT7sBQJTZvp3gGztei1V2eONrrbhxuPHojkAFTQzGE7nsWL2/TvVbOJihCq8JQtU9gSXBedNePetNLz4R7eMcZztTV9M9kxxwB5TKxjbI3l9DFDj3Q9sOUq8F1Afy8XiBfYdqvv+Haz4AWDdo6EljvXY6amrXbyBralIyXC/7exOqLs17/gx4DInfdf8ophOFbRSYbQCcRbDyxPdqT7mUY9ozfmoWaj/acjbH2gGGY26ptF9bDtkrPYLPgeIUNqYU1LsWgqqDxhL5eDYIGGvPMr5aFq9s29BjEAWdoDDAnUt8R0azhc1A6I1ONVspQToxPAMMVaYAirb1EXrQIDAQAB";  //"MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAnzJnbTT7sBQJTZvp3gGztei1V2eONrrbhxuPHojkAFTQzGE7nsWL2/TvVbOJihCq8JQtU9gSXBedNePetNLz4R7eMcZztTV9M9kxxwB5TKxjbI3l9DFDj3Q9sOUq8F1Afy8XiBfYdqvv+Haz4AWDdo6EljvXY6amrXbyBralIyXC/7exOqLs17/gx4DInfdf8ophOFbRSYbQCcRbDyxPdqT7mUY9ozfmoWaj/acjbH2gGGY26ptF9bDtkrPYLPgeIUNqYU1LsWgqqDxhL5eDYIGGvPMr5aFq9s29BjEAWdoDDAnUt8R0azhc1A6I1ONVspQToxPAMMVaYAirb1EXrQIDAQAB";



    /**
     * 手机支付2.0
     * @param payOrder
     * @return
     */
    public String mobilePay (PayOrder payOrder) throws AlipayApiException {

        AlipayClient alipayClient = new DefaultAlipayClient(alipayUrl,appId,privateKey,
                "json","utf-8",AlipayPublicKey,"RSA2");
        AlipayTradeWapPayRequest request = new AlipayTradeWapPayRequest();

        request.setNotifyUrl("http://hzit.free.idcfengye.com/alipay/notify"); //后端异步通知，更新交易订单。  //TODO
        request.setReturnUrl("http://www.baidu.com"); //支付完成，跳转此页面        //TODO

        JSONObject jsonObject = new JSONObject();

        //可选
        jsonObject.put("body",payOrder.getBody());
        jsonObject.put("timeout_express","");
        jsonObject.put("time_expire","");
        jsonObject.put("auth_token","");
        jsonObject.put("goods_type","");
        jsonObject.put("passback_params","");
        jsonObject.put("goods_type","");
        jsonObject.put("promo_params","");
//       jsonObject.put("extend_params","");
//        jsonObject.put("sys_service_provider_id","");
//        jsonObject.put("hb_fq_num","");
//        jsonObject.put("extend_params","");


        //必传
        jsonObject.put("subject",payOrder.getSubject());
        jsonObject.put("out_trade_no",payOrder.getPayOrderId());
        jsonObject.put("total_amount",payOrder.getAmount()); // 分转元 TODO
        jsonObject.put("quit_url","www.baidu.com"); // todo 用户付款中途退出返回商户网站的地址
        jsonObject.put("product_code","QUICK_WAP_WAY");



        request.setBizContent(jsonObject.toJSONString());

        AlipayTradeWapPayResponse response = alipayClient.pageExecute(request);


        if(response.isSuccess()){

            return response.getBody();

        } else {

            return null;
        }
    }

    /**
     * 调用支付宝退款接口
     * @param outTradeNo 交易订单号
     * @param refundNo 退款流水
     * @param amt 退款金额
     * @return
     */
    public AlipayTradeRefundResponse getAlipayRefund(String outTradeNo, String refundNo,String amt){
        AlipayClient alipayClient = new DefaultAlipayClient(alipayUrl,appId,privateKey,
                "json","utf-8",AlipayPublicKey,"RSA2");

        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("refund_amount", amt);
        jsonObject.put("out_request_no",refundNo);
        jsonObject.put("out_trade_no",outTradeNo);
        request.setBizContent(jsonObject.toJSONString());
        AlipayTradeRefundResponse response = null;
        try {
            response = alipayClient.execute(request);

        } catch (AlipayApiException e) {
            e.printStackTrace();
        }

        return response;

    }









    public static void main(String[] args) {
        try {

            PayOrder payOrder = new PayOrder();
            payOrder.setNotifyUrl("www.baidu.com");
            payOrder.setMchOrderNo(String.valueOf(System.currentTimeMillis()));
            payOrder.setSubject("测试");
            payOrder.setAmount(1l);
            new AlipayUtils().mobilePay(payOrder);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
    }

}
