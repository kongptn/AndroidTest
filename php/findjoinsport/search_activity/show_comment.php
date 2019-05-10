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
	$id = isset($_POST['id']) ? $_POST['id'] : '';
	//creating a query
	//$stmt = $conn->prepare("SELECT rq.req_id , a.id , a.stadium_name , a.date , a.time , a.user_id , s.status_id , s.status_name , u.user_id , u.name , u.photo_user
	 //FROM request_joinact rq INNER JOIN football_activity a on rq.id = a.id INNER JOIN status s on rq.status_id = s.status_id 
	// INNER JOIN users u on rq.user_create = u.user_id and rq.user_create!='$user_id'");

	$stmt = $conn->prepare("SELECT c.cm_id,c.cm_data ,a.id , u.user_id , u.name , u.photo_user
	FROM comment_act as c INNER JOIN users u on c.user_id = u.user_id INNER JOIN activity a on c.id = a.id WHERE a.id = '$id' ORDER BY c.cm_id DESC");
	
	//executing the query 
	$stmt->execute();
	
	//binding results to the query 
	$stmt->bind_result($cm_id, $cm_data, $id, $user_id, $name, $photo_user);
	
	$products = array(); 
	
	//traversing through all the result 
	while($stmt->fetch()){
		$temp = array();
    $temp['cm_id'] = $cm_id;
    $temp['cm_data'] = $cm_data;
    $temp['id'] = $id;
    $temp['user_id'] = $user_id;
    $temp['name'] = $name;
	$temp['photo_user'] = $photo_user;

		array_push($products, $temp);
	}
	
	//displaying the result in json format 
	echo json_encode($products);