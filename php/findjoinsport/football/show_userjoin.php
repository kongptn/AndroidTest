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
	$id = isset($_POST['id']) ? $_POST['id'] : '';
	//creating a query
	//$stmt = $conn->prepare("SELECT rq.req_id , a.id , a.stadium_name , a.date , a.time , a.user_id , s.status_id , s.status_name , u.user_id , u.name , u.photo_user
	 //FROM request_joinact rq INNER JOIN football_activity a on rq.id = a.id INNER JOIN status s on rq.status_id = s.status_id 
	// INNER JOIN users u on rq.user_create = u.user_id and rq.user_create!='$user_id'");

	$stmt = $conn->prepare("SELECT  r.req_id , r.userid_join ,a.id , u.user_id , u.name , u.photo_user
	FROM request_joinact r INNER JOIN users u on r.userid_join = u.user_id INNER JOIN activity a on r.id = a.id WHERE r.id = '$id' AND r.status_id = 'J02'");
	
	//executing the query 
	$stmt->execute();
	
	//binding results to the query 
	$stmt->bind_result($req_id, $userid_join, $id, $user_id, $name, $photo_user);
	
	$products = array(); 
	
	//traversing through all the result 
	while($stmt->fetch()){
		$temp = array();
    $temp['req_id'] = $req_id;
    $temp['userid_join'] = $userid_join;
    $temp['id'] = $id;
    $temp['user_id'] = $user_id;
    $temp['name'] = $name;
	$temp['photo_user'] = $photo_user;

		array_push($products, $temp);
	}
	
	//displaying the result in json format 
	echo json_encode($products);