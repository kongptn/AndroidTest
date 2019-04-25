<?php
if($_SERVER["REQUEST_METHOD"]=="POST"){
    include "connect.php";

    $userid_add = isset($_POST['userid_add']) ? $_POST['userid_add'] : '';
    $user_id = isset($_POST['user_id']) ? $_POST['user_id'] : '';
    $status_id = isset($_POST['status_id']) ? $_POST['status_id'] : '';
   

    $query = "INSERT INTO request_friends (user_id,userid_add, status_id) VALUES ('$user_id','$userid_add',  '$status_id')";
    if (mysqli_query($conn,$query)) {
    }else{
        die (mysqli_error($conn));
    }  
    mysqli_close($conn);
}
?>