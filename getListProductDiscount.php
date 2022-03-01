<?php 

require 'connect.php';

$query = "SELECT * FROM detail_product WHERE discount>0 ORDER BY discount DESC";
$data = mysqli_query($conn, $query);

$listDiscount = array();

class ListDiscount{
	function ListDiscount($id_product, $name_product, $price_product, $image_product, $description_product, $discount){
			$this -> IdProduct = $id_product;
			$this -> NameProduct = $name_product;
			$this -> PriceProduct = $price_product;
			$this -> ImageProduct = $image_product;
			$this -> DescriptionProduct = $description_product;
			$this -> Discount = $discount; 
	}
}

while($row=mysqli_fetch_assoc($data)){
	array_push($listDiscount, new ListDiscount(	$row['id_product'],
												$row['name_product'],
												$row['price_product'],
												$row['image_product'],
												$row['description_product'],
												$row['discount']));
}

echo json_encode($listDiscount);


?>