<?php 

require 'connect.php';

$username 		= $_POST['username'];
$password 		= md5($_POST['password']);
$email 			= $_POST['email'];
$avatar 		= $_POST['avatar'];
$phone_number 	= $_POST['phone_number'];

if(strlen($username)>0 && strlen($password)>0 && strlen($email)>0){
	$query = "SELECT * FROM user_profile WHERE FIND_IN_SET('$username', username)";
	$data = mysqli_query($conn, $query);

	$list_user = array();
	class User{
		function User($username, $password, $email, $avatar, $phone_number){
			$this -> Username 		= $username;
			$this -> Password 		= $password;
			$this -> Email 			= $email;
			$this -> Avatar 		= $avatar;
			$this -> Phone_number 	= $phone_number;
		}
	}
	while($row = mysqli_fetch_assoc($data)){
		array_push($list_user, new User($row['username'],
										$row['password'],
										$row['email'],
										$row['avatar'],
										$row['phone_number']));
	}
	if(count($list_user)>0){
		echo "failed";
		return;
	} else{
		$query_insert = "INSERT INTO user_profile VALUES(null,'$username','$password', '$email', '$avatar', '$phone_number')";
		$data_insert = mysqli_query($conn, $query_insert);
		if($data_insert){
			echo "success";
		}
	}
}






?>