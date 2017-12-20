angular.module('pad', ['ngMaterial'])
    .filter('startFrom', function () {
        return function (input, start) {
            start = +start;
            return input.slice(start);
        }
    })
    .controller('Main', function ($scope, $http, $mdDialog) {

        $scope.queryString = '';
        $scope.addString = '';
        $scope.currentPage1 = 0;
        $scope.totalPages = 0;

        $scope.title = '';
        var tempTitle;
        $scope.serialID = null;
        $scope.seasonID = null;

        var path = null;



        $scope.get = function () {
            if($scope.serialID == null && $scope.seasonID == null){
                path = 'http://165.227.232.6:1212/api/serials';
                resetVariables();
                $scope.currentPage1 = 0;
            } else if( $scope.seasonID == null) {
                path = 'http://165.227.232.6:1212/api/serials/' + $scope.serialID + '/seasons' ;
            } else {
                path = 'http://165.227.232.6:1212/api/serials/' + $scope.serialID + '/seasons/' + $scope.seasonID + '/episodes' ;
            }
            $http.get(path, {
                headers: {"API-KEY": "1", "Access-Control-Allow-Origin": "*",
                    "Content-Type": "application/json",
                    "Accept": "application/hal+json"},
                params: {
                    'page': Number($scope.currentPage1),
                    'search': 'name:' + $scope.queryString
                }
            }).then(function (response) {
                $scope.container = response.data;
                $scope.currentPage1 = Number($scope.container.page.number);
                $scope.totalPages = Number($scope.container.page.totalPages);
            });
        };
        $scope.get();

        $scope.getBack = function () {
            if($scope.serialID != null && $scope.seasonID != null){
                path = 'http://165.227.232.6:1212/api/serials/' + $scope.serialID + '/seasons';
                $scope.seasonID = null;
                $scope.title = tempTitle;
            } else {
                path = 'http://165.227.232.6:1212/api/serials/';
                $scope.serialID = null;
                $scope.seasonID = null;
                $scope.title = '';
            }

            $scope.queryString = '';
            $scope.addString = '';
            $scope.currentPage1 = 0;
            $scope.totalPages = 0;
            $scope.currentPage1 = 0;

            $http.get(path, {
                headers: {"API-KEY": "1", "Access-Control-Allow-Origin": "*",
                    "Content-Type": "application/json",
                    "Accept": "application/hal+json"},
                params: {
                    'page': Number($scope.currentPage1),
                    'search': 'name:' + $scope.queryString
                }
            }).then(function (response) {
                $scope.container = response.data;
                $scope.currentPage1 = Number($scope.container.page.number);
                $scope.totalPages = Number($scope.container.page.totalPages);
            });
        };

        $scope.getSearch = function () {

            if($scope.serialID == null && $scope.seasonID == null){
                path = 'http://165.227.232.6:1212/api/serials';
            } else if( $scope.seasonID == null) {
                path = 'http://165.227.232.6:1212/api/serials/' + $scope.serialID + '/seasons' ;
            } else {
                path = 'http://165.227.232.6:1212/api/serials/' + $scope.serialID + '/seasons/' + $scope.seasonID + '/episodes' ;
            }
            $http.get(path, {
                headers: {"API-KEY": "1", "Access-Control-Allow-Origin": "*"},
                params: {
                    'page': 0,
                    'search': 'name:' + encodeURIComponent($scope.queryString)
                }
            }).then(function (response) {
                $scope.container = response.data;
                $scope.totalPages = Number($scope.container.page.totalPages);
                $scope.currentPage1 = 0;
            });
        };

        $scope.getByLink = function (link) {
            $http.get(link, {
                headers: {"API-KEY": "1", "Access-Control-Allow-Origin": "*"}
            }).then(function (response) {
                if (response) {
                    $scope.seasons = response.data;
                }
            })
        };

        $scope.getByPage = function (page) {
            if($scope.serialID == null && $scope.seasonID == null){
                path = 'http://165.227.232.6:1212/api/serials';
            } else if( $scope.seasonID == null) {
                path = 'http://165.227.232.6:1212/api/serials/' + $scope.serialID + '/seasons' ;
            } else {
                path = 'http://165.227.232.6:1212/api/serials/' + $scope.serialID + '/seasons/' + $scope.seasonID + '/episodes' ;
            }
            $http.get(path, {
                headers: {"API-KEY": "1", "Access-Control-Allow-Origin": "*",
                    "Content-Type": "application/json",
                    "Accept": "application/hal+json"},
                params: {
                    'page': page
                }
            }).then(function (response) {
                $scope.container = response.data;
                $scope.currentPage1 = Number($scope.container.page.number);
                $scope.totalPages = Number($scope.container.page.totalPages);
            });
        };

        $scope.getBySeason = function (path, title) {
            path = path.replace("?search=", "");
            $http.get(path, {
                headers: {
                    "API-KEY": "1",
                    "Access-Control-Allow-Origin": "*",
                    "Content-Type": "application/json",
                    "Accept": "application/hal+json"
                }
            }).then(function (response) {
                $scope.container = response.data;
                resetVariables();
                $scope.title = title;
                $scope.totalPages = Number($scope.container.page.totalPages);
            })
        };


        $scope.currentPageDec = function () {
            if($scope.serialID == null && $scope.seasonID == null){
                path = 'http://165.227.232.6:1212/api/serials';
            } else if( $scope.seasonID == null) {
                path = 'http://165.227.232.6:1212/api/serials/' + $scope.serialID + '/seasons' ;
            } else {
                path = 'http://165.227.232.6:1212/api/serials/' + $scope.serialID + '/seasons/' + $scope.seasonID + '/episodes' ;
            }

            $scope.queryString = '';
            $scope.addString = '';
            $scope.totalPages = 0;

            $http.get(path, {
                headers: {"API-KEY": "1", "Access-Control-Allow-Origin": "*",
                    "Content-Type": "application/json",
                    "Accept": "application/hal+json"},
                params: {
                    'page': Number($scope.currentPage1) - 1
                }
            }).then(function (response) {
                $scope.container = response.data;
                $scope.currentPage1 = Number($scope.container.page.number);
                $scope.totalPages = Number($scope.container.page.totalPages);
            });
        };

        $scope.currentPageInc = function () {
            if($scope.serialID == null && $scope.seasonID == null){
                path = 'http://165.227.232.6:1212/api/serials';
            } else if( $scope.seasonID == null) {
                path = 'http://165.227.232.6:1212/api/serials/' + $scope.serialID + '/seasons' ;
            } else {
                path = 'http://165.227.232.6:1212/api/serials/' + $scope.serialID + '/seasons/' + $scope.seasonID + '/episodes' ;
            }

            $scope.queryString = '';
            $scope.addString = '';
            $scope.totalPages = 0;

            $http.get(path, {
                headers: {"API-KEY": "1", "Access-Control-Allow-Origin": "*",
                    "Content-Type": "application/json",
                    "Accept": "application/hal+json"},
                params: {
                    'page': Number($scope.currentPage1) + 1
                }
            }).then(function (response) {
                $scope.container = response.data;
                $scope.currentPage1 = Number($scope.container.page.number);
                $scope.totalPages = Number($scope.container.page.totalPages);
            });
        };


        $scope.addEntry = function () {

            if($scope.serialID == null && $scope.seasonID == null){
                path = 'http://165.227.232.6:1212/api/serials';
            } else if( $scope.seasonID == null) {
                path = 'http://165.227.232.6:1212/api/serials/' + $scope.serialID + '/seasons' ;
            } else {
                path = 'http://165.227.232.6:1212/api/serials/' + $scope.serialID + '/seasons/' + $scope.seasonID + '/episodes' ;
            }
            $http.put(path,'{"name": "' + $scope.addString + '"}', {
                headers: {"API-KEY": "1",
                    "Access-Control-Allow-Origin": "*",
                    "Content-Type": "application/json",
                    "Accept": "application/hal+json"
                }
            }).then(function () {
                $scope.addString = '';
                $scope.getByPage($scope.currentPage1);
            });

        };

        $scope.addEntrySeason = function(){
            if($scope.serialID == null && $scope.seasonID == null){
                path = 'http://165.227.232.6:1212/api/serials';
            } else if( $scope.seasonID == null) {
                path = 'http://165.227.232.6:1212/api/serials/' + $scope.serialID + '/seasons' ;
            } else {
                path = 'http://165.227.232.6:1212/api/serials/' + $scope.serialID + '/seasons/' + $scope.seasonID + '/episodes' ;
            }
        };

        $scope.deleteSerial = function (self) {
            var confirm = $mdDialog.confirm()
                .title('Are you sure?')
                .textContent('Are you really sure that you want to delete this entry?')
                .ok('Okay')
                .cancel('Cancel');

            $mdDialog.show(confirm).then(function() {
                $http.delete(self, {
                    headers: {
                        "API-KEY": "1",
                        "Accept": "*/*"
                    }
                }).then(function successCallBack(response) {
                    $scope.status = response.status;
                    if ($scope.status > 199 && $scope.status < 300) {
                        $scope.getByPage($scope.currentPage1);
                    }
                }, function failedCallBack($response) {
                    $mdDialog.show($mdDialog.alert()
                        .textContent($response.status + " " + $response.statusText)
                        .ok('Got it!')
                    )
                })
            });

        };

        $scope.changePage = function (path, title, seasonID, episodeID) {
            $scope.queryString = '';
            $scope.serialID = seasonID;
            $scope.seasonID = episodeID;
            $scope.get();
            if ($scope.title === ''){
                tempTitle = title;
            }
            $scope.title = title;
            $scope.totalPages = Number($scope.container.page.totalPages);

        };

        function resetVariables() {
            $scope.queryString = '';
            $scope.addString = '';
            $scope.currentPage1 = 0;
            $scope.totalPages = 0;
            $scope.title = '';
        }

        $scope.updateName = function (path, initialText) {

            var confirm = $mdDialog.prompt()
                .title('Modify entry')
                .textContent('Please input your modifications for this entry.')
                .placeholder('Description')
                .ariaLabel('Description')
                .initialValue(initialText)
                .ok('Okay')
                .cancel('Cancel');

            $mdDialog.show(confirm).then(function(modifiedText) {
                console.log(path);
                console.log(modifiedText);
                $http.patch(path, ' {"name":"' + modifiedText + '"}', {
                    headers: {
                        "API-KEY": "1",
                        "Access-Control-Allow-Origin": "*",
                        "Content-Type": "application/json",
                        "Accept": "application/hal+json"
                    }
                }).then(function ($response) {
                    $mdDialog.show($mdDialog.alert()
                        .textContent($response.status)
                        .ok('Got it!')
                    );
                    $scope.get();
                })
            });
        };

        $scope.updateDescription = function (path, initialText) {

            var confirm = $mdDialog.prompt()
                .title('Modify entry')
                .textContent('Please input your modifications for this entry.')
                .placeholder('Description')
                .ariaLabel('Description')
                .initialValue(initialText)
                .ok('Okay')
                .cancel('Cancel');

            $mdDialog.show(confirm).then(function(modifiedText) {
                console.log(path);
                console.log(modifiedText);
                $http.patch(path, ' {"description":"' + modifiedText + '"}', {
                    headers: {
                        "API-KEY": "1",
                        "Access-Control-Allow-Origin": "*",
                        "Content-Type": "application/json",
                        "Accept": "application/hal+json"
                    }
                }).then(function ($response) {
                    $mdDialog.show($mdDialog.alert()
                        .textContent($response.status + " " + $response.statusText)
                        .ok('Got it!')
                    );
                    $scope.getByPage(null);
                })
            });
        }

        $scope.view = false;

        $scope.toggle = function () {
            $scope.view = !$scope.view;
        }



});
