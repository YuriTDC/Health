angular.module "emailOverviewWebApp.services"


.service "UploadService", [
	'$http', '$q', '$timeout', 'urlConfig'
	($http, $q, $timeout, urlConfig)->

		send: (formData, progress)->
			url = "#{urlConfig.baseUrl}/upload"

			deferred = $q.defer()
			xhr = new XMLHttpRequest()
			if progress
				xhr.upload.addEventListener "progress", progress, true

			xhr.addEventListener "load", (
				(evt) ->
					if evt.target.status is 200
						$timeout ->
							deferred.resolve JSON.parse(evt.target.response)
							return
					else
						$timeout ->
							deferred.reject evt
							return
					return
			), false

			xhr.addEventListener "error", (
				(evt)->
					$timeout ->
						deferred.reject evt
						return
					return
			), false
			xhr.open "POST", url, true
			xhr.send formData
			deferred.promise

		getFile: (filename)->
			$http.get "#{baseUrl}arquivo/#{filename}#{NoCache()}"
]
