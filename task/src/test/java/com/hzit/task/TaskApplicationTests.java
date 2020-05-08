package com.hzit.task;

import com.hzit.task.config.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Iterator;
import java.util.Set;

@SpringBootTest
class TaskApplicationTests {


    @Autowired

    private RedisUtil redisUtil;

	@Test
	void contextLoads() {

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
        Long id =  redisUtil.sdiffstore("{account}:localSdiff","{account}:localTrade","{account}:union");

        System.err.println("---------------localSdiff id = " + id);
        Set<String> set = redisUtil.smembers("{account}:localSdiff");
        if(set!=null){
            Iterator<String> it = set.iterator();
            while (it.hasNext()) {
                String str = it.next();
                System.out.println("localSdiff:"+str);
            }
        }



        long id1 = redisUtil.sdiffstore("{account}:bankSdiff","{account}:bankTrade","{account}:union");
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



    @Test
    void contextLoads11() {
        //流水号            金额  状态
        //GM002215120800002,0.01,3",  "GM002215120800003,0.01,3

        //将本地t_PAy_order中的数据放入redis
        redisUtil.sadd("{account}:localSet", "GM002215120800002,0.01,3","GM002215120800003,0.01,3");

        //流水号            金额  状态
        //GM002215120800002,0.01,3",  "GM002215120800003,0.01,3
        //将支付宝的交易数据从t_recon_temp中的数据放入redis
        redisUtil.sadd("{account}:outerSet", "GM002215120800002,0.01,3","GM002215120800003,0.01,1");


        //进行2个集合的比对，得出交集union，将交集放入key”{account}:union”中
        redisUtil.sinterstore("{account}:union", "{account}:localSet", "{account}:outerSet");
        //union = GM002215120800002,0.01,3

        //localSet和outerSet分别与交集进行比较，得出差集{account}:localDif
        redisUtil.sdiffstore("{account}:localDiff", "{account}:localSet", "{account}:union");
        //localDiff = GM002215120800003,0.01,3

        redisUtil.sdiffstore("{account}:outerDiff", "{account}:outerSet", "{account}:union");
        //outerDiff = GM002215120800003,0.01,1


        Set<String> set = redisUtil.smembers("{account}:localDiff");
        if(set!=null){
            Iterator<String> it = set.iterator();
            while (it.hasNext()) {
                String str = it.next();
                System.out.println("localDiff:"+str);
            }
        }

        Set<String> set1 = redisUtil.smembers("{account}:outerDiff");

        if(set1!=null){
            Iterator<String> it = set1.iterator();
            while (it.hasNext()) {
                String str = it.next();
                System.out.println("outerDiff:"+str);
            }
        }

    }

    @Test
    void contextLoads112() {


        redisUtil.sdiffstore("test03", "test01", "test");

    }


}

