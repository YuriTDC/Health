angular.module('emailOverviewWebApp.controllers')
.controller 'TabsController',['$scope', '$location', '$filter', '$stateParams', '$state',
($scope, $location, $filter, $stateParams, $state)->

  $scope.projetoId = $stateParams.id

]
