<?php

require 'connect.php';

$query = "SELECT * FROM advertisement";
$data = mysqli_query($conn, $query);

$listAds = array();
class Advertisement{
	function Advertisement($id_ads, $image_ads, $content_ads, $id_category){
		$this -> IdAds = $id_ads;
		$this -> ImageAds = $image_ads;
		$this -> ContentAds = $content_ads;
		$this -> IdCategory = $id_category;
	}
}

while($row = mysqli_fetch_assoc($data)){
	array_push($listAds, new Advertisement(	$row['id_ads'],
											$row['image_ads'],
											$row['content_ads'],
											$row['id_category']));
}

echo json_encode($listAds);


 ?>