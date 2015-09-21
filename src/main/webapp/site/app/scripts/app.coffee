'use strict'

###*
 # @ngdoc overview
 # @name emailOverviewWebApp
 # @description
 # # emailOverviewWebApp
 #
 # Main module of the application.
###
angular
  .module('emailOverviewWebApp', [
    'emailOverviewWebApp.controllers'
    'emailOverviewWebApp.services'
    'emailOverviewWebApp.directives'
    'emailOverviewWebApp.interceptors'
    'emailOverviewWebApp.filters'
    'emailOverviewWebApp.constructors'
    'emailOverviewWebApp.feedback'
    'ngCookies'
    'ngResource'
    'ngSanitize'
    'ngTouch'
    'ui.router'
    'ui.bootstrap'
    'sbarNavigation'
    'ngGrid'
    'AngularStomp'
#    'leaflet-directive'
    'ui.select'
    'ui.date'
    'angularUtils.directives.dirPagination'
    'oitozero.ngSweetAlert'
  ])
