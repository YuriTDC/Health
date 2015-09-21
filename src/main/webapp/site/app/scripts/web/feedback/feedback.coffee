angular.module("emailOverviewWebApp.feedback", []).
# Servico de feedback utilizado para exibir diversos tipos de alertas
# este servico é uma interface para o plugin noty do jquery
service('Feedback', [
	()->
		feedback_theme = "twitter_bootstrap"
		defaults = {
			timeout: 2500,
			closeWith: ['click']
			theme: feedback_theme
			layout: "topCenter"
			dismissQueue: true
		}

		return {
			show: (options)->
				noty(_.defaults(options, defaults))

			confirm: (question, yFn, nFn)->
				closeFn = (wrapFn, $noty)->
					$noty.close()
					if wrapFn
						wrapFn()

				@show({
					text: question
					type: "alert"
					modal: true
					buttons: [{
						addClass: 'btn btn-success'
						text: 'Sim, tenho certeza'
						onClick: _.wrap(yFn, closeFn)
					},
					{
						addClass: 'btn'
						text: 'Não'
						onClick: _.wrap(nFn, closeFn)
					}]
				})

			download: (question, noFn)->
				closeFn = (wrapFn, $noty)->
					$noty.close()
					if wrapFn
						wrapFn()

				@show({
					text: question
					type: "alert"
					modal: true
					buttons: [{
						addClass: 'btn'
						text: 'Fechar'
						onClick: _.wrap(noFn, closeFn)
					}]
				})

			alert: (message)->
				@show({
					type: 'alert'
					text: message
				})

			success: (message)->
				@show({
					type: 'success'
					text: message
				})

			fail: (message)->
				@show({
					type: 'error'
					text: message
				})
		}

])
