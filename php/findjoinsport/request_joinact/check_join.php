<?php 
	define('DB_HOST', 'localhost');
	define('DB_USER', 'Findjoinsport');
	define('DB_PASS', 'Prekong@123');
	define('DB_NAME', 'Findjoinsport');
	
	//connecting to database and getting the connection object
	$con = new mysqli(DB_HOST, DB_USER, DB_PASS, DB_NAME);
	
	//Checking if any error occured while connecting
	if (mysqli_connect_errno()) {
		echo "Failed to connect to MySQL: " . mysqli_connect_error();
		die();
	}else{
		mysqli_set_charset($con, "utf8");
	}
	

    $id = isset($_POST['id']) ? $_POST['id'] : '';
    $user_id = isset($_POST['user_id']) ? $_POST['user_id'] : '';
//	$id = isset($_POST['id']);
	$sql ="SELECT rj.req_id ,a.id, u.user_id , s.status_id , s.status_name FROM request_joinact as rj 
	INNER JOIN users u ON rj.userid_join = u.user_id INNER JOIN status s ON rj.status_id= s.status_id INNER JOIN activity a ON rj.id = a.id WHERE rj.userid_join = '$user_id' AND a.id = '$id'";

//	$sql ="SELECT * FROM football_activity WHERE id = 300";
	$result = mysqli_query($con ,$sql);

while ($row = mysqli_fetch_assoc($result)) {
	
			$array[] = $row;
			echo json_encode($row,JSON_UNESCAPED_UNICODE);
	
}
//header('Content-Type:Application/json');


	mysqli_free_result($result);

	mysqli_close($con);
?>


