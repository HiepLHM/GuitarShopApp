<?php

require 'connect.php';

$id_product = $_POST['id_product'];
$name_product =$_POST['name_product'];
$price_product = $_POST['price_product'];
$image_product = $_POST['image_product'];
$description_product = $_POST['description_product'];
$discount = $_POST['discount'];
//$created_at = date("Y-m-d H:i:s");
$id_category = $_POST['id_category'];


$query = "UPDATE detail_product SET name_product = '$name_product', price_product = '$price_product', image_product ='$image_product', description_product = '$description_product', discount = '$discount', id_category = '$id_category' WHERE id_product = '$id_product' ";
$data = mysqli_query($conn, $query);

if($data){
	echo "success";
} else {
	echo "failed";
}







 ?>