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
	//creating a query
	//$stmt = $conn->prepare("SELECT rq.req_id , a.id , a.stadium_name , a.date , a.time , a.user_id , s.status_id , s.status_name , u.user_id , u.name , u.photo_user
	 //FROM request_joinact rq INNER JOIN football_activity a on rq.id = a.id INNER JOIN status s on rq.status_id = s.status_id 
	// INNER JOIN users u on rq.user_create = u.user_id and rq.user_create!='$user_id'");

	$stmt = $conn->prepare("SELECT rq.req_id , a.id , a.stadium_name , a.date , a.time , a.user_id , s.status_id , s.status_name , u.user_id , u.name , u.photo_user 
	FROM request_joinact rq INNER JOIN football_activity a on rq.id = a.id INNER JOIN status s on rq.status_id = s.status_id INNER JOIN users u on rq.user_create = u.user_id WHERE rq.userid_join = '$user_id'");
	
	//executing the query 
	$stmt->execute();
	
	//binding results to the query 
	$stmt->bind_result($req_id, $id, $stadium_name, $date, $time, $user_id, $status_id, $status_name, $user_create, $name, $photo_user);
	
	$products = array(); 
	
	//traversing through all the result 
	while($stmt->fetch()){
		$temp = array();
    $temp['req_id'] = $req_id;
    $temp['id'] = $id;
    $temp['stadium_name'] = $stadium_name;
    $temp['date'] = $date;
    $temp['time'] = $time;
	$temp['user_id'] = $user_id;
	$temp['status_id'] = $status_id;
	$temp['status_name'] = $status_name;
	$temp['user_create'] = $user_create;
	$temp['name'] = $name;
	$temp['photo_user'] = $photo_user;

		array_push($products, $temp);
	}
	
	//displaying the result in json format 
	echo json_encode($products);