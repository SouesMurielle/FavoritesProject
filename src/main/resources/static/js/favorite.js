angular
    .module("favoriteApp", [])
    .directive('scrollOnClick', function() {
        return {
            restrict: 'A',
            link: function(scope, $elm, attrs) {
                var idToScroll = attrs.href;
                $elm.on('click', function() {
                    var $target;
                    if (idToScroll) {
                        $target = $(idToScroll);
                    } else {
                        $target = $elm;
                    }
                    $("body").animate({scrollTop: $target.offset().top});
                });
            }
        }
    })
    .controller("FavoritesController", function ($scope, $http) {

        $scope.categories = [];
        $scope.realCategories = [];
        $scope.favorites = [];

        $scope.categoryList = {
            filter : 0
        };

        $scope.mode = "favoritesView";

        $scope.favorite = {};

        $scope.category = {};

        $scope.favoritesToDelete = [];

        $scope.dateSortCriteria = {
            asc : "asc",
            desc : "desc"
        }

        $scope.categorySortCriteria = {
            asc : "asc",
            desc : "desc"
        }

        $scope.cancel = function () {
            $scope.setMode("favoritesView");
        };

        $scope.cancelCategory = function () {
            $scope.setMode("categoryView");
        };

        $scope.refresh = function () {
            $http.get("api/category").then(function (response) {
                $scope.categories = [{ id: 0, label: "All", references: 0 }];
                response.data.forEach((d) => {
                    $scope.categories.push(d);
                    $scope.categories[0].references += d.references;
                });

                $http.get("api/favorite").then(function (response) {
                    $scope.favorites = response.data.filter(
                        (f) =>
                            $scope.categoryList.filter === 0 ||
                            f.category.id === $scope.categoryList.filter
                    );
                });
            });
        };

        /* ----- FAVORITES ----- */

        $scope.sortByDate = function () {
            if (($scope.dateSortCriteria === null) || ($scope.dateSortCriteria === "desc")) {
                $scope.dateSortCriteria = "asc";
            } else $scope.dateSortCriteria = "desc"
            if (($scope.dateSortCriteria !== "asc")) {
                $http.get("api/dateOrder/ASC").then(function (response) {
                    $scope.favorites = response.data.filter(
                        (f) =>
                            $scope.categoryList.filter === 0 ||
                            f.category.id === $scope.categoryList.filter
                    );
                })
            } else {
                $http.get("api/dateOrder/DESC").then(function (response) {
                    $scope.favorites = response.data.filter(
                        (f) =>
                            $scope.categoryList.filter === 0 ||
                            f.category.id === $scope.categoryList.filter
                    );
                })
            }
        }

        $scope.sortByCategory = function () {
            if (($scope.categorySortCriteria === null) || ($scope.categorySortCriteria === "desc")) {
                $scope.categorySortCriteria = "asc";
            } else $scope.categorySortCriteria = "desc"
            if (($scope.categorySortCriteria !== "asc")) {
                $http.get("api/categoryOrder/ASC").then(function (response) {
                    $scope.favorites = response.data.filter(
                        (f) =>
                            $scope.categoryList.filter === 0 ||
                            f.category.id === $scope.categoryList.filter
                    );
                })
            } else {
                $http.get("api/categoryOrder/DESC").then(function (response) {
                    $scope.favorites = response.data.filter(
                        (f) =>
                            $scope.categoryList.filter === 0 ||
                            f.category.id === $scope.categoryList.filter
                    );
                })
            }
        }

        $scope.setMode = function (text) {
            if (text === "creationFavorite") {
                toggleActiveTab(1);
                $scope.realCategories = $scope.categories.filter(function (c) {
                    return c.id !== 0;
                });
                var idx = $scope.realCategories
                    .map(function (c) {
                        return c.id;
                    })
                    .indexOf($scope.categoryList.filter);
                if (idx < 0) idx = 0;

                $scope.favorite = {
                    link: "",
                    label: "",
                    category: $scope.realCategories[idx].id
                };
            } else if ((text === "favoritesView") || (text === "updateFavorite")) {
                toggleActiveTab(1);
            } else if ((text === "creationCategory") || (text === "categoryView") || (text === "updateCategory")) {
                toggleActiveTab(2);
            }

            $scope.mode = text;
        };

        $scope.isActive = function () {
            const favoritesTab = document.getElementById("favoritesTab");
            const favoritesA = favoritesTab.children[0];
            const categoriesTab = document.getElementById("categoriesTab");
            const categoriesA = categoriesTab.children[0];

            if (categoriesTab.classList.contains("is-active")) {
                categoriesA.style.fontWeight = "bold";
                categoriesA.style.color = "black";
                favoritesA.style.color = "white";
                favoritesA.style.fontWeight = "normal";
            } else {
                favoritesA.style.color = "black";
                favoritesA.style.fontWeight = "bold";
                categoriesA.style.color = "white";
                categoriesA.style.fontWeight = "normal";
            }
        }

        function toggleActiveTab(id) {
            const favoritesTab = document.getElementById("favoritesTab");
            const categoriesTab = document.getElementById("categoriesTab");
            if (id === 1) {
                favoritesTab.classList.add("is-active");
                categoriesTab.classList.remove("is-active");
                $scope.isActive();
            }
            if (id === 2) {
                categoriesTab.classList.add("is-active");
                favoritesTab.classList.remove("is-active");
                $scope.isActive();
            }
        }

        $scope.setUpdateFavorite = function (f) {
            $scope.realCategories = $scope.categories.filter(function(c) {
                return c.id !== 0
            });
            var idx = $scope.realCategories
                .map(function(c) {
                    return c.id
                })
                .indexOf(f.category.id);
            if (idx < 0) idx = 0;

            $scope.setMode('updateFavorite');

            $scope.favorite = {
                id : f.id,
                label : f.label,
                link : f.link,
                category: $scope.realCategories[idx].id
            };
        };

        $scope.createFavorite = function () {
            var url = $scope.favorite.link;
            // Vérifie si l'URL commence par "http://" ou "https://"
            if ( ! url.startsWith ("http://") && ! url.startsWith ("https://"))
                // Si ce n'est pas le cas, ajoute "https://"
                url = "https://" + url ;
            // Regex pour valider une URL
            var urlPattern = /^([a-zA-Z0-9]+[\.-:]+?[\/]*)([a-zA-Z0-9]+[\.\/\-\?=\+&%_]?)*([a-zA-Z0-9]+[\/\-\?=\+&%_]?)$/;
            // Test de l'URL / regex

            if (!urlPattern.test(url)){
                Swal.fire({icon: 'error',
                    title: 'Invalid URL',
                    text: 'Please enter a valid URL',
                })
            }
            else {
                $http
                    .post("api/" + $scope.favorite.category + "/favorite", {
                        id: null,
                        label: $scope.favorite.label,
                        link: $scope.favorite.link,
                    })
                    .then(
                        function () {
                            $scope.refresh();
                            $scope.setMode("favoritesView");
                            Swal.fire(
                                'success',
                                'Your favorite has been created.',
                                'success'
                            )
                        },
                        function (error) {
                            Swal.fire({
                                icon : 'error',
                                title : 'Not created!',
                                text : 'Your favorite hasn\'t been created.'
                            });
                        }
                    );
            }};

        $scope.updateFavorite = function() {
            $http
                .post("api/" + $scope.favorite.category + "/favorite", {
                    id: $scope.favorite.id,
                    label: $scope.favorite.label,
                    link: $scope.favorite.link
                })
                .then(
                    function () {
                        $scope.refresh();
                        $scope.setMode("favoritesView");
                    },
                    function (error) {
                        Swal.fire({
                            icon : 'error',
                            title : 'Not updated!',
                            text : 'Your favorite hasn\'t been updated.'
                        });
                    }
                );
        }

        $scope.deleteFavorite = function(id) {
            Swal.fire({
                title: 'Are you sure?',
                text: "You won't be able to revert this!",
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: 'Yes, delete it!'
            }).then((result) => {
                if (result.isConfirmed) {
                    $http.delete('api/favorite/' + id).then(
                        function() {
                            $scope.refresh();
                            Swal.fire(
                                'Deleted!',
                                'Your favorite has been deleted.',
                                'success'
                            )
                        }, function(error) {
                            alert(error.data.message);
                            Swal.fire({
                                icon : 'error',
                                title : 'Not deleted!',
                                text : 'Your favorite hasn\'t been deleted.'
                            });
                        }
                    )
                }
            })
        }

        function selectedFavorites() {
            return $scope.favorites.filter((f) => f.selected === true);
        }

        $scope.countChecked = function () {
            return selectedFavorites().length;
        }

        $scope.allChecked = function () {
            return selectedFavorites().length === $scope.favorites.length;
        }

        $scope.checkOrUncheckAll = function () {
            if (this.allChecked()) {
                for (let i = 0; i < $scope.favorites.length; i++) {
                    $scope.favorites[i].selected = false;
                }
            } else

                for (let i = 0; i < $scope.favorites.length; i++) {
                    $scope.favorites[i].selected = true;
                }
        }

        $scope.deleteMultiple = function() {
            $scope.favoritesToDelete = selectedFavorites().map((f) => f.id);
            if ($scope.favoritesToDelete.length == 0) {
                Swal.fire(
                    'No favorite selected!',
                    'Please select at least one favorite'
                )
            } else {
                Swal.fire({
                    title: 'Are you sure?',
                    text: "You won't be able to revert this!",
                    icon: 'warning',
                    showCancelButton: true,
                    confirmButtonColor: '#3085d6',
                    cancelButtonColor: '#d33',
                    confirmButtonText: 'Yes, delete it!'
                }).then((result) => {
                    if (result.isConfirmed) {
                        $http
                            .delete('api/favorite/' + $scope.favoritesToDelete.join('-'))
                            .then(function() {
                                    $scope.refresh();
                                    Swal.fire(
                                        'Deleted!',
                                        'Yours favorites has been deleted.',
                                        'success'
                                    )
                                }, function(error) {
                                    Swal.fire({
                                        icon : 'error',
                                        title : 'Not deleted!',
                                        text : 'Yours favorites hasn\'t been deleted.'
                                    });
                                }
                            );
                    }
                })
            }
        }

        /* ----- CATEGORIES ----- */

        $scope.setUpdateCategory = function (cat) {

            $scope.setMode('updateCategory');

            $scope.category = {
                id : cat.id,
                label : cat.label
            };
        }

        $scope.createCategory = function () {
            $http
                .post("api/category", {
                    id: null,
                    label: $scope.category.label
                })
                .then(
                    function () {
                        $scope.refresh();
                        $scope.setMode("favoritesView");
                    },
                    function (error) {
                        Swal.fire({
                            icon : 'error',
                            title : 'Not created!',
                            text : 'Your category hasn\'t been created.'
                        });
                    }
                );
        };

        $scope.updateCategory = function() {
            $http
                .post("api/category", {
                    id: $scope.category.id,
                    label: $scope.category.label
                })
                .then(
                    function () {
                        $scope.refresh();
                        $scope.setMode("favoritesView");
                    },
                    function (error) {
                        Swal.fire({
                            icon : 'error',
                            title : 'Not updated!',
                            text : 'Your category hasn\'t been updated.'
                        });
                    }
                );
        }

        $scope.deleteCategory = function(id) {
            Swal.fire({
                title: 'Are you sure?',
                text: "You won't be able to revert this!",
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: 'Yes, delete it!'
            }).then((result) => {
                if (result.isConfirmed) {
                    $http.delete('api/category/' + id).then(
                        function() {
                            $scope.refresh();
                            Swal.fire(
                                'Deleted!',
                                'Your category has been deleted.',
                                'success'
                            );
                        }, function(error) {
                            Swal.fire({
                                icon : 'error',
                                title : 'Not deleted!',
                                text : 'Your category hasn\'t been deleted.'
                            });
                        }
                    );
                }
            })
        }

        $scope.format = function(item) {
            return (item.label + (item.id !== -1 ? ' (' + item.references + ')' : ''));
        }

        $scope.refresh();
    });
