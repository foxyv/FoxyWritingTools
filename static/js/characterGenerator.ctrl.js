
var app = angular.module("CharGen", []);

app.controller("CharGenCtrl", function($scope, $http) {

    $scope.character = {
        firstName : "",
        lastName : ""
    }

    $scope.randomName = function() {
        $http.get("character/generate").then(function success(response) {
            var data = response.data;


            if(data) {
                if(!$scope.firstNameFreeze) {
                    $scope.character.firstName = response.data.firstName;
                }

                if(!$scope.lastNameFreeze) {
                    $scope.character.lastName = response.data.lastName;
                }
            }
        });
    }

});