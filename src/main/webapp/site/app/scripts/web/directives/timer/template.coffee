angular.module 'timerModule.template', []

.run ($templateCache)->
	$templateCache.put "templateTimer/timer.html",
		"""
		<div>
			<span ng-bind="startingTime | filterTime:format"></span>
		</div>
		"""
