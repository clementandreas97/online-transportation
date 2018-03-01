<%@ page import="java.util.Map" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="utility.CookieChecker" %>
<%--
  Created by IntelliJ IDEA.
  User: ASUS ROG
  Date: 11/4/2017
  Time: 5:59 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>History</title>
    <link rel="stylesheet" href="css/master.css">
    <link rel="icon" href="img/blackjek.png">
</head>
<body>
<div class="order-history">
    <%@ include file = "navbar.jsp" %>
    <script src="js/master.js"></script>
    <script type="text/javascript">chooseNavbar(1);</script>
    <h1 class="font-bebas">TRANSACTION HISTORY</h1>
    <div class="history-navbar width-full">
        <ul class="history-navbar-list font-roboto bold">
            <li><a href="previousorder">MY PREVIOUS ORDERS</a></li>
            <li class="history-active"><a href="driverhistory">DRIVER HISTORY</a></li>
        </ul>
    </div>
    <br><br>
    <div class="history-list-box">
        <% List<Map<String,String>> histories = (List<Map<String, String>>) request.getAttribute("history"); %>
        <% if(histories.size() > 0) {
            for(Map<String,String> history : histories) {
        %>
            
        <div class="history-item">
            <img class="floatleft edit-pic-icon" src="data:image/jpeg;base64,<%=history.get("profile_picture")%>" onerror="putDefaultImage(this);">
            <button type="HIDE" name="hidebutton" class="red-button floatright posrelative" onclick="hideHistory(<%= history.get("order_id")%>, false)">HIDE</button>
            <span class="font-rockwell">
							<div class="item-date color-gray">
                            <% Calendar date = Calendar.getInstance();
                            SimpleDateFormat df = new SimpleDateFormat("EEEE, MMMM d'th' yyyy");
                            SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
                            %>
                            <%= df.format(df2.parse(history.get("date")))
                            %>
                            </div>
							<div class="item-name"><%= history.get("name")%></div>
							<div class="item-rest">
                                <%= history.get("picking_point")%> 
                                &rarr; 
                                <%= history.get("destination") %>
                            </div>
							<div class="item-rest flex-center">gave&nbsp;<span class="rating-numeric color-rating"><%=history.get("rating")%></span>&nbsp;star(s) for this order</div>
							<div class="item-rest">
								and left comment:<br>
								<div class="item-rest font-roboto tabbed-comment">
									<%= history.get("comment")%>
								</div>
							</div>
						</span>
            <br><br>
        </div>
        <% }%>
        <% }%>
    </div>
</div>
</body>
<script src="js/master.js"></script>
</html>
