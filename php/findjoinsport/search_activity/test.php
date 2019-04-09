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
	
	$id = isset($_POST['id']) ? $_POST['id'] : '';
//	$id = isset($_POST['id']);
	$sql ="SELECT a.id , a.stadium_name , a.description , a.photo , a.date , a.time , a.location , a.number_join , u.user_id , u.name , u.photo_user 
	FROM football_activity a INNER JOIN users u on a.user_id = u.user_id WHERE id = '$id'";

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


