'use strict';

angular.module('crudApp').controller('UserController',
    ['UserService', '$scope', '$http', '$q', 'urls', 
     function( UserService, $scope, $http, $q, urls) {

        var self = this;
        self.user = {};
        self.users=[];
        self.submit = submit;
        self.getAllUsers = getAllUsers;
        self.createUser = createUser;
        self.updateUser = updateUser;
        self.removeUser = removeUser;
        self.editUser = editUser;
        self.reset = reset;

        self.successMessage = '';
        self.errorMessage = '';
        self.done = false;

        self.onlyIntegers = /^\d+$/;
        self.onlyNumbers = /^\d+([,.]\d+)?$/;

        function submit() {
        	if(self.user.files===undefined){
        		self.errorMessage = 'Please select a photo'; 
        	}else{
        		if (self.user.id === undefined || self.user.id === null) {                
                    createUser(self.user);
                } else {
                    updateUser(self.user, self.user.id);
                   // console.log('User updated with id ', self.user.id);
                }
        	}
            
        }

        function createUser(user) {
        	var data = new FormData();
            data.append("firstName", self.user.firstName);
            data.append("lastName", self.user.lastName);
            data.append("emailAddress", self.user.emailAddress);
            data.append("mobileNumber", self.user.mobileNumber);
            data.append("files", self.user.files[0]);

            var config = {
                    transformRequest: angular.identity,
                    transformResponse: angular.identity,
                    headers: {
                        'Content-Type': undefined
                    }
                }
        	$http.post(urls.USER_SERVICE_API, data, config).then(
        			
                    function(response) {
                    	UserService.loadAllUsers();
                    	console.log('User created successfully');
                        self.successMessage = 'User created successfully';
                        self.errorMessage='';
                        self.done = true;
                        self.user={};
                        $scope.myForm.$setPristine();
                    },
                    function(errResponse) {
                    	//console.error('Error while creating User');
                        self.errorMessage = errResponse.data;
                        self.successMessage='';
                    });

        }


        function updateUser(user, id) {
        	
        	if(self.user.files===null){
        		UserService.updateUser(user, id)
                .then(
                    function(){
                    	self.successMessage = 'User updated successfully';
                        self.errorMessage= ''; 
                    },
                    function(errResponse){
                    	self.errorMessage = errResponse.data;
                        self.successMessage='';
                    }
                );
        		
        	}else{
        		var data = new FormData();
                data.append("firstName", self.user.firstName);
                data.append("lastName", self.user.lastName);
                data.append("emailAddress", self.user.emailAddress);
                data.append("mobileNumber", self.user.mobileNumber);
                data.append("files", self.user.files[0]);

                var config = {
                        transformRequest: angular.identity,
                        transformResponse: angular.identity,
                        headers: {
                            'Content-Type': undefined
                        }
                    }
                console.log("update user"); 
            	$http.post(urls.USER_SERVICE_API + id, data, config).then(
            			
                        function(response) {
                        	UserService.loadAllUsers();
                            self.successMessage = 'User updated successfully';
                            self.errorMessage='';
                            self.done = true;
                            self.user={};
                            $scope.myForm.$setPristine();
                        },
                        function(errResponse) {
                            self.errorMessage = errResponse.data;
                            self.successMessage='';
                        });
        	}
        	
        }

        function removeUser(id){
            UserService.removeUser(id)
                .then(
                    function(){
                    	self.successMessage='';
                        self.errorMessage= 'User '+id + ' removed successfully'; 
                    },
                    function(errResponse){
                        console.error('Error while removing user '+id +', Error :'+errResponse.data);
                    }
                );
        }


        function getAllUsers(){
            return UserService.getAllUsers();
        }

        function editUser(id) {
            self.successMessage='';
            self.errorMessage='';
            UserService.getUser(id).then(
                function (user) {
                    self.user = user;
                },
                function (errResponse) {
                    console.error('Error while removing user ' + id + ', Error :' + errResponse.data);
                }
            );
        }
        function reset(){
            self.successMessage='';
            self.errorMessage='';
            self.user={};
            $scope.myForm.$setPristine(); //reset Form
        }
    }


    ]);