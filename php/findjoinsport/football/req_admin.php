<?php
if($_SERVER["REQUEST_METHOD"]=="POST"){
    include "Connect.php";

    $user_id = $_POST["user_id"];
    $info = $_POST["info"];
    $req_admin_name = $_POST["req_admin_name"];
   

    $query = "INSERT INTO request_admin (user_id, info, req_admin_name) VALUES ('$user_id', '$info', '$req_admin_name')";
    if (mysqli_query($con,$query)) {
    } else {
        die (mysqli_error($con));
    }
    // or die (mysqli_error($con));
    mysqli_close($con);
}
?>