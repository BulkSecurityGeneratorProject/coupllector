(function() {
    'use strict';

    angular
        .module('coupllectorApp')
        .controller('UserProfileDetailController', UserProfileDetailController);

    UserProfileDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'UserProfile', 'User', 'Address'];

    function UserProfileDetailController($scope, $rootScope, $stateParams, previousState, entity, UserProfile, User, Address) {
        var vm = this;

        vm.userProfile = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('coupllectorApp:userProfileUpdate', function(event, result) {
            vm.userProfile = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
