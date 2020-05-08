<!DOCTYPE HTML>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="author" content="fyunli">    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>支付中心</title>


    <style>
        body{font-family: 'Microsoft YaHei';}
        #amount,#error{height: 80px; line-height: 80px; text-align: center; color: #f00; font-size: 30px; font-weight: bold;}
        #error{font-size: 20px;}
        #info{padding: 0 10px; font-size: 12px;}
        table{width: 100%; border-collapse: collapse;}
        tr{border: 1px solid #ddd;}
        td{padding: 10px;}
        .fr{text-align: right; font-weight: bold;}
    </style>

</head>
<body>

<#if (orderMap.resCode == 'SUCCESS')>
    <div id="amount">¥ ${amount}</div>
    <div id="info">
        <table>
            <tr>
                <td>购买商品</td>
                <td class="fr">${orderMap.goodsName}</td>
            </tr>
            <tr>
                <td>收款方</td>
                <td class="fr">合众艾特</td>
            </tr>
        </table>
    </div>

    <#if (client == 'alipay')>
        ${payUrl}
    </#if>


<#else>
    <div id="error">
        <#if (result == 'failed')>
            ${resMsg}
        </#if>
    </div>
</#if>


</body>
</html>