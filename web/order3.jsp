<%--
  Created by IntelliJ IDEA.
  User: ASUS ROG
  Date: 11/4/2017
  Time: 5:41 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="utility.CookieChecker" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.List" %>
<%
    if (request.getAttribute("driver_data_service") == null )
        request.getRequestDispatcher("/order3").forward(request, response);
    Map<String, String> driver_data_service = (Map<String, String>) request.getAttribute("driver_data_service");
%>
<%
    if (request.getAttribute("driver_data_account") == null)
        request.getRequestDispatcher("/order3").forward(request, response);
    Map<String, String> driver_data_account = (Map<String, String>) request.getAttribute("driver_data_account");
%>

<!DOCTYPE html>
<html>
<head>
    <title>Complete your order</title>
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
            <div class="step-field flex-center">
                <div class="step-number flex-center">2</div>
                <div class="step-text">Select a Driver</div>
            </div>
            <div class="step-field flex-center">
                <div class="step-number flex-center">3</div>
                <div class="step-text">Chat Driver</div>
            </div>
            <div class="step-field flex-center current-step">
                <div class="step-number flex-center">4</div>
                <div class="step-text">Complete your order</div>
            </div>
        </div>
    </div>
    <div class="review-driver">
        <header class="font-bebas">
            <h2>How was it?</h2>
        </header>
        <div class="driver-profile font-rockwell">
            <%--<center><img src="data:image/jpeg;base64,<?=base64_encode($driver["profile_picture"])?>" class="round-image center" onerror="putDefaultImage(this);">--%>
                <div class="bold username-profile text-center>">@<%=driver_data_account.get("uname")%></div>
                <%=driver_data_service.get("name")%>

        </div>
        <div class="give-rating">
            <img src="img/empty-star.png" class="rating-icon" onclick="changeRating(1)">
            <img src="img/empty-star.png" class="rating-icon" onclick="changeRating(2)">
            <img src="img/empty-star.png" class="rating-icon" onclick="changeRating(3)">
            <img src="img/empty-star.png" class="rating-icon" onclick="changeRating(4)">
            <img src="img/empty-star.png" class="rating-icon" onclick="changeRating(5)">
        </div>
        <div class="comment-field">
            <form method="post" class="width-full posrelative" action="finishorder" onsubmit="return validateOrder(this)">
                <input type="hidden" name="driverId" value="<%=request.getParameter("driverId")%>">
                <input type="hidden" name="pick" value="<%=request.getAttribute("pick")%>">
                <input type="hidden" name="dest" value="<%=request.getAttribute("dest")%>">
                <input type="hidden" name="rating" value="0">
                <textarea class="large-text-comment" name="comment" placeholder="Your comment..." cols="40" rows="3"></textarea>
                <br>
                <span class="pos-complete-order-button flex-end">
						<button class="green-button" type="submit">COMPLETE<br>ORDER</button>
					</span>
            </form>
        </div>
    </div>
</div>
</body>
<script src="js/master.js"></script>
</html>
