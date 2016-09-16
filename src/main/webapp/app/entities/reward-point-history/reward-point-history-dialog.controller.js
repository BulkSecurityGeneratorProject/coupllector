(function() {
    'use strict';

    angular
        .module('coupllectorApp')
        .controller('RewardPointHistoryDialogController', RewardPointHistoryDialogController);

    RewardPointHistoryDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'RewardPointHistory', 'User'];

    function RewardPointHistoryDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, RewardPointHistory, User) {
        var vm = this;

        vm.rewardPointHistory = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.users = User.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.rewardPointHistory.id !== null) {
                RewardPointHistory.update(vm.rewardPointHistory, onSaveSuccess, onSaveError);
            } else {
                RewardPointHistory.save(vm.rewardPointHistory, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('coupllectorApp:rewardPointHistoryUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.changedDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
