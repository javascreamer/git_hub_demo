<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<head>
<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
</head>
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
  <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.4.2/angular.min.js"></script>
<div class="container">
  <div>

    <hr></hr>
    <table class="table table-striped">
    <tr>
    <th>Id</th>
    <th>Product Name</th>
    <th>price</th>
    <th>desc</th>
     <th>category</th>
    <th>Product Image</th>
    </tr>
        <tr >
             <td>${item.itemId}</td>
            <td>${ item.itemName}</td>
            <td>${item.itemPrice}</td>
               <td>${item.itemDescription}</td>
               <td>${item.category}</td>
            <td><img src="resources/images/${item.itemId}.jpg" style="width: 200px;height:150px"></td>
           </div>
<body ng-app="addToCartApp">
    <div ng-controller="addToCartCtrl">
        <a href="javascript:void(0)" ng-click="addItemToCart(${item.itemId})">Add To kart </a>
        &nbsp;  &nbsp;  &nbsp; 
        <a href="<spring:url value="/user/cart" />"
										   class="btn btn-primary"><span class="glyphicon glyphicon-hand-right"></span>View Cart</a>
    </div>
    <script>
    var cartApp = angular.module ("addToCartApp", []);

    cartApp.controller("addToCartCtrl", function ($scope, $http){
    	
    	 /*
         * retrieveCart method is used to get all cart items at the starting of the application
         */
        $scope.retrieveCart = function (cartId) {
            $scope.cartId = cartId;
            $scope.refreshCartItems(cartId);
        };
        
        $scope.showAlert = function () {
            alert("This is an example of ng-click");
        }
        /*
         * removeItemFromCart method is used to remove a item from the cart
         */
        $scope.removeItemFromCart = function (itemId) {
            $http.put('http://localhost:8760/ekart/usercart/cart/removeItem/'+itemId).success(function (data) {
                $scope.refreshCartItems();
            });
        };

    	 /*
         * addItemToCart method is used to add items into the cart
         */
        $scope.addItemToCart = function (itemId) {
        	 alert("test");
            $http.put('http://localhost:8760/ekart/usercart/cart/addItem/'+itemId).success(function () {
                alert("Item added to the cart!")
            });
        };
       
       
        /*
         * GrandTotalOfItems method is called on cart page to calculate grand total for the added items
         */
        $scope.GrandTotalOfItems = function () {
            var grandTotal=0;

            for (var i=0; i<$scope.cart.cartItems.length; i++) {
                grandTotal+=$scope.cart.cartItems[i].totalPrice;
           }

            return grandTotal;
        };
        /*
    	 * refreshCartItems method is called by others methods in same controller to refresh the cart
    	 */
        $scope.refreshCartItems = function () {
            $http.get('http://localhost:8760/ekart/usercart/cart/refreshCart/'+$scope.cartId).success(function (data) {
               $scope.cart=data;
            });
        };
        
        /*
         * clearCartItems method is used to delete all items from the cart
         */
        $scope.clearCartItems = function () {
        	$http['delete']('http://localhost:8760/ekart/usercart/cart/clearCartItems/'+$scope.cartId).success(function (data) {$scope.refreshCartItems()});
        };
    });
</script>
</body>
</html>