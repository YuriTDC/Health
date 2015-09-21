angular.module('emailOverviewWebApp.services')
.service('FaseProjetoService', [
  '$rootScope', '$http', 'httpUtils','urlConfig'
  ($rootScope, $http, httpUtils, urlConfig) ->

      user = {}

      findAll: ->
        $http
          url: "#{urlConfig.baseUrl}/faseProjeto",
          method: "GET"

      findByRepositorio: (repositorioId) ->
        $http
          url: "#{urlConfig.baseUrl}/faseProjeto/repositorio/#{repositorioId}",
          method: "GET"

      recuperar: (id)->
        $http
          url: "#{urlConfig.baseUrl}/faseProjeto/#{id}",
          method: "GET"

      excluir: (id)->
        $http
          url: "#{urlConfig.baseUrl}/faseProjeto/#{id}",
          method: "DELETE"

      editar: (id, faseProjeto)->
        $http
          url: "#{urlConfig.baseUrl}/faseProjeto/#{id}",
          method: "PUT"
          data:
            faseProjeto
          headers:
            "Content-Type": "application/json"

      create: (faseProjeto)->
        $http
          url: "#{urlConfig.baseUrl}/faseProjeto",
          method: "POST"
          data:
            faseProjeto
          headers:
            "Content-Type": "application/json"
  ])