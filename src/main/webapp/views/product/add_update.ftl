<!DOCTYPE html>
<html>
<head>
    <#include "../common.ftl">
</head>
<body class="childrenBody">
<form class="layui-form" style="width:80%;">
    <#-- 隐藏域:设置产品ID -->
    <input name="id" type="hidden" value="${(product.id?c)!}"/>

    <div class="layui-form-item layui-row layui-col-xs12">
        <label class="layui-form-label">产品名称</label>
        <div class="layui-input-block">
            <input type="text" class="layui-input"
                   lay-verify="required" name="name" id="name" value="${(product.name)!}"
                   placeholder="请输入产品名称">
        </div>
    </div>
    <div class="layui-form-item layui-row layui-col-xs12">
        <label class="layui-form-label">产品商家</label>
        <div class="layui-input-block">
            <input type="text" class="layui-input"
                   lay-verify="required" name="merchant" id="merchant" value="${(product.merchant)!}"
                   placeholder="请输入产品商家">
        </div>
    </div>
    <div class="layui-form-item layui-row layui-col-xs12">
        <label class="layui-form-label">产品单价</label>
        <div class="layui-input-block">
            <input type="text" class="layui-input"
                   lay-verify="required" name="price" id="price" value="${(product.price)!}"
                   placeholder="请输入产品单价">
        </div>
    </div>
    <div class="layui-form-item layui-row layui-col-xs12">
        <label class="layui-form-label">产品类型</label>
        <div class="layui-input-block">
            <input type="text" class="layui-input"
                   lay-verify="required" name="type" id="type" value="${(product.type)!}"
                   placeholder="请输入产品类型">
        </div>
    </div>
    <div class="layui-form-item layui-row layui-col-xs12">
        <label class="layui-form-label">引进日期</label>
        <div class="layui-input-block">
            <input type="datetime" class="layui-input"
                    name="createDate" id="createDate" value="${(product.createDate?string('yyyy-MM-dd HH:mm:ss'))!}"
                   placeholder="请输入引进日期">
        </div>
    </div>

    <br/>
    <div class="layui-form-item layui-row layui-col-xs12">
        <div class="layui-input-block">
            <button class="layui-btn layui-btn-lg" lay-submit=""
                    lay-filter="addOrUpdateProduct">确认
            </button>
            <button class="layui-btn layui-btn-lg layui-btn-normal" id="closeBtn">取消</button>
        </div>
    </div>
</form>
<script type="text/javascript" src="${ctx}/js/product/add.update.js"></script>
</body>
</html>