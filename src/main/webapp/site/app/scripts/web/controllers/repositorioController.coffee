angular.module('emailOverviewWebApp.controllers' )
.controller 'RepositorioController',['$scope', '$location', '$timeout', '$http', 'urlConfig', 'repositorios', '$state', 'RepositorioService', 'Feedback',
($scope, $location, $timeout, $http, urlConfig, repositorios, $state, RepositorioService, Feedback)->

  $scope.repositorios = repositorios
  $scope.btnDisabled = false

  $scope.filtro = [
    { nome: 'Todos os projetos' }
    { nome: 'Somente com notificação', param: {notificar:true, toggle:false} }
    { nome: 'Somente sem notificação', param: {notificar:false, toggle:false} }
  ]

  $scope.sort = (keyname) ->
    $scope.sortKey = keyname
    $scope.reverse = !$scope.reverse

  $scope.setNotificacao = (repo) ->
    RepositorioService.setNotificacao(repo).then( (result) ->
      console.log 'Status de notificação atualizado com sucesso.'
      (error) ->
        Feedback.fail 'Falha ao atualizar status de notificação do repositório.'
    )

  $scope.enviarEmails = ->
    $scope.btnDisabled = true
    RepositorioService.enviarEmail().then(
      (result) ->
        $scope.btnDisabled = false
        Feedback.success 'E-mails enviados com sucesso.'
      (error) ->
        $scope.btnDisabled = false
        Feedback.success "Falha ao enviar e-mails. #{error.message.data}"
    )

  $scope.atualizarRepositorios = ->
    $scope.btnDisabled = true
    RepositorioService.updateRepositories().then( (result) ->
      Feedback.success 'Repositórios atualizados com sucesso.'
      $scope.repositorios = result.data
      $scope.btnDisabled = false
    (error) ->
      Feedback.fail 'Falha ao atualizar os repositórios.'
      $scope.btnDisabled = false
    )

]
