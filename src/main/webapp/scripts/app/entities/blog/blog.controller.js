'use strict';

angular.module('blogApp')
    .controller('BlogController', function ($scope, $state, Blog) {

        $scope.blogs = [];
        $scope.loadAll = function() {
            Blog.query(function(result) {
               $scope.blogs = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.blog = {
                name: null,
                handle: null,
                id: null
            };
        };
    });
