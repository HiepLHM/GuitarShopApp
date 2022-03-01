<?php 

require 'connect.php';

$query = "SELECT * FROM detail_product WHERE quantily_sold > 0 ORDER BY quantily_sold DESC";
$data = mysqli_query($conn, $query);

$listProduct = array();

class ListBestSelling{
	function ListBestSelling($id_product, $name_product, $price_product, $image_product, $description_product, $discount, $quantily_sold){
		$this -> IdProduct = $id_product;
		$this -> NameProduct = $name_product;
		$this -> PriceProduct = $price_product;
		$this -> ImageProduct = $image_product;
		$this -> DescriptionProduct = $description_product;
		$this -> Discount = $discount;
		$this -> QuantilySold = $quantily_sold; 
	}
}

while($row = mysqli_fetch_assoc($data)){
	array_push($listProduct, new ListBestSelling(	$row['id_product'],
													$row['name_product'],
													$row['price_product'],
													$row['image_product'],
													$row['description_product'],
													$row['discount'],
													$row['quantily_sold']));
}

echo json_encode($listProduct);



?>