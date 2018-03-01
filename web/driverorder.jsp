<%--
  Created by IntelliJ IDEA.
  User: Clement
  Date: 19/11/17
  Time: 11:44 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="utility.CookieChecker" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html data-ng-app="driverConversation">
<head>
    <title>Blackjek Order</title>
    <link rel="stylesheet" type="text/css" href="css/master.css">
    <link rel="icon" href="img/blackjek.png">

    <style>
        body                    { padding-top:50px; }
        #chat-list              { margin-bottom:30px; }
        #chat-form              { margin-bottom:50px; }
        .me                     { float: right; }
        .else                   { float: left; }
        .chat-box               { overflow-y: scroll; height: 200px; }
        .message-table          { width: 100%; }
        .message-box            { width: 90%; }
    </style>

    <script src="//ajax.googleapis.com/ajax/libs/angularjs/1.2.16/angular.min.js"></script><!-- load angular -->
    <script src="js/angular/controllers/drivercontroller.js"></script> <!-- load up our controller -->
    <script src="js/angular/services/driverservice.js"></script> <!-- load our todo service -->
    <script src="js/angular/core.js"></script>
</head>
<body id="body" data-ng-controller="mainController" data-ng-init='init(<%="\"" + request.getAttribute("username") + "\", \"" + request.getAttribute("user_id") + "\""%>)'>
<div class="order">
    <%@ include file = "navbar.jsp" %>
    <script src="js/master.js"></script>
    <script type="text/javascript">chooseNavbar(0);</script>
    <div class="order-title">
        <div class="font-bebas">
            <h1>LOOKING FOR AN ORDER</h1>
        </div>
    </div>
    <div class="trip-form" data-ng-hide = "finding || hasOrder">
        <form>
            <br><br><br><br><br>
            <div class="btn-wrapper">
                <button type="submit" class="green-button" data-ng-click="findOrder()">FIND ORDER</button>
            </div>
        </form>
    </div>
    <div class="trip-form" data-ng-show = "finding && !hasOrder">
        <form>
            <br><br><br><br>
            <h2 class="font-bebas text-center" >Finding Order....</h2>
            <br><br><br>
            <div class="btn-wrapper">
                <button type="submit" class="red-button" data-ng-click="cancelOrder()">CANCEL</button>
            </div>
        </form>
    </div>
    <div class="trip-form" data-ng-show = "hasOrder">
        <h2 class="font-bebas text-center color-light-green" >Got an Order!</h2>
        <div class="font-bebas text-center">{{ formData.to }}</div>
        <!-- LIST CHAT -->
        <div scroll-glue id="chat-list" class="chat-box">
            <!-- LOOP CHAT IN $scope.chats -->
            <table class="message-table">
                <tr data-ng-repeat="chat in chats">
                    <td align ="right" data-ng-if="chat.messageFrom == formData.from">
                        <div class="me message-bubble-me" > {{ chat.message }} </div>
                    </td>
                    <td align="left" data-ng-if="chat.messageTo == formData.from">
                        <div class="else message-bubble-else" > {{ chat.message }} </div>
                    </td>
                </tr>
            </table>
        </div>

        <!-- FORM INPUT CHAT -->
        <table class="message-table">
            <tr>
                <div id="chat-form">
                    <form>
                        <td class ="message-box">
                            <!-- BIND VALUE TO formData.from and formData.to IN ANGULAR -->
                            <input type="text" class="message-table" placeholder="Messages" data-ng-model="formData.message">
                        </td>

                        <!-- createToDo() WILL CREATE NEW TODOS -->
                        <td align="right">
                            <button type="submit" class="green-button-kirim" data-ng-click="sendMessage()">Kirim</button>
                        </td>
                    </form>
                </div>
            </tr>
        </table>
    </div>
</div>
<script src="https://www.gstatic.com/firebasejs/4.6.2/firebase.js"></script>
<script>
    // Initialize Firebase
    var config = {
        apiKey: "AIzaSyAnJbLqmQeBCQeIcR7AflQsiZYWAVuEA88",
        authDomain: "pensidoto.firebaseapp.com",
        databaseURL: "https://pensidoto.firebaseio.com",
        projectId: "pensidoto",
        storageBucket: "pensidoto.appspot.com",
        messagingSenderId: "1077735229704"
    };
    firebase.initializeApp(config);
