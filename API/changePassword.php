<?php 

require 'connect.php';

$password = md5($_POST['password']);//$_POST['password'];
$id = $_POST['id_user'];

if(strlen($password)>0){
	$query = "UPDATE user_profile SET password = '$password' WHERE id_user= '$id'";
	$data = mysqli_query($conn, $query);

	if($data){
		echo "success";
	} else {
		echo "failed";
	}	
}


?>