<?php 

require 'connect.php';
require 'classProduct.php';

$name = $_POST['name_product'];

$query = "SELECT * FROM detail_product WHERE name_product LIKE '%$name%' ";
$data = mysqli_query($conn, $query);

$list_product = array();

while($row = mysqli_fetch_assoc($data)){
	array_push($list_product, new DetailProduct($row['id_product'],
												$row['name_product'],
												$row['price_product'],
												$row['image_product'],
												$row['description_product'],
												$row['discount'],
												$row['created_at'],
												$row['quantily_sold'],
												$row['id_category']));
}

echo json_encode($list_product);





?>