<?php 
	//Creating a connection
	include_once 'connect.php';
	/*Get the id of the last visible item in the RecyclerView from the request and store it in a variable. For            
	the first request id will be zero.*/	
  $invite_id = isset($_POST['invite_id']) ? $_POST['invite_id'] : '';
  $status_id = isset($_POST['status_id']) ? $_POST['status_id'] : '';

    $sql = "UPDATE invite_joinact SET status_id = '$status_id' WHERE invite_id = $invite_id";
    
   // $id = isset($_POST['id']) ? $_POST['id'] : '';
  
   // $sql = "UPDATE football_activity SET number_join = 1 WHERE id = $id";

    if ($con->query($sql) === TRUE) {
        echo "true";
    } else {
        echo "false";
    }
$con->close();
?>
