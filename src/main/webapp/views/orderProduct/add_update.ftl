<!DOCTYPE html>
<html>
<head>
    <#include "../common.ftl">
</head>
<body class="childrenBody">
<form class="layui-form" style="width:80%;">
    <#-- 隐藏域:设置ID -->
    <input name="opId" type="hidden" value="${(orderProduct.id?c)!}"/>
    <input name="oId" type="hidden" value="${(orderProduct.orderId?c)!}"/>
    <input name="pId" type="hidden" value="${(orderProduct.productId?c)!}"/>

    <div class="layui-form-item layui-row layui-col-xs12">
        <label class="layui-form-label">产品名称</label>
        <div class="layui-input-block">
            <select name="name" id="name" lay-verify="required">
                <option value="">请选择</option>
            </select>
        </div>
    </div>

    <div class="layui-form-item layui-row layui-col-xs12">
        <label class="layui-form-label">产品数量</label>
        <div class="layui-input-block">
            <input type="text" class="layui-input" lay-verify="required" name="number" id="number" value="${(orderProduct.number?c)!}"
                   placeholder="请输入产品数量">
        </div>
    </div>

    <br/>
    <div class="layui-form-item layui-row layui-col-xs12">
        <div class="layui-input-block">
            <button class="layui-btn layui-btn-lg" lay-submit=""
                    lay-filter="addOrUpdate">确认
            </button>
            <button class="layui-btn layui-btn-lg layui-btn-normal" id="closeBtn">取消</button>
        </div>
    </div>
</form>
<script type="text/javascript" src="${ctx}/js/orderProduct/add.update.js"></script>
</body>
</html>