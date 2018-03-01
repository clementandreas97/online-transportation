<%@ page import="utility.CookieChecker" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Login</title>
    <link rel="stylesheet" href="css/master.css">
    <link rel="icon" href="img/blackjek.png">
</head>
<body>
<div class="log-template">
    <div class="flex-log posrelative">
        <hr noshade>
        <div class="log-title posrelative"><center>LOGIN</center></div>
        <hr noshade>
    </div>
    <br><br><br>
    <form class="log-form" class="posrelative" method="post" action="login" onsubmit="return isFormEmpty(this)">
        <table class="posrelative noborder color-green font-roboto">
            <tr>
                <td class="txtalignleft align-top-cell">Username</td>
                <td class="paddedcell"> <input type="text" name="uname" class="log-input"> <br>
                    <span class="errormsg"> &nbsp; </span>
                </td>
            </tr>
            <tr>
                <td class="txtalignleft align-top-cell">Password</td>
                <td class="paddedcell"> <input type="password" name="pass" class="log-input"> <br>
                    <span class="errormsg"> &nbsp; </span>
                </td>
            </tr>
        </table>
        <br><br>
        <div class="errormsg">
            <%
                if (request.getAttribute("errormessage") != null) {
                    out.println(request.getAttribute("errormessage"));
                }
            %>&nbsp;
        </div>
        <br>
        <span class="flex-justified flex-center">
				<a href="signup">
					<font class="log-link"><u>Don't have an account?</u></font>
				</a>
				<button type="submit" class="green-button"> GO!</button>
			</span>
    </form>
</div>
</body>
<script src="js/master.js"></script>
</html>