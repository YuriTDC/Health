angular.module('emailOverviewWebApp.controllers')
.controller 'CriarIntegranteController',['$scope', '$location', '$timeout', '$http', 'urlConfig', '$state', 'IntegranteService', 'Feedback', 'UploadService',
($scope, $location, $timeout, $http, urlConfig, $state, IntegranteService, Feedback, UploadService)->

  $scope.integrante = {
    nome: '',
    email: '',
    avatar: ''
  }

  $scope.btnDisabled = false
  $scope.tipoForm = 'NOVO INTEGRANTE'

  $scope.save = (integrante) ->
    $scope.btnDisabled = true
    IntegranteService.create($scope.integrante).then (result) ->
      $scope.btnDisabled = false
      $state.go 'web.integrante.listar'
      Feedback.success 'Integrante salvo com sucesso.'
    (error) ->
      Feedback.fail 'Falha ao salvar integrante.'
      $scope.btnDisabled = false

  $scope.upload = (inputFile) ->
    file = inputFile[0].files[0]
    $scope.btnDisabled = true
    $scope.disableUploadButton = true
    #     method do clear input file
    clearInput = ->
      input = $(inputFile[0])
      input.replaceWith ( input = input.clone(true) ) #TODO: find another way to clear input file

    uploadCallback = (result)->
      $scope.integrante.avatar = result.path
      $scope.filename = result.file_name
      $scope.disableUploadButton = false
      $scope.btnDisabled = false
      #$scope.fileUrl = "#{urlConfig.baseUrl}arquivo/#{result.fileName}"
      return

    errorCallback = ->
      $scope.disableUploadButton = false
      $scope.btnDisabled = false
      do clearInput

    progress = (evt)->
      $timeout ->
        if evt.lengthComputable
          $scope.progress = Math.round(evt.loaded * 100 / evt.total)
          $scope.progressActive = if $scope.progress < 100 or $scope.progress is 0 then true else false
          $scope.progress
        return

    if file
      fd = new FormData()
      fd.append "file", file
      UploadService.send(fd, progress).then uploadCallback, errorCallback
    return

]
