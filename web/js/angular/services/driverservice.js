angular.module('driverService', [])

	// super simple service
	// each function returns a promise object 
	.factory('DriverChat', ['$http',function($http) {
		return {
			get : function(data) {
				console.log(data);
				return $http.post('http://localhost:8081/api/chats/getconversation', data);
			},
            send : function(data) {
                return $http.post('http://localhost:8081/api/chats/addchat', data);
            },
			find : function(data) {
			    return $http.post('http://localhost:8081/api/users/findingorder', data);
            },
            cancel : function(data) {
                return $http.post('http://localhost:8081/api/users/cancellingorder', data);
            },
            sendToken : function (data) {
                return $http.post('http://localhost:8081/api/users/updatetoken', data);
            },
            getChats : function (data) {
                return $http.post('http://localhost:8081/api/chats/getconversation', data);
            },
            startOrder : function (data) {
                return $http.post('http://localhost:8081/api/users/startorder', data);
            }
		}
	}]);