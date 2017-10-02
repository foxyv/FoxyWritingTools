
var app = angular.module("CharGen", []);

app.controller("CharGenCtrl", function($scope, $http) {

    $scope.randomName = function() {
        $http.get("character/generate").then(function success(response) {
            $scope.character = response.data;
        });
    }

});