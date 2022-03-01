<?php

require 'connect.php';

$idproduct = "2";//$_POST['id_product'];

$query = "SELECT * FROM detail_product WHERE id_product= '$idproduct'";
$data = mysqli_query($conn, $query);

$listProduct = array();

class DetailProduct{
	function DetailProduct($id_product, $name_product, $price_product, $image_product, $description_product, $quantily_sold, $id_category){
		$this -> IdProduct 			= $id_product;
		$this -> NameProduct 		= $name_product;
		$this -> PriceProduct 		= $price_product;
		$this -> ImageProduct 		= $image_product;
		$this -> DescriptionProduct = $description_product;
		$this -> QuantilySold 		= $quantily_sold;
		$this -> IdCategory 		= $id_category;
	}
}

while($row = mysqli_fetch_assoc($data)){
	array_push($listProduct, new DetailProduct(			$row['id_product'],
														$row['name_product'],
														$row['price_product'],
														$row['image_product'],
														$row['description_product'],
														$row['quantily_sold'],
														$row['id_category']));
}

echo json_encode($listProduct);



 ?>