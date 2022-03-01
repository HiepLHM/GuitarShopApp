<?php

require 'connect.php';

$id_product =$_POST['id_product'];

$query = "DELETE FROM detail_product WHERE id_product='$id_product'";
$data = mysqli_query($conn, $query);

if($data){
	echo "success";
} else {
	echo "failed";
}






 ?>