<!DOCTYPE html>
<html>
<#include "/include/head.ftl">
<body>
<#include "/include/support.ftl">
<#include "/include/header.ftl">
<div class="g-doc">
    <div class="n-result">
        <h3>404</h3>
        你访问的网页暂时找不到了，<#if !user && user.usertype==0 && x.isBuy><a href="/mooc/login">登录看看</a></#if>
    </div>
</div>
<#include "/include/footer.ftl">
<script type="text/javascript" src="/mooc/js/global.js"></script>
<script type="text/javascript" src="/mooc/js/pageShow.js"></script>
</body>
</html>