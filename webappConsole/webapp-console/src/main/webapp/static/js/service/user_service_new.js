userApp.factory('userservice', [ '$resource', function($resource) {
	return new User($resource);
} ]);
function User(resource) {
	this.resource = resource;
	this.createUser = function(user, scope) {
		// 
		// Save Action Method
		//
		var User = resource('/users/new');
		User.save(user, function(response) {
			scope.message = response.message;
		});
	}
	this.getUser = function(id, scope) {
		//
		// GET Action Method
		//
		var User = resource('/users/:userId', {
			userId : '@userId'
		});
		User.get({
			userId : id
		}, function(user) {
			scope.user = user;
		})
	}
	this.getUsers = function(scope) {
		//
		// Query Action Method
		//
		var Users = resource('/users/all');
		Users.query(function(users) {
			scope.users = users;
		});
	}
}