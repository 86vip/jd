<!DOCTYPE html>
<html>
<head>
    <#include "../common.ftl">
</head>
<body class="childrenBody">
<form class="layui-form" style="width:80%;">
    <#-- 隐藏域:设置客户ID -->
    <input name="id" type="hidden" value="${(customer.id?c)!}"/>

    <div class="layui-form-item layui-row layui-col-xs12">
        <label class="layui-form-label">客户姓名</label>
        <div class="layui-input-block">
            <input type="text" class="layui-input"
                   lay-verify="required" name="name" id="name" value="${(customer.name)!}"
                   placeholder="请输入客户姓名">
        </div>
    </div>
    <div class="layui-form-item layui-row layui-col-xs12">
        <label class="layui-form-label">联系号码</label>
        <div class="layui-input-block">
            <input type="text" class="layui-input"
                   lay-verify="required" name="phone" id="phone" value="${(customer.phone)!}"
                   placeholder="请输入联系号码">
        </div>
    </div>
    <div class="layui-form-item layui-row layui-col-xs12">
        <label class="layui-form-label">联系地址</label>
        <div class="layui-input-block">
            <input type="text" class="layui-input"
                   lay-verify="required" name="address" id="address" value="${(customer.address)!}"
                   placeholder="请输入联系地址">
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
<script type="text/javascript" src="${ctx}/js/customer/add.update.js"></script>
</body>
</html>