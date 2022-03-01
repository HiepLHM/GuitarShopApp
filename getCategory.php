<?php 

require "connect.php";

$query = "SELECT * FROM category";
$data = mysqli_query($conn, $query);

$listCategory = array();

class Category{
	function Category($id_category, $name_category){
		$this -> IdCategory = $id_category;
		$this -> NameCategory = $name_category;
	}
}

while($row = mysqli_fetch_assoc($data)){
	array_push($listCategory, new Category($row['id_category'],
											$row['name_category']));
}

echo json_encode($listCategory);




?>