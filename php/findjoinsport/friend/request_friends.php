<?php
// Create connection
include_once 'connect.php';

$userid_add = isset($_POST['userid_add']) ? $_POST['userid_add'] : '';
$user_id = isset($_POST['user_id']) ? $_POST['user_id'] : '';
$status_id = isset($_POST['status_id']) ? $_POST['status_id'] : '';

     // $id = mysqli_real_escape_string($con, trim($_POST['id']));
     // $userid_invite = mysqli_real_escape_string($con, trim($_POST['userid_invite']));
    //  $user_id = mysqli_real_escape_string($con, trim($_POST['user_id']));

      
      $sql = "INSERT INTO request_friend (user_id,userid_add, status_id) VALUES ('$user_id','$userid_add','$status_id')";
      
      if ($con->query($sql) === TRUE) {
          echo "true";
      } else {
          echo "false";
      }
$con->close();
?>