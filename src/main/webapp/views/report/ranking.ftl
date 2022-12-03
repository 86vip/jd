<!DOCTYPE html>
<html>
<head>
    <title>客户消费排行</title>
    <#include "../common.ftl">
</head>
<body class="childrenBody">

<form class="layui-form">
    <table id="List" class="layui-table" lay-filter="ranking"></table>
</form>
<script type="text/javascript" src="${ctx}/js/report/ranking.js"></script>

</body>
</html>