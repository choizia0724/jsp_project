<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>JSP AND SPRINGBOOT</title>
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <style>
        @import url('https://webfontworld.github.io/score/SCoreDream.css');
        @import url("https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css");
        * {
            font-family: 'SCoreDream';
            padding: 0;
            margin: 0;
        }
        .k-appbar .k-appbar-section{
            max-width:120px;
        }
        .text {
            overflow: hidden;
            white-space: nowrap;
            text-overflow: ellipsis;
            word-break: break-all;
        }
    </style>
    <link href="/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <link type="text/css" rel="stylesheet" href="/css/default-ocean-blue.css">
    <script type="text/javascript" src="https://code.jquery.com/jquery-3.7.0.min.js"></script>
    <script type="text/javascript" src="/js/kendo.all.min.js"></script>
    <script type="text/javascript" src="/js/kendo-ui-license.js"></script>
</head>
<body>
<div id="container">
    <%@ include file="component/header.jsp" %>
    <div class="container">
        <nav class="bg-transparent k-pt-5" id="breadcrumb"></nav>
    </div>
    <script>
        $(document).ready(()=>{
            var title = "<%=(String) request.getAttribute("bodyItem")%>";
            $("#breadcrumb").kendoBreadcrumb({
                items: [
                    {
                        type: "rootitem",
                        text: "Home",
                        showText: true,
                        icon: "home",
                        showIcon: true
                    },
                    {
                        type: "item",
                        text: title,
                        showText: true
                    }
                ]
            });
        })
    </script>
    <%
        String bodyItem = (String) request.getAttribute("bodyItem");
        if (bodyItem.equals("Member")) {
    %>
    <%@ include file="page/member.jsp" %>
    <%
    } else if (bodyItem.equals("Write")) {
    %>
    <%@ include file="page/post.jsp" %>
    <%
    } else if (bodyItem.equals("Read")) {
    %>
    <%@ include file="page/read.jsp" %>
    <%
    } else {
    %>
    <%@ include file="page/dash.jsp" %>
    <%
        }
    %>


</div>
<script src="/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>

</body>
</html>
