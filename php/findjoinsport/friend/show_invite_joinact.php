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
	$user_id = isset($_POST['user_id']) ? $_POST['user_id'] : '';
	//creating a query
	
	//SELECT rq.req_id ,rq.userid_join, a.id , a.stadium_name , a.date , a.time , a.user_id , s.status_id , s.status_name , u.user_id , u.name , u.photo_user FROM football_activity as a INNER JOIN request_joinact rq ON a.id = rq.id INNER JOIN users u ON a.user_id = u.user_id INNER JOIN status s ON rq.status_id = s.status_id  WHERE a.user_id=33
	$stmt = $conn->prepare("SELECT i.invite_id, i.userid_invite, a.id, a.stadium_name, a.photo, a.date, a.time, a.number_join, u.user_id, u.name, u.photo_user, s.status_id, 
	s.status_name FROM invite_joinact as i INNER JOIN activity a ON i.id = a.id INNER JOIN users u ON i.user_create = u.user_id 
	INNER JOIN status s ON i.status_id = s.status_id WHERE i.user_get = '$user_id' ORDER BY i.invite_id DESC");
	
	//executing the query 
	$stmt->execute();
	
	//binding results to the query 
	$stmt->bind_result($invite_id,$userid_invite,$id,$stadium_name,$photo,$date,$time,$number_join,$user_id,$name,$photo_user,$status_id,$status_name);
	
	$products = array(); 
	
	//traversing through all the result 
	while($stmt->fetch()){
		$temp = array();
	$temp['invite_id'] = $invite_id;
	$temp['userid_invite'] = $userid_invite;
	$temp['id'] = $id;
    $temp['stadium_name'] = $stadium_name;
	$temp['photo'] = $photo;
	$temp['date'] = $date;
	$temp['time'] = $time;
	$temp['number_join'] = $number_join;
    $temp['user_id'] = $user_id;
    $temp['name'] = $name;
	$temp['photo_user'] = $photo_user;
	$temp['status_id'] = $status_id;
    $temp['status_name'] = $status_name;
	

		array_push($products, $temp);
	}
	
	//displaying the result in json format 
	echo json_encode($products);