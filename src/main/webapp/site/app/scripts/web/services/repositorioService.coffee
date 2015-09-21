angular.module('emailOverviewWebApp.services')
.service('RepositorioService', [
  '$rootScope', '$http', 'httpUtils','urlConfig'
  ($rootScope, $http, httpUtils, urlConfig) ->

      user = {}

      findAll: (params)->
        $http
          url: "#{urlConfig.baseUrl}/repositorio",
          method: "GET"

      getClientes: (id)->
        $http
          url: "#{urlConfig.baseUrl}/repositorio/#{id}/clientes",
          method: "GET"

      getTime: (id)->
        $http
          url: "#{urlConfig.baseUrl}/repositorio/#{id}/integrantes",
          method: "GET"

      getPremio: (id) ->
        $http
          url: "#{urlConfig.baseUrl}/repositorio/#{id}/premios",
          method: "GET"

      updateRepositories: ->
        $http
          url: "#{urlConfig.baseUrl}/repositorio/update",
          method: "GET"

      saveClientes: (id, clientes) ->
        $http
          url: "#{urlConfig.baseUrl}/repositorio/#{id}/clientes",
          method: "PUT"
          data:
              clientes: clientes
          headers:
            "Content-Type": "application/json"

      saveIntegrantes: (id, integrantes) ->
        $http
          url: "#{urlConfig.baseUrl}/repositorio/#{id}/integrantes",
          method: "PUT"
          data:
            time: integrantes
          headers:
            "Content-Type": "application/json"

      savePremio: (id, premios) ->
        $http
          url: "#{urlConfig.baseUrl}/repositorio/#{id}/premios",
          method: "PUT"
          data:
            premios: premios
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
