package com.hzit.pay.controller;

/**
 * 对账
 */
public class ReconController {



    public void toRecon(){
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

        //支付服务的对账文件
        //流水号            金额  状态
        //GM002215120800002,0.01,3",  "GM002215120800003,100.00,3












    }

}
