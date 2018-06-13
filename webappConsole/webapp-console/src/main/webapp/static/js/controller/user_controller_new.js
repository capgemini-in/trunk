// Controller when the main page/view loads
userApp.controller("SpaCtrl", [ '$scope', function($scope) {
} ]);
/// / Controller for All Users View u
serApp.controller("UsersCtrl", [ '$scope','userservice', function($scope, userservice) {
userservice.getUsers( $scope );
} ]);
/// / Controller for New User View u
serApp.controller("NewUserCtrl", [ '$scope','userservice', function($scope, userservice) {
userservice.getUsers( $scope );
$scope.createNewUser = function(){
var newuser = { 'firstname':$scope.firstname, 'lastname': $scope.lastname, 'address':$scope.address, 'email':$scope.email };
/// / Call UserService to create a new user /
// / u
serservice.createUser ( newuser, $scope );
/// / Push new user to existing table column /
// / $
scope.users.push( newuser );
/// / Reset fields values /
// / $
scope.firstname='';
$scope.lastname='';
$scope.address='';
$scope.email='';
};
} ]);
/// / Controller for Individual User View u
serApp.controller("UsersByIdCtrl", [ '$scope','userservice', '$routeParams', function($scope, userservice, $routeParams) {
userservice.getUser($routeParams.userId, $scope);
} ]);