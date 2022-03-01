<?php

require 'connect.php';

$username = $_POST['username'];
$password = md5($_POST['password']);

$query = "SELECT * FROM user_profile WHERE FIND_IN_SET('$username', username) AND FIND_IN_SET('$password', password)";
$data = mysqli_query($conn, $query);

$list_user = array();
class User{
		function User($id_user, $username, $password, $email, $avatar, $phone_number, $role_account){
			$this -> IdUser 		= $id_user;
			$this -> Username 		= $username;
			$this -> Password 		= $password;
			$this -> Email 			= $email;
			$this -> Avatar 		= $avatar;
			$this -> Phone_number 	= $phone_number;
			$this -> RoleAccount 	= $role_account;
		}
	}
while($row = mysqli_fetch_assoc($data)){
	array_push($list_user, new User($row['id_user'],
									$row['username'],
									$row['password'],
									$row['email'],
									$row['avatar'],
									$row['phone_number'],
									$row['role_account']));
	}

echo json_encode($list_user);
?>