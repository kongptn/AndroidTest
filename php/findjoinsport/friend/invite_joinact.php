<?php
// Create connection
include_once 'connect.php';

$id = isset($_POST['id']) ? $_POST['id'] : '';
$userid_invite = isset($_POST['userid_invite']) ? $_POST['userid_invite'] : '';
$user_get = isset($_POST['user_get']) ? $_POST['user_get'] : '';
$user_create = isset($_POST['user_create']) ? $_POST['user_create'] : '';
     // $id = mysqli_real_escape_string($con, trim($_POST['id']));
     // $userid_invite = mysqli_real_escape_string($con, trim($_POST['userid_invite']));
    //  $user_id = mysqli_real_escape_string($con, trim($_POST['user_id']));

      
      $sql = "INSERT INTO invite_joinact (id, userid_invite , user_get , user_create ,status_id)
      VALUES ('$id', '$userid_invite', '$user_get', '$user_create', 'J01')";
      
      if ($con->query($sql) === TRUE) {
          echo "true";
      } else {
          echo "false";
      }
$con->close();
?>