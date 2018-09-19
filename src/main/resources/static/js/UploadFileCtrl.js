// CONTROLLER UPLOAD FILE
mainApp.controller('UploadFileController', function($scope, $http) {
 
    $scope.uploadResult ="";
     
    $scope.myForm = {
    	firstName: "",
    	lastName: "",
    	emailAddress: "",
    	mobileNumber: "",
    	description: "",
        files: []
    }
 
    
    $scope.doUploadFile = function() { 
    	        
        if($scope.myForm.files.length<=0){
        	alert("Please Upload User Photo."); 
        } else{
        	var url = "/rest/uploadMultiFiles";
            var data = new FormData();
            data.append("firstName", $scope.myForm.firstName);
            data.append("lastName", $scope.myForm.lastName);
            data.append("emailAddress", $scope.myForm.emailAddress);
            data.append("mobileNumber", $scope.myForm.mobileNumber);
            
            for (i = 0; i < $scope.myForm.files.length; i++) {
                data.append("files", $scope.myForm.files[i]);
            }
     
            var config = {
                transformRequest: angular.identity,
                transformResponse: angular.identity,
                headers: {
                    'Content-Type': undefined
                }
            }
            
            $http.post(url, data, config).then(
                    // Success
                    function(response) {
                        $scope.uploadResult =  response.data;
                    },
                    // Error
                    function(response) {
                        $scope.uploadResult = response.data;
                    });
            
        }
        
                
    };
 
});