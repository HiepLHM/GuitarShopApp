<?php

require 'connect.php';

$id_user = $_POST['id_user'];

$query = "SELECT * FROM detail_order where id_user ='$id_user'";
$data = mysqli_query($conn, $query);

$listOrder = array();

class ListOrder{
	function ListOrder($id_order, $name_product, $sumPrice, $quantily, $image_product, $customer_name, $phone_number, $address, $status){
		$this -> IdOrder = $id_order;
		$this -> NameProduct = $name_product;
		$this -> SumPrice = $sumPrice;
		$this -> Quantily = $quantily;
		$this -> ImageProduct = $image_product;
		$this -> CustomerName = $customer_name;
		$this -> PhoneNumber = $phone_number;
		$this -> Address = $address;
		$this -> Status = $status;
	}
}
while($row = mysqli_fetch_assoc($data)){
	if ($row['status']!=0) {
		$row['status'] = true;
	}
	array_push($listOrder, new ListOrder(	$row['id_order'],
											$row['name_product'],
											$row['sumPrice'],
											$row['quantily'],
											$row['image_product'],
											$row['customer_name'],
											$row['phone_number'],
											$row['address'],
											$row['status']));
}

echo json_encode($listOrder);


 ?>