</script>
<script>
    // [START get_messaging_object]
    // Retrieve Firebase Messaging object.
    const messaging = firebase.messaging();
    // [END get_messaging_object]
    // [START refresh_token]
    // Callback fired if Instance ID token is updated.
    messaging.onTokenRefresh(function() {
        messaging.getToken()
            .then(function(refreshedToken) {
                console.log('Token refreshed.');
                // Indicate that the new Instance ID token has not yet been sent to the
                // app server.
                setTokenSentToServer(false);
                // Send Instance ID token to app server.
                sendTokenToServer(refreshedToken);
            })
            .catch(function(err) {
                console.log('Unable to retrieve refreshed token ', err);
            });
    });
    // [END refresh_token]
    // [START receive_message]
    // Handle incoming messages. Called when:
    // - a message is received while the app has focus
    // - the user clicks on an app notification created by a sevice worker
    //   `messaging.setBackgroundMessageHandler` handler.
    messaging.onMessage(function(payload) {
        console.log("Message received. ", payload.data.text);
        if (payload.data.text === "chat") {
            // TODO: update chat
            angular.element(document.getElementById('body')).scope().receiveMessage(payload.data.message);
        } else if (payload.data.text === "start") {
            // TODO: start chat
            angular.element(document.getElementById('body')).scope().startOrder(payload.data.username);
        } else if (payload.data.text === "finish") {
            // TODO: end chat
            angular.element(document.getElementById('body')).scope().finishOrder();
        }
    });
    // [END receive_message]
    function resetUI() {
//        clearMessages();
//        showToken('loading...');
        // [START get_token]
        // Get Instance ID token. Initially this makes a network call, once retrieved
        // subsequent calls to getToken will return from cache.
        messaging.getToken()
            .then(function(currentToken) {
                if (currentToken) {
                    sendTokenToServer(currentToken);
                    console.log(currentToken);
                } else {
                    // Show permission request.
                    console.log('No Instance ID token available. Request permission to generate one.');
                    // Show permission UI.
                    requestPermission();
                    setTokenSentToServer(false);
                }
            })
            .catch(function(err) {
                console.log('An error occurred while retrieving token. ', err);
                setTokenSentToServer(false);
            });
    }
    // [END get_token]
    // Send the Instance ID token your application server, so that it can:
    // - send messages back to this app
    // - subscribe/unsubscribe the token from topics
    function sendTokenToServer(currentToken) {
        if (!isTokenSentToServer()) {
            console.log('Sending token to server...');
            // TODO(developer): Send the current token to your server.
            angular.element(document.getElementById('body')).scope().sendTokenToNode(currentToken);
            setTokenSentToServer(true);
        } else {
            console.log('Token already sent to server so won\'t send it again ' +
                'unless it changes');
        }
    }
    function isTokenSentToServer() {
        return window.localStorage.getItem('sentToServer') == 1;
    }
    function setTokenSentToServer(sent) {
        window.localStorage.setItem('sentToServer', sent ? 1 : 0);
    }
    function requestPermission() {
        console.log('Requesting permission...');
        // [START request_permission]
        messaging.requestPermission()
            .then(function() {
                console.log('Notification permission granted.');
                // TODO(developer): Retrieve an Instance ID token for use with FCM.
                // [START_EXCLUDE]
                // In many cases once an app has been granted notification permission, it
                // should update its UI reflecting this.
                resetUI();
                // [END_EXCLUDE]
            })
            .catch(function(err) {
                console.log('Unable to get permission to notify.', err);
            });
        // [END request_permission]
    }
    function deleteToken() {
        // Delete Instance ID token.
        // [START delete_token]
        messaging.getToken()
            .then(function(currentToken) {
                messaging.deleteToken(currentToken)
                    .then(function() {
                        console.log('Token deleted.');
                        setTokenSentToServer(false);
                        // [START_EXCLUDE]
                        // Once token is deleted update UI.
                        resetUI();
                        // [END_EXCLUDE]
                    })
                    .catch(function(err) {
                        console.log('Unable to delete token. ', err);
                    });
                // [END delete_token]
            })
            .catch(function(err) {
                console.log('Error retrieving Instance ID token. ', err);
                showToken('Error retrieving Instance ID token. ', err);
            });
    }
    setTokenSentToServer(false);
    resetUI();
</script>
</body>
<script src="js/master.js"></script>
</html>
