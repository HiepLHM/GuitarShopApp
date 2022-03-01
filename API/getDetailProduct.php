<?php 

require 'connect.php';
require 'product.php';

$query = "SELECT * FROM detail_product";
$data = mysqli_query($conn, $query);

$list_detail_product = array();

// class DetailProduct{
// 	function DetailProduct($id_product, $name_product, $price_product, $image_product, $description_product, $discount, $quantily_sold, $id_category){
// 		$this -> IdProduct 			= $id_product;
// 		$this -> NameProduct 		= $name_product;
// 		$this -> PriceProduct 		= $price_product;
// 		$this -> ImageProduct 		= $image_product;
// 		$this -> DescriptionProduct = $description_product;
// 		$this -> Discount  			= $discount;
// 		$this -> QuantilySold 		= $quantily_sold;
// 		$this -> IdCategory 		= $id_category;
// 	}
// }

class DetailProduct extends Product{
	function __contruct(){
		parent::__contruct();
	}
}

while($row = mysqli_fetch_assoc($data)){
	array_push($list_detail_product, new DetailProduct(	$row['id_product'],
														$row['name_product'],
														$row['price_product'],
														$row['image_product'],
														$row['description_product'],
														$row['discount'],
														$row['quantily_sold'],
														$row['id_category']));
}

echo json_encode($list_detail_product);

?>