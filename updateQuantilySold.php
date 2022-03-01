<?php

require 'connect.php';

$quantilySold = $_POST['quantily_sold'];
$id = $_POST['id_product'];

$query = "UPDATE detail_product SET quantily_sold = '$quantilySold' WHERE id_product = '$id' ";
$data = mysqli_query($conn, $query);

if($data){
	echo "success";
} else {
	echo "failed";
}






 ?>