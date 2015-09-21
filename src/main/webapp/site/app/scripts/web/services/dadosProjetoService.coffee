angular.module('emailOverviewWebApp.services')
.service('DadosProjetoService', [
  '$rootScope', '$http', 'httpUtils','urlConfig'
  ($rootScope, $http, httpUtils, urlConfig) ->

      user = {}

      findByRepositorio: (repositorioId) ->
        $http
          url: "#{urlConfig.baseUrl}/dadosProjeto/repositorio/#{repositorioId}",
          method: "GET"

      create: (id, dadosProjeto)->
        $http
          url: "#{urlConfig.baseUrl}/dadosProjeto/#{id}",
          method: "POST"
          data:
            dadosProjeto
          headers:
            "Content-Type": "application/json"

      update: (id, dadosProjeto)->
        $http
          url: "#{urlConfig.baseUrl}/dadosProjeto/#{id}",
          method: "PUT"
          data:
            dadosProjeto
          headers:
            "Content-Type": "application/json"

  ])