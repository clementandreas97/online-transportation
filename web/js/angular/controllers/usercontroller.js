angular.module('userController', [])

	.controller('mainController', ['$scope','$http','UserChat', function($scope, $http, UserChat) {
		$scope.formData = {};
		$scope.data = {};
		$scope.chats = {};

		var scrollUntilBottom = function() {
            setTimeout(function () {
                var objDiv = document.getElementById("chat-list");
                objDiv.scrollTop = objDiv.scrollHeight;
            }, 1); // wait until all chats has been load
        };

		// GET =====================================================================
        $scope.receiveMessage = function (message) {
            $scope.chats.push({'messageFrom': $scope.formData.to, 'messageTo': $scope.formData.from,
                'message': message});
            $scope.$apply();
            scrollUntilBottom();
        };

        $scope.getChats = function () {
            console.log($scope.formData);
            UserChat.getChats($scope.formData)
                .success(function(data) {
                    $scope.chats = data;
                    scrollUntilBottom();
                });
        };

		// CREATE ==================================================================
		// when submitting the send form, send the text to the node API
		$scope.sendMessage = function() {

			// validate the formData to make sure that something is there
			// if form is empty, nothing will happen
			if ($scope.formData.message != undefined) {

				// call the create function from our service (returns a promise object)
                UserChat.send($scope.formData)

					// if successful creation, call our get function to get all the new chats
					.success(function() {
                        $scope.chats.push({'messageFrom': $scope.formData.from, 'messageTo': $scope.formData.to,
                            'message': $scope.formData.message});
						$scope.formData.message = undefined; // clear the form so our user is ready to enter another
						// $scope.chats = data; // assign our new list of chats
                        scrollUntilBottom();
					});
			}
		};

		$scope.finishOrder = function() {
            // send to node rest api
            UserChat.finish($scope.formData)

                .success(function(data) {
                    console.log("success finishing order");
                });
        };

        $scope.sendTokenToNode = function (currentToken) {
            console.log(currentToken, $scope.data.username, $scope.data.userId);
            var data = {};
            data.token = currentToken;
            data.userId = $scope.data.userId;
            data.username = $scope.formData.from;
            UserChat.sendToken(data)

                .success(function (data) {
                    console.log('token sent');
                })
        };

		$scope.init = function(username, drivername, userId) {
		    $scope.formData.from = username;
		    $scope.formData.to = drivername;
		    $scope.data.userId = userId;
        }

        $scope.getChats();
	}]);