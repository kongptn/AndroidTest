<?php 
	//Creating a connection
    include "Connect.php";
	/*Get the id of the last visible item in the RecyclerView from the request and store it in a variable. For            
	the first request id will be zero.*/	
  $id = isset($_POST['id']) ? $_POST['id'] : '';

    $sql = "UPDATE activity SET status_id = 'ปิดรับแล้ว' WHERE id = '$id'";
    
   // $id = isset($_POST['id']) ? $_POST['id'] : '';
  
   // $sql = "UPDATE football_activity SET number_join = 1 WHERE id = $id";

    if ($con->query($sql) === TRUE) {
        echo "true";
    } else {
        echo "false";
    }
$con->close();
?>
