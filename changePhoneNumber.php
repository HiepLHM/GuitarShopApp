<?php 

require 'connect.php';

$phoneNumber =$_POST['phone_number'];
$idUser = $_POST['id_user'];

if(strlen($phoneNumber)>0){
	$query = "UPDATE user_profile SET phone_number = '$phoneNumber' WHERE id_user = '$idUser'";
	$data = mysqli_query($conn, $query);
	
	if($data){
		echo "success";
	} else{
		echo "failed";
	}
}


 ?>