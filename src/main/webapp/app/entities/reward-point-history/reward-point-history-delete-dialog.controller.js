(function() {
    'use strict';

    angular
        .module('coupllectorApp')
        .controller('RewardPointHistoryDeleteController',RewardPointHistoryDeleteController);

    RewardPointHistoryDeleteController.$inject = ['$uibModalInstance', 'entity', 'RewardPointHistory'];

    function RewardPointHistoryDeleteController($uibModalInstance, entity, RewardPointHistory) {
        var vm = this;

        vm.rewardPointHistory = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            RewardPointHistory.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
