(function() {
    'use strict';

    angular
        .module('coupllectorApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('reward-point-history', {
            parent: 'entity',
            url: '/reward-point-history?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'coupllectorApp.rewardPointHistory.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/reward-point-history/reward-point-histories.html',
                    controller: 'RewardPointHistoryController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('rewardPointHistory');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('reward-point-history-detail', {
            parent: 'entity',
            url: '/reward-point-history/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'coupllectorApp.rewardPointHistory.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/reward-point-history/reward-point-history-detail.html',
                    controller: 'RewardPointHistoryDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('rewardPointHistory');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'RewardPointHistory', function($stateParams, RewardPointHistory) {
                    return RewardPointHistory.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'reward-point-history',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('reward-point-history-detail.edit', {
            parent: 'reward-point-history-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/reward-point-history/reward-point-history-dialog.html',
                    controller: 'RewardPointHistoryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['RewardPointHistory', function(RewardPointHistory) {
                            return RewardPointHistory.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('reward-point-history.new', {
            parent: 'reward-point-history',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/reward-point-history/reward-point-history-dialog.html',
                    controller: 'RewardPointHistoryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                changedDate: null,
                                changedValue: null,
                                balance: null,
                                transaction: null,
                                comment: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('reward-point-history', null, { reload: 'reward-point-history' });
                }, function() {
                    $state.go('reward-point-history');
                });
            }]
        })
        .state('reward-point-history.edit', {
            parent: 'reward-point-history',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/reward-point-history/reward-point-history-dialog.html',
                    controller: 'RewardPointHistoryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['RewardPointHistory', function(RewardPointHistory) {
                            return RewardPointHistory.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('reward-point-history', null, { reload: 'reward-point-history' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('reward-point-history.delete', {
            parent: 'reward-point-history',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/reward-point-history/reward-point-history-delete-dialog.html',
                    controller: 'RewardPointHistoryDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['RewardPointHistory', function(RewardPointHistory) {
                            return RewardPointHistory.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('reward-point-history', null, { reload: 'reward-point-history' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
