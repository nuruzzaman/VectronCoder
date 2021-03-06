<!DOCTYPE html>

<html lang="en" ng-app="crudApp">
    <head>
        <title>Vectron</title>
        <link href="css/bootstrap.css" rel="stylesheet"/>
        <link href="css/app.css" rel="stylesheet"/>
    </head>
    <body>

		<nav class="navbar navbar-default navbar-fixed-top">
		    <div class="container text-center">
		        <h3>Code Challenge - Vectron </h3>
		    </div>
		</nav></br></br> 
	
        <div ui-view></div>
        
        <script src="js/lib/angular.min.js" ></script>
        <script src="js/lib/angular-ui-router.min.js" ></script>
        <script src="js/lib/localforage.min.js" ></script>
        <script src="js/lib/ngStorage.min.js"></script>
        
        <script src="js/app/app.js"></script>
        <script src="js/app/UserService.js"></script>
        <script src="js/app/UserController.js"></script>
        
    </body>
</html>