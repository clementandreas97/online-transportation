<%--
  Created by IntelliJ IDEA.
  User: ASUS ROG
  Date: 11/3/2017
  Time: 11:08 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String username = "";
    Cookie[] cookies = null;
    cookies = request.getCookies();
    if (cookies != null) {
        for (Cookie cooky : cookies) {
            String cookieName = cooky.getName();
            String cookieValue = cooky.getValue();
            if (cookieName.equals("uname")) {
                username = cookieValue;
            }
        }
    }
%>
<div class="flex-justified posrelative">
    <div>
        <div id="title">
            <span id="title-green">PR</span><span id="title-black">-</span><span id="title-red">OJEK</span>
        </div>
        <span id="motto">wushh... wushhh... ngeeeeeenggg...</span>
    </div>
    <div>
        <div class="font-roboto">Hi, <span class="bold"><%=username%></span>!</div>
        <div class="flex-end"><a href="/logout" id="logout-link" class="font-roboto">Logout</a></div>
    </div>
</div>
<div class="posrelative">
    <ul class="navbar-list bold">
        <li><a href="order1">ORDER</a></li>
        <li><a href="previousorder">HISTORY</a></li>
        <li><a href="profile">MY PROFILE</a></li>
    </ul>
</div>
