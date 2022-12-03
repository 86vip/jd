<!DOCTYPE html>
<html>
<head>
    <#include "../common.ftl">
</head>
<body class="childrenBody">
<form class="layui-form" style="width:80%;">
<#--
    &lt;#&ndash; 隐藏域:设置订单ID &ndash;&gt;
    <input name="id" type="hidden" value="${(order.id?c)!}"/>
-->

    <div class="layui-form-item layui-row layui-col-xs12">
        <label class="layui-form-label">客户姓名</label>
        <div class="layui-input-block">
            <select name="name" id="name" lay-verify="required">
                <option value="">请选择</option>
            </select>
        </div>
    </div>
   <#-- <div class="layui-form-item layui-row layui-col-xs12">
        <label class="layui-form-label">联系号码</label>
        <div class="layui-input-block">
            <input type="text" class="layui-input"
                   lay-verify="required" name="phone" id="phone" value="${(order.phone)!}"
                   placeholder="请输入联系号码">
        </div>
    </div>-->
    <div class="layui-form-item layui-row layui-col-xs12">
        <label class="layui-form-label">送货地址</label>
        <div class="layui-input-block">
            <input type="text" class="layui-input"
                   lay-verify="required" name="address" id="address" value="${(order.address)!}"
                   placeholder="请输入送货地址">
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
<script type="text/javascript" src="${ctx}/js/order/add.js"></script>
</body>
</html>