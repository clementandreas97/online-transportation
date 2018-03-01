<%--
  Created by IntelliJ IDEA.
  User: ASUS ROG
  Date: 11/2/2017
  Time: 8:03 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html >
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Sign Up</title>
    <link rel="stylesheet" href="css/master.css">
    <link rel="icon" href="img/blackjek.png">
</head>
<body>
<div class="log-template">
    <div class="flex-log posrelative">
        <hr noshade>
        <div class="log-title posrelative"><center>SIGN UP</center></div>
        <hr noshade>
    </div>
    <form class="log-form" method="post" action="register" onsubmit="return isSignUpValid(this)">
        <table id="register-table" class="posrelative noborder color-green font-roboto">
            <tr>
                <td class="txtalignleft align-top-cell">Your Name</td>
                <td class="paddedcell"> <input type="text" name="name" class="log-input"
                                               placeholder="Full name">
                </td>
            </tr>
            <tr>
                <td class="txtalignleft align-top-cell">Username</td>
                <td class="paddedcell"> <input type="text" name="uname" placeholder="Can not contain space"
                                               class="field-with-validation" onblur="isUnameValid(this)">
                    <span class="validation-icon">&nbsp;</span>
                </td>
            </tr>
            <tr>
                <td class="txtalignleft align-top-cell">Email</td>
                <td class="paddedcell"> <input type="text" name="email"  placeholder="example@example.ex"
                                               class="field-with-validation" onblur="isEmailValid(this)">
                    <span class="validation-icon">&nbsp;</span>
                </td>
            </tr>
            <tr>
                <td class="txtalignleft align-top-cell">Password</td>
                <td class="paddedcell"> <input type="password" name="pass" class="log-input"
                                               placeholder="Input your secret password">
                </td>
            </tr>
            <tr>
                <td class="txtalignleft align-top-cell">Confirm Password</td>
                <td class="paddedcell"> <input type="password" name="confpass" class="log-input"
                                               placeholder="Passwords should match">
                    <br><span class="pass-not-same"></span>
                </td>
            </tr>
            <tr>
                <td class="txtalignleft align-top-cell">Phone Number</td>
                <td class="paddedcell"> <input type="text" name="phnum" class="log-input"
                                               placeholder="All numeric, start with 0">
                </td>
            </tr>
        </table>
        <input type="checkbox" value="1" name="isdriver">
        <span class="color-green font-roboto">Also sign me up as a driver!</span>
        <br><br>
        <span class="errormsg">
            <%--&lt;%&ndash;TODO :fix this, still not work, also the ajax&ndash;%&gt;--%>
				<%--<%--%>
                    <%--if(request.getParameter("unametaken") != null) {--%>
                        <%--out.println("Username has been used.");--%>
                    <%--} else if (request.getParameter("emailtaken") != null) {--%>
                        <%--out.println("Email has been used.");--%>
                    <%--}--%>
                <%--%>;--%>&nbsp
			</span>
        <br><br>
        <span class="flex-justified flex-center">
				<a href="index.jsp">
					<font class="log-link"><u>Already have an account?</u></font>
				</a>
				<button type="submit" class="green-button">REGISTER</button>
			</span>
    </form>
</div>
</body>
<script src="js/master.js"></script>
</html>
