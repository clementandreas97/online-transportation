<%@ page import="utility.CookieChecker" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.List" %>
<%--
  Created by IntelliJ IDEA.
  User: ASUS ROG
  Date: 11/4/2017
  Time: 5:41 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    if (request.getAttribute("preferred_driver") == null)
        request.getRequestDispatcher("/order2").forward(request, response);
    List<Map<String, String>> preferred_driver = (List<Map<String, String>>) request.getAttribute("preferred_driver");
%>
<%
    if (request.getAttribute("driver_destination") == null )
        request.getRequestDispatcher("/order2").forward(request, response);
    List<Map<String, String>> driver_destination = (List<Map<String, String>>) request.getAttribute("driver_destination");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Select driver</title>
    <link rel="stylesheet" type="text/css" href="css/master.css">
    <link rel="icon" href="img/blackjek.png">
</head>
<body>
<div class="order">
    <%@ include file = "navbar.jsp" %>
    <script src="js/master.js"></script>
    <script type="text/javascript">chooseNavbar(0);</script>
    <div class="order-title">
        <div class="font-bebas">
            <h1>Make an order</h1>
        </div>
        <div class="order-steps">
            <div class="step-field flex-center">
                <div class="step-number flex-center">1</div>
                <div class="step-text">Select Destination</div>
            </div>
            <div class="step-field flex-center current-step">
                <div class="step-number flex-center">2</div>
                <div class="step-text">Select a Driver</div>
            </div>
            <div class="step-field flex-center">
                <div class="step-number flex-center">3</div>
                <div class="step-text">Chat Driver</div>
            </div>
            <div class="step-field flex-center">
                <div class="step-number flex-center">4</div>
                <div class="step-text">Complete your order</div>
            </div>
        </div>
    </div>
    <div class="driver-select">
        <div class="driver-list">
            <header class="font-bebas">
                <h2>Preferred drivers</h2>
            </header>
            <%if (preferred_driver.size() == 0) { %>
            <p class="no-driver">
                Nothing to display :(
            </p>
            <% } else {
                    for (Map<String, String> pref_driver : preferred_driver){
            %>
            <div class="driver-item">
                <img class="floatleft edit-pic-icon" src="data:image/jpeg;base64,<%----%>" onerror="putDefaultImage(this);">
                <div class="font-rockwell"><%=pref_driver.get("name")%></div>
                <div class="font-rockwell">
                    <img src="img/star.png" class="small-icon">
                    <span class="color-rating"><%=(double) Math.round(Float.parseFloat(pref_driver.get("rating")) * 10) / 10%></span> (<%=pref_driver.get("votes")%> votes)
                </div>
                <form method="get" action="chat">
                    <input type="hidden" name="pick" value="<%=request.getParameter("pick")%>">
                    <input type="hidden" name="dest" value="<%=request.getParameter("dest")%>">
                    <button class="pos-choose-button green-button" name="driverId" type="submit" value="<%=pref_driver.get("user_id")%>">I CHOOSE YOU!</button>
                </form>
            </div>
            <% }
            } %>



        </div>
        <div class="driver-list">
            <header class="font-bebas">
                <h2>Other drivers</h2>
            </header>
            <%if (driver_destination.size() == 0) { %>
            <p class="no-driver">
                Nothing to display :(
            </p>
            <% } else {
                for (Map<String, String> dest_driver : driver_destination){
            %>
            <div class="driver-item">
                <img class="floatleft edit-pic-icon" src="data:image/jpeg;base64,<%----%>" onerror="putDefaultImage(this);">
                <div class="font-rockwell"><%=dest_driver.get("name")%></div>
                <div class="font-rockwell">
                    <img src="img/star.png" class="small-icon">
                    <span class="color-rating"><%=(double) Math.round(Float.parseFloat(dest_driver.get("rating")) * 10) / 10%></span> (<%=dest_driver.get("votes")%> votes)
                </div>
                <form method="get" action="/chat">
                    <input type="hidden" name="pick" value="<%=request.getParameter("pick")%>">
                    <input type="hidden" name="dest" value="<%=request.getParameter("dest")%>">
                    <button class="pos-choose-button green-button" name="driverId" type="submit" value="<%=dest_driver.get("user_id")%>">I CHOOSE YOU!</button>
                </form>
            </div>
            <% }
            } %>
        </div>
    </div>
</div>
</body>
</html>