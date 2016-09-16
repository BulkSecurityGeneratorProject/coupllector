(function() {
    'use strict';

    angular
        .module('coupllectorApp')
        .controller('RewardPointHistoryDetailController', RewardPointHistoryDetailController);

    RewardPointHistoryDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'RewardPointHistory', 'User'];

    function RewardPointHistoryDetailController($scope, $rootScope, $stateParams, previousState, entity, RewardPointHistory, User) {
        var vm = this;

        vm.rewardPointHistory = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('coupllectorApp:rewardPointHistoryUpdate', function(event, result) {
            vm.rewardPointHistory = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
