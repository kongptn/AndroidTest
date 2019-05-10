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
	$userid_join = isset($_POST['userid_join']) ? $_POST['userid_join'] : '';
	//creating a query
	//SELECT rq.req_id ,rq.userid_join, a.id , a.stadium_name , a.date , a.time , a.user_id , s.status_id , s.status_name , u.user_id , u.name , u.photo_user FROM football_activity as a INNER JOIN request_joinact rq ON a.id = rq.id INNER JOIN users u ON a.user_id = u.user_id INNER JOIN status s ON rq.status_id = s.status_id  WHERE a.user_id=33
	$stmt = $conn->prepare("SELECT rq.req_id , a.id , a.stadium_name , a.date , a.time , a.user_id , a.number_join , s.status_id , s.status_name , u.user_id , u.name , u.photo_user 
	FROM request_joinact rq INNER JOIN activity a on rq.id = a.id INNER JOIN status s on rq.status_id = s.status_id INNER JOIN users u on rq.userid_join = u.user_id WHERE rq.user_create = '$userid_join' ORDER BY rq.req_id DESC");
	
	//executing the query 
	$stmt->execute();
	
	//binding results to the query 
	$stmt->bind_result($req_id, $id, $stadium_name, $date, $time, $user_id,$number_join, $status_id, $status_name, $userid_join, $name, $photo_user);
	
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
	$temp['number_join'] = $number_join;
	$temp['status_id'] = $status_id;
	$temp['status_name'] = $status_name;
	$temp['userid_join'] = $userid_join;
	$temp['name'] = $name;
	$temp['photo_user'] = $photo_user;
	

		array_push($products, $temp);
	}
	
	//displaying the result in json format 
	echo json_encode($products);