angular.module('userService', [])

// super simple service
// each function returns a promise object
    .factory('UserChat', ['$http',function($http) {
        return {
            getChats : function(data) {
                return $http.post('http://localhost:8081/api/chats/getconversation', data);
            },
            send : function(data) {
                return $http.post('http://localhost:8081/api/chats/addchat', data);
            },
            sendToken : function (data) {
                return $http.post('http://localhost:8081/api/users/updatetoken', data);
            },
            finish : function(data) {
                return $http.post('http://localhost:8081/api/users/finsihorder', data);
            }
        }
    }]);