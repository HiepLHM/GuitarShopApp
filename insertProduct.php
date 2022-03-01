<?php 

require 'connect.php';
date_default_timezone_set('Asia/Ho_Chi_Minh');


$name_product = $_POST['name_product'];
$price_product = $_POST['price_product'];
$image_product = $_POST['image_product'];
$description_product = $_POST['description_product'];
$discount = $_POST['discount'];
$created_at = date("Y-m-d H:i:s");
//$quantily_sold = "0";
$id_category =$_POST['id_category'];


$query = "INSERT INTO detail_product VALUES(null, '$name_product', '$price_product', '$image_product', '$description_product', '$discount', '$created_at', null, '$id_category')";
$data = mysqli_query($conn, $query);
if($data){
	echo "success";
} else {
	echo "failed";
}






?>