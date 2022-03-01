<?php

require 'connect.php';

$id = $_POST['id_user'];
$avatar = $_POST['avatar'];

$query = "UPDATE user_profile SET avatar = '$avatar' WHERE id_user = '$id'";
$data = mysqli_query($conn, $query);

if($data){
	echo "success";
} else {
	echo "failed";
}



 ?>