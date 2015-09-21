'use strict'

describe 'Controller: NavigationCtrl', ->

  # load the controller's module
  beforeEach module 'sescBertiogaWebApp'

  NavigationCtrl = {}
  scope = {}

  # Initialize the controller and a mock scope
  beforeEach inject ($controller, $rootScope) ->
    scope = $rootScope.$new()
    NavigationCtrl = $controller 'NavigationCtrl', {
      $scope: scope
    }

  it 'A Lista de Items deve ser igual a quatro', ->
    expect(scope.items.length).toBe 4
