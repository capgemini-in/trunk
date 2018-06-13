'use strict';

angular.module('myApp').factory('UserService', ['$http', '$q', function($http, $q){

    var REST_SERVICE_URI = 'http://localhost:8082/mywebapp/users/';

    var factory = {
        fetchAllUsers: fetchAllUsers,
        addEditUserScreen: addEditUserScreen,
        createUser: createUser,
        updateUser:updateUser,
        deleteUser:deleteUser
    };

    return factory;

    function fetchAllUsers() {
        var deferred = $q.defer();
        $http.get(REST_SERVICE_URI)
            .then(
            function (response) {
                deferred.resolve(response.data);
            },
            function(errResponse){
                console.error('Error while fetching Users');
                deferred.reject(errResponse);
            }
        );
        return deferred.promise;
    }
    function addEditUserScreen(action){
    	var REST_SERVICE_URI_NEW_UPDATE = 'http://localhost:8082/mywebapp/newUser/';
    	if(action =='UPDATE'){
    		REST_SERVICE_URI_NEW_UPDATE = 'http://localhost:8082/mywebapp/updateUser/';
    	}
    	
        var deferred = $q.defer();
        $http.post(REST_SERVICE_URI_NEW_UPDATE, user)
            .then(
            function (response) {
                deferred.resolve(response.data);
            },
            function(errResponse){
                console.error('Error while new User Screen');
                deferred.reject(errResponse);
            }
        );
        return deferred.promise;
    	
    }
    function createUser(user) {
    	var REST_SERVICE_URI_CREATE = 'http://localhost:8082/mywebapp/createUser/';
        var deferred = $q.defer();
        $http.post(REST_SERVICE_URI_CREATE, user)
            .then(
            function (response) {
                deferred.resolve(response.data);
            },
            function(errResponse){
                console.error('Error while creating User');
                deferred.reject(errResponse);
            }
        );
        return deferred.promise;
    }


    function updateUser(user, username) {
        var deferred = $q.defer();
        $http.put(REST_SERVICE_URI+username, user)
            .then(
            function (response) {
                deferred.resolve(response.data);
            },
            function(errResponse){
                console.error('Error while updating User');
                deferred.reject(errResponse);
            }
        );
        return deferred.promise;
    }

    function deleteUser(username) {
        var deferred = $q.defer();
        $http.delete(REST_SERVICE_URI+username)
            .then(
            function (response) {
                deferred.resolve(response.data);
            },
            function(errResponse){
                console.error('Error while deleting User');
                deferred.reject(errResponse);
            }
        );
        return deferred.promise;
    }

}]);

//# sourceURL=user_service.js
