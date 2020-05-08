package com.hzit.repay.controller;

import com.hzit.repay.req.RepyementReq;
import com.hzit.repay.service.IRepaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * 还款
 */
@Controller
public class RepayController {

    private Logger logger = LoggerFactory.getLogger(RepayController.class);


    @Autowired
    private IRepaymentService iRepaymentService;

    @RequestMapping("/repay/payment")
    public String repyement(HttpServletRequest request, ModelMap modelMap){

//        ModelAndView modelAndView = new ModelAndView("/qrpay");

        //1.生成还款流水

        //1.1,调用借款服务
//         String planId =  repyementReq.getPlanId();

        RepyementReq repyementReq = new RepyementReq();

        repyementReq.setAmount(request.getParameter("userId"));
        repyementReq.setChannelId(request.getParameter("channelId"));
        repyementReq.setPeid(request.getParameter("peid"));
        repyementReq.setPlanId(request.getParameter("planId"));
        repyementReq.setAmount(request.getParameter("amount"));

        //TODO 参数判断
        String url = iRepaymentService.createRepayment(repyementReq);

        logger.info("url = " + url);


        modelMap.addAttribute("client","alipay");
        modelMap.addAttribute("payUrl",url);
        modelMap.addAttribute("amount",repyementReq.getAmount());

        HashMap orderMap = new HashMap();
        orderMap.put("resCode","SUCCESS");
        orderMap.put("goodsName","还款");
        modelMap.addAttribute("orderMap",orderMap);
        //跳转url

        return "qrpay";

    }






}
