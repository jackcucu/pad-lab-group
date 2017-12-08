angular.module('pad', [])
    .filter('startFrom', function () {
        return function (input, start) {
            start = +start;
            return input.slice(start);
        }
    })
    .controller('Main', function ($scope, $http) {

        $scope.queryString = '';
        $scope.addString = '';
        $scope.currentPage1 = 0;
        $scope.totalPages = 0;

        $scope.title = '';


        $scope.get = function (path) {
            if (path == null) {
                path = 'http://165.227.232.6:1212/api/serial'
            }
            $http.get(path, {
                headers: {"API-KEY": "1", "Access-Control-Allow-Origin": "*"},
                params: {
                    'page': Number($scope.currentPage1),
                    'search': 'name:' + $scope.queryString
                }
            }).then(function (response) {
                $scope.container = response.data;
                $scope.currentPage1 = Number($scope.container.page.number) + Number(1);
                $scope.totalPages = Number($scope.container.page.totalPages);
            });
        };
        $scope.get(null);

        $scope.getSearch = function () {
            $http.get('http://165.227.232.6:1212/api/serial', {
                headers: {"API-KEY": "1", "Access-Control-Allow-Origin": "*"},
                params: {
                    'page': Number($scope.currentPage1) - Number(1),
                    'search': 'name:' + encodeURIComponent($scope.queryString)
                }
            }).then(function (response) {
                $scope.container = response.data;
                $scope.totalPages = Number($scope.container.page.totalPages);
                $scope.currentPage1 = 1;
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

        $scope.getByPage = function () {
            $http.get("http://165.227.232.6:1212/api/serial", {
                headers: {
                    "API-KEY": "1",
                    "Access-Control-Allow-Origin": "*",
                    "Content-Type": "application/json",
                    "Accept": "application/hal+json"
                },
                params: {
                    'page': Number($scope.currentPage1) - Number(1)
                }
            }).then(function (response) {

                $scope.container = response.data;
            })
        };

        $scope.getBySeason = function (path) {
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
                $scope.queryString = '';
                $scope.addString = '';
                $scope.currentPage1 = 1;
                $scope.totalPages = 0;
            })
        };


        $scope.currentPageDec = function () {
            $http.get("http://165.227.232.6:1212/api/serial", {
                headers: {
                    "API-KEY": "1",
                    "Access-Control-Allow-Origin": "*",
                    "Content-Type": "application/json",
                    "Accept": "application/hal+json"
                },
                params: {
                    'page': Number($scope.currentPage1) - 2,
                    'search': 'name:' + $scope.queryString
                }
            }).then(function (response) {
                $scope.container = response.data;
                $scope.currentPage1--;
            })
        };

        $scope.currentPageInc = function () {
            console.log($scope.container);
            $http.get("http://165.227.232.6:1212/api/serial", {
                headers: {
                    "API-KEY": "1",
                    "Access-Control-Allow-Origin": "*",
                    "Content-Type": "application/json",
                    "Accept": "application/hal+json"
                },
                params: {
                    'page': Number($scope.currentPage1),
                    'search': 'name:' + $scope.queryString
                }
            }).then(function (response) {
                $scope.container = response.data;
                $scope.currentPage1++;
            })
        };

        $scope.addSerial = function () {
            $http.put("http://165.227.232.6:1212/api/serial", '{"name": "' + $scope.addString + '"}', {
                headers: {
                    "API-KEY": "1",
                    "Access-Control-Allow-Origin": "*"
                },
                "Content-Type": "application/json",
                "Accept": "application/hal+json"
            }).then(function () {
                $scope.addString = '';
                $scope.getByPage();
            })
        };

        $scope.deleteSerial = function (self) {
            $http.delete(self, {
                headers: {
                    "API-KEY": "1",
                    "Accept": "*/*"
                }
            }).then(function successCallBack(response) {
                $scope.status = response.status;
                if ($scope.status > 199 && $scope.status < 300) {
                    $scope.getByPage();
                }
            })
        };

        $scope.changePage = function (path) {
            console.log(path)
            $scope.getBySeason(path);
        };

    });