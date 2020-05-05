package com.hzit.pay.controller;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.hzit.common.PayUtil;
import com.hzit.pay.model.MchInfo;
import com.hzit.pay.model.PayChannel;
import com.hzit.pay.model.PayOrder;
import com.hzit.pay.resp.RespVo;
import com.hzit.pay.service.IPayService;
import com.hzit.pay.utils.AlipayUtils;
import com.hzit.pay.utils.ParamasCheck;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 支付接口
 */

@RestController
public class PayController {

    private Logger logger = LoggerFactory.getLogger(PayController.class);

    @Autowired
    private ParamasCheck paramasCheck;

    @Autowired
    private IPayService iPayService;

    @Autowired
    private AlipayUtils alipayUtils;

    // entity,model-数据库  bean vo，domain, pojo-- sevice req-  resp -请求参数，返回参数 ，

    /**
     * 统一支付接口
     *       //1.请求参数
     *         //2.请求路径
     *         //3.返回参数
     *         //4.接口规范，json，http/https.restful风格
     *         //5.加密规则
     */
    @RequestMapping("/api/pay/create_order")
    public RespVo pay(@RequestParam String params){


        //1. 解析params,订单号：111，金额：1，支付方式：alipay, sign:C380BEC2BFD727A4B6845133519F3AD6

        //按自然序排序

        //2. 订单号：111，金额：1，支付方式：alipay 做MD5 生成 newSign：C380BEC2BFD727A4B6845133519F3AD


        //3.比较sign 和newSign


        logger.info("支付请求参数：{}....",params);
        RespVo respVo = new RespVo();

        if (StringUtils.isEmpty(params)){
            respVo.setCode(-1);
            respVo.setMsg("参数为空");
            respVo.setT(null);
            return respVo;
        }

       //1.解析json
        JSONObject po = JSONObject.parseObject(params);

        //2.参数空判断
        String msg = paramasCheck.validateParams(po);

        if(!StringUtils.isEmpty(msg)){
            respVo.setCode(-1);
            respVo.setMsg(msg);
            return respVo;
        }

        //3.验证签名
        // 判断商户id 是否存在

        String mchId = po.getString("mchId");
        String channelId = po.getString("channelId");

        MchInfo mchInfo =  iPayService.queryById(mchId);

        if(ObjectUtils.isEmpty(mchInfo)){
            respVo.setCode(-1);
            respVo.setMsg("商户号不存在");
            return respVo;
        }

        PayChannel payChannel =  iPayService.querychannelMchId(mchId,channelId);

        if(ObjectUtils.isEmpty(payChannel)){
            respVo.setCode(-1);
            respVo.setMsg("商户不支持["+channelId + "]支付方式" );
            return respVo;
        }

        //验签

        Map mapObj = JSONObject.parseObject(params,Map.class);


        boolean flag = PayUtil.verifyPaySign(mapObj,mchInfo.getReqKey());
        if(!flag){
            respVo.setCode(-1);
            respVo.setMsg("签名失败" );
            return respVo;
        }

        //4.生成流水
        PayOrder payOrder =  iPayService.createPayOrder(po);


        //5.根据选择支付方式调用对应的 支付公司的接口


        //TODO 改成策略模式
        if("Alpay".equalsIgnoreCase(channelId)){

            try {
                String url = alipayUtils.mobilePay(payOrder);
                //提交url
            } catch (AlipayApiException e) {
                logger.error("AlipayApiException",e);
                //TODO
//                e.printStackTrace();
            }


        } else if("Wechat".equalsIgnoreCase(channelId)){

        }




        return  null;
        //返回参数
        //code , msg  data:{}
    }


}
