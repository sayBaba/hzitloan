package com.hzit.task.job;

import com.hzit.task.config.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.Set;

/**
 * 支付宝对账
 */
@EnableScheduling   //
@Component
public class AliapyReconTask {


    @Autowired
    private RedisUtil redisUtil;


    @Scheduled(cron = "0 0/1 * * * ?") //-每隔一分钟执行一次
    public void recon(){


        //1.获取对账文件
        // 1.1.下载对账文件。io流
        // 1.2 去ftp/SFTP 服务器下载
        // 1.3 银行主动推送到我们的服务器

        //2.解析对账文件，入库， 临时表

        //3. 临时表 和 交易表 进行对比

        //3.1.总的交易笔数， 总的交易金额，对总账。

        //3.2.对明细，找出差役的数据。差役数据存入 对账差役表

        //3.3.最后更新对账结果


        //TODO redis set集合对比数据

        //银行的对账文件
        //流水号            金额  状态
        //GM002215120800002,0.01,3",  "GM002215120800003,0.01,3
        redisUtil.sadd("{account}:bankTrade","GM002215120800002,0.01,3",  "GM002215120800003,0.01,3");


        //支付服务的对账文件，放入 redis中
        //流水号            金额  状态
        redisUtil.sadd("{account}:localTrade","GM002215120800002,0.01,3",  "GM002215120800003,100.00,3");

        //求出交集，两个集合共同的数据

        redisUtil.sinterstore("{account}:union","{account}:localTrade","{account}:bankTrade");


        //{account}:union 就是交集

        //找出 交集中和本地交易的差役数据，存入：{account}:localSdiff
      Long id =  redisUtil.sdiffstore("{account}:localSdiff","{account}:union","{account}:localTrade");

      System.err.println("---------------localSdiff id = " + id);
        Set<String> set = redisUtil.smembers("{account}:localSdiff");
        if(set!=null){
            Iterator<String> it = set.iterator();
            while (it.hasNext()) {
                String str = it.next();
                System.out.println("localSdiff:"+str);
            }
        }



        long id1 = redisUtil.sdiffstore("{account}:bankSdiff","{account}:union","{account}:bankTrade");
        System.err.println("---------------bankSdiff id = " + id1);


        Set<String> set1 = redisUtil.smembers("{account}:bankSdiff");
        if(set!=null){
            Iterator<String> it = set1.iterator();
            while (it.hasNext()) {
                String str = it.next();
                System.out.println("bankSdiff:"+str);
            }
        }

        //取出account}:localSdiff， 和 {account}:bankSdiff 存入差役表

        //更新对账结果，并清掉临时表中的数据


    }
}
