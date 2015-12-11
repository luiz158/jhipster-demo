'use strict';

angular.module('blogApp')
	.controller('TagDeleteController', function($scope, $uibModalInstance, entity, Tag) {

        $scope.tag = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Tag.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
