angular.module('emailOverviewWebApp.services')
.service('ClienteService', [
  '$rootScope', '$http', 'httpUtils','urlConfig'
  ($rootScope, $http, httpUtils, urlConfig) ->

      user = {}

      findAll: ->
        $http
          url: "#{urlConfig.baseUrl}/cliente",
          method: "GET"

      findOthers: (id) ->
        $http
          url: "#{urlConfig.baseUrl}/cliente/others/#{id}",
          method: "GET"

      recuperar: (id)->
        $http
          url: "#{urlConfig.baseUrl}/cliente/#{id}",
          method: "GET"

      excluir: (id)->
        $http
          url: "#{urlConfig.baseUrl}/cliente/#{id}",
          method: "DELETE"

      editar: (id, cliente)->
        $http
          url: "#{urlConfig.baseUrl}/cliente/#{id}",
          method: "PUT"
          data:
            cliente
          headers:
            "Content-Type": "application/json"

      create: (cliente)->
        $http
          url: "#{urlConfig.baseUrl}/cliente",
          method: "POST"
          data:
            cliente
          headers:
            "Content-Type": "application/json"

  ])
