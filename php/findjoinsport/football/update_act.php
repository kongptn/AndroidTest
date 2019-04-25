<?php
if($_SERVER["REQUEST_METHOD"]=="POST"){
    include "Connect.php";
	/*Get the id of the last visible item in the RecyclerView from the request and store it in a variable. For            
	the first request id will be zero.*/	
  //$req_id = isset($_POST['req_id']) ? $_POST['req_id'] : '';
  //$status_id = isset($_POST['status_id']) ? $_POST['status_id'] : '';

   // $sql = "UPDATE request_joinact SET status_id = '$status_id' WHERE req_id = $req_id";
    
    $id = isset($_POST['id']) ? $_POST['id'] : '';
    $stadium_name = isset($_POST['stadium_name']) ? $_POST['stadium_name'] : '';
    $description = isset($_POST['description']) ? $_POST['description'] : '';
    $photo = isset($_POST['photo']) ? $_POST['photo'] : '';
    $date = isset($_POST['date']) ? $_POST['date'] : '';
    $time = isset($_POST['time']) ? $_POST['time'] : '';
    $location = isset($_POST['location']) ? $_POST['location'] : '';
    $Latitude = isset($_POST['Latitude']) ? $_POST['Latitude'] : '';
    $Longitude = isset($_POST['Longitude']) ? $_POST['Longitude'] : '';
  

    $image = $_POST['image'];
    $upload_path = "uploads/$photo.jpg";
    $sql = "UPDATE activity SET stadium_name = '$stadium_name', description = '$description' , photo = '$upload_path' , date = '$date', time = '$time',
     location = '$location', Latitude = '$Latitude', Longitude = '$Longitude' WHERE id = $id";
    if (mysqli_query($con,$sql)) {
        file_put_contents($upload_path,base64_decode($image));
    } else {
        die (mysqli_error($con));
    }
    // or die (mysqli_error($con));
    mysqli_close($con);
}
?>