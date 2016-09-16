(function() {
    'use strict';

    angular
        .module('coupllectorApp')
        .controller('UserProfileDialogController', UserProfileDialogController);

    UserProfileDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'UserProfile', 'User', 'Address'];

    function UserProfileDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, UserProfile, User, Address) {
        var vm = this;

        vm.userProfile = entity;
        vm.clear = clear;
        vm.save = save;
        vm.users = User.query();
        vm.streetaddresses = Address.query({filter: 'userprofile-is-null'});
        $q.all([vm.userProfile.$promise, vm.streetaddresses.$promise]).then(function() {
            if (!vm.userProfile.streetAddressId) {
                return $q.reject();
            }
            return Address.get({id : vm.userProfile.streetAddressId}).$promise;
        }).then(function(streetAddress) {
            vm.streetaddresses.push(streetAddress);
        });
        vm.postaladdresses = Address.query({filter: 'userprofile-is-null'});
        $q.all([vm.userProfile.$promise, vm.postaladdresses.$promise]).then(function() {
            if (!vm.userProfile.postalAddressId) {
                return $q.reject();
            }
            return Address.get({id : vm.userProfile.postalAddressId}).$promise;
        }).then(function(postalAddress) {
            vm.postaladdresses.push(postalAddress);
        });

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.userProfile.id !== null) {
                UserProfile.update(vm.userProfile, onSaveSuccess, onSaveError);
            } else {
                UserProfile.save(vm.userProfile, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('coupllectorApp:userProfileUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
