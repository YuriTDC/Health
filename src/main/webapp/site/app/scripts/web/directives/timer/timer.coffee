angular.module "timerModule", [
	"timerModule.template"
]

.directive "timer", [
	'$interval', '$timeout'
	($interval, $timeout)->
		restrict: 'EA'
		scope:
			counterTime: '=counter'
			timerFormat: '@'
			timerCallback: '='
			object: '=timerObject'
			template: '@'
			index: '='
		template: '<div class="timer" ng-include="template"></div>'
		link: (scope, element, attrs) ->

			counter = undefined
			started = false

			attrs.$observe 'template', (value)->
				if !scope.template
					scope.template = 'templateTimer/timer.html'

			attrs.$observe 'timerFormat', ->
				if !scope.timerFormat
					scope.timerFormat = 'm:ss'

			cancelInterval = ->
				scope.timerCallback scope.startingTime.asSeconds(), scope.object, scope.index if scope.timerCallback
				$interval.cancel counter
				started = false
				return

			intervalFn = ->
				scope.startingTime.subtract 1, 'second'
				$timeout ->
					scope.timerCallback scope.startingTime.asSeconds(), scope.object, scope.index if scope.timerCallback
				if scope.startingTime.asSeconds() <= 0 and started is true
					do cancelInterval


			startCounting = ()->
				started = true
				scope.startingTime = moment.duration scope.counterTime.time, scope.counterTime.type
				if scope.startingTime
					counter = $interval intervalFn, 1000
				console.log 'STARTED TIMER WITH: ', scope.startingTime

			scope.$watch 'counterTime', ->
				if arguments[0] and started is false
					do startCounting

			element.on '$destroy', ->
				do cancelInterval
				do scope.$destroy
				return

]


.filter 'filterTime', [
	() ->
		setFormat = (duration, format)->
			if duration
				moment.duration(duration, 'minutes').format(format)
			else
				""

		(input, format)->
			setFormat input, format

]

