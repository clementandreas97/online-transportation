<%@ page import="utility.CookieChecker" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.List" %>
<%@ page import="sun.misc.BASE64Encoder" %>
<%--
  Created by IntelliJ IDEA.
  User: ASUS ROG
  Date: 11/3/2017
  Time: 10:58 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    if (request.getAttribute("user_profile") == null || request.getAttribute("username") == null)
        request.getRequestDispatcher("/profile").forward(request, response);
    Map<String, String> user = (Map<String, String>) request.getAttribute("user_profile");
%>
<!DOCTYPE html>
<html>
<head>
    <title>My Profile</title>
    <link rel="stylesheet" href="css/master.css">
    <link rel="icon" href="img/blackjek.png">
    <script src="js/master.js"></script>
</head>
<body>
<div class="myprofile">
    <%@ include file = "navbar.jsp" %>
    <script type="text/javascript">chooseNavbar(2);</script>
    <div class="posrelative flex-justified flex-center">
        <h1 class="font-bebas">MY PROFILE</h1>
        <a href="/editprofile"><img class="edit-icon" src="img/edit.png"></a>
    </div>
    <br><br>
    <center class="font-rockwell">
        <img src="data:image/jpeg;base64,<%=user.get("profile_picture")%>" class="round-image center" alt="Image not found" onerror="putDefaultImage(this);">
        <div id="username-profile" class="bold text-center>">@<%=request.getAttribute("username")%></div>
        <%=user.get("name")%><br>
        <% if (user.get("is_driver").equals("true")) {%>
        Driver | <img src="img/star.png" class="small-icon">&nbsp;
        <span class="color-rating"><%=(double) Math.round(Float.parseFloat(user.get("rating")) * 10) / 10%></span>&nbsp;
        (<%=user.get("votes")%> votes)<br>
        <% } else { %>
        Non Driver<br>
        <% } %>
        <img class="small-icon" src="img/email.png"> <%=user.get("email")%><br>
        <img class="small-icon" src="img/phone.png"> <%=user.get("phone")%><br><br>
    </center>
    <% if (user.get("is_driver").equals("true")) {%>
    <div class="posrelative flex-justified flex-center">
        <h2 class="font-bebas">PREFERRED LOCATIONS:</h2>
        <a href="/editlocation">
            <img class="profile-location-icon" src="img/edit.png">
        </a>
    </div>
    <br>
    <%
        List<Map<String, String>> locations = (List<Map<String, String>>) request.getAttribute("locations");
        int row_number = 0;
    %>
    <div class="preferred_location font-rockwell">
        <ul>
            <% for (Map<String, String> location: locations) {%>
            <li style="margin-left:<%=20*row_number%>px"><%=location.get("location")%></li>
            <% ++row_number; }%>
        </ul>
    </div>
    <% }%>
</div>
</body>
</html>
