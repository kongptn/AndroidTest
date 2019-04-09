<?php
if($_SERVER["REQUEST_METHOD"]=="POST"){
    include "connect.php";

    $userid_join = $_POST["userid_join"];
    $id = $_POST["id"];
    $status_id = $_POST["status_id"];
    $user_create = $_POST["user_create"];

   
    $query = "INSERT INTO request_joinact (userid_join, id, user_create, status_id) VALUES ('$userid_join', '$id', '$user_create', '$status_id')";
    if (mysqli_query($con,$query)) {
    } else {
        die (mysqli_error($con));
    }
    // or die (mysqli_error($con));
    mysqli_close($con);
}
?>