
mainApp.controller('GetFilesController', function($scope, $http) {
 
    $scope.allFiles = [];
 
 
    $scope.getAllFiles = function() {
        var url = "/rest/getAllFiles";
        $http.get(url).then(
            // Success
            function(response) {
                $scope.allFiles = response.data;
            },
            function(response) {
                alert("Error: " + response.data);
            }
        );
    };
    
    
    //Delete 
    $scope.deleteUser = function(user) {
    	console.log("delete user-------------- "+user.id); 
    	// delete URL
		var url = "/rest/deleteUser/" + user.id;
		
		var config = {
            headers : {
            	'Accept': 'text/plain'
            }
        }
    	
		$http.delete(url, config).then(function (response) {
			$scope.error = false;
			//console.log(response.data);
			$scope.uploadResult =  response.data;
			
		}, function error(response) {
			$scope.uploadResult =  response.data;
		});
		var url = "/rest/getAllFiles";
        $http.get(url).then(function(response) {
	            $scope.allFiles = response.data;
	        },
	        function(response) {
	            alert("Error: " + response.data);
	        });
    };     
    
    
    $scope.editUser = function(user) {
    	console.log("edit user-------------- "+user.id); 
		var url = "/rest/updateUser/" + user.id;
		var data = $scope.user;
		var config = {
                headers : {
                	'Content-Type': 'application/json',
                	'Accept': 'text/plain'
                }
        }
		
		$http.put(url, data, config).then(function (response) {
			$scope.uploadResult =  response.data;
		}, function error(response) {
			$scope.uploadResult =  response.data;
		});
		
    }; 
    
});
