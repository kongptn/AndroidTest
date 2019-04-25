<?php 
	//Creating a connection
	include_once 'connect.php';
	/*Get the id of the last visible item in the RecyclerView from the request and store it in a variable. For            
	the first request id will be zero.*/	
 
  $rf_id = isset($_POST['rf_id']) ? $_POST['rf_id'] : '';
 

    $sql = "DELETE FROM request_friend WHERE rf_id = '$rf_id'";

    //$sql = "DELETE FROM request_joinact WHERE id = '$id'";
    
   // $id = isset($_POST['id']) ? $_POST['id'] : '';
  
   // $sql = "UPDATE football_activity SET number_join = 1 WHERE id = $id";

    if ($con->query($sql) === TRUE) {
        echo "true";
    } else {
        echo "false";
    }
$con->close();
?>
