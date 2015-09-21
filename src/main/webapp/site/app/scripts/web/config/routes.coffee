angular.module "emailOverviewWebApp"

.config([
    '$provide', '$urlRouterProvider', '$stateProvider', '$httpProvider'
    ($provide, $urlRouterProvider, $stateProvider, $httpProvider)->

      #config http
      $httpProvider.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded'

      $urlRouterProvider.otherwise('/login');
      $stateProvider
      .state('web', {
          url: ''
          abstract: true
          views:
            "":
              templateUrl: 'views/web/web.html'
              controller: 'WebController'
            "header@web":
              templateUrl: 'views/web/header.html'
              controller:  'HeaderController'
            "footer@web":
              templateUrl: 'views/web/footer.html'
        })

      .state('web.login', {
          url: '/login'
          views:
            "":
              templateUrl: 'views/web/login.html'
              controller:'LoginController'
            "header@web":
              templateUrl: 'views/web/header.html'
              controller:  'HeaderController'

        })

      .state('web.home', {
          url: '/home'
          restrict: true
          views:
            "":
              templateUrl: 'views/web/home.html'
              controller:'HomeController'
            "header@web":
              templateUrl: 'views/web/header.html'
              controller:  'HeaderController'
        })

      .state('web.projeto', {
          url: '/projeto'
          restrict: true
          views:
            "":
              templateUrl: 'views/web/projeto/projeto.html'
            "header@web":
              templateUrl: 'views/web/header.html'
              controller:  'HeaderController'
        }) 
      
      .state('web.projeto.listar', {
          url: '/listar'
          restrict: true
          views:
            "":
              templateUrl: 'views/web/projeto/listar.html'
              controller:'RepositorioController'
              resolve:
                repositorios: [ 'RepositorioService', (RepositorioService) ->
                    RepositorioService.findAll().then (result) ->
                      result.data
                  ]
            "header@web":
              templateUrl: 'views/web/header.html'
              controller:  'HeaderController'
        }) 

      .state('web.projeto.gerenciar', {
          url: '/gerenciar/:id'
          views:
            "":
              templateUrl: 'views/web/projeto/gerenciar.html'
              controller:   'TabsController'
            "header@web":
              templateUrl: 'views/web/header.html'
              controller:  'HeaderController'
        })

      .state('web.projeto.gerenciar.colaboradores', {
          url: '/colaboradores'
          restrict: true
          views:
            "":
              templateUrl: 'views/web/projeto/colaboradores.html'
              controller:'RepositorioClientesController'
              resolve:
                clientes: [ 'RepositorioService', '$stateParams', (RepositorioService, $stateParams) ->
                  RepositorioService.getClientes($stateParams.id).then (result) ->
                    result.data
                ],
                listaClientes: [ 'ClienteService', '$stateParams', (ClienteService, $stateParams) ->
                  ClienteService.findOthers($stateParams.id).then (result) ->
                    result.data
                ]
                time: [ 'RepositorioService', '$stateParams', (RepositorioService, $stateParams) ->
                  RepositorioService.getTime($stateParams.id).then (result) ->
                    result.data
                ],
                integrantes: [ 'IntegranteService', '$stateParams', (IntegranteService, $stateParams) ->
                  IntegranteService.findOthers($stateParams.id).then (result) ->
                    result.data
                ]                
            "header@web":
              templateUrl: 'views/web/header.html'
              controller:  'HeaderController'
        })

      .state('web.projeto.gerenciar.dados', {
          url: '/dados'
          restrict: true
          views:
            "":
              templateUrl: 'views/web/dadosProjeto/form.html'
              controller: 'CriarDadosProjetoController'
              resolve: 
                dados: [ 'DadosProjetoService', '$stateParams', (DadosProjetoService, $stateParams) ->
                  return DadosProjetoService.findByRepositorio($stateParams.id).then (result) ->
                    result.data[0]
                ],
                resolveRepositorios: ['RepositorioService', (RepositorioService) ->
                  return RepositorioService.findAll()
                ]
            "header@web":
              templateUrl: 'views/web/header.html'
              controller:  'HeaderController'
        })

      .state('web.projeto.gerenciar.fases', {
          url: '/fases'
          restrict: true
          views:
            "":
              templateUrl: 'views/web/faseProjeto/fases.html'
        })

      .state('web.projeto.gerenciar.fases.listar', {
          url: '/listar'
          restrict: true
          views:
            "":
              templateUrl: 'views/web/faseProjeto/fases-listar.html'
              controller: 'FaseProjetoController'
              resolve:
                faseProjetoResolve: ['FaseProjetoService', '$stateParams', (FaseProjetoService, $stateParams) ->
                  return FaseProjetoService.findByRepositorio($stateParams.id)
                ]
            "header@web":
              templateUrl: 'views/web/header.html'
              controller:  'HeaderController'
        })

      .state('web.projeto.gerenciar.fases.criar', {
          url: '/create'
          restrict: true
          views:
            "":
              templateUrl: 'views/web/faseProjeto/form.html'
              controller: 'CriarFaseProjetoController'
            "header@web":
              templateUrl: 'views/web/header.html'
              controller:  'HeaderController'

        })

      .state('web.projeto.gerenciar.fases.editar', {
          url: '/edit/:faseId'
          restrict: true
          views:
            "":
              templateUrl: 'views/web/faseProjeto/formEdit.html'
              controller:'EditarFaseProjetoController'
              resolve:
                faseProjeto: [ 'FaseProjetoService', '$stateParams', (FaseProjetoService, $stateParams) ->
                  FaseProjetoService.recuperar($stateParams.faseId).then (result) ->
                    result.data
                ],
                resolveRepositorios: ['RepositorioService', (RepositorioService) ->
                  return RepositorioService.findAll()
                ],
                entregaSprintResolve: ['EntregaSprintService', '$stateParams', (EntregaSprintService, $stateParams) ->
                  return EntregaSprintService.findByFase($stateParams.faseId)
                ]
            "header@web":
              templateUrl: 'views/web/header.html'
              controller:  'HeaderController'
        })

      .state('web.projeto.gerenciar.fases.sprint', {
          url: '/edit/:faseId/sprint'
          abstract: true
          templateUrl: 'views/web/entregaSprint/entregaSprint.html'

        })

      .state('web.projeto.gerenciar.fases.sprint.criar', {
          url: '/create'
          restrict: true
          views:
            "":
              templateUrl: 'views/web/entregaSprint/form.html'
              controller: 'CriarEntregaSprintController'
              resolve: 
                resolveTime: ['RepositorioService', '$stateParams', (RepositorioService, $stateParams)->
                  return RepositorioService.getTime($stateParams.id).then (result) ->
                    result.data
                ]
            "header@web":
              templateUrl: 'views/web/header.html'
              controller:  'HeaderController'
        })

      .state('web.projeto.gerenciar.fases.sprint.editar', {
        url: '/edit/:entregaId'
        restrict: true
        views:
          "":
            templateUrl: 'views/web/entregaSprint/form.html'
            controller: 'EditarEntregaSprintController'
            resolve: 
              entregaSprint: [ 'EntregaSprintService', '$stateParams', (EntregaSprintService, $stateParams) ->
                EntregaSprintService.recuperar($stateParams.entregaId).then (result) ->
                  result.data
                ],
              resolveTime: ['RepositorioService', '$stateParams', (RepositorioService, $stateParams)->
                  return RepositorioService.getTime($stateParams.id).then (result) ->
                    result.data
                ]
            "header@web":
              templateUrl: 'views/web/header.html'
              controller:  'HeaderController'
        })

      .state('web.integrantes', {
          url: '/projeto/:id/integrantes'
          restrict: true
          views:
            "":
              templateUrl: 'views/web/projeto/integrantes.html'
              controller:'RepositorioIntegrantesController'
              resolve:
                time: [ 'RepositorioService', '$stateParams', (RepositorioService, $stateParams) ->
                  RepositorioService.getTime($stateParams.id).then (result) ->
                    result.data
                ],
                integrantes: [ 'IntegranteService', '$stateParams', (IntegranteService, $stateParams) ->
                  IntegranteService.findOthers($stateParams.id).then (result) ->
                    result.data
                ]
            "header@web":
              templateUrl: 'views/web/header.html'
              controller:  'HeaderController'
        })

      .state('web.integrante', {
          url: '/integrante'
          restrict: true
          views:
            "":
              templateUrl: 'views/web/integrante/integrantes.html'
            "header@web":
              templateUrl: 'views/web/header.html'
              controller:  'HeaderController'
        })

      .state('web.integrante.listar', {
          url: '/integrante/list'
          restrict: true
          views:
            "":
              templateUrl: 'views/web/integrante/listar.html'
              controller:'IntegranteController'
              resolve:
                integrantes: [ 'IntegranteService', (IntegranteService) ->
                  IntegranteService.listTokens().then (result) ->
                    result.data
                ]
            "header@web":
              templateUrl: 'views/web/header.html'
              controller:  'HeaderController'
        })

      .state('web.integrante.criar', {
          url: '/integrante/create'
          restrict: true
          views:
            "":
              templateUrl: 'views/web/integrante/form.html'
              controller:'CriarIntegranteController'
            "header@web":
              templateUrl: 'views/web/header.html'
              controller:  'HeaderController'
        })

      .state('web.integrante.editar', {
          url: '/integrante/edit/:id'
          restrict: true
          views:
            "":
              templateUrl: 'views/web/integrante/form.html'
              controller:'EditarIntegranteController'
              resolve:
                integrante: [ 'IntegranteService', '$stateParams', (IntegranteService, $stateParams) ->
                  IntegranteService.recuperar($stateParams.id).then (result) ->
                    result.data
                ]
            "header@web":
              templateUrl: 'views/web/header.html'
              controller:  'HeaderController'
        })

      .state('web.cliente', {
          url: '/cliente'
          restrict: true
          abstract: true
          views:
            "":
              templateUrl: 'views/web/cliente/clientes.html'
            "header@web":
              templateUrl: 'views/web/header.html'
              controller:  'HeaderController'
        })

      .state('web.cliente.listar', {
          url: '/list'
          restrict: true
          views:
            "":
              templateUrl: 'views/web/cliente/listar.html'
              controller:'ClienteController'
              resolve:
                clientes: [ 'ClienteService', (ClienteService) ->
                  ClienteService.findAll().then (result) ->
                    result.data
                ]
            "header@web":
              templateUrl: 'views/web/header.html'
              controller:  'HeaderController'
        })

      .state('web.cliente.editar', {
          url: '/edit/:id'
          restrict: true
          views:
            "":
              templateUrl: 'views/web/cliente/form.html'
              controller:'EditarClienteController'
              resolve:
                cliente: [ 'ClienteService', '$stateParams', (ClienteService, $stateParams) ->
                  ClienteService.recuperar($stateParams.id).then (result) ->
                    result.data
                ]
            "header@web":
              templateUrl: 'views/web/header.html'
              controller:  'HeaderController'
        })

      .state('web.cliente.criar', {
          url: '/create'
          restrict: true
          views:
            "":
              templateUrl: 'views/web/cliente/form.html'
              controller:'CriarClienteController'
            "header@web":
              templateUrl: 'views/web/header.html'
              controller:  'HeaderController'
        })

      .state('web.premio', {
          url: '/premio'
          restrict: true
          abstract: true
          views: 
            "":
              templateUrl: 'views/web/premio/premio.html'
            "header@web":
              templateUrl: 'views/web/header.html'
              controller:  'HeaderController'
        })

      .state('web.premio.criar', {
          url: '/premio/create'
          restrict: true
          views:
            "":
              templateUrl: 'views/web/premio/form.html'
              controller: 'CriarPremioController'
            "header@web":
              templateUrl: 'views/web/header.html'
              controller:  'HeaderController'

          })

      .state('web.premio.listar', {
          url: '/list'
          restrict: true
          views: 
            "":
              templateUrl: 'views/web/premio/listar.html'
              controller: 'PremioController'
              resolve:
                premio: ['PremioService', (PremioService) ->
                  PremioService.findAll().then (result) ->
                    result.data
                  ]
            "header@web":
              templateUrl: 'views/web/header.html'
              controller:  'HeaderController'
        })

      .state('web.premio.editar', {
          url: '/premio/edit/:id'
          restrict: true
          views: 
            "":
              templateUrl: 'views/web/premio/form.html'
              controller: 'EditarPremioController'
              resolve:            
                  premio: [ 'PremioService', '$stateParams', (PremioService, $stateParams) ->
                    PremioService.recuperar($stateParams.id).then (result) ->
                      result.data
                  ]
            "header@web":
              templateUrl: 'views/web/header.html'
              controller:  'HeaderController'
        })


      .state('web.trocaPremio', {
          url: '/trocaPremio'
          restrict: true
          abstract: true
          views:
            "":
              templateUrl: 'views/web/trocaPremio/trocaPremio.html'
            "header@web":
              templateUrl: 'views/web/header.html'
              controller:  'HeaderController'
        })

      .state('web.trocaPremio.criar', {
          url: '/trocaPremio/create'
          restrict: true
          views:
            "":
              templateUrl: 'views/web/trocaPremio/form.html'
              controller: 'CriarTrocaPremioController'
              resolve: 
                integrantesResolve: ['IntegranteService', (IntegranteService) ->
                  return IntegranteService.listTokens()
                ],
                premiosResolve: ['PremioService', (PremioService) ->
                  return PremioService.findAll()
                ]
            "header@web":
              templateUrl: 'views/web/header.html'
              controller:  'HeaderController'
        })

      .state('web.trocaPremio.listar', {
          url: '/list'
          restrict: true
          views: 
            "":
              templateUrl: 'views/web/trocaPremio/listar.html'
              controller: 'TrocaPremioController'
              resolve:
                trocaPremioResolve: ['TrocaPremioService', (TrocaPremioService) ->
                  return TrocaPremioService.findAll()
                ]
            "header@web":
              templateUrl: 'views/web/header.html'
              controller:  'HeaderController'
        })
    ])