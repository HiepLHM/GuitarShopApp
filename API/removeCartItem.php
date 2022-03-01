<?php

require 'connect.php';

$id_cart = $_POST['id_cart'];

$query = "DELETE FROM cart WHERE id_cart ='$id_cart'";
$data = mysqli_query($conn, $query);
if($data){
	echo "success";
}




 ?>