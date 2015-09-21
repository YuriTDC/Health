angular.module('sbarNavigation',['template-sbarNavigation'])
.controller 'SbarNavigationController',['$scope','$state',($scope, $state)->
	@mover = (origem, destino, tipo)->
		if tipo is 'acomodacao'
			guardarPocisao = $scope.items[0].acomodacoes[destino]
			$scope.items[0].acomodacoes[destino] = $scope.items[0].acomodacoes[origem]
			$scope.items[0].acomodacoes[origem] = guardarPocisao
		else
			guardarPocisao = $scope.items[0].periodos[destino]
			$scope.items[0].periodos[destino] = $scope.items[0].periodos[origem]
			$scope.items[0].periodos[origem] = guardarPocisao

	@remover = (index, tipo)->
		if tipo is 'acomodacao'
			$scope.$emit "removeAcomodacao", $scope.items[0].acomodacoes[index].id
			$scope.items[0].acomodacoes.splice(index, 1)
		else
			$scope.$emit "removiDaSidebar", $scope.items[0].periodos[index].id
			$scope.$emit "removePeriodo", $scope.items[0].periodos[index].id
			$scope.items[0].periodos.splice(index, 1)

	@irPara = (caminho)->
		$state.transitionTo(caminho)

	return
]

.directive 'sbarNavigation',[
		()->
			{
				restrict: "EA"
				controller: "SbarNavigationController"
				scope:
					items: '='
					collapsed: '='
					btn: '='
				template: "<div ng-include=\"templateFile\"></div>"
				link:(scope, elem, attr, controller)->

					attr.$observe 'templateUrl', (tpl)->
						if tpl
							return scope.templateFile = tpl
						scope.templateFile = 'sidebar-navigation/template/pai.html'

					scope.descer = (index, tipo)->
						controller.mover(index, index + 1, tipo)
					scope.subir = (index, tipo)->
						controller.mover(index, index - 1, tipo)
					scope.remover = (index, tipo)->
						controller.remover(index, tipo)
					scope.redirecionar = ()->
						controller.irPara(attr.caminho)
			}
]

.filter("somenteData",[
	()->
		(value)->
			moment(value, 'DD/MM/YYYY').format('DD/MM/YYYY')
])
