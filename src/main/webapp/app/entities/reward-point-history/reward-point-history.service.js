(function() {
    'use strict';
    angular
        .module('coupllectorApp')
        .factory('RewardPointHistory', RewardPointHistory);

    RewardPointHistory.$inject = ['$resource', 'DateUtils'];

    function RewardPointHistory ($resource, DateUtils) {
        var resourceUrl =  'api/reward-point-histories/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.changedDate = DateUtils.convertDateTimeFromServer(data.changedDate);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
