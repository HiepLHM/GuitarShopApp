<?php

require 'connect.php';
$id_user = $_POST['id_user'];
$query = "SELECT * FROM cart WHERE FIND_IN_SET('$id_user', id_user)";

$data = mysqli_query($conn, $query);

$listCart = array();
class Cart{
	function Cart($id_cart, $id_product, $name_product, $price_product, $image_product, $id_user){
		$this -> IdCart = $id_cart;
		$this -> IdProduct = $id_product;
		$this -> NameProduct = $name_product;
		$this -> PriceProduct = $price_product;
		$this -> ImageProduct = $image_product;
		//$this -> QuantilyProduct = $quantily_product;
		$this -> IdUser = $id_user;
	}
}

while ($row = Mysqli_fetch_assoc($data)) {
	array_push($listCart, new Cart(	$row['id_cart'],
									$row['id_product'],
									$row['name_product'],
									$row['price_product'],
									$row['image_product'],
									//$row['quantily_product'],
									$row['id_user']));
}

echo json_encode($listCart);




 ?>