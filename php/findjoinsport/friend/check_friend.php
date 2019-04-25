<?php 
	define('DB_HOST', 'localhost');
	define('DB_USER', 'root');
	define('DB_PASS', '');
	define('DB_NAME', 'findjoinsport');
	
	//connecting to database and getting the connection object
	$con = new mysqli(DB_HOST, DB_USER, DB_PASS, DB_NAME);
	
	//Checking if any error occured while connecting
	if (mysqli_connect_errno()) {
		echo "Failed to connect to MySQL: " . mysqli_connect_error();
		die();
	}else{
		mysqli_set_charset($con, "utf8");
	}
	

    $user_id = isset($_POST['user_id']) ? $_POST['user_id'] : '';
    $user_get = isset($_POST['user_get']) ? $_POST['user_get'] : '';
//	$id = isset($_POST['id']);
	$sql ="SELECT rf.rf_id , u.user_id , s.status_id , s.status_name FROM request_friend as rf 
	INNER JOIN users u ON rf.user_id = u.user_id OR rf.userid_add = u.user_id INNER JOIN status s ON rf.status_id = s.status_id WHERE (rf.user_id = '$user_get' OR rf.userid_add = '$user_get') AND u.user_id = '$user_id'";

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


