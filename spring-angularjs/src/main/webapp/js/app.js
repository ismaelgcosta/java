angular.module("sprang.services", ["ngResource","restangular"]).
    
	factory('Book', function ($resource) {
        var Book = $resource('api/books/:bookId', {bookId: '@id'}, {update: {method: 'PUT'}});
        
        Book.prototype.isNew = function(){
            return (typeof(this.id) === 'undefined');
        };
        
        return Book;
    })
    
    .factory('Contact', function ($resource) {
        var Contact = $resource('http://localhost\\:8080/examples-resteasy-springMVC/contacts/data:lastName', {lastName: '@lastName'}, { get: { method: "GET", isArray: true } });
        
        Contact.prototype.isNew = function(){
                return (typeof(this.lastName) === 'undefined');
            };
            return Contact;
     });

angular.module("sprang", ["sprang.services"]).
    config(function ($routeProvider) {
        $routeProvider
            .when('/books', {templateUrl: 'views/books/list.html', controller: BookListController})
            .when('/contacts', {templateUrl: 'views/contacts/list.html', controller: BookListController})
            .when('/books/:bookId', {templateUrl: 'views/books/detail.html', controller: BookDetailController});
    });

function BookListController($scope, Book, Contact) {
    $scope.doops = Book.query();
    $scope.contacts = Contact.get();
    
    $scope.deleteBook = function(book) {
        book.$delete(function() {
            $scope.books.splice($scope.books.indexOf(book),1);
            toastr.success("Deleted");
        });
    };
}

function BookDetailController($scope, $routeParams, $location, Book) {
    var bookId = $routeParams.bookId;
    if (bookId === 'new') {
        $scope.book = new Book();
    } else {
        $scope.book = Book.get({bookId: bookId});
    }
    $scope.save = function () {
        if ($scope.book.isNew()) {
            $scope.book.$save(function (book, headers) {
                toastr.success("Created");
                $location.path('/books/');
            });
        } else {
            $scope.book.$update(function() {
                toastr.success("Updated");
            });
        }
    };
}
