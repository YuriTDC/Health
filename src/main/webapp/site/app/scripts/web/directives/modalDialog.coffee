
angular.module("modalDialogModule", [])

.directive 'modalDialog', [
  () ->
    {
      restrict: "AE"
 #     controller: "modalIntegranteController"
      scope: {
        show: '='
      }
      replace: true
      transclude: true

      link: (scope, element, attrs) ->

        scope.dialogStyle = {}

        if attrs.width
          scope.dialogStyle.width = attrs.width
        if attrs.height
          scope.dialogStyle.height = attrs.height

        scope.hideModal = ->
          scope.show = false
      

      template: "<div class='ng-modal' ng-show='show'>
                  <div class='ng-modal-overlay' ng-click='hideModal()'></div>
                  <div class='ng-modal-dialog' ng-style='dialogStyle'>
                    <div class='ng-modal-close' ng-click='hideModal()'>X</div>
                    <div class='ng-modal-dialog-content' ng-transclude></div>
                  </div>  
              </div>"
    }
]

# .controller 'modalIntegranteController',['$scope', 'IntegranteService',($scope, IntegranteService)->
#   $scope.modalShown = false

#   $scope.toggleModal = ()->    
#     # promise =  IntegranteService.recuperar(int.id)
#     # promise.success = (data) ->
#     #   int = data

#     # promise.fail = ->
#     #   alert 'asdasdasdsacsaaaaaaaaaacascsacasasdadasd'

#   $scope.modalShown = !$scope.modalShown

#   ]
  