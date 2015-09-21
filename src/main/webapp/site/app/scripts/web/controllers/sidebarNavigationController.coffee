angular.module("emailOverviewWebApp.controllers")

.controller "SidebarNavigationController",['$scope', '$state','InscricaoService','Feedback','WizardService','HospedesService', 'AcomodacaoService', 'HospedagemService'
($scope, $state, InscricaoService, Feedback, WizardService, HospedesService, AcomodacaoService, HospedagemService)->

	do HospedagemService.clearData

	setWizardData = (data)->
		$scope.wizardData = data
		WizardService.setResumo data
		$state.go "web.hospedagemWizard.finalizado"


	# CALLBACK PARA ATUALIZAR PERÍODOS DA SIDEBAR
	updatePeriodosSidebar = ()->
		$scope.menu[0].periodos = HospedagemService.getPasso1()
		items = _.pluck $scope.menu[0].periodos, 'id'
		WizardService.validarPasso(1, items).then(
			(data) ->
				console.log 'Passo validado com sucesso'
			(error) ->
				Feedback.fail(error)
		)
		return

	HospedagemService.setPeriodosFn updatePeriodosSidebar


	# CALLBACK PARA ATUALIZAR HOSPEDES NA SIDEBAR
	updateHospedesSidebar = ()->
		$scope.menu[0].hospedes = HospedagemService.getPasso2().hospedes.length
		items = _.pluck $scope.menu[0].periodos, 'id'

		WizardService.validarPasso2(1,items, HospedagemService.getPasso2().hospedes, HospedagemService.getPasso2().cadeirantes).then(
			(data) ->
				console.log 'Passo validado com sucesso'
			(error) ->
				Feedback.fail(error)
		)

	HospedagemService.setHospedesFn updateHospedesSidebar


	# CALLBACK PARA ATUALIZAR ACOMODAÇÕES DA SIDEBAR
	updateAcomodacoesSidebar = ()->
		$scope.menu[0].acomodacoes = HospedagemService.getPasso3()
		items = _.pluck $scope.menu[0].periodos, 'id'
		convidadosInclusos = []
		if HospedagemService.getPasso2().hospedes
			for value, key in HospedagemService.getPasso2().hospedes
				if value.incluso
					convidadosInclusos.push _.omit value, 'datepickerOpen','incluso'
		WizardService.validarPasso3(1,items, convidadosInclusos, HospedagemService.getPasso2().cadeirantes, $scope.menu[0].acomodacoes).then (data)->
			console.log data
		(error)->
			console.log error

	HospedagemService.setAcomodacoesFn updateAcomodacoesSidebar

	$scope.menu = [
		{
			titulo: {
				nome:"NOVA HOSPEDAGEM"
				url:"#/hospedagem"
			}
			periodos: HospedagemService.getPasso1(),
			acomodacoes: HospedagemService.getPasso3(),
			hospedes: null,
		}
		{
			titulo: {
				nome: "MINHAS RESERVAS"
				url:"#/minhas-reservas"
			}

		}
		{
			titulo: {
				nome: "RASCUNHO"
				url:"#/rascunho"
			}
		}
	]

	$scope.$on "zerarAcomodacoes", (evt, item)->
		$scope.menu[0].acomodacoes = []

	$scope.$on "adicionarPeriodo", (evt, item)->
		# $scope.menu[0].periodos.push _.omit(item, 'visible')
		$scope.menu[0].periodos.push item

	$scope.$watch "menu[0].periodos.length",(data)->
		$scope.$emit "qtdItens", data

	$scope.$watch "menu[0].periodos",(data)->
		$scope.$emit "estadoSidebar", data
	,true

	$scope.$on "salvarP2", (evt, value)->
		items = _.pluck $scope.menu[0].periodos, 'id'
		WizardService.salvarPasso2(1, items, HospedagemService.getPasso2().hospedes, HospedagemService.getPasso2().cadeirantes).then(
			(data) ->
				Feedback.success "Hóspede(s) salvo com sucesso."
			(error) ->
				Feedback.fail(error)
		)

	$scope.$on "salvarAcomodacoes", (evt, value)->
		items = _.pluck $scope.menu[0].periodos, 'id'
		convidadosInclusos = []
		for value in HospedagemService.getPasso2().hospedes
			if value.incluso
				convidadosInclusos.push _.omit value, 'datepickerOpen','incluso'
		WizardService.salvarPasso3(1,items, convidadosInclusos, HospedagemService.getPasso2().cadeirantes, HospedagemService.getPasso3()).then(
			(data) ->
				console.log data
			(error) ->
				console.log error
		)

	$scope.$on "finaliza", (evt, value)->
		items = _.pluck $scope.menu[0].periodos, 'id'
		convidadosInclusos = []
		for value in HospedagemService.getPasso2().hospedes
			if value.incluso
				convidadosInclusos.push _.omit value, 'datepickerOpen','incluso'


		WizardService.finalizar(4, HospedagemService.getPasso1(), HospedagemService.getPasso2().hospedes, HospedagemService.getPasso2().cadeirantes, $HospedagemService.getPasso3().acomodacoes, value).then(
			(data) ->
				console.log data
				setWizardData {
					id: data.id
					usuarioId: data.usuarioId
					status: data.status
					periodos: data.periodos
					hospedes: data.hospedes
					cadeirantes: data.cadeirantes
					tipoAcomodacao: data.tipoAcomodacao
					dadosPessoais: value

				}
			(error) ->
				console.log error
		)


	$scope.$on "incluirAcomodacao", -> $scope.menu[0].acomodacoes = HospedagemService.getPasso3()

]
