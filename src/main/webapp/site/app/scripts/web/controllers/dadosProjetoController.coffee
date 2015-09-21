angular.module('emailOverviewWebApp.controllers')
.controller 'DadosProjetoController', ['$scope', '$location', '$timeout', '$http', 'urlConfig', 'dadosProjetoResolve', '$state', '$stateParams' , 'DadosProjetoService', 'Feedback',
($scope, $location, $timeout, $http, urlConfig, dadosProjetoResolve, $state, $stateParams, DadosProjetoService, Feedback)->
  
  $scope.dadosProjetoResolve = dadosProjetoResolve?.data
  $scope.btnDisabled = false


  ]

