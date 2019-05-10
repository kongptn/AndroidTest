<?php 

	/*
	* Created by Belal Khan
	* website: www.simplifiedcoding.net 
	* Retrieve Data From MySQL Database in Android
	*/
	
	//database constants
	define('DB_HOST', 'localhost');
	define('DB_USER', 'Findjoinsport');
	define('DB_PASS', 'Prekong@123');
	define('DB_NAME', 'Findjoinsport');
	
	//connecting to database and getting the connection object
	$conn = new mysqli(DB_HOST, DB_USER, DB_PASS, DB_NAME);
	
	//Checking if any error occured while connecting
	if (mysqli_connect_errno()) {
		echo "Failed to connect to MySQL: " . mysqli_connect_error();
		die();
	}else{
		mysqli_set_charset($conn, "utf8");
	}
	
	//creating a query
	$stmt = $conn->prepare("SELECT a.id , a.stadium_name , a.description , a.photo , a.date , a.time , a.location , a.number_join , u.user_id , u.name , u.photo_user , sp.type_id FROM activity a INNER JOIN users u on a.user_id = u.user_id INNER JOIN sport_type sp on a.type_id = sp.type_id WHERE sp.type_id=2 ORDER BY a.id DESC");
	
	//executing the query 
	$stmt->execute();
	
	//binding results to the query 
	$stmt->bind_result($id, $stadium_name, $description, $photo, $date, $time, $location, $number_join, $user_id, $name, $photo_user, $type_id);
	
	$products = array(); 
	
	//traversing through all the result 
	while($stmt->fetch()){
		$temp = array();
    $temp['id'] = $id;
    $temp['stadium_name'] = $stadium_name;
    $temp['description'] = $description;
    $temp['photo'] = $photo;
    $temp['date'] = $date;
	$temp['time'] = $time;
	$temp['location'] = $location;
	$temp['number_join'] = $number_join;
	$temp['user_id'] = $user_id;
	$temp['name'] = $name;
	$temp['photo_user'] = $photo_user;
	
		array_push($products, $temp);
	}
	
	//displaying the result in json format 
	echo json_encode($products);