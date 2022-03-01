<?php

require 'connect.php';

$id_product =$_POST['id_product'];
$name_product = $_POST['name_product'];
$sumPrice =$_POST['sumPrice'];
$quantily =$_POST['quantily'];
$image_product =$_POST['image_product'];
$customer_name = $_POST['customer_name'];
$phone_number = $_POST['phone_number'];
$address = $_POST['address'];
$id_user = $_POST['id_user'];

$query = "INSERT INTO detail_order VALUES(null, '$id_product', '$name_product', '$sumPrice', '$quantily', '$image_product', '$customer_name', '$phone_number', '$address', '$id_user')";
$data = mysqli_query($conn, $query);

if($data){
	echo "success";
} else{
	echo "failed";
}

 ?>