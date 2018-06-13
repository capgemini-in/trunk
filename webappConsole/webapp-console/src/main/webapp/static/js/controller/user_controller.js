'use strict';

angular.module('myApp').controller('UserController', ['$scope', 'UserService', function($scope, UserService) {
    var self = this;
    self.user={username:'',firstName:'',lastName:''};
    self.users=[];

    self.submit = submit;
    self.edit = edit;
    self.remove = remove;
    self.reset = reset;
    self.addEditUserScreen = addEditUserScreen;

    fetchAllUsers();

    function fetchAllUsers(){
        UserService.fetchAllUsers()
            .then(
            function(d) {
                self.users = d;
            },
            function(errResponse){
                console.error('Error while fetching Users');
            }
        );
    }
 
    function addEditUserScreen(action){
    	UserService.addEditUserScreen(action).then(
                function(errResponse){
                    console.error('Error while creating User');
                }
            );
    }
   
    function createUser(user){
        UserService.createUser(user)
            .then(
            fetchAllUsers,
            function(errResponse){
                console.error('Error while creating User');
            }
        );
    }

    function updateUser(user, username){
        UserService.updateUser(user, username)
            .then(
            fetchAllUsers,
            function(errResponse){
                console.error('Error while updating User');
            }
        );
    }

    function deleteUser(username){
        UserService.deleteUser(username)
            .then(
            fetchAllUsers,
            function(errResponse){
                console.error('Error while deleting User');
            }
        );
    }

    function submit() {
        if(self.user.username===null){
            console.log('Saving New User', self.user);
            createUser(self.user);
        }else{
            updateUser(self.user, self.user.username);
            console.log('User updated with username ', self.user.username);
        }
        reset();
    }

    function edit(username){
        console.log('username to be edited', username);
        for(var i = 0; i < self.users.length; i++){
            if(self.users[i].username === username) {
                self.user = angular.copy(self.users[i]);
                break;
            }
        }
    }

    function remove(username){
        console.log('username to be deleted', username);
        if(self.user.username === username) {//clean form if the user to be deleted is shown there.
            reset();
        }
        deleteUser(username);
    }


    function reset(){
        self.user={username:'',firstName:'',lastName:''};
        $scope.myForm.$setPristine(); //reset Form
    }

}]);
//# sourceURL=user_controller.js