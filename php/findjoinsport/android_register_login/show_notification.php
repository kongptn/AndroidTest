<?php 

	/*
	* Created by Belal Khan
	* website: www.simplifiedcoding.net 
	* Retrieve Data From MySQL Database in Android
	*/
	
	//database constants
	define('DB_HOST', 'localhost');
	define('DB_USER', 'root');
	define('DB_PASS', '');
	define('DB_NAME', 'findjoinsport');
	
	//connecting to database and getting the connection object
	$conn = new mysqli(DB_HOST, DB_USER, DB_PASS, DB_NAME);
	
	//Checking if any error occured while connecting
	if (mysqli_connect_errno()) {
		echo "Failed to connect to MySQL: " . mysqli_connect_error();
		die();
	}else{
		mysqli_set_charset($conn, "utf8");
	}
	$user_id = isset($_POST['user_id']) ? $_POST['user_id'] : '';
	$status_noti = isset($_POST['status_noti']) ? $_POST['status_noti'] : '';
	//creating a query
	$stmt = $conn->prepare("SELECT n.noti_id, n.user_send, n.user_get, u.user_id, u.name, u.photo_user, s.status_id, s.status_name FROM notification as n INNER JOIN users u ON n.user_send = u.user_id INNER JOIN status_noti s ON n.status_id = s.status_id WHERE n.user_get = '$user_id' ORDER BY n.noti_id DESC");
	
	//executing the query 
	$stmt->execute();
	
	//binding results to the query 
	$stmt->bind_result($noti_id, $user_send, $user_get, $user_id, $name, $photo_user, $status_id, $status_name);
	
	$products = array(); 
	
	//traversing through all the result 
	while($stmt->fetch()){
		$temp = array();
    $temp['noti_id'] = $noti_id;
    $temp['user_send'] = $user_send;
    $temp['user_get'] = $user_get;
    $temp['user_id'] = $user_id;
	$temp['name'] = $name;
	$temp['photo_user'] = $photo_user;
	$temp['status_id'] = $status_id;
	$temp['status_name'] = $status_name;
	
		array_push($products, $temp);
	}
	
	//displaying the result in json format 
	echo json_encode($products);