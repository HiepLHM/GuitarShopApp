<?php 

require 'connect.php';

$query = "SELECT * FROM category";
$data = mysqli_query($conn, $query);

$list_category = array();

class Category{
	function Category($id_category, $name_category, $image_category){
		$this -> IdCategory = $id_category;
		$this -> NameCategory = $name_category;
		$this -> ImageCategory = $image_category;
	}
}

while($row = mysqli_fetch_assoc($data)){
	array_push($list_category, new Category($row['id_category'],
											$row['name_category'],
											$row['image_category']));
}

echo json_encode($list_category);

?>