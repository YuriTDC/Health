angular.module('emailOverviewWebApp.controllers')
.controller 'ClienteController',['$scope', '$location', '$timeout', '$http', 'urlConfig', 'clientes', '$state', 'ClienteService', 'Feedback', '$modal','SweetAlert',
($scope, $location, $timeout, $http, urlConfig, clientes, $state, ClienteService, Feedback, $modal, SweetAlert)->

  $scope.clientes = clientes
  $scope.btnDisabled = false

  $scope.alterarCliente = (cliente) ->
    $state.go('web.cliente.editar', {'id': cliente.id})

  $scope.novoCliente = ->
    $state.go('web.cliente.criar')

  $scope.excluirCliente = (cliente)->
    SweetAlert.swal {
      title: 'Você tem certeza?'
      text: 'Não será possível desfazer essa operação!!'
      type: 'warning'
      showCancelButton: true
      closeOnConfirm: true
    },
    (isConfirmed)->
      if isConfirmed
      	ClienteService.excluir(cliente.id)
	        .success (data)->
	          SweetAlert.swal({title:"Item exluído!",text:"Operação realizada com sucesso!",type:"success"})
	          $state.reload()
	        .error (error)->
	          SweetAlert.swal({title:"OPS!",text:error.message,type:"error"})
  ]
