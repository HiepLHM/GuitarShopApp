<?php 

require 'connect.php';

$password =md5($_POST['password']);
$id = $_POST['id_user'];

$query = "SELECT password from user_profile WHERE id_user='$id'";
$data = mysqli_query($conn, $query);

while($row = mysqli_fetch_assoc($data)){
	if(strlen($password) && $password == $row['password']){
		echo "correct";
	} else{
		echo "failed";
	}
}


?>