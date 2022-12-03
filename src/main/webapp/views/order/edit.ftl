<!DOCTYPE html>
<html>
<head>
    <title>订单管理</title>
    <#include "../common.ftl">
</head>
<body class="childrenBody">
<div class="layui-col-md12">
    <div class="layui-card">
        <div class="layui-card-body">
            <form class="layui-form">
                <#--订单id-->
                <input name="id" type="hidden" value="${(order.id?c)!}"/>
                <div class="layui-form-item layui-row">
                    <div class="layui-col-xs6">
                        <label class="layui-form-label">客户姓名</label>
                        <div class="layui-input-block">
                            <input type="text" class="layui-input"
                                   name="name" id="name" value="${(order.name)!}" readonly="readonly">
                        </div>
                    </div>
                    <div class="layui-col-xs6">
                        <label class="layui-form-label">联系号码</label>
                        <div class="layui-input-block">
                            <input type="text" class="layui-input"
                                   name="phone" id="phone" value="${(order.phone)!}" readonly="readonly">
                        </div>
                    </div>
                </div>

                <div class="layui-form-item layui-row">
                    <div class="layui-col-xs6">
                        <label class="layui-form-label">送货地址</label>
                        <div class="layui-input-block">
                            <input type="text" class="layui-input"
                                   name="address" lay-verify="required" value="${(order.address)!}"
                                   readonly="readonly">
                        </div>
                    </div>

                    <div class="layui-col-xs6">
                        <label class="layui-form-label">订单状态</label>
                        <div class="layui-input-block">
                            <#if order.state==0>
                                <input type="text" class="layui-input"
                                       name="overview" value="未付款" id="state" readonly="readonly">
                            </#if>
                            <#if order.state==1>
                                <input type="text" class="layui-input"
                                       name="overview" value="送货中" id="state" readonly="readonly">
                            </#if>
                            <#if order.state==2>
                                <input type="text" class="layui-input"
                                       name="overview" value="已完成" id="state" readonly="readonly">
                            </#if>
                        </div>
                    </div>
                </div>

                <div class="layui-form-item layui-row">
                    <div class="layui-col-xs6">
                        <label class="layui-form-label">订单金额</label>
                        <div class="layui-input-block">
                            <input type="text" class="layui-input"
                                   name="overview" value="${(order.price?c)!}" id="price" readonly="readonly">
                        </div>
                    </div>
                </div>

            </form>
        </div>
    </div>
</div>


<div class="layui-col-md12">
    <table id="List" class="layui-table" lay-filter="orderProduct"></table>
</div>


<#if order.state==0||order.state==1>
<#--头部工具栏-->
    <script type="text/html" id="toolbarDemo">
        <div class="layui-btn-container">
            <a class="layui-btn layui-btn-normal addNews_btn layui-bg-red" lay-event="add">
                <i class="layui-icon">&#xe608;</i>
                添加产品
            </a>
            <#if order.state==0>
            <a class="layui-btn layui-btn-normal addNews_btn layui-bg-orange" lay-event="delivery">
                <i class="layui-icon">&#xe608;</i>
                送货中
            </a>
            </#if>
            <a class="layui-btn layui-btn-normal addNews_btn layui-bg-green" lay-event="finish">
                <i class="layui-icon">&#xe608;</i>
                已完成
            </a>
        </div>
    </script>

<#--行工具栏-->
    <!--操作-->
    <script id="toolDemo" type="text/html">
        <a class="layui-btn layui-btn-xs" id="edit" lay-event="edit">编辑</a>
        <a class="layui-btn layui-btn-xs layui-btn-danger" lay-event="del">删除</a>
    </script>
</#if>

<script type="text/javascript" src="${ctx}/js/order/edit.js"></script>
</body>
</html>