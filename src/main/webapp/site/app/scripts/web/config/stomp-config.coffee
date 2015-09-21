setStompClass = ->

	if Stomp
		Stomp.WebSocketClass = SockJS if SockJS


do setStompClass
