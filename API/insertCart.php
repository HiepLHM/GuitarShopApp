<?php 

require 'connect.php';

$id_product = $_POST['id_product'];
$name_product = $_POST['name_product'];
$price_product = $_POST['price_product'];
$image_product = $_POST['image_product'];
//$quantily_product = $_POST['quantily_product'];
$id_user = $_POST['id_user'];


$query = "INSERT INTO cart VALUES(null, '$id_product', '$name_product', '$price_product', '$image_product', '$id_user')";
$data = mysqli_query($conn, $query);

if($data){
	echo "success";
} else {
	echo "failed";
}



?>