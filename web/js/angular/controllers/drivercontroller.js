angular.module('driverController', [])

    .controller('mainController', ['$scope','$http','DriverChat', function($scope, $http, DriverChat) {
        $scope.formData = {};
        $scope.data = {};
        $scope.chats = {};
        $scope.finding = false;
        $scope.hasOrder = false;

        var scrollUntilBottom = function() {
            setTimeout(function () {
                var objDiv = document.getElementById("chat-list");
                objDiv.scrollTop = objDiv.scrollHeight;
            }, 1); // wait until all chats has been load
        };

        $scope.receiveMessage = function (message) {
            $scope.chats.push({'messageFrom': $scope.formData.to, 'messageTo': $scope.formData.from,
                'message': message});
            $scope.$apply();
            scrollUntilBottom();
        };

        $scope.getChats = function () {
            console.log($scope.formData);
            DriverChat.getChats($scope.formData)
                .success(function(data) {
                    $scope.chats = data;
                    scrollUntilBottom();
                });
        };

        $scope.sendMessage = function() {
            console.log($scope.formData.message);
            // validate the formData to make sure that something is there
            // if form is empty, nothing will happen
            if ($scope.formData.message != undefined) {

                // call the create function from our service (returns a promise object)
                DriverChat.send($scope.formData)

                // if successful creation, call our get function to get all the new chats
                    .success(function() {
                        $scope.chats.push({'messageFrom': $scope.formData.from, 'messageTo': $scope.formData.to,
                            'message': $scope.formData.message});
                        $scope.formData.message = undefined; // clear the form so our user is ready to enter another
                        scrollUntilBottom();
                    });
            }
        };

        $scope.startOrder = function (username) {
            $scope.formData.to = username;
            DriverChat.getChats($scope.formData)
                .success(function(data) {
                    $scope.hasOrder = true;
                    $scope.finding = false;
                    $scope.chats = data;
                    scrollUntilBottom();
                });
        };

        // CREATE ==================================================================
        // when submitting the send form, send the text to the node API

        $scope.findOrder = function() {
            // send to node rest api
            DriverChat.find($scope.data)

                .success(function(data) {
                    $scope.finding = true;
                    console.log("Success finding order");
                });
        };

        $scope.cancelOrder = function() {
            // send to node rest api
            DriverChat.cancel($scope.data)

                .success(function(data) {
                    $scope.finding = false;
                    console.log("Success cancelling order");
                });
        };

        $scope.sendTokenToNode = function (currentToken) {
            var data = {};
            data.token = currentToken;
            data.userId = $scope.data.userId;
            data.username = $scope.data.username;
            DriverChat.sendToken(data)

                .success(function (data) {
                    console.log('token sent');
                })
        };

        $scope.finishOrder = function () {
            $scope.finding = false;
            $scope.hasOrder = false;
            window.location.reload();
        };

        $scope.init = function(username, userId) {
            $scope.formData.from = username;
            $scope.data.username = username;
            $scope.data.userId = userId;
        }
    }]);