<?php

require 'connect.php';

$query = "SELECT * FROM detail_product ORDER BY created_at DESC";
$data = mysqli_query($conn, $query);
$listProduct = array();

class ListCreatedAt{
	function ListCreatedAt($id_product, $name_product, $price_product, $image_product, $description_product, $discount, $created_at){
			$this -> IdProduct = $id_product;
			$this -> NameProduct = $name_product;
			$this -> PriceProduct = $price_product;
			$this -> ImageProduct = $image_product;
			$this -> DescriptionProduct = $description_product;
			$this -> Discount = $discount; 
			$this -> CreatedAt = $created_at;
	}
}

while($row=mysqli_fetch_assoc($data)){
	array_push($listProduct, new ListCreatedAt(	$row['id_product'],
												$row['name_product'],
												$row['price_product'],
												$row['image_product'],
												$row['description_product'],
												$row['discount'],
												$row['created_at']));
}

echo json_encode($listProduct);




 ?>