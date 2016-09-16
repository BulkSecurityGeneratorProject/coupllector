'use strict';

describe('Controller Tests', function() {

    describe('RewardPointHistory Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockRewardPointHistory, MockUser;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockRewardPointHistory = jasmine.createSpy('MockRewardPointHistory');
            MockUser = jasmine.createSpy('MockUser');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'RewardPointHistory': MockRewardPointHistory,
                'User': MockUser
            };
            createController = function() {
                $injector.get('$controller')("RewardPointHistoryDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'coupllectorApp:rewardPointHistoryUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
