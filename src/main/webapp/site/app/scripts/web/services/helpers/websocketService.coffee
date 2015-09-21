angular.module "emailOverviewWebApp.services"

.factory "AngularWebSocket", [
	()->

		client = null


		WSObj = (url)->
			if url
				@url = url
				ws = new SockJS url
				return Stomp.over ws
			throw "URL not defined"
			undefined


#      TODO: testar a implementação da factory como um objeto WebSocket
		###WSObj::_wsdisconnect = ->
			do @disconnect
			return

		WSObj::_wsconnect = (user, pass, callbackFn)->
			@connnect user, pass, callbackFn if @client
			return

		WSObj::_wscallback = (message)->
			message.body = JSON.parse message.body if message.body
			@subscribeCallback message if @subscribeCallback
			return

		WSObj::_wssubscribe = (url, callback)->
			@.subscribeCallback = callback if callback
			if @connected then @subscribe url, WSObj::_wscallback
			return



###
		WSObj

]
