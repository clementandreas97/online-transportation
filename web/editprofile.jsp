<%@ page import="java.util.Map" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    if (request.getAttribute("user_profile") == null)
        request.getRequestDispatcher("/editprofile").forward(request, response);
    Map<String, String> user = (Map<String, String>) request.getAttribute("user_profile");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Edit Profile</title>
    <link rel="stylesheet" href="css/master.css">
    <link rel="icon" href="img/blackjek.png">
    <script src="js/master.js"></script>
</head>
<body>
<div class="edit-profile">
    <div class="editing-title font-bebas">EDIT PROFILE INFORMATION</div><br>
    <form class="edit-profile-form posrelative" method="post" action="/updateprofile" enctype="multipart/form-data" onsubmit="return isEditProfileValid(this);">
        <div class="update-picture">
            <img class="edit-pic-icon floatleft" src="data:image/jpeg;base64,<%=user.get("profile_picture")%>" alt="Image not found" onerror="putDefaultImage(this);">
            <br><br><span class="font-rockwell">Update profile picture</span> <br>
            <input type="file" name="newpic" id="load-file">
        </div>
        <table class="edit-profile-table posrelative width-full font-roboto">
            <tr>
                <td class="txtalignleft">Your Name</td>
                <td class="paddedcell"> <input class="width-full" type="text" name="newname" value="<%=user.get("name")%>"></td>
            </tr>
            <tr>
                <td class="txtalignleft">Phone</td>
                <td class="paddedcell"> <input class="width-full" type="text" name="phone" value="<%=user.get("phone")%>"></td>
            </tr>
            <tr>
                <div class="inline">
                    <td class="txtalignleft">Status Driver</td>
                    <td>
                        <div class="wrapper">
                            <input type="checkbox" name="status" id="toggle" <% if(user.get("is_driver").equals("true")){%>checked<% }%>>
                            <label for="toggle"></label>
                        </div>
                    </td>
                </div>
            </tr>
            <br>
        </table>
        <br>
        <button type="SAVE" name="savebutton" class="green-button floatright posrelative">SAVE</button>
    </form>
    <a href="/profile"><button type="BACK" name="backbutton" class="red-button floatleft posrelative">BACK</button></a>
</div>
</div>
</body>
<script src="js/master.js"></script>
</html>