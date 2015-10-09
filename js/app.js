// Ionic Starter App

// angular.module is a global place for creating, registering and retrieving Angular modules
// 'starter' is the name of this angular module example (also set in a <body> attribute in index.html)
// the 2nd parameter is an array of 'requires'
// 'starter.services' is found in services.js
// 'starter.controllers' is found in controllers.js
angular.module('health', ['ionic', 'health.controllers', 'health.services'])

.run(function($ionicPlatform) {
  $ionicPlatform.ready(function() {
    // Hide the accessory bar by default (remove this to show the accessory bar above the keyboard
    // for form inputs)
    if(window.cordova && window.cordova.plugins.Keyboard) {
      cordova.plugins.Keyboard.hideKeyboardAccessoryBar(true);
    }
    if(window.StatusBar) {
      // org.apache.cordova.statusbar required
      StatusBar.styleDefault();
    }
  });
})

.config(function($stateProvider, $urlRouterProvider) {

  // Ionic uses AngularUI Router which uses the concept of states
  // Learn more here: https://github.com/angular-ui/ui-router
  // Set up the various states which the app can be in.
  // Each state's controller can be found in controllers.js
  $stateProvider

    // setup an abstract state for the tabs directive
    .state('tab', {
      url: "/tab",
      abstract: true,
      templateUrl: "templates/tabs.html"
    })

    .state('login', {
      url: "/login",
      abstract: true,
      templateUrl: "templates/login-tabs.html"
    })

    // Each tab has its own nav history stack:

    .state('login.form', {
      url: '/form',
      views: {
        'login-tab': {
          templateUrl: 'templates/login.html',
          controller: 'LoginCtrl'
        }
      }
    })

    .state('login.medico', {
      url: '/form/medico',
      views: {
        'login-tab': {
          templateUrl: 'templates/medico.html',
          controller: 'CadastroCtrl'
        }
      }
    })

    .state('login.paciente', {
      url: '/form/paciente',
      views: {
        'login-tab': {
          templateUrl: 'templates/paciente.html',
          controller: 'CadastroCtrl'
        }
      }
    })

    .state('tab.conta', {
      url: '/conta',
      views: {
        'tab-conta': {
          templateUrl: 'templates/conta.html',
          controller: 'ContaCtrl'
        }
      }
    })

    .state('tab.pesquisa', {
      url: '/pesquisa',
      views: {
        'tab-pesquisa': {
          templateUrl: 'templates/pesquisa.html',
          controller: 'PesquisaCtrl'
        }
      }
    })

    .state('tab.pesquisa-medicoesp', {
      url: '/pesquisa/medico-esp',
      views: {
        'tab-pesquisa': {
          templateUrl: 'templates/pesquisa-med-esp.html',
          controller: 'PesqMedicoEspCtrl'
        }
      }
    })

    .state('tab.pesquisa-medicoesp-detalhes', {
      url: '/pesquisa/medico-esp/:esp',
      views: {
        'tab-pesquisa': {
          templateUrl: 'templates/pesquisa-med-esp-detalhes.html',
          controller: 'PesqMedicoEspDetCtrl'
        }
      }
    })

    .state('tab.pesquisa-medico-contato', {
      url: '/pesquisa/medico-contato/:medId',
      views: {
        'tab-pesquisa': {
          templateUrl: 'templates/pesquisa-med-contato.html',
          controller: 'PesqMedicoContatoCtrl'
        }
      }
    })

    .state('tab.pesquisa-medico-contato-mapa', {
      url: '/pesquisa/medico-contato/:medId/:mapaId',
      views: {
        'tab-pesquisa': {
          templateUrl: 'templates/pesquisa-med-contato-mapa.html',
          controller: 'PesqMedicoContatoMapaCtrl'
        }
      }
    })
	

//-------------------------------------------------------------

    .state('tab.pesquisa-prestadoresp', {
      url: '/pesquisa/prestador-esp',
      views: {
        'tab-pesquisa': {
          templateUrl: 'templates/pesquisa-pre-esp.html',
          controller: 'PesqPrestadorEspCtrl'
        }
      }
    })

    .state('tab.pesquisa-prestadoresp-detalhes', {
      url: '/pesquisa/prestador-esp/:esp',
      views: {
        'tab-pesquisa': {
          templateUrl: 'templates/pesquisa-pre-esp-detalhes.html',
          controller: 'PesqPrestadorEspDetCtrl'
        }
      }
    })

    .state('tab.pesquisa-prestador-contato', {
      url: '/pesquisa/prestador-contato/:prestadorId',
      views: {
        'tab-pesquisa': {
          templateUrl: 'templates/pesquisa-pre-contato.html',
          controller: 'PesqPrestadorContatoCtrl'
        }
      }
    })

//-------------------------------------------------------------

    .state('tab.pesquisa-farmacia', {
      url: '/pesquisa/farmacia',
      views: {
        'tab-pesquisa': {
          templateUrl: 'templates/pesquisa-far-esp-detalhes.html',
          controller: 'PesqFarmaciaCtrl'
        }
      }
    })

    .state('tab.pesquisa-farmacia-contato', {
      url: '/pesquisa/farmacia-contato/:farmaciaId',
      views: {
        'tab-pesquisa': {
          templateUrl: 'templates/pesquisa-far-contato.html',
          controller: 'PesqFarmaciaContatoCtrl'
        }
      }
    })

    .state('tab.socorros', {
      url: '/socorros',
      views: {
        'tab-socorros': {
          templateUrl: 'templates/socorros.html',
          controller: 'SocorrosCtrl'
        }
      }
    })

    .state('tab.socorros-detalhes', {
      url: '/socorros/:socorrosId',
      views: {
        'tab-socorros': {
          templateUrl: 'templates/socorros-detalhes.html',
          controller: 'SocorrosDetalhesCtrl'
        }
      }
    })

    .state('tab.emergencia', {
      url: '/emergencia',
      views: {
        'tab-emergencia': {
          templateUrl: 'templates/emergencia.html',
          controller: 'EmergenciaCtrl'
        }
      }
    })
	
	  .state('tab.favoritos', {
      url: '/favoritos',
      views: {
        'tab-favoritos': {
          templateUrl: 'templates/favoritos.html',
          controller: 'FavoritosCtrl'
        }
      }
    })



  // Caso nao encontre alguma pagina
  $urlRouterProvider.otherwise('/login/form');

});

