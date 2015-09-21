angular.module("emailOverviewWebApp.services", ['ngResource']).

service('httpUtils', ['$q', ($q)->
	###
     * Cria uma nova promise para o objeto http enviado.
     * Esse método encapa uma requisição http e cobre alguns casos de uso onde
     * a requisição é resolvida com erro.
     *
     * @param http O objeto http que será encapado com a promise.
  ###
	return {
		makePromise: (http)->
			deferred = $q.defer();

			### Caso o request seja bem concluído com sucesso apenas resolvemos ###
			### o deferred object utilizando a resposta enviada pelo server-side ###
			http.success((data, status)->
				deferred.resolve(data)
				return
			)


			### Caso o $http for executado e retornar um erro. ###
			http.error((error, status, xhr)->
				### Rejeita o deferred object ###
				deferred.reject.apply(this, arguments)
				return
			)

			deferred.promise
	}
])
