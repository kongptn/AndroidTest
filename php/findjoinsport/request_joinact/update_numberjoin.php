<?php 
	//Creating a connection
	include_once 'connect.php';
	/*Get the id of the last visible item in the RecyclerView from the request and store it in a variable. For            
	the first request id will be zero.*/	
  //$req_id = isset($_POST['req_id']) ? $_POST['req_id'] : '';
  //$status_id = isset($_POST['status_id']) ? $_POST['status_id'] : '';

   // $sql = "UPDATE request_joinact SET status_id = '$status_id' WHERE req_id = $req_id";
    
    $id = isset($_POST['id']) ? $_POST['id'] : '';
    $number_join = isset($_POST['number_join']) ? $_POST['number_join'] : '';
  
    $sql = "UPDATE activity SET number_join = $number_join WHERE id = $id";

    if ($con->query($sql) === TRUE) {
        echo "true";
    } else {
        echo "false";
    }
$con->close();
?>
