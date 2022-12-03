<!DOCTYPE html>
<html>
<head>
    <title>订单管理</title>
    <#include "../common.ftl">
</head>
<body class="childrenBody">

<form class="layui-form">
    <blockquote class="layui-elem-quote quoteBox">
        <form class="layui-form">
            <div class="layui-inline">
                <div class="layui-input-inline">
                    <input type="text" name="id"
                           class="layui-input
					searchVal" placeholder="订单号"/>
                </div>
                <div class="layui-input-inline">
                    <input type="text" name="customerName"
                           class="layui-input
					searchVal" placeholder="客户姓名"/>
                </div>
                <div class="layui-input-inline">
                    <select name="state" id="state">
                        <option value="">订单状态</option>
                        <option value="0">未付款</option>
                        <option value="1">送货中</option>
                        <option value="2">已完成</option>
                    </select>
                </div>
                <a class="layui-btn search_btn" data-type="reload"><i
                            class="layui-icon">&#xe615;</i> 搜索</a>
            </div>
        </form>
    </blockquote>

    <table id="List" class="layui-table" lay-filter="order"></table>

    <script type="text/html" id="toolbarDemo">
        <div class="layui-btn-container">
            <a class="layui-btn layui-btn-normal addNews_btn" lay-event="add">
                <i class="layui-icon">&#xe608;</i>
                添加订单
            </a>
            <a class="layui-btn layui-btn-normal delNews_btn" lay-event="del">
                <i class="layui-icon">&#xe608;</i>
                删除订单
            </a>
        </div>
    </script>
    <!--操作-->
    <script id="toolDemo" type="text/html">
        {{# if (d.state=== 0 || d.state===1) { }}
        <a href="javascript:;"  class="layui-btn layui-btn-warm layui-btn-xs"  lay-event="edit">管理</a>
        {{# } else { }}
        <a href="javascript:;" class="layui-btn layui-btn-normal layui-btn-xs"  lay-event="info">详情</a>
        {{# } }}
    </script>
</form>
<script type="text/javascript" src="${ctx}/js/order/order.js"></script>

</body>
</html>