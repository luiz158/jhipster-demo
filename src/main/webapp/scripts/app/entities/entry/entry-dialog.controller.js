'use strict';

angular.module('blogApp').controller('EntryDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Entry', 'Blog', 'Tag',
        function($scope, $stateParams, $uibModalInstance, entity, Entry, Blog, Tag) {

        if (!entity.date) {
            entity.date = new Date();
        }
        $scope.entry = entity;
        $scope.blogs = Blog.query();
        $scope.tags = Tag.query();
        $scope.load = function(id) {
            Entry.get({id : id}, function(result) {
                $scope.entry = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('blogApp:entryUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.entry.id != null) {
                Entry.update($scope.entry, onSaveSuccess, onSaveError);
            } else {
                Entry.save($scope.entry, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
