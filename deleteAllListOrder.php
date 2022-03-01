<?php 

require 'connect.php';

$query = "DELETE FROM detail_order";
$data = mysqli_query($conn, $query);

if($data){
	echo "success";
}



?>