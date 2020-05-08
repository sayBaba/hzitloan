package com.hzit.task.job;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * 代扣的定时任务
 */
@EnableScheduling   // 2.开启定时任务
public class WithholdTask {






    @Scheduled(cron = "0 0/1 * * * ?") //-每隔一分钟执行一次
    public void sendTradeQuery(){
        System.out.println("--------------------------");

        // 调用pay服务的代扣接口，fegin调用

        //1.从还款计划表中，读取今天需要还款的计划

        //2.调用代扣接口






    }
}
