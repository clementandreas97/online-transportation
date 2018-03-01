<%@ page import="java.util.Map" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: ASUS INDONESIA
  Date: 11/5/2017
  Time: 1:11 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    if (request.getAttribute("locations") == null)
        request.getRequestDispatcher("/editlocation").forward(request, response);
    Map<String, String> user = (Map<String, String>) request.getAttribute("user_profile");
    List<Map<String, String>> locations = (List<Map<String, String>>) request.getAttribute("locations");
    int row_number = 1;
%>
<!DOCTYPE html>
<html>
<head>
    <title>Edit Profile</title>
    <link rel="stylesheet" href="css/master.css">
    <link rel="icon" href="img/blackjek.png">
</head>
<body>
<div class="edit-preferred-location">
    <div class="editing-title font-bebas">EDIT PREFERRED LOCATIONS</div><br>
    <table class="table-location font-rockwell posrelative">
        <tr>
            <td class="no-row text-center color-black">No</td>
            <td class="text-center color-black">Location</td>
            <td class="action-row text-center color-black">Actions</td>
        </tr>
        <% for (Map<String, String> location : locations) {%>
        <tr>
            <td class="table-left"><%=row_number%></td>
            <form method="POST" class="edit-location-form" action="/updatelocation?oldlocation=<%=location.get("location")%>" onsubmit="return editLocation('<%=row_number%>')">
                <div id="edit-text">
                    <td class="table-left" id="not-editable-text"><%=location.get("location")%></td>
                    <td class="table-left" id="editable-text" hidden="true"><input id="form-edit-loc" type="text" name="newlocation" value="<%=location.get("location")%>"></td>
                </div>
                <td>
                    <input class="floatleft edit-location-icon" type="image" name="submit" src="img/edit.png" border="0" alt="submit"/>
                    <img class="floatright delete-icon" src="img/delete.png" onclick="deleteLocation('<%=location.get("location")%>')">
                </td>
            </form>
        </tr>
        <% ++row_number;%>
        <% }%>
    </table>
    <br><br>
    <h2 class="font-bebas">ADD NEW LOCATION:</h2>
    <form method="post" class="edit-profile-form posrelative" action="/addlocation" onsubmit="return isLocationValid(this);">
        <div class="flex-justified flex-center align-top-cell">
            <input class="width-full" type="text" name="newlocation" id="form-add-loc">
            <button type="input" name="addbutton" class="green-button floatright posrelative">ADD</button>
        </div>
    </form>
    <br><br>
    <a href="/profile"><button type="BACK" name="backbutton" class="red-button floatleft posrelative">BACK</button></a>
</div>
</body>
<script src="js/master.js"></script>
</html>
