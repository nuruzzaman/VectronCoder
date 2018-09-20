<div class="generic-container">
    <div class="panel panel-default">
        <!-- Default panel contents -->
        <div class="panel-heading"><span class="lead">User </span></div>
		<div class="panel-body">
	        <div class="formcontainer">
	            <div class="alert alert-success" role="alert" ng-if="ctrl.successMessage">{{ctrl.successMessage}}</div>
	            <div class="alert alert-danger" role="alert" ng-if="ctrl.errorMessage">{{ctrl.errorMessage}}</div>
	            
	            <form ng-submit="ctrl.submit()" name="myForm" class="form-horizontal">
	                
	                <input type="hidden" ng-model="ctrl.user.id" />
	                
	                <div class="row">
	                    <div class="form-group col-md-12">
	                        <label class="col-md-2 control-lable" for="firstName">First Name</label>
	                        <div class="col-md-7">
	                            <input type="text" ng-model="ctrl.user.firstName" id="firstName" class="username form-control input-sm" placeholder="Enter your first name" required ng-minlength="3"/>
	                        </div>
	                    </div>
	                </div>
					<div class="row">
	                    <div class="form-group col-md-12">
	                        <label class="col-md-2 control-lable" for="lastName">Last Name</label>
	                        <div class="col-md-7">
	                            <input type="text" ng-model="ctrl.user.lastName" id="lastName" class="username form-control input-sm" placeholder="Enter your last name" required ng-minlength="3"/>
	                        </div>
	                    </div>
	                </div>
					<div class="row">
	                    <div class="form-group col-md-12">
	                        <label class="col-md-2 control-lable" for="emailAddress">Email Address</label>
	                        <div class="col-md-7">
	                            <input type="text" ng-model="ctrl.user.emailAddress" id="emailAddress" class="username form-control input-sm" placeholder="Enter your email address" required />
	                        </div>
	                    </div>
	                </div>

	                <div class="row">
	                    <div class="form-group col-md-12">
	                        <label class="col-md-2 control-lable" for="mobileNumber">Mobile Number</label>
	                        <div class="col-md-7">
	                            <input type="text" ng-model="ctrl.user.mobileNumber" id="mobileNumber" class="form-control input-sm" placeholder="Enter your mobile number" required ng-pattern="ctrl.onlyIntegers"/>
	                        </div>
	                    </div>
	                </div>
	                
	                <div class="row">
	                    <div class="form-group col-md-12">
	                        <label class="col-md-2 control-lable" for="files">File upload</label>
	                        <div class="col-md-4">
	                            <input type="file" file-model="ctrl.user.files[0]"/>
	                        </div>
	                    </div>
	                 </div>	

					<div class="row">
	                    <div class="form-group col-md-12">
	                        <label class="col-md-2 control-lable" for="description"></label>
	                        <div class="col-md-4">
	                            <input type="submit"  value="{{!ctrl.user.id ? 'Add' : 'Update'}}" class="btn btn-primary btn-sm" ng-disabled="myForm.$invalid || myForm.$pristine">
		                        <button type="button" ng-click="ctrl.reset()" class="btn btn-warning btn-sm">Reset Form</button>
	                        </div>
	                    </div>
	                 </div>	

	            </form>
    	    </div>
		</div>	
    </div>
    <div class="panel panel-default">
        <!-- Default panel contents -->
        <div class="panel-heading"><span class="lead">List of Users </span></div>
		<div class="panel-body">
			<div class="table-responsive">
		        <table class="table table-hover">
		            <thead>
		            <tr>
		                <th>ID</th>
		                <th>FIRST NAME</th>
		                <th>LAST NAME</th>
		                <th>EMAIL ADDRESS</th>
		                <th>MOBILE NUMBER</th>
		                <th>PHOTO</th>
		                <th>CREATE DATE</th>
		                <th>UPDATE DATE</th>
		                <th width="20px"></th>
		                <th width="20px"></th>
		            </tr>
		            </thead>
		            <tbody>
		            <tr ng-repeat="u in ctrl.getAllUsers()">
		                <td>{{u.id}}</td>
		                <td>{{u.firstName}}</td>
		                <td>{{u.lastName}}</td>
		                <td>{{u.emailAddress}}</td>
		                <td>{{u.mobileNumber}}</td>
		                <td><a href='/api/rest/files/{{u.imgName}}'>{{u.imgName}}</td>
		                <td>{{u.createdDate}}</td>
		                <td>{{u.lastUpdatedDate}}</td>
		                <td><button type="button" ng-click="ctrl.editUser(u.id)" class="btn btn-success btn-sm">Edit</button></td>
		                <td><button type="button" ng-click="ctrl.removeUser(u.id)" class="btn btn-danger btn-sm">Delete</button></td>
		            </tr>
		            </tbody>
		        </table>		
			</div>
		</div>
    </div>
</div>