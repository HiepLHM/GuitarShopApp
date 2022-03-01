<?php 

class Product{
	function Product($id_product, $name_product, $price_product, $image_product, $description_product, $discount, $quantily_sold, $id_category){
		$this -> IdProduct 			= $id_product;
		$this -> NameProduct 		= $name_product;
		$this -> PriceProduct 		= $price_product;
		$this -> ImageProduct 		= $image_product;
		$this -> DescriptionProduct = $description_product;
		$this -> Discount  			= $discount;
		$this -> QuantilySold 		= $quantily_sold;
		$this -> IdCategory 		= $id_category;
	}
}




?>