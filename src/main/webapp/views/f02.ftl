<#--
    freeMarker数据类型
        布尔类型
            在freemaker中不能直接输出，如果要输出需要转换成字符串
            方式1: ?c
            方式2：?string或?string('为true时显示的内容','为false时显示的内容')
-->
<h3>FreeMarker 数据类型</h3>
<h5>布尔类型</h5>
${flag?c}<br>
${flag?string}<br>
${flag?string('yes','no')}<br>
${flag?string('喜欢','不喜欢')}<br>
<br>
<br>
<br>
<#--
    日期类型 在freemarker中日期类型不能直接输出；
    如果输出要先转成日期型或字符串
        1.?date 年月日
        2.?time 时分秒
        3.?datetime 年月日时分秒
        4.?string("自定义格式")
            y：年     M：月     d：日
            H：时     m：分     s：秒
-->
<h5>日期类型</h5>
${createDate?date}<br>
${createDate?time}<br>
${createDate?datetime}<br>
${createDate?string("yyyy年MM月dd日 HH:mm:ss")}<br>
<#--
    数据类型 在freemarker中可以直接输出；

-->
<h5>数值类型</h5>
${age}<br>
${price}<br>
<#--转成普通字符串-->
${price?c}<br>
<#--转成货币类型-->
${price?string.currency}<br>
<#--转成百分比-->
${price?string.percent}<br>
<#--浮点型保留指定小数位（每#是一位）-->
${price?string["0.###"]}

<br>
<br>
${msg}<br>
${msg?substring(1,3)}

<br>
----
<#--如果值不存在，直接输出会报错-->
<#--${str}-->
<#--使用!，当值不存在时，默认显示空字符串-->
${str!}<br>
<#--使用!"xx"，当值不存在时，默认显示指定字符串-->
${str!"这是一个默认值"}<br>
<#--使用??，判断字符串是否为空；返回布尔类型。
如果想要输出，需要将布尔类型转换成字符串-->
${(str??)?string}<br>
<hr>
<h5>macro自定义指令</h5>
<#macro address>
    ©2022 Dongguan University Of Technology All Rights Reserved
</#macro>
<@address></@address>
<hr>
<#--九九乘法表-->
<#macro cfb>
    <#list 1..9 as i>
        <#list 1..i as j>
            ${i}*${j}=${i*j}&nbsp;
            </#list>
        <br>
    </#list>
</#macro>
<@cfb></@cfb>
<#--传参的乘法表-->
<#macro cfb2 num>
    <#list 1..num as i>
        <#list 1..i as j>
            ${i}*${j}=${i*j}&nbsp;&nbsp;
        </#list>
        <br>
    </#list>
</#macro>
<@cfb2 num=16></@cfb2>