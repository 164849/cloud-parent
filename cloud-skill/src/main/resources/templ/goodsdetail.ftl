<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>秒杀商品详情页</title>
</head>
<body>
<div>
    <!--    1.秒杀活动-->
    <div>
        <h2>${dto.title}</h2>
        <h3>开始时间：${dto.stime?string('yyyy-MM-dd HH:mm:ss')}
            ---结束时间：${dto.etime?string('yyyy-MM-dd HH:mm:ss')}</h3>
    </div>
    <!--    2.秒杀商品信息-->
    <h1>${dto.gtitle}</h1>
    <img src="${dto.picurl}">
    <h5>原价：${dto.price}，秒杀价格：${dto.currprice}</h5>
    <h6>库存：${dto.stock}</h6>
    <div>

    </div>
    <!--    3.下单-->
    <div>
        <button onclick="sendOrder()">立即抢购</button>
    </div>
</div>
<script>
    function sendOrder() {
        //1.请求接口--获取秒杀真正接口
        //ajax
        //2.再请求秒杀接口
        //ajax

    }
</script>
</body>
</html>
